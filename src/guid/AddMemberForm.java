package guid;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.Util;

public class AddMemberForm extends JFrame implements LibWindow{
	private static final long serialVersionUID = 1L;
	public static final AddMemberForm INSTANCE = new AddMemberForm();
	ControllerInterface ci = new SystemController();
	
	private boolean isInitialized = false;
	private JPanel mainPanel;
	private JPanel upperHalf;
	private JPanel middleHalf;
	private JPanel lowerHalf;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel leftTextPanel;
	private JPanel rightTextPanel;	
	
	
    private JTextField memberID;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField phoneNumber;
    private JTextField state;
    private JTextField city;
    private JTextField zip;
    private JTextField street;
    private JButton addNewMember;
    private JPanel lowerPanel;
    
	public void init() {
		mainPanel = new JPanel();
		
    	defineUpperHalf();
    	defineMiddleHalf();
    	defineLowerHalf();
    	
    	BorderLayout bl = new BorderLayout();
    	bl.setVgap(30);
    	mainPanel.setLayout(bl);

    	mainPanel.add(upperHalf, BorderLayout.NORTH);
    	mainPanel.add(middleHalf, BorderLayout.CENTER);
    	mainPanel.add(lowerHalf, BorderLayout.SOUTH);
    	getContentPane().add(mainPanel);
    	isInitialized(true);
    	pack();
	}
	
    private void defineUpperHalf() {
		
		upperHalf = new JPanel();
		upperHalf.setLayout(new BorderLayout());
		defineTopPanel();
		defineMiddlePanel();
		defineLowerPanel();
		upperHalf.add(topPanel, BorderLayout.NORTH);
		upperHalf.add(middlePanel, BorderLayout.CENTER);
		upperHalf.add(lowerPanel, BorderLayout.SOUTH);
		
	}
	private void defineMiddleHalf() {
		middleHalf = new JPanel();
		middleHalf.setLayout(new BorderLayout());
		JSeparator s = new JSeparator();
		s.setOrientation(SwingConstants.HORIZONTAL);
		//middleHalf.add(Box.createRigidArea(new Dimension(0,50)));
		middleHalf.add(s, BorderLayout.SOUTH);
		
	}
	private void defineLowerHalf() {

		lowerHalf = new JPanel();
		lowerHalf.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JButton backButton = new JButton("<= Back to Main");
		backButton.addActionListener(new BackToMainListener());
		lowerHalf.add(backButton);
		
	}
	private void defineTopPanel() {
		topPanel = new JPanel();
		JPanel intPanel = new JPanel(new BorderLayout());
		intPanel.add(Box.createRigidArea(new Dimension(0,20)), BorderLayout.NORTH);
		JLabel loginLabel = new JLabel("Add member");
		Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
		intPanel.add(loginLabel, BorderLayout.CENTER);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(intPanel);
		
	}
	
	
	
	private void defineMiddlePanel() {
		middlePanel=new JPanel();
		middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		defineLeftTextPanel();
		defineRightTextPanel();
		middlePanel.add(leftTextPanel);
		middlePanel.add(rightTextPanel);
	}
	
	private void defineLowerPanel() {
		
		lowerPanel = new JPanel();
		addNewMember = new JButton("Add new member");
		addAddMemberButtonListener(addNewMember);
		lowerPanel.add(addNewMember);
		
	}
	
