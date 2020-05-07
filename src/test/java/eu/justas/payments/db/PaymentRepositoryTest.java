package eu.justas.payments.db;

import eu.justas.payments.domain.Payment;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static eu.justas.payments.usecases.fixtures.PaymentFixture.payment;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {

    PaymentRepository paymentRepository;

    @Before
    public void setUp() {
        paymentRepository = new PaymentRepository();
    }

    @Test
    public void can_add_and_find() {
        Payment payment = payment();
        paymentRepository.add(payment);

        assertEquals(1, paymentRepository.getCount());
        Optional<Payment> optionalPayment = paymentRepository.findById(UUID.fromString(payment.getId()));
        assertTrue(optionalPayment.isPresent());
    }

    @Test
    public void find_nothing_with_wrong_id() {
        Payment payment = payment();
        paymentRepository.add(payment);

        assertEquals(1, paymentRepository.getCount());
        Optional<Payment> optionalPayment = paymentRepository.findById(UUID.randomUUID());
        assertFalse(optionalPayment.isPresent());

    }

}
