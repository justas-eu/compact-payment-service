package eu.justas.payments.api;

import eu.justas.payments.api.dto.CreatePaymentRequest;
import eu.justas.payments.api.dto.PaymentConverter;
import eu.justas.payments.api.dto.PaymentResponse;
import eu.justas.payments.domain.Payment;
import eu.justas.payments.usecases.CalculateCancellationFee;
import eu.justas.payments.usecases.CreatePayment;
import eu.justas.payments.usecases.QueryPayments;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.InvalidParameterException;
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
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created. Payment created."),
            @ApiResponse(code = 400, message = "Bad Request. Invalid request data.")})
    public ResponseEntity<?> create(@Valid
                           @RequestBody CreatePaymentRequest req) {

        Payment payment;
        try {
            payment = createPayment.create(req.getType(),
                    req.getCurrency(),
                    req.getAmount(),
                    req.getDebtorIban(),
                    req.getCreditorIban(),
                    req.getDetails());
        } catch (InvalidParameterException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(payment.getId(), HttpStatus.CREATED);

    }

    @RequestMapping(value="/{paymentId}", method = GET)
    public ResponseEntity<?> findById(@PathVariable("paymentId") @NotNull UUID paymentId) {
        Optional<Payment> paymentOptional = queryPayments.findById(paymentId);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            double  cancellationFee = calculateCancellationFee.calculate(payment.getCreatedAt(), payment.getType());
            PaymentResponse paymentResponse = PaymentConverter.convert(payment, cancellationFee);
            return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(String.format("Payment with id %s not found", paymentId), HttpStatus.NOT_FOUND);
        }
    }
}
