package br.com.washington.order_service;

import br.com.washington.order_service.domain.model.Order;
import br.com.washington.order_service.domain.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

@SpringBootTest // Sobe o Spring inteiro
@Testcontainers // Habilita o suporte ao Docker no teste
class OrderIntegrationTest {

    // Cria um container do Postgres real para o teste
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private OrderService orderService;

    @Test
    void shouldSaveAndRetrieveOrderFromRealDatabase() {
        // Arrange
        Order order = Order.builder()
                .customerId("cliente-integracao-1")
                .totalPrice(new BigDecimal("250.00"))
                .build();

        // Act
        Order savedOrder = orderService.createOrder(order);

        // Assert
        Assertions.assertNotNull(savedOrder.getId());
        Assertions.assertEquals("cliente-integracao-1", savedOrder.getCustomerId());

        // Verifica se o ID realmente foi gerado pelo banco/hibernate
        Assertions.assertNotNull(savedOrder.getCreatedAt());
    }
}