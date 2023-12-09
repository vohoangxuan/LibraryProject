package guid;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import business.CheckoutRecordEntry;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.Util;

public class AllMembersForm extends JFrame implements LibWindow{
	private static final long serialVersionUID = 1L;
	public static final AllMembersForm INSTANCE = new AllMembersForm();
	ControllerInterface ci = new SystemController();
	CustomTableModel model;
	private boolean isInitialized = false;
	private JPanel mainPanel;
	private JPanel upperHalf;
	private JPanel middleHalf;
	private JPanel lowerHalf;
	private JPanel topPanel;
	private JPanel middlePanel;
	
    private JPanel lowerPanel;
    private JPanel lowerPanelRow2;
    JTable table;
	JScrollPane scrollPane;
	private String parentForm;
	
	//table data and config
	private final String[] DEFAULT_COLUMN_HEADERS = {"Member ID", "First name", "Last name","Phone number"};
	
	private LibraryMember libraryMember;
	
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
		
		if(!"AddMemberForm".equals(parentForm)) {
			JButton backButton = new JButton("<= Back to Main");
			backButton.addActionListener(new BackToMainListener());
			lowerHalf.add(backButton);
		} else {
			JButton closeButton = new JButton("Close");
			closeButton.addActionListener(new CloseListener());
			lowerHalf.add(closeButton);
		}
	}
	private void defineTopPanel() {
		topPanel = new JPanel();
		JPanel intPanel = new JPanel(new BorderLayout());
		intPanel.add(Box.createRigidArea(new Dimension(0,20)), BorderLayout.NORTH);
		JLabel loginLabel = new JLabel("All members");
		Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
		intPanel.add(loginLabel, BorderLayout.CENTER);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(intPanel);
		
	}
	
	
	
	private void defineMiddlePanel() {
		middlePanel=new JPanel();
//		middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		middlePanel.setLayout(new BorderLayout());
		/*
		middlePanel.add(middlePanelRow1, BorderLayout.NORTH);
		middlePanel.add(leftTextPanel, BorderLayout.CENTER);
		middlePanel.add(rightTextPanel, BorderLayout.SOUTH);
		*/
	}
	
	private void defineLowerPanel() {
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new BorderLayout());
		
		defineLowerPanelRow1();
		lowerPanelRow2 = new JPanel();
		
		lowerPanel.add(scrollPane, BorderLayout.NORTH);//lowerPanelRow1
		lowerPanel.add(lowerPanelRow2, BorderLayout.CENTER);
	
	}

	//table
	public void defineLowerPanelRow1(){
		createTableAndTablePane();
	}
	
	// --------------------------------------------------------------------
	private void createTableAndTablePane() {
		updateModel(); 
		table = new JTable(model);
		createCustomColumns(table, 700,
				new float []{0.1f, 0.2f, 0.2f, 0.15f}, DEFAULT_COLUMN_HEADERS);
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(
				new Dimension(700, 300));
		scrollPane.getViewport().add(table);
	}

	private void updateModel() {
		if(model == null) {
			model = new CustomTableModel();
		}
		setValues(model);
		if(table != null)
			table.updateUI();
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

	
	private void setValues(CustomTableModel model) {
		model.removeAll();
		List<LibraryMember> list = ci.getAllMembers();
		List<String[]> data = new ArrayList<String[]>();
		
		for(LibraryMember m: list) {
			if(m == null) 
				continue;

			String[] entry = new String[4];
			entry[0] = m.getMemberId();
			entry[1] = m.getFirstName();
			entry[2] = m.getLastName();
			entry[3] = m.getTelephone();
			data.add(entry);
		}
		model.setTableValues(data);	
	}	
	// --------------------------------------------------------------------
	

	
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

	class CloseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			AllMembersForm.INSTANCE.setVisible(false);
    		
		}
	}
	
	public LibraryMember getLibraryMember() {
		return libraryMember;
	}

	private void printEntries() {}
    public String getParentForm() {
		return parentForm;
	}

	public void setParentForm(String parentForm) {
		this.parentForm = parentForm;
	}	
	
}
