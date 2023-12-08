package guid;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import business.Address;
import business.CheckoutRecordEntry;
import business.ControllerInterface;
import business.LibraryMember;
import business.SearchMemberException;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.Util;

public class SearchMemberForm extends JFrame implements LibWindow{
	private static final long serialVersionUID = 1L;
	public static final SearchMemberForm INSTANCE = new SearchMemberForm();
	ControllerInterface ci = new SystemController();
	CustomTableModel model;
	private boolean isInitialized = false;
	private JPanel mainPanel;
	private JPanel upperHalf;
	private JPanel middleHalf;
	private JPanel lowerHalf;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel leftTextPanel;
	private JPanel rightTextPanel;	
	private JPanel middlePanelRow1;
	
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
    private JPanel lowerPanelRow1;
    private JPanel lowerPanelRow2;
    private JButton searchMember;
    private final boolean USE_DEFAULT_DATA = true;
    JTable table;
	JScrollPane scrollPane;
	
    //table data and config
	private final String[] DEFAULT_COLUMN_HEADERS = {"Book title", "ISBN", "Check out date","Due date","Copy"};
	
    private Address address;
	private LibraryMember libraryMember;
	
	public void init() {
		if(isInitialized())
    		return;
		try {
////		firstName.setText(libraryMember.getFirstName());
////		lastName.setText(libraryMember.getLastName());
////		phoneNumber.setText(libraryMember.getTelephone()); 
//		Address addr = libraryMember.getAddress();
//		if(addr != null) {
//			street.setText(addr.getStreet());
//			city.setText(addr.getCity());
//			zip.setText(addr.getZip());
//			state.setText(addr.getState());
//		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
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
		JLabel loginLabel = new JLabel("Search member");
		Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
		intPanel.add(loginLabel, BorderLayout.CENTER);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(intPanel);
		
	}
	
	
	
	private void defineMiddlePanel() {
		middlePanel=new JPanel();
//		middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		middlePanel.setLayout(new BorderLayout());
		defineMiddlePanelRow();
		defineLeftTextPanel();
		defineRightTextPanel();
		
		middlePanel.add(middlePanelRow1, BorderLayout.NORTH);
		middlePanel.add(leftTextPanel, BorderLayout.CENTER);
		middlePanel.add(rightTextPanel, BorderLayout.SOUTH);
		
		
		firstName.setEnabled(false);
		lastName.setEnabled(false);
		phoneNumber.setEnabled(false);
		street.setEnabled(false);
		city.setEnabled(false);
		state.setEnabled(false);
		zip.setEnabled(false);
	}
	
	private void defineLowerPanel() {
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new BorderLayout());
		
		defineLowerPanelRow1();
		lowerPanelRow2 = new JPanel();
		
		lowerPanel.add(scrollPane, BorderLayout.NORTH);//lowerPanelRow1
		lowerPanel.add(lowerPanelRow2, BorderLayout.CENTER);
		/*
		addNewMember = new JButton("Save");
		addAddMemberButtonListener(addNewMember);
		lowerPanel.add(addNewMember);
		*/
		
	}

	//table
	public void defineLowerPanelRow1(){
		createTableAndTablePane();
		/*
		GuiControl.createCustomColumns(table, 
		                               800,
		                               new float []{0.4f, 0.2f, 0.2f, 0.2f},
		                               DEFAULT_COLUMN_HEADERS);
		                   		
		lowerPanelRow1 = GuiControl.createStandardTablePanePanel(table,tablePane);
				*/
	}
	
	// --------------------------------------------------------------------
	private void createTableAndTablePane() {
		updateModel(); 
		table = new JTable(model);
		createCustomColumns(table, 800,
				new float []{0.25f, 0.15f, 0.15f, 0.15f, 0.15f}, DEFAULT_COLUMN_HEADERS);
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(
				new Dimension(800, 300));
		scrollPane.getViewport().add(table);
	}

	private void updateModel() {
		List<String[]> list = new ArrayList<String[]>();
		if(model == null) {
			model = new CustomTableModel();
		}
		model.setTableValues(list);
	}
	
