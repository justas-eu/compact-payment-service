package eu.justas.payments.usecases;

import eu.justas.payments.domain.Payment;
import eu.justas.payments.domain.PaymentType;
import org.junit.Before;
import org.junit.Test;

import static eu.justas.payments.usecases.fixtures.PaymentFixture.payment;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateCancellationFeeTest {

    CalculateCancellationFee calculateCancellationFee;

    @Before
    public void setUp() {
        calculateCancellationFee = new CalculateCancellationFee();
    }

    @Test
    public void should_calculate_fee_for_type1() {
        Payment payment = payment();
        // minus 5.5 - so 5 hours full.
        payment.setCreatedAt(payment.getCreatedAt().minusHours(5).minusMinutes(30));
        Double fee = calculateCancellationFee.calculate(payment);
        assertEquals(0.25, fee);
    }
    @Test
    public void should_calculate_0_fee_for_type1_less_than_hours() {
        Payment payment = payment();
        payment.setCreatedAt(payment.getCreatedAt().minusMinutes(30));
        Double fee = calculateCancellationFee.calculate(payment);
        assertEquals(0, fee);
    }

    @Test
    public void should_calculate_fee_for_type2() {
        Payment payment = payment();
        payment.setType(PaymentType.TYPE2);
        // minus 5.5 - so 5 hours full.
        payment.setCreatedAt(payment.getCreatedAt().minusHours(5).minusMinutes(30));
        Double fee = calculateCancellationFee.calculate(payment);
        assertEquals(0.5, fee);
    }

    @Test
    public void should_calculate_fee_for_type3() {
        Payment payment = payment();
        payment.setType(PaymentType.TYPE3);
        // minus 5.5 - so 5 hours full.
        payment.setCreatedAt(payment.getCreatedAt().minusHours(5).minusMinutes(30));
        Double fee = calculateCancellationFee.calculate(payment);
        assertEquals(0.75, fee);
    }

}
