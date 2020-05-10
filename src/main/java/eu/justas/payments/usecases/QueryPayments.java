package eu.justas.payments.usecases;

import eu.justas.payments.db.PaymentRepository;
import eu.justas.payments.domain.Payment;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class QueryPayments {

    PaymentRepository repository;

    public QueryPayments(PaymentRepository repository) {
        this.repository = repository;
    }

    public Optional<Payment> findById(UUID id) {
        return repository.findById(id);
    }
}
