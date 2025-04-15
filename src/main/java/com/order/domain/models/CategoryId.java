package com.order.domain.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class CategoryId {
    private final UUID value;

    public CategoryId() {
        this.value = UUID.randomUUID();
    }
}
