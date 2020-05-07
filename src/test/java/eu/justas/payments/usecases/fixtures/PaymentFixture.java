package eu.justas.payments.usecases.fixtures;

import eu.justas.payments.domain.Payment;

import java.util.UUID;

import static eu.justas.payments.domain.PaymentType.TYPE1;

public class PaymentFixture {

    private PaymentFixture() {
    }

    public static Payment payment() {
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID().toString());
        payment.setAmount(11.11);
        payment.setType(TYPE1);
        payment.setCurrency("USD");
        payment.setCreditorIban("CreditorIban");
        payment.setDebtorIban("DebtorIban");
        return payment;
    }
    
}
