package br.com.washington.order_service.domain.service;

import br.com.washington.order_service.domain.model.Order;
import br.com.washington.order_service.domain.model.OrderStatus;
import br.com.washington.order_service.infrastructure.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(Order order) {
        if (order.getTotalPrice() == null || order.getTotalPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("O preço total do pedido deve ser maior que zero.");
        }
        
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    // Buscar por ID
    public Order findById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com o ID: " + id));
    }

    // Deletar
    @Transactional
    public void delete(UUID id) {
        Order order = findById(id); // Garante que existe antes de deletar
        orderRepository.delete(order);
    }

    // Atualizar (Ex: Mudar status ou preço)
    @Transactional
    public Order update(UUID id, Order details) {
        Order order = findById(id);
        order.setCustomerId(details.getCustomerId());
        order.setTotalPrice(details.getTotalPrice());
        // Você pode permitir atualizar o status também se quiser
        return orderRepository.save(order);
    }

    public List<Order> listAll() {
        return orderRepository.findAll();
    }
}