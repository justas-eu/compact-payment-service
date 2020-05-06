package eu.justas.payments.api.req;

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
        return request;
    }
}
