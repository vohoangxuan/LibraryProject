package guid;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
import business.BookCopy;
import business.BookException;
import business.CheckoutRecordEntry;
import business.ControllerInterface;
import business.LibraryMember;
import business.SearchMemberException;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.Util;

public class SearchOverDueBookForm extends JFrame implements LibWindow{
	private static final long serialVersionUID = 1L;
	public static final SearchOverDueBookForm INSTANCE = new SearchOverDueBookForm();
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
	
    private JTextField isbn;
    private JPanel lowerPanel;
    private JPanel lowerPanelRow1;
    private JPanel lowerPanelRow2;
    private JButton searchOvd;
    JTable table;
	JScrollPane scrollPane;
	
    //table data and config
	private final String[] DEFAULT_COLUMN_HEADERS = {"Book title", "ISBN", "Copy","Member ID","Member name", "Due date"};
	
	private LibraryMember libraryMember;
	
	public void init() {
		if(isInitialized)
			return;
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
		JLabel searchLabel = new JLabel("Search overdue entries");
		Util.adjustLabelFont(searchLabel, Color.BLUE.darker(), true);
		intPanel.add(searchLabel, BorderLayout.CENTER);
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
//		middlePanel.add(leftTextPanel, BorderLayout.CENTER);
//		middlePanel.add(rightTextPanel, BorderLayout.SOUTH);
		
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
		createCustomColumns(table, 800,
				new float []{0.25f, 0.15f, 0.15f, 0.15f, 0.15f, 0.15f}, DEFAULT_COLUMN_HEADERS);
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


	private void setValues(CustomTableModel model, HashMap<BookCopy, LibraryMember> map) {
		model.removeAll();
		List<String[]> data = new ArrayList<String[]>();
		if(map == null | map.isEmpty()) 
			return;
		
		
		
		for (BookCopy c: map.keySet()) {
			if(c.getBook() == null)
				continue;
			LibraryMember member = map.get(c);
			if(member == null)
				continue;
			
			List<CheckoutRecordEntry> entries = member.getRecord().getRecord();
			for(CheckoutRecordEntry e:entries) {
				String[] entry = new String[6];//"Title", "ISBN", "Check out date","Due date", "Copy num"
	
				entry[0] = c.getBook().getTitle();
				entry[1] = c.getBook().getIsbn();
				entry[2] = String.valueOf(c.getCopyNum());
				
				
				entry[3] = member.getMemberId();
				entry[4] = member.getFirstName() + " " + member.getLastName();
				entry[5] = Util.formatMMDDYYYY(e.getDueDate());
				data.add(entry);
			}
			
		}
		model.setTableValues(data);	
	}	
	// --------------------------------------------------------------------
	
	private void search() {
		String isbnText = isbn.getText().trim();
		if("".equals(isbn)) {
			Util.showMessage(this, "Please input Isbn");
			return;
		}
		HashMap<BookCopy, LibraryMember> map = new HashMap<BookCopy, LibraryMember>();
		try {
			map = ci.findOverdueEntries(isbnText, LocalDate.now());
		} catch (BookException e) {
			Util.showMessage(this, e.getMessage());
		}

		setValues(model, map);
		table.updateUI();
		printEntries();

	}
	
	
	//Search part
	private void defineMiddlePanelRow() {
		middlePanelRow1 = new JPanel();
		middlePanelRow1.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		
		//Isbn ID
		JPanel panelIsbnLbl = new JPanel();
		JLabel label4 = new JLabel("Isbn");
		panelIsbnLbl.add(label4);

		isbn = new JTextField();
		isbn.setColumns(10);
        JPanel panelMemFld = new JPanel();
        panelMemFld.add(isbn);
        
        
        searchOvd = new JButton("Search ovd");
        addSearchButtonListener(searchOvd);
        
        middlePanelRow1.add(panelIsbnLbl);
        middlePanelRow1.add(panelMemFld);
        middlePanelRow1.add(searchOvd);

	}
	private void defineLeftTextPanel() {}
	private void defineRightTextPanel() {}


	
	private void addSearchButtonListener(JButton butn) {
		butn.addActionListener(evt -> search()
		);
	}
	

	
	public void resetForm() {
		isbn.setText("");
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
			resetForm();
			SearchMemberForm.INSTANCE.dispose();
			model.removeAll();
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
    		
		}
	}

	public LibraryMember getLibraryMember() {
		return libraryMember;
	}

	private void printEntries() {}
}
