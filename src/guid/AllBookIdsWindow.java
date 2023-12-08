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
	private static String title = "All Book IDs";
	public static final AllBookIdsWindow INSTANCE = new AllBookIdsWindow();
	ControllerInterface ci = new SystemController();
	private JTable table;
	private JPanel mainPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private TextArea textArea = new TextArea();
	private JButton addBookBtn;
	private JButton addCopyBtn;
	private JScrollPane scrollPane;
	private boolean isInitialized = false;
	private DefaultTableModel model;

	private AllBookIdsWindow() {}

	public void init() {
		if(isInitialized) {
			return;
		}
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineMiddlePanel();
		defineLowerPanel();
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		setTitle(title);
		getContentPane().add(mainPanel);
		isInitialized = true;
	}

	public void defineMiddlePanel() {
		middlePanel = new JPanel(new BorderLayout());
		List<Book> data = getAllBook();
		String[] columnNames = new String[]{"ISBN","Title","Max Checkout Length", "Available Count / Total Copies"};

		String[][] dataTable = new String[data.size()][5];

		for(int i = 0; i< data.size();i++){
			Book info = data.get(i);

			dataTable[i] = new String[]{info.getIsbn(), info.getTitle(), String.valueOf(info.getMaxCheckoutLength()), String.valueOf(info.availableCount()) + "/" + String.valueOf(info.totalCopies())};

		}
		model = new DefaultTableModel(dataTable, columnNames){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		middlePanel.add(scrollPane, BorderLayout.CENTER);
	}

	public void defineLowerPanel() {
		JPanel contentPanel = new JPanel(new BorderLayout());
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
		contentPanel.add(addBookBtn, BorderLayout.EAST);
		addCopyBtn = new JButton("Add Copy");
		addCopyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LibrarySystem.hideAllWindows();
				AddBookCopyForm.INSTANCE.init();
				AddBookCopyForm.INSTANCE.pack();
				AddBookCopyForm.INSTANCE.setVisible(true);
				Util.centerFrameOnDesktop(AddBookCopyForm.INSTANCE);
			}
		});
		contentPanel.add(addCopyBtn, BorderLayout.CENTER);
		JButton backBtn = new JButton("<== Back to Main");
		backBtn.addActionListener(new BackToMainListener());
		contentPanel.add(backBtn, BorderLayout.WEST);
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new BorderLayout());;
		lowerPanel.add(contentPanel, BorderLayout.CENTER);
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
		for(int i = 0; i < model.getRowCount(); i++) {
			String isbn = model.getValueAt(i, 0).toString();
			if(book.getIsbn().equals(isbn)) {
				model.setValueAt(book.availableCount() + "/" + book.totalCopies(), i, 3);
				reloadUI();
				return;
			}
		}
	}

	public void refreshTable(Book newBook){
		model.addRow(new String[]{newBook.getIsbn(), newBook.getTitle(), String.valueOf(newBook.getMaxCheckoutLength()), String.valueOf(newBook.availableCount()) + "/" + String.valueOf(newBook.totalCopies())});
		reloadUI();
	}

	@Override
	public boolean isInitialized() {
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
