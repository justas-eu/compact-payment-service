package eu.justas.payments.domain;

import java.time.LocalDateTime;

public class Payment {

    String id;
    PaymentType type;
    Double amount;
    String currency;
    String debtorIban;
    String creditorIban;
    String details;
    LocalDateTime createdAt = LocalDateTime.now();

    public Payment() {
    }

    public Payment(PaymentType type, Double amount, String currency, String debtorIban, String creditorIban, String details, LocalDateTime createdAt) {
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.debtorIban = debtorIban;
        this.creditorIban = creditorIban;
        this.details = details;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDebtorIban() {
        return debtorIban;
    }

    public void setDebtorIban(String debtorIban) {
        this.debtorIban = debtorIban;
    }

    public String getCreditorIban() {
        return creditorIban;
    }

    public void setCreditorIban(String creditorIban) {
        this.creditorIban = creditorIban;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Payment copy() {
        Payment copy = new Payment();

        copy.setId(this.getId());
        copy.setAmount(this.getAmount());
        copy.setType(this.getType());
        copy.setCurrency(this.getCurrency());
        copy.setDebtorIban(this.getDebtorIban());
        copy.setCreditorIban(this.getCreditorIban());
        copy.setDetails(this.getDetails());
        copy.setCreatedAt(this.getCreatedAt());

        return this;
    }
}
