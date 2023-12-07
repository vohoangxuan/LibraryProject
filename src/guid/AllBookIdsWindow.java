package guid;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import business.Book;
import business.BookException;
import business.ControllerInterface;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.Util;


public class AllBookIdsWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static final AllBookIdsWindow INSTANCE = new AllBookIdsWindow();
    ControllerInterface ci = new SystemController();
	private JTable table;
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private TextArea textArea = new TextArea();
	private JButton addBookBtn;
	private boolean isInitialized = false;
	private DefaultTableModel model;
	//Singleton class
	private AllBookIdsWindow() {}
	
	public void init() {
		if(isInitialized) {
			return;
		}
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTopPanel();
		defineMiddlePanel();
		defineLowerPanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized = true;
	}
	
	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("All Books");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
	}
	
	public void defineMiddlePanel() {
		middlePanel = new JPanel(new BorderLayout());
		createTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					JTable target = (JTable) e.getSource();
					int row = target.getSelectedRow();
					TableModel tm = table.getModel();
					String isbn = tm.getValueAt(row, 0).toString();

					EventQueue.invokeLater(() -> {
						BookDetailForm bookDetailForm = new BookDetailForm();
						try {
							bookDetailForm.setBook(isbn);
						} catch (BookException err) {
							throw new RuntimeException(err);
						}
						bookDetailForm.init();
						bookDetailForm.setVisible(true);
					});
				}
			}
		});
		middlePanel.add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	public void defineLowerPanel() {

		JPanel featurePanel = new JPanel(new BorderLayout());

		addBookBtn = new JButton("Add Book");
		addBookBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LibrarySystem.hideAllWindows();
				AddBookForm.INSTANCE.init();
				AddBookForm.INSTANCE.pack();
				AddBookForm.INSTANCE.setVisible(true);
				Util.centerFrameOnDesktop(AddBookForm.INSTANCE);
			}
		});
		featurePanel.add(addBookBtn, BorderLayout.EAST);

		JButton backBtn = new JButton("<- Back to Main");
		backBtn.addActionListener(new BackToMainListener());
		featurePanel.add(backBtn, BorderLayout.WEST);
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new BorderLayout());;
		lowerPanel.add(featurePanel, BorderLayout.CENTER);
	}


	class BackToMainListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);

		}
	}

	private List<Book> getAllBook() {
		return ci.getAllBook();
	}

	public void setData(String data) {
		textArea.setText(data);
	}

	public void updateAvailableCountRecord(Book book) {
		createTable();
		for(int i = 0; i < model.getRowCount(); i++) {
			String isbn = model.getValueAt(i, 0).toString();
			if(book.getIsbn().equals(isbn)) {
				model.setValueAt(book.availableCount(), i, 3);
				reloadUI();
				return;
			}
		}
	}
	public void createTable() {
		List<Book> data = getAllBook();
		String[] columnNames = new String[]{"ISBN","Title","Max Checkout Length", "Available Count"};

		String[][] dataTable = new String[data.size()][5];

		for(int i = 0; i< data.size();i++){
			Book lm = data.get(i);

			dataTable[i] = new String[]{lm.getIsbn(), lm.getTitle(), String.valueOf(lm.getMaxCheckoutLength()), String.valueOf(lm.availableCount())};

		}
		model = new DefaultTableModel(dataTable, columnNames){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
	}

	public void refreshTable(Book newBook){
		createTable();
		model.addRow(new String[]{newBook.getIsbn(), newBook.getTitle(), String.valueOf(newBook.getMaxCheckoutLength()), String.valueOf(newBook.availableCount())});
		reloadUI();
	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
		
	}

	private void reloadUI() {
		model.fireTableDataChanged();
		table.repaint();
		table.updateUI();
		repaint();
	}
}
