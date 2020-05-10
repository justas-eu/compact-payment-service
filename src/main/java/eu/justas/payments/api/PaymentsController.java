package eu.justas.payments.api;

import eu.justas.payments.api.dto.CreatePaymentRequest;
import eu.justas.payments.api.dto.PaymentConverter;
import eu.justas.payments.api.dto.PaymentResponse;
import eu.justas.payments.domain.Payment;
import eu.justas.payments.usecases.CalculateCancellationFee;
import eu.justas.payments.usecases.CreatePayment;
import eu.justas.payments.usecases.QueryPayments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    private final CreatePayment createPayment;
    private final QueryPayments queryPayments;

    @Autowired
    public PaymentsController(CreatePayment createPayment, QueryPayments queryPayments) {
        this.createPayment = createPayment;
        this.queryPayments = queryPayments;
    }

    @RequestMapping(method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid
                           @RequestBody CreatePaymentRequest req) {

        createPayment.create(req.getType(), req.getCurrency(), req.getAmount(), req.getDebtorIban(), req.getCreditorIban());

    }

    @RequestMapping(value="/{paymentId}", method = GET)
    public PaymentResponse findById(@PathVariable("paymentId") @NotNull UUID paymentId) {
        Optional<Payment> payment = queryPayments.findById(paymentId);
        if (payment.isPresent()) {
            Double  cancelationFee = CalculateCancellationFee.calculate(payment.get());
            return PaymentConverter.convert(payment.get(), cancelationFee);
        } else {
            return new PaymentResponse();
        }
    }
}
