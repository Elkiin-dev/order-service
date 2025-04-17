package com.order.domain.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ProductId {
    private final UUID value;

    public ProductId() {this.value = UUID.randomUUID();}
}
