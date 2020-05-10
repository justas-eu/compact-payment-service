package eu.justas.payments.usecases;

import eu.justas.payments.domain.Payment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class CalculateCancellationFee {

    private static final Double TYPE1_COEFFICIENT = 0.05;
    private static final Double TYPE2_COEFFICIENT = 0.1;
    private static final Double TYPE3_COEFFICIENT = 0.15;

    public Double calculate(Payment payment) {
        Double coefficient;
        LocalDateTime timeNow = getNow();
        long hoursPassed = hoursPassed(payment.getCreatedAt(), timeNow);

        switch (payment.getType()) {
            case TYPE1:
                coefficient = TYPE1_COEFFICIENT;
                break;
            case TYPE2:
                coefficient = TYPE2_COEFFICIENT;
                break;
            case TYPE3:
                coefficient = TYPE3_COEFFICIENT;
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return hoursPassed * coefficient;
    }

    private long hoursPassed(LocalDateTime createdAt, LocalDateTime timeNow) {
        return createdAt.until( timeNow, ChronoUnit.HOURS );
    }

    private LocalDateTime getNow() {
        return LocalDateTime.now();
    }
}
