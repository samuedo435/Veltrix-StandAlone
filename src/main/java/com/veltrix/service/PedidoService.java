package com.veltrix.service;

import com.veltrix.dto.checkout.CheckoutRequest;
import com.veltrix.dto.checkout.CheckoutResponse;
import com.veltrix.dto.checkout.ItemCheckoutDTO;
import com.veltrix.enums.EstadoPago;
import com.veltrix.enums.EstadoPedido;
import com.veltrix.enums.MetodoPago;
import com.veltrix.exception.ResourceNotFoundException;
import com.veltrix.model.*;
import com.veltrix.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final PagoRepository pagoRepository;
    private final ClienteRepository clienteRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            ProductoRepository productoRepository,
            DetallePedidoRepository detallePedidoRepository,
            PagoRepository pagoRepository,
            ClienteRepository clienteRepository) {

        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
        this.pagoRepository = pagoRepository;
        this.clienteRepository = clienteRepository;
    }

    public Pedido guardar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pedido no encontrado con id: " + id));
    }

    public Pedido actualizar(Long id, Pedido pedidoActualizado) {

        Pedido pedido = obtenerPorId(id);

        pedido.setFechaPedido(pedidoActualizado.getFechaPedido());
        pedido.setMontoTotal(pedidoActualizado.getMontoTotal());
        pedido.setEstado(pedidoActualizado.getEstado());
        pedido.setCliente(pedidoActualizado.getCliente());

        return pedidoRepository.save(pedido);
    }

    public void eliminar(Long id) {

        Pedido pedido = obtenerPorId(id);

        pedidoRepository.delete(pedido);
    }

    @Transactional
    public CheckoutResponse realizarCheckout(CheckoutRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correo = authentication.getName();

        Cliente cliente = clienteRepository.findByUsuarioCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        // Si el usuario especificó una nueva dirección en el checkout, se actualiza el registro del cliente
        if (request.getDireccionEnvio() != null && !request.getDireccionEnvio().isBlank()) {
            cliente.setDireccion(request.getDireccionEnvio());
            clienteRepository.save(cliente);
        }

        // 1. Preparar las relaciones del detalle y validar stock/monto antes de persistir el Pedido
        double total = 0;
        List<DetallePedido> detalles = new ArrayList<>();

        for (ItemCheckoutDTO item : request.getProductos()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

            if (producto.getStock() < item.getCantidad()) {
                throw new IllegalArgumentException("No hay stock suficiente de " + producto.getNombre());
            }

            double subtotal = producto.getPrecio() * item.getCantidad();
            total += subtotal;

            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setSubtotal(subtotal);

            detalles.add(detalle);

            // Actualizar stock del producto
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);
        }

        // 2. Crear y guardar el Pedido con su monto correcto desde el inicio
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setMontoTotal(total); // <-- Ahora total es > 0

        pedido = pedidoRepository.save(pedido);

        // 3. Vincular y guardar los detalles del pedido
        for (DetallePedido detalle : detalles) {
            detalle.setPedido(pedido);
            detallePedidoRepository.save(detalle);
        }

        // 4. Registrar el pago
        Pago pago = new Pago();
        pago.setPedido(pedido);
        pago.setFechaPago(LocalDateTime.now());
        pago.setMonto(total);
        pago.setMetodoPago(MetodoPago.valueOf(request.getMetodoPago()));
        pago.setEstadoPago(EstadoPago.PENDIENTE);

        pagoRepository.save(pago);

        return new CheckoutResponse(pedido.getId(), "Pedido creado correctamente");
    }
}