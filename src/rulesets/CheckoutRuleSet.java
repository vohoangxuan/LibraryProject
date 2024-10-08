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
		StringBuilder msg = new StringBuilder();
		try{
			nonemptyRule();
		}
		catch (RuleException e){
			msg.append(e.getMessage());
		}
		try{
			numericDueDate();
		}
		catch (RuleException e){
			msg.append("".equals(msg.toString())? "": "\n").append(e.getMessage());
		}
		if(!"".equals(msg.toString()))
			throw new RuleException(msg.toString());
		
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
		int parseVal;
		try {
			parseVal = Integer.parseInt(val);
			//val is integer
		} catch(NumberFormatException e) {
			throw new RuleException("Due date must be numeric");
		}
		if (parseVal < 1){
			throw new RuleException("Due date must be greater than 0");
		}
	}
	
}