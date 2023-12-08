package rulesets;

import business.BookException;
import guid.AddBookForm;

import java.awt.*;

/**
 * Rules:
 * 1. Fields cannot be empty
 */
public class AddBookRuleSet implements RuleSet {
    private AddBookForm addBookForm;
    @Override
    public void applyRules(Component ob) throws RuleException {
        addBookForm = (AddBookForm) ob;
        nonemptyRule();
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

}
