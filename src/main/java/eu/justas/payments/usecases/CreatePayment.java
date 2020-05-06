package eu.justas.payments.usecases;

import eu.justas.payments.domain.Payment;
import org.springframework.stereotype.Component;

@Component
public class CreatePayment {

    public void create(String type, String currency, Double amount, String debtorIban, String creditorIban) {
        Payment payment = new Payment();
        System.out.println("hit");
    }
}
