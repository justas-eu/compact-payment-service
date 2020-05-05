package eu.justas.payments.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    @RequestMapping(method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create() {
        System.out.println("hit");

    }
}
