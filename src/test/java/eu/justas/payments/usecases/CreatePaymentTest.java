package eu.justas.payments.usecases;

import eu.justas.payments.db.PaymentRepository;
import eu.justas.payments.domain.Payment;
import eu.justas.payments.domain.PaymentType;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreatePaymentTest {

    PaymentRepository repository;
    CreatePayment createPayment;

    @Before
    public void setUp() {
        repository = new PaymentRepository();
        createPayment = new CreatePayment(repository);
    }

    @Test
    public void can_add_payment() {

        String typeString = PaymentType.TYPE1.toString();
        String currency = "EUR";
        Double amount = 0.11;
        String debtorIban = "debtorIban";
        String creditorIban = "creditorIban";
        Payment created = createPayment.create(typeString, currency, amount, debtorIban, creditorIban);

        assertNotNull(UUID.fromString(created.getId()));
        assertEquals(typeString, created.getType().toString());
        assertEquals(currency, created.getCurrency());
        assertEquals(amount, created.getAmount());
        assertEquals(debtorIban, created.getDebtorIban());
        assertEquals(creditorIban, created.getCreditorIban());
        assertEquals(1, repository.getCount());
    }
}
