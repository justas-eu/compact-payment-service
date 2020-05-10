package eu.justas.payments.api.dto;

import eu.justas.payments.domain.Payment;

public class PaymentConverter {
    public static PaymentResponse convert(Payment payment, Double cancelationFee) {

        PaymentResponse response = new PaymentResponse();
        response.setId(payment.getId());
        response.setAmount(payment.getAmount());
        response.setType(payment.getType().toString());
        response.setCurrency(payment.getCurrency());
        response.setDebtorIban(payment.getDebtorIban());
        response.setCreditorIban(payment.getCreditorIban());
        response.setCancellationFee(cancelationFee);

        return response;
    }
}
