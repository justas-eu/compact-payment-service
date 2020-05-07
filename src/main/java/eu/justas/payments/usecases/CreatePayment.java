package eu.justas.payments.usecases;

import eu.justas.payments.db.PaymentRepository;
import eu.justas.payments.domain.Payment;
import eu.justas.payments.domain.PaymentType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreatePayment {

    PaymentRepository repository;

    public CreatePayment(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment create(String type, String currency, Double amount, String debtorIban, String creditorIban) {

        Payment payment = populatePayment(type, currency, amount, debtorIban, creditorIban);

        repository.add(payment);

        return payment;

    }

    private Payment populatePayment(String type, String currency, Double amount, String debtorIban, String creditorIban) {
        Payment payment = new Payment();

        PaymentType paymentType = PaymentType.valueOf(type);
        payment.setId(UUID.randomUUID().toString());
        payment.setType(paymentType);
        payment.setCurrency(currency);
        payment.setAmount(amount);
        payment.setDebtorIban(debtorIban);
        payment.setCreditorIban(creditorIban);

        return payment;
    }
}
