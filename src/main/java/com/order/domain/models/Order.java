package com.order.domain.models;

import com.order.domain.exceptions.InvalidBuyerDetailException;
import com.order.domain.exceptions.InvalidCancelOrderException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@EqualsAndHashCode
public class Order {
    private final OrderId orderId;
    private final List<Product> products;
    private final PaymentDetails paymentDetails;
    private final OrderStatus status;
    private final BuyerDetails buyerDetails;

    public Order(final OrderId orderId,
                 final List<Product> products,
                 final PaymentDetails paymentDetails,
                 final OrderStatus status,
                 final BuyerDetails buyerDetails
    ) {
        this.ensureSeatPositionExist(buyerDetails);
        this.orderId = orderId;
        this.products = products;
        this.paymentDetails = paymentDetails;
        this.status = status;
        this.buyerDetails = buyerDetails;
    }

    private void ensureSeatPositionExist(BuyerDetails buyerDetails){
        if(
            buyerDetails == null ||
            buyerDetails.getSeat().getLetter() == null ||
            buyerDetails.getSeat().getLetter().isBlank() ||
            buyerDetails.getSeat().getNumber() == null ||
            buyerDetails.getSeat().getNumber() <= 0
        ){
            throw new InvalidBuyerDetailException();
        }
    }

    public Order cancelOrder(){
        if(this.status == OrderStatus.FINISHED){
            throw new InvalidCancelOrderException(this.status);
        }
        return new Order(
                this.orderId,
                this.products,
                this.paymentDetails,
                OrderStatus.DROPPED,
                this.buyerDetails
        );
    }

    public Order finishOrder(PaymentStatus status, String cardToken, String gateway) {
        if (status == null || cardToken == null || cardToken.isBlank() || gateway == null) {
            throw new IllegalArgumentException("Payment information is required to finish the order.");
        }

        PaymentDetails payment = new PaymentDetails(
                this.paymentDetails.getTotalPrice(),
                cardToken,
                status,
                LocalDateTime.now(),
                gateway
        );

        return new Order(
                this.orderId,
                this.products,
                payment,
                OrderStatus.FINISHED,
                this.buyerDetails
        );
    }

    public Order updateBuyerAndProducts(BuyerEmail newEmail, List<Product> newProducts) {
        BigDecimal newTotal = newProducts.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        PaymentDetails updatedPayment = new PaymentDetails(
                newTotal,
                null, null, null, null // Aún no hay pago
        );

        return new Order(
                this.orderId,
                newProducts,
                updatedPayment,
                this.status,
                new BuyerDetails(newEmail, this.buyerDetails.getSeat())
        );
    }

}
