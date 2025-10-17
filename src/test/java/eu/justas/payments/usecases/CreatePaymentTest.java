package eu.justas.payments.usecases;

import eu.justas.payments.db.PaymentRepository;
import eu.justas.payments.domain.Payment;
import eu.justas.payments.domain.PaymentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreatePaymentTest {

    PaymentRepository repository;
    CreatePayment createPayment;

    @BeforeEach
    void setUp() {
        repository = new PaymentRepository();
        createPayment = new CreatePayment(repository);
    }

    @Test
    void can_add_payment() {

        String typeString = PaymentType.TYPE1.toString();
        String currency = "EUR";
        Double amount = 0.11;
        String debtorIban = "debtorIban";
        String creditorIban = "creditorIban";
        String details = "details";

        Payment created = createPayment.create(typeString, currency, amount, debtorIban, creditorIban, details);

        assertNotNull(UUID.fromString(created.getId()));
        assertEquals(typeString, created.getType().toString());
        assertEquals(currency, created.getCurrency());
        assertEquals(amount, created.getAmount());
        assertEquals(debtorIban, created.getDebtorIban());
        assertEquals(creditorIban, created.getCreditorIban());
        assertEquals(details, created.getDetails());
        assertEquals(1, repository.getCount());
    }
}
