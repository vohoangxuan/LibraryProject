package guid;

import business.*;
import librarysystem.LibWindow;
import librarysystem.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class BookDetailForm extends JFrame implements LibWindow {
    public static String title = "Book Detail";

    public ControllerInterface bi = new SystemController();
    private JLabel headerLabel = new JLabel(title);

    private JPanel leftAlignPanel = new JPanel();
    private JPanel rightAlignPanel = new JPanel();
    private JPanel dataTablePanel = new JPanel();

    private JLabel isbnLabel = new JLabel("ISBN");
    private JTextField isbnText = new JTextField(17);

    private JLabel titleLabel = new JLabel("Title");
    private JTextField titleText = new JTextField(17);

    private JLabel maxCheckOutLabel = new JLabel("Max Check Out Length");
    private JTextField maxCheckOutText = new JTextField(17);

    private JPanel middlePanel = new JPanel(new FlowLayout());
    private JPanel middleWrapperPanel = new JPanel(new BorderLayout());

    private JPanel bottomPanel = new JPanel(new FlowLayout());
    private JPanel mainPanel = new JPanel(new BorderLayout());
    private boolean isInitialized = false;

    private JButton addBookCopyBtn;
    private JTable bookCopyTable;
    private JTable authorTable;
    private Book book;

    private DefaultTableModel copiesModel;
    protected BookDetailForm() {

    }
    @Override
    public void init() {
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setupLeftAlignPanel();
        setupRightAlignPanel();
        defineMiddleTableDataPanel();
        mainPanel.add(middleWrapperPanel, BorderLayout.CENTER);
        middleWrapperPanel.add(middlePanel, BorderLayout.NORTH);
        middleWrapperPanel.add(dataTablePanel, BorderLayout.CENTER);
        middleWrapperPanel.add(bottomPanel, BorderLayout.SOUTH);
        setTitle(title);
        isInitialized(true);
        add(mainPanel);
        pack();
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    private void setupLeftAlignPanel() {
        leftAlignPanel.setLayout(new BoxLayout(leftAlignPanel,BoxLayout.Y_AXIS));

        Component[] items = new Component[]{
                isbnLabel,
                titleLabel,
                maxCheckOutLabel
        };

        for (Component item: items) {
            leftAlignPanel.add(item);
            leftAlignPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        }

        middlePanel.add(leftAlignPanel);
    }

    private void setupRightAlignPanel() {
        rightAlignPanel.setLayout(new BoxLayout(rightAlignPanel,BoxLayout.Y_AXIS));
        Component[] items = new Component[]{
                isbnText,
                titleText,
                maxCheckOutText
        };
        for (Component item: items) {
            rightAlignPanel.add(item);
            rightAlignPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }
        isbnText.setText(book.getIsbn());
        isbnText.setEditable(false);
        titleText.setText(book.getTitle());
        titleText.setEditable(false);
        maxCheckOutText.setText(String.valueOf(book.getMaxCheckoutLength()));
        maxCheckOutText.setEditable(false);
        middlePanel.add(rightAlignPanel);
    }

    public void defineMiddleTableDataPanel() {
        dataTablePanel = new JPanel(new BorderLayout());

        JPanel featurePanel = new JPanel();
        featurePanel.setLayout(new BorderLayout());

        addBookCopyBtn = new JButton("Add Copy");
        featurePanel.add(addBookCopyBtn, BorderLayout.EAST);
        addBookCopyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BookCopy bookCopy = bi.addBookCopy(book);
                    refreshCopyTable(bookCopy);
                } catch (BookException err) {
                    throw new RuntimeException(err);
                }
            }

        });

        JButton backButton = new JButton("<== Back to List");
        backButton.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            AllBookIdsWindow.INSTANCE.init();
            AllBookIdsWindow.INSTANCE.pack();
            AllBookIdsWindow.INSTANCE.setVisible(true);
            Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
            this.dispose();
        });
        featurePanel.add(backButton, BorderLayout.WEST);

        dataTablePanel.add(featurePanel, BorderLayout.NORTH);

        java.util.List<BookCopy> data = Arrays.asList(book.getCopies());
        String[] columnNames = new String[]{"BookNumber","Is available"};

        String[][] dataTable = new String[data.size()][2];

        for(int i = 0; i< data.size();i++){
            BookCopy lm = data.get(i);

            dataTable[i] = new String[]{String.valueOf(lm.getCopyNum()), String.valueOf(lm.isAvailable())};

        }
        copiesModel = new DefaultTableModel(dataTable, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookCopyTable = new JTable(copiesModel);

        dataTablePanel.add(new JScrollPane(bookCopyTable), BorderLayout.CENTER);

        List<Author> authors = book.getAuthors();
        String[] columnNameAuthors = new String[]{"First Name","Last Name","Tel.","Bio","Street","City","Zipcode","State"};

        String[][] dataTableAuthor = new String[authors.size()][2];

        for(int i = 0; i< authors.size();i++){
            Author lm = authors.get(i);

            dataTableAuthor[i] = new String[]{lm.getFirstName(), lm.getLastName(), lm.getTelephone(), lm.getBio(),
                    lm.getAddress().getStreet(),lm.getAddress().getCity(),lm.getAddress().getZip(),lm.getAddress().getState()};

        }
        DefaultTableModel modelAuthor = new DefaultTableModel(dataTableAuthor, columnNameAuthors){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        authorTable = new JTable(modelAuthor);
        dataTablePanel.add(new JScrollPane(authorTable), BorderLayout.SOUTH);
    }

    private void refreshCopyTable(BookCopy bookCopy) {
        copiesModel.addRow(new String[]{String.valueOf(bookCopy.getCopyNum()), String.valueOf(bookCopy.isAvailable())});
        reloadCopiesUI();
        AllBookIdsWindow.INSTANCE.updateAvailableCountRecord(book);
    }

    private void reloadCopiesUI() {
        copiesModel.fireTableDataChanged();
    }

    public void setBook(String isbn) throws BookException {
        this.book = bi.getBookById(isbn);
    }
}
