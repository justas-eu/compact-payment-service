package eu.justas.payments.api;

import com.google.gson.Gson;
import eu.justas.payments.api.req.CreatePaymentRequest;
import eu.justas.payments.usecases.CreatePayment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static eu.justas.payments.api.req.PaymentRequestFixture.paymentRequest;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentsController.class)
public class PaymentsControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CreatePayment createPayment;

    @Test
    public void creates_payment() throws Exception {

        Gson gson = new Gson();
        CreatePaymentRequest paymentRequest = paymentRequest();
        String paymentRequestJsonString = gson.toJson(paymentRequest);

        mvc.perform(
                post("/payments")
                        .contentType("application/json")
                        .content(paymentRequestJsonString))
                .andExpect(status().isCreated());

        verify(createPayment).create(
                eq(paymentRequest.getType()),
                eq(paymentRequest.getCurrency()),
                eq(paymentRequest.getAmount()),
                eq(paymentRequest.getDebtorIban()),
                eq(paymentRequest.getCreditorIban()));
    }

}
