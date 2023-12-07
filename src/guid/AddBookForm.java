package guid;

import business.*;
import librarysystem.LibWindow;
import librarysystem.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class AddBookForm extends JFrame implements LibWindow {
    private static final long serialVersionUID = 1L;
    public static String title = "Add New Book Form";
    public static final AddBookForm INSTANCE = new AddBookForm();
    private boolean isInitialized = false;
    private JLabel headerLabel = new JLabel(title);
    private JPanel leftAlignPanel = new JPanel();
    private JPanel rightAlignPanel = new JPanel();
    private JPanel dataTablePanel = new JPanel();

    // For book: ISBN, Title, MaxCheckOut
    ControllerInterface bookI = new SystemController();

    private JLabel isbnLabel = new JLabel("ISBN");
    private JTextField isbnText = new JTextField(10);

    private JLabel titleLabel = new JLabel("Title");
    private JTextField titleText = new JTextField(10);

    private JLabel maxCheckOutLabel = new JLabel("Max Check Out Length");
    private JTextField maxCheckOutText = new JTextField(10);

    private JPanel middlePanel = new JPanel(new FlowLayout());
    private JPanel middleWrapperPanel = new JPanel(new BorderLayout());

    private JTextField textArea;
    private JPanel bottomPanel = new JPanel(new FlowLayout());
    private JPanel mainPanel = new JPanel(new BorderLayout());

    private JButton addBookBtn;

    private JTable authorTable;
    private Book book;

    private JLabel numberOfCopiesLabel = new JLabel("Number of copies");
    private JTextField numberOfCopiesText = new JTextField(10);

    private JSplitPane splitPaneOuter;

    private JPanel bottomPPanel = new JPanel(new BorderLayout());

    // For Author
    ControllerInterface authorI = new SystemController();

    private List<Author> authors;

    DefaultTableModel modelAuthor;

    protected AddBookForm() {

    }

    public void init() {
        if(isInitialized) {
            return;
        }

        book = new Book("", "", 0, new ArrayList<Author>());
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateHeaderLabel();
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
        getContentPane().add(mainPanel);
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    private void updateHeaderLabel() {
        Util.adjustLabelFont(headerLabel, Util.DARK_BLUE, true);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(headerLabel);
        mainPanel.add(panel, BorderLayout.NORTH);
    }

    private void setupLeftAlignPanel() {
        leftAlignPanel.setLayout(new BoxLayout(leftAlignPanel, BoxLayout.Y_AXIS));

        Component[] items = new Component[] {
                isbnLabel,
                titleLabel,
                maxCheckOutLabel,
                numberOfCopiesLabel
        };

        for(Component c : items) {
            leftAlignPanel.add(c);
            leftAlignPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        }

        middlePanel.add(leftAlignPanel);
    }

    private void setupRightAlignPanel() {
        rightAlignPanel.setLayout(new BoxLayout(rightAlignPanel, BoxLayout.Y_AXIS));

        numberOfCopiesText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                String value = numberOfCopiesText.getText();
                int l = value.length();
                if(e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
                    numberOfCopiesText.setEditable(true);
                } else {
                    numberOfCopiesText.setEditable(false);
                }
            }
        });

        maxCheckOutText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                String value = numberOfCopiesText.getText();
                int l = value.length();
                if(e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
                    maxCheckOutText.setEditable(true);
                } else {
                    maxCheckOutText.setEditable(false);
                }
            }
        });

        Component[] items = new Component[] {
                isbnText,
                titleText,
                maxCheckOutText,
                numberOfCopiesText
        };
        for(Component c : items) {
            rightAlignPanel.add(c);
            rightAlignPanel.add(Box.createRigidArea(new Dimension(0,8)));
        }

        middlePanel.add(rightAlignPanel);
    }

    public void defineMiddleTableDataPanel() {
        dataTablePanel = new JPanel(new BorderLayout());

        authors = authorI.getAllAuthor();
        Object[] columnNameAuthors = new Object[]{"Is Author?","First Name","Last Name","Tel","Bio","Street","City","Zipcode","State"};

        Object[][] dataTableAuthor = new Object[authors.size()][2];

        for(int i = 0; i< authors.size();i++){
            Author lm = authors.get(i);

            dataTableAuthor[i] = new Object[]{false, lm.getFirstName(), lm.getLastName(), lm.getTelephone(), lm.getBio(),
                    lm.getAddress().getStreet(),lm.getAddress().getCity(),lm.getAddress().getZip(),lm.getAddress().getState()};

        }
        modelAuthor = new DefaultTableModel(dataTableAuthor, columnNameAuthors){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column == 0)
                    return true;
                return false;
            }
        };
        authorTable = new JTable(modelAuthor){
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Boolean.class;
                    default:
                        return String.class;
                }
            }
        };
        dataTablePanel.add(new JScrollPane(authorTable), BorderLayout.NORTH);

        JPanel featurePanel = new JPanel();
        featurePanel.setLayout(new FlowLayout());

        JButton backButton = new JButton("<- Back to List");
        backButton.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.init();
            LibrarySystem.INSTANCE.pack();
            LibrarySystem.INSTANCE.setVisible(true);
            Util.centerFrameOnDesktop(LibrarySystem.INSTANCE);
            this.dispose();
        });
        featurePanel.add(backButton);

        addBookBtn = new JButton("Save");
        featurePanel.add(addBookBtn);
        addBookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Author> newAuthors = new ArrayList<>();
                TableModel tm = authorTable.getModel();
                for(int i = 0; i < authors.size(); i++) {
                    boolean isCheck = Boolean.parseBoolean(tm.getValueAt(i, 0).toString());
                    if(isCheck) {
                        newAuthors.add(authors.get(i));
                    }
                }

                int numberOfCopies = Integer.parseInt(numberOfCopiesText.getText().trim());
                if(numberOfCopies > 1) {
                    for(int i = 1; i < numberOfCopies; i++) {
                        book.addCopy();
                    }
                }

                try {
                    Book newBook = bookI.addBook(isbnText.getText(), titleText.getText(), Integer.parseInt(maxCheckOutText.getText().trim()), newAuthors, List.of(book.getCopies()));
                    AllBookIdsWindow.INSTANCE.refreshTable(newBook);
                    clearTextFields();
                } catch(BookException err) {
                    throw new RuntimeException(err);
                }
            }
        });
        JButton goButton = new JButton("See All Books");
        goButton.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            AllBookIdsWindow.INSTANCE.init();
            AllBookIdsWindow.INSTANCE.pack();
            AllBookIdsWindow.INSTANCE.setVisible(true);
            Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
            this.dispose();
        });
        featurePanel.add(goButton);
        dataTablePanel.add(featurePanel, BorderLayout.SOUTH);
    }

    private void clearTextFields() {
        maxCheckOutText.setText("");
        isbnText.setText("");
        titleText.setText("");
        numberOfCopiesText.setText("");
        book = new Book("","",0,new ArrayList<>());

        for (int i = 0; i < modelAuthor.getRowCount(); i++){
            modelAuthor.setValueAt(false,i,0);
        }
        modelAuthor.fireTableDataChanged();
        authorTable.repaint();
        authorTable.updateUI();
        repaint();
    }
}
