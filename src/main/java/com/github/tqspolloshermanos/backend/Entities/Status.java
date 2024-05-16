package com.github.tqspolloshermanos.backend.Entities;

import jakarta.persistence.*;

public enum Status {
    PENDING,
    PROCESSING,
    DONE,
    DELIVERED,
    CANCELLED
}
