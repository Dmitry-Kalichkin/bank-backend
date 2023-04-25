package com.bank.loans.data.enums;

public enum KafkaTopic {
    LOANS_PAYMENTS_OPERATIONS_TOPIC_NAME("loans-payments-operations"),
    PAYMENT_RESPONSES_TOPIC_NAME("loans-payments-responses"),
    OPERATIONS_TOPIC_NAME("operations");

    private String value;
    KafkaTopic(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
