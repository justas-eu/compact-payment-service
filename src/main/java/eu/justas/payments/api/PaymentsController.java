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
import org.springframework.http.ResponseEntity;
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
    private final CalculateCancellationFee calculateCancellationFee;


    @Autowired
    public PaymentsController(CreatePayment createPayment, QueryPayments queryPayments,CalculateCancellationFee calculateCancellationFee ) {
        this.createPayment = createPayment;
        this.queryPayments = queryPayments;
        this.calculateCancellationFee = calculateCancellationFee;
    }

    @RequestMapping(method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Valid
                           @RequestBody CreatePaymentRequest req) {

        Payment payment = createPayment.create(req.getType(), req.getCurrency(), req.getAmount(), req.getDebtorIban(), req.getCreditorIban());
        return payment.getId();

    }

    @RequestMapping(value="/{paymentId}", method = GET)
    public ResponseEntity<?> findById(@PathVariable("paymentId") @NotNull UUID paymentId) {
        Optional<Payment> payment = queryPayments.findById(paymentId);
        if (payment.isPresent()) {
            Double  cancellationFee = calculateCancellationFee.calculate(payment.get());
            PaymentResponse paymentResponse = PaymentConverter.convert(payment.get(), cancellationFee);
            return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(String.format("Payment with id %s not found", paymentId), HttpStatus.NOT_FOUND);
        }
    }
}
