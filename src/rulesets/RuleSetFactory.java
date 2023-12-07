package rulesets;

import java.awt.Component;
import java.util.HashMap;

import guid.AddMemberForm;
import guid.LoginForm;


final public class RuleSetFactory {
	private RuleSetFactory(){}
	static HashMap<Class<? extends Component>, RuleSet> map = new HashMap<>();
	static {
		map.put(AddMemberForm.class, new AddMemberRuleSet());
		map.put(LoginForm.class, new LoginRuleSet());
	}
	public static RuleSet getRuleSet(Component c) {
		Class<? extends Component> cl = c.getClass();
		if(!map.containsKey(cl)) {
			throw new IllegalArgumentException(
					"No RuleSet found for this Component");
		}
		return map.get(cl);
	}
}
