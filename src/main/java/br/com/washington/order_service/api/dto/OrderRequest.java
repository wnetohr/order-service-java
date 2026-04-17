package br.com.washington.order_service.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderRequest{
    @NotBlank(message = "O ID do cliente é obrigatório.")
    private String customerId;

    @NotNull(message = "O preço total é obrigatório.")
    @Positive(message = "O preço total deve ser maior que zero.")
    private BigDecimal totalPrice;
}