package com.github.tqspolloshermanos.entities;

public enum EOrderStatus {
    PENDING, // created, but not paid for yet
    PROCESSING, // paid for, in making by the restaurant
    DONE, // ready for collecting
    DELIVERED,
    CANCELLED
}
