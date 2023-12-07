package rulesets;

import java.awt.Component;

import business.LibraryMember;
import guid.AddMemberForm;



/**
 * Rules:
 *  1. All fields must be nonempty 
 *  2. ID field must be numeric 
 *  3. Zip must be numeric with exactly 5 digits 
 *  4. State must have exactly two characters in the range A-Z 5. 
 *  5. ID field may not equal zip field.
 *
 */

public class AddMemberRuleSet implements RuleSet {
	private LibraryMember libraryMember;

	@Override
	public void applyRules(Component ob) throws RuleException {
		libraryMember = ((AddMemberForm)ob).getLibraryMember(); 
//		member = AddMemberForm.INSTANCE;
		if(libraryMember == null) {
			throw new RuleException("Invalid member!");
		}
		nonemptyRule();
//		idNumericRule();
		zipNumericRule();
		stateRule();
//		idNotZipRule();
	}

	private void nonemptyRule() throws RuleException {
		if(libraryMember.getMemberId().trim().isEmpty() ||
				libraryMember.getFirstName().trim().isEmpty()||
				libraryMember.getLastName().trim().isEmpty()||
				libraryMember.getTelephone().trim().isEmpty()||
				libraryMember.getAddress()== null ||
				libraryMember.getAddress().getStreet().trim().isEmpty()||
				libraryMember.getAddress().getCity().trim().isEmpty() ||
				libraryMember.getAddress().getZip().trim().isEmpty() ||
				libraryMember.getAddress().getState().trim().isEmpty()) {
			throw new RuleException("All fields must be non-empty!");
		}
	}

//	private void idNumericRule() throws RuleException {
//		String val = member.getIdValue().trim();
//		try {
//			Integer.parseInt(val);
//			//val is numeric
//		} catch(NumberFormatException e) {
//			throw new RuleException("ID must be numeric");
//		}		
//	}

	private void zipNumericRule() throws RuleException {
		String val = libraryMember.getAddress().getZip().trim();
		try {
			Integer.parseInt(val);
			//val is numeric
		} catch(NumberFormatException e) {
			throw new RuleException("Zipcode must be numeric");
		}
		if(val.length() != 5) throw new RuleException("Zipcode must have 5 digits");
	}

	private void stateRule() throws RuleException {
		String state = libraryMember.getAddress().getState().trim();
		if(state.length() != 2) throw new RuleException("State field must have two characters");
		if(!Util.isInRangeAtoZ(state.charAt(0)) 
				|| !Util.isInRangeAtoZ(state.charAt(1))) {
			throw new RuleException("Characters is state field must be in range A-Z");
		}
	}
	
	

//	private void idNotZipRule() throws RuleException {
//		String zip = libraryMember.getAddress().getZip().trim();
////		String id = libraryMember.getIdValue().trim();
//		if(zip.equals(id)) throw new RuleException("ID may not be the same as zipcode");
//	}

}
