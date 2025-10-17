package eu.justas.payments.db;

import eu.justas.payments.domain.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static eu.justas.payments.usecases.fixtures.PaymentFixture.payment;
import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
    }

    @Test
    void can_add_and_find() {
        Payment payment = payment();
        paymentRepository.add(payment);

        assertEquals(1, paymentRepository.getCount());
        Optional<Payment> optionalPayment = paymentRepository.findById(UUID.fromString(payment.getId()));
        assertTrue(optionalPayment.isPresent());
    }

    @Test
    void find_nothing_with_wrong_id() {
        Payment payment = payment();
        paymentRepository.add(payment);

        assertEquals(1, paymentRepository.getCount());
        Optional<Payment> optionalPayment = paymentRepository.findById(UUID.randomUUID());
        assertFalse(optionalPayment.isPresent());

    }

}
