package eu.justas.payments.usecases;

import eu.justas.payments.domain.Payment;
import eu.justas.payments.domain.ValidatablePayment;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.stereotype.Component;

@Component
public class ValidatePayment {

    private ValidatePaymentRules paymentRules = new ValidatePaymentRules();

    public ValidatablePayment validate(Payment payment) {

        ValidatablePayment validatablePayment = new ValidatablePayment(payment);
        validatablePayment.setValid(true);

        Rules rules = paymentRules.defineRules(validatablePayment);
        Facts facts = paymentRules.defineFacts(payment, validatablePayment);

        // fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);

        return validatablePayment;
    }
}
