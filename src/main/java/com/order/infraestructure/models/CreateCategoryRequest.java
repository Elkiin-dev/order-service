package com.order.infraestructure.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateCategoryRequest {
    private final String name;
    private UUID parentId;
}
