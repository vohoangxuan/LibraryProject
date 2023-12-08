package guid;

import business.*;
import librarysystem.LibWindow;
import librarysystem.Util;
import rulesets.RuleException;
import rulesets.RuleSet;
import rulesets.RuleSetFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddBookForm extends JFrame implements LibWindow {
    private static final long serialVersionUID = 1L;
    public static String title = "Add New Book Form";
    public static final AddBookForm INSTANCE = new AddBookForm();
    private boolean isInitialized = false;
    private JPanel leftPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JPanel dataTablePanel = new JPanel();

    // For book: ISBN, Title, MaxCheckOut, Authors
    ControllerInterface bookI = new SystemController();

    private JLabel isbnLabel = new JLabel("ISBN");
    private JTextField isbnText = new JTextField(10);

    private JLabel titleLabel = new JLabel("Title");
    private JTextField titleText = new JTextField(10);

    private JLabel maxCheckOutLabel = new JLabel("Max Check Out Length");
    private JTextField maxCheckOutText = new JTextField(10);

    private JButton authorBtn;
    private JTextField authorText = new JTextField(10);

    private JPanel middlePanel = new JPanel(new FlowLayout());

    private JPanel bottomPanel = new JPanel(new FlowLayout());
    private JPanel mainPanel = new JPanel(new BorderLayout());

    private JButton addBookBtn;
    private JDialog dialog;
    private Book book;

    private JLabel numberOfCopiesLabel = new JLabel("Number of copies");
    private JTextField numberOfCopiesText = new JTextField(10);

    protected AddBookForm() {

    }

    public void init() {
        if(isInitialized) {
            return;
        }

        book = new Book("", "", 0, new ArrayList<Author>());
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        defineLeftPanel();
        defineRightPanel();
        defineMiddleTableDataPanel();
        mainPanel.add(middlePanel, BorderLayout.NORTH);
        mainPanel.add(dataTablePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
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

    private void defineLeftPanel() {
        authorBtn = new JButton("Authors");
        authorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAuthorSelectionDialog();
            }
        });
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        Component[] items = new Component[] {
                isbnLabel,
                titleLabel,
                maxCheckOutLabel,
                numberOfCopiesLabel,
                authorBtn
        };

        for(Component c : items) {
            leftPanel.add(c);
            leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        }

        middlePanel.add(leftPanel);
    }

    private void defineRightPanel() {
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        maxCheckOutText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                String value = numberOfCopiesText.getText();
                int l = value.length();
                if(e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
                    maxCheckOutText.setEditable(true);
                } else {
                    maxCheckOutText.setEditable(false);
                    JOptionPane.showMessageDialog(mainPanel, "Please enter the number");
                }
            }
        });
        numberOfCopiesText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                String value = numberOfCopiesText.getText();
                int l = value.length();
                if(e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
                    numberOfCopiesText.setEditable(true);
                } else {
                    numberOfCopiesText.setEditable(false);
                    JOptionPane.showMessageDialog(mainPanel, "Please enter the number");
                }

            }
        });

        Component[] items = new Component[] {
                isbnText,
                titleText,
                maxCheckOutText,
                numberOfCopiesText,
                authorText
        };
        for(Component c : items) {
            rightPanel.add(c);
            rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        }
        authorText.setEditable(false);
        middlePanel.add(rightPanel);
    }

    public void defineMiddleTableDataPanel() {
        dataTablePanel = new JPanel(new BorderLayout());

        JPanel featurePanel = new JPanel();
        featurePanel.setLayout(new FlowLayout());

        JButton backButton = new JButton("<== Back to Main");
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
                try {
                    RuleSet rules = RuleSetFactory.getRuleSet(AddBookForm.this);
                    rules.applyRules(AddBookForm.this);
                } catch(RuleException err) {
                    JOptionPane.showMessageDialog(mainPanel,err.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                }
                List<Author> newAuthors = new ArrayList<>();
                List<Author> authors = bookI.getAllAuthor();
                String[] selectedAuthorNames = authorText.getText().split(", ");

                for(String name : selectedAuthorNames) {
                    for(Author author : authors) {
                        if(author.getFullName().equals(name)) {
                            newAuthors.add(author);
                        }
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
        JButton goButton = new JButton("See List Book IDs");
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


    private void showAuthorSelectionDialog() {
        Author[] allAuthors = bookI.getAllAuthor().toArray(new Author[0]);
        dialog = new JDialog(this, "Select Authors", true);
        dialog.setLayout(new BorderLayout());
        List<JCheckBox> checkBoxes = new ArrayList<>();
        for (Author author : allAuthors) {
            JCheckBox checkBox = new JCheckBox(author.getFullName());
            checkBoxes.add(checkBox);
        }
        JPanel checkBoxPanel = new JPanel(new GridLayout(0, 1));
        for (JCheckBox checkBox : checkBoxes) {
            checkBoxPanel.add(checkBox);
        }

        JButton confirmButton = new JButton("OK");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> selectedAuthors = new ArrayList<>();
                for (JCheckBox checkBox : checkBoxes) {
                    if (checkBox.isSelected()) {
                        selectedAuthors.add(checkBox.getText());
                    }
                }

                authorText.setText(String.join(", ", selectedAuthors));

                dialog.dispose();
            }
        });
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(checkBoxPanel, BorderLayout.CENTER);
        contentPanel.add(confirmButton, BorderLayout.SOUTH);

        dialog.add(contentPanel);

        dialog.setSize(200, 150);
        dialog.setVisible(true);

    }


    private void clearTextFields() {
        maxCheckOutText.setText("");
        isbnText.setText("");
        titleText.setText("");
        numberOfCopiesText.setText("");
        authorText.setText("");
        book = new Book("","",0,new ArrayList<>());
        repaint();
    }

    public String getISBN() {
        return isbnText.getText().trim();
    }

    public String getTitle() {
        return titleText.getText().trim();
    }

    public String getMaxCheckOut() {
        return maxCheckOutText.getText().trim();
    }

    public String getNumberOfCopies() {
        return numberOfCopiesText.getText().trim();
    }

    public String getAuthorName() {
        return authorText.getText().trim();
    }
}
