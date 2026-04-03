package com.jugger.springcommerce.modules.order.enums;

public enum OrderStatus {
    PENDING_PAYMENT,
    PAYMENT_SUBMITTED,
    PAYMENT_VERIFIED,
    PAYMENT_REJECTED,
    PROCESSING,
    COMPLETED,
    CANCELLED
}
