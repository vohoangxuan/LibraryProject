package guid;

import business.*;
import librarysystem.LibWindow;
import librarysystem.Util;
import rulesets.RuleSet;
import rulesets.RuleSetFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CheckoutForm extends JFrame implements LibWindow {
    private static final long serialVersionUID = 1L;
    public static String title = "Checkout Book Form";
    public static final CheckoutForm INSTANCE = new CheckoutForm();
    private boolean isInitialized = false;
    private JLabel headerLabel = new JLabel(title);
    private JPanel leftAlignPanel = new JPanel();
    private JPanel rightAlignPanel = new JPanel();
    private JPanel dataTablePanel = new JPanel();

    ControllerInterface cf = new SystemController();

    private JLabel memberLabel = new JLabel("Member ID");
    private JTextField memberText = new JTextField(10);

    private JLabel isbnLabel = new JLabel("ISBN");
    private JTextField isbnText = new JTextField(10);

    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); 
    String defaultDate = formatter.format(LocalDate.now());
    private JLabel checkout = new JLabel("Checkout date");
    private JTextField checkoutDate = new JFormattedTextField(df);

    private JLabel dueDate = new JLabel("Due after (days)");
    private JTextField dueDateText = new JTextField("7");

    private JPanel middlePanel = new JPanel(new FlowLayout());
    private JPanel middleWrapperPanel = new JPanel(new BorderLayout());

    private JPanel bottomPanel = new JPanel(new FlowLayout());
    private JPanel mainPanel = new JPanel(new BorderLayout());

    private JButton checkoutBtn;

    // For Author
    ControllerInterface authorI = new SystemController();

    DefaultTableModel modelAuthor;

    protected CheckoutForm() {

    }

    public void init() {
        if(isInitialized) {
            return;
        }
        setSize(600, 400);
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
        checkoutDate.setText(defaultDate);
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
                memberLabel,    
                isbnLabel,
                checkout,
                dueDate
        };

        for(Component c : items) {
            leftAlignPanel.add(c);
            leftAlignPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        }

        middlePanel.add(leftAlignPanel);
    }

    private void setupRightAlignPanel() {
        rightAlignPanel.setLayout(new BoxLayout(rightAlignPanel, BoxLayout.Y_AXIS));

        Component[] items = new Component[] {
                memberText,
                isbnText,
                checkoutDate,
                dueDateText
        };
        for(Component c : items) {
            rightAlignPanel.add(c);
            rightAlignPanel.add(Box.createRigidArea(new Dimension(0,8)));
        }

        middlePanel.add(rightAlignPanel);
    }

    public void defineMiddleTableDataPanel() {
        dataTablePanel = new JPanel(new BorderLayout());

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

        checkoutBtn = new JButton("Checkout");
        featurePanel.add(checkoutBtn);
        checkoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String isbnNumb = isbnText.getText().trim();
                String memId = memberText.getText().trim();
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate ld = LocalDate.parse(checkoutDate.getText(), formatter);

                try{
                    RuleSet rules = RuleSetFactory.getRuleSet(CheckoutForm.this);
                    rules.applyRules(CheckoutForm.this);
                    int due = Integer.parseInt(dueDateText.getText());
                    cf.addCheckoutEntry(memId, isbnNumb, ld, due);

                    Book book = cf.getBookById(isbnNumb);
                    AllBookIdsWindow.INSTANCE.updateAvailableCountRecord(book);
                    clearTextFields();
                }
                catch(Exception err) {
                    JOptionPane.showMessageDialog(CheckoutForm.this, err.getMessage()); 
                }
            }
        });
        dataTablePanel.add(featurePanel, BorderLayout.NORTH);
    }

    private void clearTextFields() {
        memberText.setText("");
        isbnText.setText("");
        checkoutDate.setText("");
        dueDateText.setText("7");
        checkoutDate.setText(defaultDate);
        repaint();
    }

    public String getMemberText() {
        return memberText.getText();
    }

    public String getIsbnText() {
        return isbnText.getText();
    }

    public String getCheckoutDateText() {
        return checkoutDate.getText();
    }

    public String getDueDateText() {
        return dueDateText.getText();
    }
}

