package com.order.infraestructure.persistence.entities;

import com.order.domain.models.OrderStatus;
import com.order.infraestructure.persistence.embeddables.BuyerDetailsEmbeddable;
import com.order.infraestructure.persistence.embeddables.PaymentDetailsEmbeddable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrderEntity {

    @Id
    private UUID orderId;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEntity> products = new ArrayList<>();

    @Embedded
    private PaymentDetailsEmbeddable paymentDetails;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private BuyerDetailsEmbeddable buyerDetails;

    public OrderEntity(){}

    public OrderEntity(
            final UUID orderId,
            final List<ProductEntity> products,
            final PaymentDetailsEmbeddable paymentDetails,
            final OrderStatus status,
            final  BuyerDetailsEmbeddable buyerDetails
    ) {
        this.orderId = orderId;
        this.products = products;
        this.paymentDetails = paymentDetails;
        this.status = status;
        this.buyerDetails = buyerDetails;
    }
}

