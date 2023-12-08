package rulesets;

import java.awt.Component;

import guid.CheckoutForm;

/**
 * Rules:
 *  1. All fields must be nonempty 
 *  2. Due date must be integer
 *
 */

public class CheckoutRuleSet implements RuleSet {
	private CheckoutForm checkoutForm;

    
	@Override
	public void applyRules(Component ob) throws RuleException {
		checkoutForm = (CheckoutForm) ob; 
		nonemptyRule();
		numericDueDate();
	}

	private void nonemptyRule() throws RuleException {
		if(checkoutForm.getMemberText().trim().isEmpty() ||
        checkoutForm.getIsbnText().trim().isEmpty()||
        checkoutForm.getCheckoutDateText().trim().isEmpty()||
        checkoutForm.getDueDateText().trim().isEmpty()) {
			throw new RuleException("All field must be filled");
		}
	}

	private void numericDueDate() throws RuleException {
		String val = checkoutForm.getDueDateText().trim();
		try {
			Integer.parseInt(val);
			//val is integer
		} catch(NumberFormatException e) {
			throw new RuleException("Due date must be numeric");
		}
	}
}