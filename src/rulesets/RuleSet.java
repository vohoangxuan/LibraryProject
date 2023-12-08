package rulesets;

import business.BookException;

import java.awt.Component;

public interface RuleSet {
	public void applyRules(Component ob) throws RuleException;
}
