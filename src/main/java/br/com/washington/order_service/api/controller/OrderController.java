package br.com.washington.order_service.api.controller;

import br.com.washington.order_service.api.dto.OrderRequest;
import br.com.washington.order_service.domain.model.Order;
import br.com.washington.order_service.domain.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestBody @Valid OrderRequest request) {
        // Converte o DTO em Entidade
        Order newOrder = Order.builder()
                .customerId(request.getCustomerId())
                .totalPrice(request.getTotalPrice())
                .build();

        return orderService.createOrder(newOrder);
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable UUID id) {
        return orderService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        orderService.delete(id);
    }

    @PutMapping("/{id}")
    public Order update(@PathVariable UUID id, @RequestBody @Valid OrderRequest request) {
        Order details = Order.builder()
                .customerId(request.getCustomerId())
                .totalPrice(request.getTotalPrice())
                .build();
        return orderService.update(id, details);
    }

    @GetMapping
    public List<Order> list() {
        return orderService.listAll();
    }
}