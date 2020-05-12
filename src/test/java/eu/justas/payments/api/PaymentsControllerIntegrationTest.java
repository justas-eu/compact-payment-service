package eu.justas.payments.api;

import com.google.gson.Gson;
import eu.justas.payments.api.dto.CreatePaymentRequest;
import eu.justas.payments.domain.Payment;
import eu.justas.payments.usecases.CalculateCancellationFee;
import eu.justas.payments.usecases.CreatePayment;
import eu.justas.payments.usecases.QueryPayments;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static eu.justas.payments.api.dto.PaymentRequestFixture.paymentRequest;
import static eu.justas.payments.usecases.fixtures.PaymentFixture.payment;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentsController.class)
public class PaymentsControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CreatePayment createPayment;

    @MockBean
    private QueryPayments queryPayments;

    @MockBean
    private CalculateCancellationFee calculateCancellationFee;

    @Test
    public void creates_payment() throws Exception {

        Gson gson = new Gson();
        CreatePaymentRequest paymentRequest = paymentRequest();
        String paymentRequestJsonString = gson.toJson(paymentRequest);
        when(createPayment.create(any(),any(),any(),any(),any())).thenReturn(payment());


        mvc.perform(
                post("/payments")
                        .contentType("application/json")
                        .content(paymentRequestJsonString))
                .andExpect(status().isCreated())
                .andExpect(content().string(is(matchesPattern("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"))));


        verify(createPayment).create(
                eq(paymentRequest.getType()),
                eq(paymentRequest.getCurrency()),
                eq(paymentRequest.getAmount()),
                eq(paymentRequest.getDebtorIban()),
                eq(paymentRequest.getCreditorIban()));
    }

    @Test
    public void find_payment_by_id() throws Exception {

        Gson gson = new Gson();
        CreatePaymentRequest paymentRequest = paymentRequest();
        String paymentRequestJsonString = gson.toJson(paymentRequest);

        Payment foundPayment = payment();
        foundPayment.setCreatedAt(foundPayment.getCreatedAt().minusHours(5).minusMinutes(30));

        Optional<Payment> found = Optional.of(foundPayment);
        when(queryPayments.findById(any())).thenReturn(found);
        when(createPayment.create(any(),any(),any(),any(),any())).thenReturn(payment());


        mvc.perform(
                post("/payments")
                        .contentType("application/json")
                        .content(paymentRequestJsonString))
                .andExpect(status().isCreated());

        String paymentId = UUID.randomUUID().toString();
        mvc.perform(
                get("/payments/" + paymentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(matchesPattern("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"))));
    }
}
