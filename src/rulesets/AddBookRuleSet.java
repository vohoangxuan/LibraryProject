package rulesets;

import business.BookException;
import guid.AddBookForm;

import java.awt.*;

/**
 * Rules:
 * 1. Fields cannot be empty
 * 2. Copy must be > 0
 */
public class AddBookRuleSet implements RuleSet {
    private AddBookForm addBookForm;
    @Override
    public void applyRules(Component ob) throws RuleException {
        addBookForm = (AddBookForm) ob;
        nonemptyRule();
        copyRule();
    }

    private void nonemptyRule() throws RuleException {
        if(addBookForm.getISBN().trim().isEmpty() ||
        addBookForm.getMaxCheckOut().trim().isEmpty() ||
        addBookForm.getTitle().trim().isEmpty() ||
        addBookForm.getNumberOfCopies().isEmpty() ||
        addBookForm.getAuthorName().isEmpty()
        ) {
            throw new RuleException("Fields cannot be empty");
        }
    }

    private void copyRule() throws RuleException {
        if(Integer.parseInt(addBookForm.getNumberOfCopies().trim()) < 1) {
            throw new RuleException("Copy must be at least 1");
        }
    }

}
