package eu.justas.payments.api.dto;

public class PaymentRequestFixture {

    private PaymentRequestFixture() {
    }

    public static CreatePaymentRequest paymentRequest() {
        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setAmount(11.11);
        request.setType("TYPE1");
        request.setCurrency("USD");
        request.setCreditorIban("CreditorIban");
        request.setDebtorIban("DebtorIban");
        request.setDetails("details");
        return request;
    }
}