	private void createCustomColumns(JTable table, int width, float[] proportions,
		  String[] headers) {
		table.setAutoCreateColumnsFromModel(false);
        int num = headers.length;
        for(int i = 0; i < num; ++i) {
            TableColumn column = new TableColumn(i);
            column.setHeaderValue(headers[i]);
            column.setMinWidth(Math.round(proportions[i]*width));
            table.addColumn(column);
        }
	}

	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setValues(model);
			table.updateUI();			
		}
	}
	
	private void setValues(CustomTableModel model) {
		model.removeAll();
		List<String[]> data = new ArrayList<String[]>();
		if(libraryMember == null) 
			return;
		
		if(libraryMember.getRecord() == null)
			return;
		List<CheckoutRecordEntry> recordEntries = libraryMember.getRecord().getRecord();
		
		for (CheckoutRecordEntry e : recordEntries) {
			String[] entry = new String[5];//"Title", "ISBN", "Check out date","Due date", "Copy num"
			if(e.getBookCopy() == null || e.getBookCopy().getBook() == null) {
				entry[0] = "";
				entry[1] = "";
				entry[4] = "";
			} else {
				entry[0] = e.getBookCopy().getBook().getTitle();
				entry[1] = e.getBookCopy().getBook().getIsbn();
				entry[4] = String.valueOf(e.getBookCopy().getCopyNum());
			}
			entry[2] = Util.formatMMDDYYYY(e.getCheckoutDate());
			entry[3] = Util.formatMMDDYYYY(e.getDueDate());
			data.add(entry);
		}
		model.setTableValues(data);	
	}	
	// --------------------------------------------------------------------
	
	private void searchMember() {
		try {
			String memberId = memberID.getText().trim();
			libraryMember = ci.searchMember(memberId);
			firstName.setText(libraryMember.getFirstName());
			lastName.setText(libraryMember.getLastName());
			phoneNumber.setText(libraryMember.getTelephone()); 
			Address addr = libraryMember.getAddress();
			if(addr != null) {
				street.setText(addr.getStreet());
				city.setText(addr.getCity());
				zip.setText(addr.getZip());
				state.setText(addr.getState());
			}
			

			setValues(model);
			table.updateUI();
			printEntries();
			
			/*
			String fname = firstName.getText();
			String lname = lastName.getText();
			String tel = phoneNumber.getText();
			address = new Address(street.getText(), city.getText(), state.getText(), zip.getText());
			libraryMember = new LibraryMember(memberId, fname, lname, tel, address);
			//validate
			RuleSet ruleSet = RuleSetFactory.getRuleSet(this);
			ruleSet.applyRules(this);
			
			Util.showMessage(this, "Member added!");
			*/
		} catch (SearchMemberException e) {
			Util.showMessage(this, e.getMessage());
			resetForm();
		}
	}
	
	
	//Search part
	private void defineMiddlePanelRow() {
		middlePanelRow1 = new JPanel();
		middlePanelRow1.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		
		//Member ID
		JPanel panelMemIDLbl = new JPanel();
		JLabel label4 = new JLabel("Member ID");
		panelMemIDLbl.add(label4);

		memberID = new JTextField();
        memberID.setColumns(10);
        JPanel panelMemFld = new JPanel();
        panelMemFld.add(memberID);
        
        
        searchMember = new JButton("Search");
        addSearchMemberButtonListener(searchMember);
        
        middlePanelRow1.add(panelMemIDLbl);
        middlePanelRow1.add(panelMemFld);
        middlePanelRow1.add(searchMember);

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
		
		//Member ID
		/*
		JPanel panelMemIDLbl = new JPanel();
		JLabel label4 = new JLabel("Member ID");
		panelMemIDLbl.add(label4);

		memberID = new JTextField();
        memberID.setColumns(10);
        JPanel panelMemFld = new JPanel();
        panelMemFld.setSize(500, 50);
        panelMemFld.add(memberID);
        */
        //First Name
		JPanel panelFnLbl = new JPanel();
		JLabel label6 = new JLabel("First Name");
		panelFnLbl.add(label6);
		firstName = new JTextField();
        firstName.setColumns(10);
        JPanel panelFnFld = new JPanel(); 
        panelFnFld.add(firstName);
        

        //Last Name
        JPanel panelLnLbl = new JPanel();
		JLabel lblLn = new JLabel("Last Name");
		lblLn.setSize(180, 30);
		panelLnLbl.add(lblLn);

        //---- Last name ----
		lastName = new JTextField();
        lastName.setColumns(10);
        JPanel panelLnFld = new JPanel(); 
        panelLnFld.add(lastName);
        

//        leftTextPanel11.add(panelMemIDLbl, BorderLayout.NORTH);
//        leftTextPanel11.add(panelMemFld, BorderLayout.CENTER);

		leftTextPanel12.add(panelFnLbl, BorderLayout.NORTH);
        leftTextPanel12.add(panelFnFld, BorderLayout.CENTER);
        
        //----------------------
		JPanel leftTextPanel21 = new JPanel();
		leftTextPanel21.setLayout(new BorderLayout());
		JPanel leftTextPanel22 = new JPanel();	
		leftTextPanel22.setLayout(new BorderLayout());
		


		//Phone number
		JPanel panelTelLbl = new JPanel();
		JLabel lblTel = new JLabel("Phone number");
		panelTelLbl.add(lblTel);

        //---- memberID3 ----
		phoneNumber = new JTextField();
        phoneNumber.setColumns(10);
        JPanel panelTelFld = new JPanel(); 
        panelTelFld.add(phoneNumber);

        
        leftTextPanel21.add(panelLnLbl, BorderLayout.NORTH);
        leftTextPanel21.add(panelLnFld, BorderLayout.CENTER);

		leftTextPanel22.add(panelTelLbl, BorderLayout.NORTH);
        leftTextPanel22.add(panelTelFld, BorderLayout.CENTER);
        
		
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
		
        JPanel panelStreetLbl = new JPanel();
        panelStreetLbl.setLayout(new FlowLayout());
        
        //Street
        JLabel label5 = new JLabel("Street");
        label5.setSize(180, 30);
        panelStreetLbl.add(label5);
        
        JPanel panelStreetFld = new JPanel();
        street = new JTextField();
        street.setColumns(10);
        panelStreetFld.add(street);        
        
        //City
        JPanel panelCityLbl = new JPanel();
        panelCityLbl.setLayout(new FlowLayout());
        JLabel label7 = new JLabel("City");
        panelCityLbl.add(label7);

        JPanel panelCityFld = new JPanel();
        city = new JTextField();
        city.setColumns(10);
        panelCityFld.add(city);
        
        //Zip
        JPanel panelZipLbl = new JPanel();
        panelZipLbl.setLayout(new FlowLayout());

        JLabel lblZip = new JLabel("Zip");
        panelZipLbl.add(lblZip);

        JPanel panelZipFld = new JPanel();
        zip = new JTextField();
        zip.setColumns(10);
        panelZipFld.add(zip);
        
        //State
        JPanel panelStateLbl = new JPanel();
        panelStateLbl.setLayout(new FlowLayout());
        JLabel lblState = new JLabel("State");
        panelStateLbl.add(lblState);

            //---- state ----
        JPanel panelStateFld = new JPanel();
        state = new JTextField();
        state.setColumns(10);
        panelStateFld.add(state);
        
        
		JPanel rightTextPanel11 = new JPanel();
		rightTextPanel11.setLayout(new BorderLayout());
		JPanel rightTextPanel12 = new JPanel();
		rightTextPanel12.setLayout(new BorderLayout());
		
        rightTextPanel11.add(panelStreetLbl, BorderLayout.NORTH);
        rightTextPanel11.add(panelStreetFld, BorderLayout.CENTER);

		rightTextPanel12.add(panelCityLbl, BorderLayout.NORTH);
        rightTextPanel12.add(panelCityFld, BorderLayout.CENTER);
        
        //----------------------
		JPanel rightTextPanel21 = new JPanel();
		rightTextPanel21.setLayout(new BorderLayout());
		JPanel rightTextPanel22 = new JPanel();	
		rightTextPanel22.setLayout(new BorderLayout());
        
        
        rightTextPanel21.add(panelZipLbl, BorderLayout.NORTH);
        rightTextPanel21.add(panelZipFld, BorderLayout.CENTER);

		rightTextPanel22.add(panelStateLbl, BorderLayout.NORTH);
        rightTextPanel22.add(panelStateFld, BorderLayout.CENTER);
        
		
		rightTextPanel1.add(rightTextPanel11);
		rightTextPanel1.add(rightTextPanel12);
		rightTextPanel2.add(rightTextPanel21);
		rightTextPanel2.add(rightTextPanel22);
		rightTextPanel.add(rightTextPanel1);
		rightTextPanel.add(rightTextPanel2);
	}


	
	private void addSearchMemberButtonListener(JButton butn) {
		butn.addActionListener(evt -> searchMember()
		);
	}
	

	
	public void resetForm() {
		memberID.setText("");
		firstName.setText("");
		lastName.setText("");
		phoneNumber.setText("");
		street.setText("");
		city.setText("");
		state.setText("");
		zip.setText("");
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

	public LibraryMember getLibraryMember() {
		return libraryMember;
	}

	private void printEntries() {
		System.out.printf("---------------------------------------------------------------------------------------------%n");
		System.out.printf("");
		System.out.printf("%-40s %n", " Member ID: " + libraryMember.getMemberId());
		System.out.printf("");
		System.out.printf("---------------------------------------------------------------------------------------------%n");
		System.out.printf("| %-40s | %-10s | %-15s | %-15s | %n", "Book title", "ISBN", "Check out date","Due date","Copy");
		System.out.printf("---------------------------------------------------------------------------------------------%n");

		
		if(libraryMember.getRecord() == null)
			return;
		List<CheckoutRecordEntry> recordEntries = libraryMember.getRecord().getRecord();
		
		for (CheckoutRecordEntry e : recordEntries) {
			String[] entry = new String[5];//"Title", "ISBN", "Check out date","Due date", "Copy num"
			if(e.getBookCopy() == null || e.getBookCopy().getBook() == null) {
				entry[0] = "";
				entry[1] = "";
				entry[4] = "";
			} else {
				entry[0] = e.getBookCopy().getBook().getTitle();
				entry[1] = e.getBookCopy().getBook().getIsbn();
				entry[4] = String.valueOf(e.getBookCopy().getCopyNum());
			}
			entry[2] = Util.formatMMDDYYYY(e.getCheckoutDate());
			entry[3] = Util.formatMMDDYYYY(e.getDueDate());
			System.out.printf("| %-40s | %-10s | %-15s | %-15s | %n", entry[0], entry[1], entry[2],entry[3],entry[4]);
		}
	
		System.out.printf("---------------------------------------------------------------------------------------------%n");
	}
}
