package eu.justas.payments.usecases;

import eu.justas.payments.domain.PaymentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class CalculateCancellationFee {

    @Value("${feeCoefficient.type1}")
    private double TYPE1_COEFFICIENT = 0.05;

    @Value("${feeCoefficient.type2}")
    private double TYPE2_COEFFICIENT = 0.1;

    @Value("${feeCoefficient.type3}")
    private double TYPE3_COEFFICIENT = 0.15;


    public double calculate(LocalDateTime createdAt, PaymentType type) {
        double coefficient;
        LocalDateTime timeNow = getNow();
        long hoursPassed = hoursPassed(createdAt, timeNow);

        switch (type) {
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
