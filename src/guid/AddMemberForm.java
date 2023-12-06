package guid;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.Util;

public class AddMemberForm extends JFrame implements LibWindow{
	private static final long serialVersionUID = 1L;
	public static final AddMemberForm INSTANCE = new AddMemberForm();
	ControllerInterface ci = new SystemController();
	
	private boolean isInitialized = false;
	private JPanel mainPanel;
	private JLabel label1;
    private JPanel panel1;
    private JPanel panel4;
    private JLabel label4;
    private JTextField memberID;
    private JPanel panel3;
    private JLabel label3;
    private JTextField firstName;
    private JPanel panel2;
    private JLabel label2;
    private JTextField lastName;
    private JPanel panel6;
    private JLabel label6;
    private JTextField phoneNumber;
    private JPanel panel5;
    private JLabel label5;
    private JTextField state;
    private JPanel panel7;
    private JLabel label7;
    private JTextField city;
    private JPanel panel8;
    private JLabel label8;
    private JTextField zip;
    private JPanel panel9;
    private JLabel label9;
    private JTextField street;
    private JButton addNewMember;
    private JPanel lowerPanel;
    private JPanel panelR1;
    private JPanel panelR2;
    
	public void init() {
		mainPanel = new JPanel();
        label1 = new JLabel();
        panel1 = new JPanel();
        panel4 = new JPanel();
        label4 = new JLabel();
        memberID = new JTextField();
        panel3 = new JPanel();
        label3 = new JLabel();
        firstName = new JTextField();
        panel2 = new JPanel();
        label2 = new JLabel();
        lastName = new JTextField();
        panel6 = new JPanel();
        label6 = new JLabel();
        phoneNumber = new JTextField();
        panel5 = new JPanel();
        label5 = new JLabel();
        state = new JTextField();
        panel7 = new JPanel();
        label7 = new JLabel();
        city = new JTextField();
        panel8 = new JPanel();
        label8 = new JLabel();
        zip = new JTextField();
        panel9 = new JPanel();
        label9 = new JLabel();
        street = new JTextField();
        addNewMember = new JButton();
        addAddMemberButtonListener(addNewMember);

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(8, 8));

        //---- label1 ----
        label1.setText("Add new member");
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(label1, BorderLayout.NORTH);

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout());

            //======== panel4 ========
            {
                panel4.setLayout(new FlowLayout());

                //---- label4 ----
                label4.setText("Member ID");
                panel4.add(label4);

                //---- memberID3 ----
                memberID.setColumns(10);
                panel4.add(memberID);

                //======== panel3 ========
                {
                    panel3.setLayout(new FlowLayout());

                    //---- label3 ----
                    label3.setText("First Name");
                    panel3.add(label3);

                    //---- firstName ----
                    firstName.setColumns(10);
                    panel3.add(firstName);
                }
                panel4.add(panel3);
            }
            panel1.add(panel4);

            //======== panel2 ========
            {
                panel2.setLayout(new FlowLayout());

                //---- label2 ----
                label2.setText("Last Name");
                panel2.add(label2);

                //---- lastName ----
                lastName.setColumns(10);
                panel2.add(lastName);

                //======== panel6 ========
                {
                    panel6.setLayout(new FlowLayout());

                    //---- label6 ----
                    label6.setText("Phone number");
                    panel6.add(label6);

                    //---- phoneNumber ----
                    phoneNumber.setColumns(10);
                    panel6.add(phoneNumber);
                }
                panel2.add(panel6);
            }
            panel1.add(panel2);

            //======== panel5 ========
            {
                panel5.setLayout(new FlowLayout());

                //---- label5 ----
                label5.setText("State");
                panel5.add(label5);

                //---- state ----
                state.setColumns(10);
                panel5.add(state);
            }
            panel1.add(panel5);

            //======== panel7 ========
            {
                panel7.setLayout(new FlowLayout());

                //---- label7 ----
                label7.setText("City");
                panel7.add(label7);

                //---- city ----
                city.setColumns(10);
                panel7.add(city);
            }
            panel1.add(panel7);

            //======== panel8 ========
            {
                panel8.setLayout(new FlowLayout());

                //---- label8 ----
                label8.setText("Zip");
                panel8.add(label8);

                //---- zip ----
                zip.setColumns(10);
                panel8.add(zip);
            }
            panel1.add(panel8);

            //======== panel9 ========
            {
                panel9.setLayout(new FlowLayout());

                //---- label9 ----
                label9.setText("street");
                panel9.add(label9);

                //---- street ----
                street.setColumns(10);
                panel9.add(street);
            }
            panel1.add(panel9);
        }
        contentPane.add(panel1, BorderLayout.NORTH);

        //---- addNewMember ----
        addNewMember.setText("Add new member");
        contentPane.add(addNewMember, BorderLayout.CENTER);
        defineLowerPanel();
        contentPane.add(lowerPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getOwner());
	}

	public void defineLowerPanel() {
		
		JButton backToMainButn = new JButton("<= Back to Main");
		backToMainButn.addActionListener(new BackToMainListener());
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));;
		lowerPanel.add(backToMainButn);
	}
	
	private void addAddMemberButtonListener(JButton butn) {
		butn.addActionListener(evt -> addNewMember()
		);
	}
	
	private void addNewMember() {
		
		String memberId = memberID.getText();
		if(memberID == null || "".equals(memberID)) {
			Util.showMessage(this, "Member ID cannot be bank!");
			return;
		}
		String fname = firstName.getText();
		String lname = lastName.getText();
		String tel = phoneNumber.getText();
		Address address = new Address(street.getText(), city.getText(), state.getText(), zip.getText());
		LibraryMember member = new LibraryMember(memberId, fname, lname, tel, address);
		ci.saveNewMember(member);
		Util.showMessage(this, "Member added!");
		
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
	
}
