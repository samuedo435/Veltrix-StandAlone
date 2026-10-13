package com.veltrix;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"DB_PASSWORD=1234", "JWT_SECRET=clave_ de_prueba_segura_para_tests_junit_2026_veltrix"})
class VeltrixApplicationTests {

    @Test
    void contextLoads() {
    }

}
