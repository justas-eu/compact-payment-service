package eu.justas.payments.usecases;

import eu.justas.payments.domain.Payment;

public class CalculateCancellationFee {
    public static Double calculate(Payment payment) {
        return 0.11;
    }
}
