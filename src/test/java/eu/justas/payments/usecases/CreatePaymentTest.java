package eu.justas.payments.usecases;

import eu.justas.payments.domain.Payment;
import eu.justas.payments.domain.PaymentType;
import org.junit.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreatePaymentTest {

    @Test
    public void can_add_payment() {

        CreatePayment createPayment = new CreatePayment();
        String typeString = PaymentType.TYPE1.toString();
        String currency = "EUR";
        Double ammount = 0.11;
        String debtorIban = "debtorIban";
        String creditorIban = "creditorIban";
        Payment created = createPayment.create(typeString, currency, ammount, debtorIban, creditorIban);

        assertNotNull(UUID.fromString(created.getId()));
        assertEquals(typeString, created.getType().toString());
        assertEquals(currency, created.getCurrency());
        assertEquals(ammount, created.getAmount());
        assertEquals(debtorIban, created.getDebtorIban());
        assertEquals(creditorIban, created.getCreditorIban());

    }
}
