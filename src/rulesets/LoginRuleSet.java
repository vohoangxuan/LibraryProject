package rulesets;

import java.awt.Component;

import dataaccess.User;
import guid.LoginForm;



/**
 * Rules:
 * 1. all fields non empty
 *
 */
public class LoginRuleSet implements RuleSet {

	private User loginUser;
	@Override
	public void applyRules(Component ob) throws RuleException {
		loginUser = ((LoginForm)ob).getUser();
		nonemptyRule();
//		idNumericRule();
//		favRestAndMovieRule();		
//		correctCharTypeRule();
		
	}
	
	private void nonemptyRule() throws RuleException {
		if(loginUser.getId().trim().isEmpty() ||
				loginUser.getPassword().trim().isEmpty()){
			   throw new RuleException("Username and password must be nonempty");
		}
	}
	
}
