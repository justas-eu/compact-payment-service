package eu.justas.payments.usecases;

import eu.justas.payments.db.PaymentRepository;
import eu.justas.payments.domain.Payment;
import eu.justas.payments.domain.PaymentType;
import eu.justas.payments.domain.ValidatablePayment;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.util.UUID;

@Component
public class CreatePayment {

    PaymentRepository repository;
    ValidatePayment validatePayment;

    public CreatePayment(PaymentRepository repository) {
        this.repository = repository;
        this.validatePayment =  new ValidatePayment();
    }

    public Payment create(String type, String currency, Double amount, String debtorIban, String creditorIban, String details) {

        Payment payment = populatePayment(type, currency, amount, debtorIban, creditorIban, details);

        repository.add(payment);

        ValidatablePayment validatablePayment = validatePayment.validate(payment);

        if (!validatablePayment.isValid()) {
            throw new InvalidParameterException(validatablePayment.getError());
        }

        return payment;
    }

    private Payment populatePayment(String type, String currency, Double amount, String debtorIban, String creditorIban, String details) {
        Payment payment = new Payment();

        PaymentType paymentType = PaymentType.valueOf(type);
        payment.setId(UUID.randomUUID().toString());
        payment.setType(paymentType);
        payment.setCurrency(currency);
        payment.setAmount(amount);
        payment.setDebtorIban(debtorIban);
        payment.setCreditorIban(creditorIban);
        payment.setDetails(details);

        return payment;
    }
}
