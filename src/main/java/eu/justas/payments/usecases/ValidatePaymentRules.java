package eu.justas.payments.usecases;

import eu.justas.payments.domain.Payment;
import eu.justas.payments.domain.ValidatablePayment;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.RuleBuilder;
import org.springframework.util.StringUtils;

public class ValidatePaymentRules {

    public Rules defineRules(ValidatablePayment validatablePayment) {
        Rule positiveAmount = new RuleBuilder()
                .name("Positive amount")
                .description("Amount must be positive")
                .when(facts -> facts.get("amountPositive").equals(false))
                .then(facts -> {
                    validatablePayment.setValid(false);
                    validatablePayment.setError(validatablePayment.getError() + "Amount must be positive. ");
                })
                .build();

        Rule type1 = new RuleBuilder()
                .name("TYPE1 for EUR payments")
                .description("TYPE1 is only applicable for EUR payments")
                .when(facts -> facts.get("type").equals("TYPE1") && !facts.get("currency").equals("EUR"))
                .then(facts -> {
                    validatablePayment.setValid(false);
                    validatablePayment.setError(validatablePayment.getError() + "TYPE1 is only applicable for EUR payments. ");
                })
                .build();

        Rule type2 = new RuleBuilder()
                .name("TYPE2 for USD payments")
                .description("TYPE2 is only applicable for USD payments")
                .when(facts -> facts.get("type").equals("TYPE2") && !facts.get("currency").equals("USD"))
                .then(facts -> {
                    validatablePayment.setValid(false);
                    validatablePayment.setError(validatablePayment.getError() + "TYPE2 is only applicable for USD payments. ");
                })
                .build();

        Rule type1Details = new RuleBuilder()
                .name("TYPE1 details field")
                .description("TYPE1 has additional field 'details' (text) which is mandatory")
                .when(facts -> facts.get("type").equals("TYPE1") && StringUtils.isEmpty(facts.get("details")))
                .then(facts -> {
                    validatablePayment.setValid(false);
                    validatablePayment.setError(validatablePayment.getError() + "TYPE1 has additional field 'details' (text) which is mandatory. ");
                })
                .build();


        Rules rules = new Rules();
        rules.register(positiveAmount);
        rules.register(type1);
        rules.register(type2);
        rules.register(type1Details);
        return rules;
    }


    public Facts defineFacts(Payment payment, ValidatablePayment validatablePayment) {
        Facts facts = new Facts();
        facts.put("amount", payment.getAmount());
        facts.put("amountPositive", validatablePayment.isAmountPositive());
        facts.put("currency", payment.getCurrency());
        facts.put("type", payment.getType().toString());
        facts.put("details", payment.getDetails());
        return facts;
    }
}
