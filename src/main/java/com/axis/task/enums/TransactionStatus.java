package com.axis.task.enums;

public enum TransactionStatus {
    SUCCESS("SUCCESS"),
    FAILED("FAILED"),
    REVERSED("REVERSED");

    private final String status;

    TransactionStatus(String status) {
        this.status = status;
    }

}
