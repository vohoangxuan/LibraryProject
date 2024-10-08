package guid;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import business.ControllerInterface;
import business.LoginException;
import business.SystemController;
import dataaccess.User;
import librarysystem.LibWindow;
import librarysystem.Util;
import rulesets.RuleException;
import rulesets.RuleSet;
import rulesets.RuleSetFactory;


public class LoginForm extends JInternalFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static final LoginForm INSTANCE = new LoginForm();
    private User loginUser;
	private boolean isInitialized = false;
	
	private JPanel mainPanel;
	private JPanel upperHalf;
	private JPanel middleHalf;
	private JPanel lowerHalf;
	
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private JPanel leftTextPanel;
	private JPanel rightTextPanel;
	
	private JTextField username;
	private JTextField password;
	private JLabel label;
	private JButton loginButton;
	
	
	ControllerInterface ci = new SystemController();
	
	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	private JTextField messageBar = new JTextField();
	public void clear() {
		messageBar.setText("");
	}
	
	/* This class is a singleton */
    private LoginForm () {}
    
    public void init() {    
    	if(isInitialized())
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
//    	pack();
    	setSize(647, 450);


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
//    		middleHalf.add(s, BorderLayout.SOUTH);
    		
    	}
    	private void defineLowerHalf() {

    		lowerHalf = new JPanel();
    		lowerHalf.setLayout(new FlowLayout(FlowLayout.LEFT));
    		
    		JButton backButton = new JButton("<= Back to Main");
    		addBackButtonListener(backButton);
//    		lowerHalf.add(backButton);
    		
    	}
    	private void defineTopPanel() {
    		topPanel = new JPanel();
    		JPanel intPanel = new JPanel(new BorderLayout());
    		intPanel.add(Box.createRigidArea(new Dimension(0,20)), BorderLayout.NORTH);
    		JLabel loginLabel = new JLabel("Login");
    		Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
    		intPanel.add(loginLabel, BorderLayout.CENTER);
    		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    		topPanel.add(intPanel);
    		
    	}
    	
    	
    	
    	private void defineMiddlePanel() {
    		middlePanel=new JPanel();
    		middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    		defineLeftTextPanel();
    		defineRightTextPanel();
    		middlePanel.add(leftTextPanel);
    		middlePanel.add(rightTextPanel);
    	}
    	private void defineLowerPanel() {
    		lowerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    		loginButton = new JButton("Submit");
    		addLoginButtonListener(loginButton);
			JButton closeButton = new JButton("Close");
			addCloseButtonListener(closeButton);
    		lowerPanel.add(loginButton);
    		lowerPanel.add(closeButton);
    	}

    	private void defineLeftTextPanel() {
    		
    		JPanel topText = new JPanel();
    		JPanel bottomText = new JPanel();
    		topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
    		bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));		
    		
    		username = new JTextField(10);
    		label = new JLabel("Username");
    		label.setFont(Util.makeSmallFont(label.getFont()));
    		topText.add(label);
    		bottomText.add(username);
    		
    		leftTextPanel = new JPanel();
    		leftTextPanel.setLayout(new BorderLayout());
    		leftTextPanel.add(topText,BorderLayout.NORTH);
    		leftTextPanel.add(bottomText,BorderLayout.CENTER);
    	}
    	private void defineRightTextPanel() {
    		
    		JPanel topText = new JPanel();
    		JPanel bottomText = new JPanel();
    		topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
    		bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));		
    		
    		password = new JPasswordField(10);
    		label = new JLabel("Password");
    		label.setFont(Util.makeSmallFont(label.getFont()));
    		topText.add(label);
    		bottomText.add(password);
    		
    		rightTextPanel = new JPanel();
    		rightTextPanel.setLayout(new BorderLayout());
    		rightTextPanel.add(topText,BorderLayout.NORTH);
    		rightTextPanel.add(bottomText,BorderLayout.CENTER);
    	}
    	
    	private void addBackButtonListener(JButton butn) {
    		butn.addActionListener(evt -> {
    			LibrarySystem.hideAllWindows();
    			LibrarySystem.INSTANCE.setVisible(true);
    		});
    	}
    	
    	private void addLoginButtonListener(JButton butn) {
    		butn.addActionListener(evt -> checkUsernamePassword()
    		);
    	}
	
    	private void checkUsernamePassword() {
    		try {
    			RuleSet ruleSet = RuleSetFactory.getRuleSet(LoginForm.this);
    			this.loginUser = new User(username.getText(), password.getText(), null);
    			ruleSet.applyRules(this);
    			
    			ci.login(loginUser);
        		LoginForm.INSTANCE.setVisible(false);
        		LoginForm.INSTANCE.dispose();
        		resetForm();
        		
        		
        		LibrarySystem.INSTANCE.init();
        		LibrarySystem.INSTANCE.addMenuItems();
 	            Util.centerFrameOnDesktop(LibrarySystem.INSTANCE);
 	            LibrarySystem.INSTANCE.setImage();
 	            LibrarySystem.INSTANCE.repaint();
 	            LibrarySystem.INSTANCE.setVisible(true);
 	            
    		} catch (RuleException e) {
    			Util.showMessage(this, e.getMessage());
    		} catch (LoginException e) {
    			// TODO Auto-generated catch block
    			Util.showMessage(this, e.getMessage());
    			e.printStackTrace();
    		}

    		
    	}        
    	
       	
    	private void addCloseButtonListener(JButton butn) {
    		butn.addActionListener(evt -> 
    		{
    			LibrarySystem.INSTANCE.dispose();
    			LibrarySystem.INSTANCE.setVisible(false);
    		}
    				);
    	}
    	
    	private void resetForm() {
    		username.setText("");
    		password.setText("");
    	}
    	public User getUser() {
    		return loginUser;
    	}

        public void showMessage(String msg){
            JOptionPane.showMessageDialog(this, msg);
        }
}