package ru.bank.accounts.data.enums;

public enum KafkaTopic {
    OPERATIONS_TOPIC_NAME("operations"),
    LOANS_PAYMENTS_OPERATIONS_TOPIC_NAME("loans-payments-operations"),
    PAYMENT_RESPONSES_TOPIC_NAME("loans-payments-responses");

    private String value;
    KafkaTopic(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
