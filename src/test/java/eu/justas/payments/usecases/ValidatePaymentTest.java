package eu.justas.payments.usecases;

import eu.justas.payments.domain.Payment;
import eu.justas.payments.domain.PaymentType;
import eu.justas.payments.domain.ValidatablePayment;
import org.junit.Before;
import org.junit.Test;

import static eu.justas.payments.usecases.fixtures.PaymentFixture.payment;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ValidatePaymentTest {

    ValidatePayment validatePayment;

    @Before
    public void setUp() {
        validatePayment = new ValidatePayment();
    }

    @Test
    public void should_set_invalid_with_negative_amount() {

        Payment payment = payment();
        payment.setAmount(-1.0);
        ValidatablePayment validatablePayment = validatePayment.validate(payment);
        assertThat(validatablePayment.isValid(), is(false));
        assertThat(validatablePayment.getError(), is("Amount must be positive. "));
    }

    @Test
    public void should_set_invalid_for_type1_with_non_EUR() {

        Payment payment = payment();
        payment.setCurrency("LTL");
        ValidatablePayment validatablePayment = validatePayment.validate(payment);
        assertThat(validatablePayment.isValid(), is(false));
    }
    @Test
    public void should_set_valid_for_type1_with_EUR() {

        Payment payment = payment();
        payment.setCurrency("EUR");
        ValidatablePayment validatablePayment = validatePayment.validate(payment);
        assertThat(validatablePayment.isValid(), is(true));
    }

    @Test
    public void should_set_invalid_for_type2_with_non_USD() {

        Payment payment = payment();
        payment.setType(PaymentType.TYPE2);
        payment.setCurrency("EUR");
        ValidatablePayment validatablePayment = validatePayment.validate(payment);
        assertThat(validatablePayment.isValid(), is(false));
        assertThat(validatablePayment.getError(), is("TYPE2 is only applicable for USD payments. "));
    }

    @Test
    public void should_set_invalid_for_type1_without_details() {

        Payment payment = payment();
        payment.setDetails("");
        ValidatablePayment validatablePayment = validatePayment.validate(payment);
        assertThat(validatablePayment.isValid(), is(false));
    }

    @Test
    public void should_set_valid_for_type2_without_details() {

        Payment payment = payment();
        payment.setType(PaymentType.TYPE2);
        payment.setCurrency("USD");
        payment.setDetails("");
        ValidatablePayment validatablePayment = validatePayment.validate(payment);
        assertThat(validatablePayment.isValid(), is(true));
    }


}
