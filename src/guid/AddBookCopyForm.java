package guid;

import business.Book;
import business.BookException;
import business.ControllerInterface;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookCopyForm extends JFrame implements LibWindow {
    public static final AddBookCopyForm INSTANCE = new AddBookCopyForm();
    private boolean isInitialized = false;
    private JTextArea textArea;

    private ControllerInterface bi = new SystemController();
    private JPanel bottomPPanel = new JPanel(new BorderLayout());

    private JSplitPane splitPaneOuter;
    public static String title = "Add Book Copy";

    private JLabel headerLabel = new JLabel(title);

    private JPanel leftAlignPanel = new JPanel();
    private JPanel rightAlignPanel = new JPanel();

    private JLabel isbnLabel = new JLabel("ISBN");
    private JTextField isbnText = new JTextField(10);

    private JPanel middlePanel = new JPanel(new FlowLayout());
    private JPanel middleWrapperPanel = new JPanel(new BorderLayout());

    private JButton addBtn = new JButton("Add Copy");
    private JPanel bottomPanel = new JPanel();
    private JPanel mainPanel = new JPanel(new BorderLayout());

    private AddBookCopyForm() {

    }

    @Override
    public void init() {
        if(isInitialized) {
            return;
        }

        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setupLeftAlignPanel();
        setupRightAlignPanel();
        setupBtn();
        mainPanel.add(middleWrapperPanel, BorderLayout.CENTER);
        middleWrapperPanel.add(middlePanel, BorderLayout.NORTH);
        setTitle(title);
        isInitialized(true);
        getContentPane().add(mainPanel);
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
        Component item = isbnLabel;
        leftAlignPanel.add(item);
        leftAlignPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        middlePanel.add(leftAlignPanel);
    }

    private void setupRightAlignPanel() {
        rightAlignPanel.setLayout(new BoxLayout(rightAlignPanel,BoxLayout.Y_AXIS));
        Component item = isbnText;
        rightAlignPanel.add(item);
        rightAlignPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        middlePanel.add(rightAlignPanel);
    }

    private void setupBtn() {
        JButton backBtn = new JButton("<== Back to Main");

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LibrarySystem.hideAllWindows();
                LibrarySystem.INSTANCE.setVisible(true);
            }
        });
        bottomPanel.add(backBtn);

        addBtn.setSize(100, 30);
        addBtn.addActionListener((evt) -> {
            try {
                Book book = bi.addBookCopyByISBN(getISBN());
                JPanel messagePanel = new JPanel(new BorderLayout());
                JLabel messageLabel = new JLabel("Copy of the book was added successfully");
                messagePanel.add(messageLabel);
                JOptionPane.showMessageDialog(this, messagePanel, "Success", JOptionPane.INFORMATION_MESSAGE);
                AllBookIdsWindow.INSTANCE.updateAvailableCountRecord(book);
            } catch(BookException e) {
                bi.showError(e.getMessage());
                JOptionPane.showMessageDialog(this,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        bottomPanel.add(addBtn);
        middleWrapperPanel.add(bottomPanel, BorderLayout.CENTER);
    }

    public String getISBN() {
        return isbnText.getText().trim();
    }
}
