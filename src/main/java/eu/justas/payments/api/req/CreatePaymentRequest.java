package eu.justas.payments.api.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreatePaymentRequest {

    @NotBlank(message = "Type must not be blank!")
    String type;

    @NotNull(message = "Amount must not be blank!")
    Double amount;

    @NotBlank(message = "Currency must not be blank!")
    String currency;

    @NotBlank(message = "Debtor IBAN must not be blank!")
    String debtorIban;

    @NotBlank(message = "Creditor IBAN must not be blank!")
    String creditorIban;

    public String getType() {
        return type;
    }

    public void setType(String type) {
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


}