	private void defineLeftTextPanel() {
		leftTextPanel = new JPanel();
		leftTextPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel leftTextPanel1 = new JPanel();
		JPanel leftTextPanel2 = new JPanel();
		leftTextPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		leftTextPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel leftTextPanel11 = new JPanel();
		leftTextPanel11.setLayout(new BorderLayout());
		JPanel leftTextPanel12 = new JPanel();
		leftTextPanel12.setLayout(new BorderLayout());
		
		JPanel panelMemIDLbl = new JPanel();
		JLabel label4 = new JLabel("Member ID");
		label4.setSize(180, 30);
		panelMemIDLbl.add(label4);

        //---- memberID3 ----
		memberID = new JTextField();
        memberID.setColumns(10);
        JPanel panelMemFld = new JPanel(); 
        panelMemFld.add(memberID);
        
        JPanel panelStreetLbl = new JPanel();
        panelStreetLbl.setLayout(new FlowLayout());

        JLabel label5 = new JLabel("Street");
        label5.setSize(180, 30);
        panelStreetLbl.add(label5);

            //---- state ----
        JPanel panelStreetFld = new JPanel();
        street = new JTextField();
        street.setColumns(10);
        panelStreetFld.add(street);
        
        leftTextPanel11.add(panelMemIDLbl, BorderLayout.NORTH);
        leftTextPanel11.add(panelStreetLbl, BorderLayout.CENTER);

		leftTextPanel12.add(panelMemFld, BorderLayout.NORTH);
        leftTextPanel12.add(panelStreetFld, BorderLayout.CENTER);
        
        //----------------------
		JPanel leftTextPanel21 = new JPanel();
		leftTextPanel21.setLayout(new BorderLayout());
		JPanel leftTextPanel22 = new JPanel();	
		leftTextPanel22.setLayout(new BorderLayout());
		
		JPanel panelFnLbl = new JPanel();
		JLabel label6 = new JLabel("First Name");
		panelFnLbl.add(label6);

        //---- memberID3 ----
		firstName = new JTextField();
        firstName.setColumns(10);
        JPanel panelFnFld = new JPanel(); 
        panelFnFld.add(firstName);
        
        JPanel panelCityLbl = new JPanel();
        panelCityLbl.setLayout(new FlowLayout());

        JLabel label7 = new JLabel("City");
        panelCityLbl.add(label7);

            //---- city ----
        JPanel panelCityFld = new JPanel();
        city = new JTextField();
        city.setColumns(10);
        panelCityFld.add(city);
        
        leftTextPanel21.add(panelFnLbl, BorderLayout.NORTH);
        leftTextPanel21.add(panelCityLbl, BorderLayout.CENTER);

		leftTextPanel22.add(panelFnFld, BorderLayout.NORTH);
        leftTextPanel22.add(panelCityFld, BorderLayout.CENTER);
        
		
		leftTextPanel1.add(leftTextPanel11);
		leftTextPanel1.add(leftTextPanel12);
		leftTextPanel2.add(leftTextPanel21);
		leftTextPanel2.add(leftTextPanel22);
		leftTextPanel.add(leftTextPanel1);
		leftTextPanel.add(leftTextPanel2);
		
	}
	private void defineRightTextPanel() {
		rightTextPanel = new JPanel();
		rightTextPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel rightTextPanel1 = new JPanel();
		JPanel rightTextPanel2 = new JPanel();
		rightTextPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		rightTextPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel rightTextPanel11 = new JPanel();
		rightTextPanel11.setLayout(new BorderLayout());
		JPanel rightTextPanel12 = new JPanel();
		rightTextPanel12.setLayout(new BorderLayout());
		
		JPanel panelLnLbl = new JPanel();
		JLabel label4 = new JLabel("Last Name");
		label4.setSize(180, 30);
		panelLnLbl.add(label4);

        //---- Last name ----
		lastName = new JTextField();
        lastName.setColumns(10);
        JPanel panelMemFld = new JPanel(); 
        panelMemFld.add(lastName);
        
        JPanel panelZipLbl = new JPanel();
        panelZipLbl.setLayout(new FlowLayout());

        JLabel label5 = new JLabel("Zip");
        label5.setSize(180, 30);
        panelZipLbl.add(label5);

            //---- zip ----
        JPanel panelZipFld = new JPanel();
        zip = new JTextField();
        zip.setColumns(10);
        panelZipFld.add(zip);
        
        rightTextPanel11.add(panelLnLbl, BorderLayout.NORTH);
        rightTextPanel11.add(panelZipLbl, BorderLayout.CENTER);

		rightTextPanel12.add(panelMemFld, BorderLayout.NORTH);
        rightTextPanel12.add(panelZipFld, BorderLayout.CENTER);
        
        //----------------------
		JPanel rightTextPanel21 = new JPanel();
		rightTextPanel21.setLayout(new BorderLayout());
		JPanel rightTextPanel22 = new JPanel();	
		rightTextPanel22.setLayout(new BorderLayout());
		
		JPanel panelTelLbl = new JPanel();
		JLabel label6 = new JLabel("Phone number");
		panelTelLbl.add(label6);

        //---- memberID3 ----
		phoneNumber = new JTextField();
        phoneNumber.setColumns(10);
        JPanel panelFnFld = new JPanel(); 
        panelFnFld.add(phoneNumber);
        
        JPanel panelStateLbl = new JPanel();
        panelStateLbl.setLayout(new FlowLayout());

        JLabel label7 = new JLabel("State");
        panelStateLbl.add(label7);

            //---- state ----
        JPanel panelStateFld = new JPanel();
        state = new JTextField();
        state.setColumns(10);
        panelStateFld.add(state);
        
        rightTextPanel21.add(panelTelLbl, BorderLayout.NORTH);
        rightTextPanel21.add(panelStateLbl, BorderLayout.CENTER);

		rightTextPanel22.add(panelFnFld, BorderLayout.NORTH);
        rightTextPanel22.add(panelStateFld, BorderLayout.CENTER);
        
		
		rightTextPanel1.add(rightTextPanel11);
		rightTextPanel1.add(rightTextPanel12);
		rightTextPanel2.add(rightTextPanel21);
		rightTextPanel2.add(rightTextPanel22);
		rightTextPanel.add(rightTextPanel1);
		rightTextPanel.add(rightTextPanel2);
		
	}
	
	private void addAddMemberButtonListener(JButton butn) {
		butn.addActionListener(evt -> addNewMember()
		);
	}
	
	private void addNewMember() {
		
		String memberId = memberID.getText();
		if(memberID == null || "".equals(memberID)) {
			Util.showMessage(this, "Member ID cannot be bank!");
			return;
		}
		String fname = firstName.getText();
		String lname = lastName.getText();
		String tel = phoneNumber.getText();
		Address address = new Address(street.getText(), city.getText(), state.getText(), zip.getText());
		LibraryMember member = new LibraryMember(memberId, fname, lname, tel, address);
		ci.saveNewMember(member);
		Util.showMessage(this, "Member added!");
		
	}
	
	@Override
	public boolean isInitialized() {
		return isInitialized;
	}


	@Override
	public void isInitialized(boolean val) {
		isInitialized =val;
		
	}
	
	class BackToMainListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
    		
		}
	}
	
}
