package eu.justas.payments.domain;

public class ValidatablePayment {

    private boolean valid = false;
    private String error = "";
    private Payment payment;

    public ValidatablePayment(Payment payment) {
        this.payment = payment;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public boolean isAmountPositive() {
        return payment.getAmount() > 0;
    }
}
