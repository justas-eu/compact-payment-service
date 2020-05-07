package eu.justas.payments.usecases;

import eu.justas.payments.db.PaymentRepository;
import eu.justas.payments.domain.Payment;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static eu.justas.payments.usecases.fixtures.PaymentFixture.payment;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueryPaymentsTest {

    PaymentRepository repository;
    QueryPayments queryPayments;

    @Before
    public void setUp() {
        repository = new PaymentRepository();
        queryPayments = new QueryPayments(repository);
    }

    @Test
    public void can_find_payment() {

        Payment payment = payment();
        repository.add(payment);

        UUID id = UUID.fromString(payment.getId());
        Optional<Payment> paymentOptional = queryPayments.findById(id);

        assertTrue(paymentOptional.isPresent());

    }
}
