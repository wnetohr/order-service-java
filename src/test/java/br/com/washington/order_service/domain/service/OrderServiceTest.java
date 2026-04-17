package br.com.washington.order_service.domain.service;

import br.com.washington.order_service.domain.model.Order;
import br.com.washington.order_service.infrastructure.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("Deve salvar um pedido com sucesso quando os dados forem válidos")
    void shouldCreateOrderWithSuccess() {
        // Arrange (Configuração)
        Order order = Order.builder()
                .customerId("cliente-1")
                .totalPrice(new BigDecimal("100.00"))
                .build();

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act (Execução)
        Order savedOrder = orderService.createOrder(order);

        // Assert (Verificação)
        assertNotNull(savedOrder);
        assertEquals("cliente-1", savedOrder.getCustomerId());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar pedido com preço zero ou negativo")
    void shouldThrowExceptionWhenPriceIsInvalid() {
        // Arrange
        Order order = Order.builder()
                .customerId("cliente-1")
                .totalPrice(BigDecimal.ZERO)
                .build();

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(order);
        });

        assertEquals("O preço total do pedido deve ser maior que zero.", exception.getMessage());
        verify(orderRepository, never()).save(any(Order.class));
    }
}