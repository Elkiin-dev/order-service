package com.order.domain.models;

import com.order.domain.exceptions.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@EqualsAndHashCode
public class Order {
    private final OrderId orderId;
    private final List<Product> products;
    private final PaymentDetails paymentDetails;
    private final OrderStatus status;
    private final BuyerDetails buyerDetails;

    public static final int PRODUCT_MIN_STOCK = 1;

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

    public Order finishOrder(PaymentDetails payment) {
        if (
                payment.getPaymentStatus() == null ||
                payment.getCardToken() == null ||
                payment.getCardToken().isBlank()
        ) {
            throw new InvalidPaymentDetailsException();
        }

        return switch (payment.getPaymentStatus()) {
            case PAID, OFFLINE_PAYMENT -> new Order(
                    this.orderId,
                    this.products,
                    payment,
                    OrderStatus.FINISHED,
                    this.buyerDetails
            );

            case PAYMENT_FAILED -> throw new PaymentFailedException();
        };

    }

    public Order updateBuyerAndProducts(BuyerEmail newEmail, List<Product> newProducts) {
        BigDecimal newTotal = newProducts.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        PaymentDetails updatedPayment = new PaymentDetails(
                newTotal,
                null, null, null, null
        );

        return new Order(
                this.orderId,
                newProducts,
                updatedPayment,
                this.status,
                new BuyerDetails(newEmail, this.buyerDetails.getSeat())
        );
    }

    public void orderIsOpen() {
        if (this.status != OrderStatus.OPEN) {
            throw new OrderUpdateNotAllowedException(this.status);
        }
    }



    public void checkProductStock(List<Product> products) {
        for (Product product : products) {
            if (product.getStock() < PRODUCT_MIN_STOCK) {
                throw new InsufficientProductException(product.getName());
            }
        }
    }

}
