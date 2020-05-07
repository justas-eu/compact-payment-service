package eu.justas.payments.db;

import eu.justas.payments.domain.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentRepository.class);

    Map<String, Payment> paymentMap = new HashMap<>();

    public void add(Payment payment) {
        LOG.trace("Persisting payment id={}", payment.getId());
        paymentMap.put(payment.getId(), payment);
    }

    public long getCount() {
        return paymentMap.size();
    }
}
