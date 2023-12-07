package rulesets;

import java.awt.Component;

import guid.LoginForm;



/**
 * Rules:
 * 1. all fields non empty
 * 2. favorite restaurant cannot equal favorite movie
 * 3. id must be numeric
 * 4. firstname and lastname fields may not contain spaces or 
 * characters other than a-z, A-Z.
 *
 */
public class LoginRuleSet implements RuleSet {

	private LoginForm loginForm;
	@Override
	public void applyRules(Component ob) throws RuleException {
	loginForm = (LoginForm)ob;
		nonemptyRule();
//		idNumericRule();
//		favRestAndMovieRule();		
//		correctCharTypeRule();
		
	}
	
	private void nonemptyRule() throws RuleException {
		if(loginForm.getUsername().trim().isEmpty() ||
				loginForm.getPassword().trim().isEmpty()){
			   throw new RuleException("Username and password must be nonempty");
		}
	}
	
}
