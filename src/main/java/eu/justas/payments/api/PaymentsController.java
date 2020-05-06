package eu.justas.payments.api;

import eu.justas.payments.api.req.CreatePaymentRequest;
import eu.justas.payments.usecases.CreatePayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    private final CreatePayment createPayment;

    @Autowired
    public PaymentsController(CreatePayment createPayment) {
        this.createPayment = createPayment;
    }


    @RequestMapping(method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid
                           @RequestBody CreatePaymentRequest req) {


        createPayment.create(req.getType(), req.getCurrency(), req.getAmount(), req.getDebtorIban(), req.getCreditorIban());

    }
}
