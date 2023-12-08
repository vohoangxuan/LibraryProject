package guid;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import business.ControllerInterface;
import business.SystemController;
import librarysystem.AllMemberIdsWindow;
import librarysystem.LibWindow;
import librarysystem.Util;


public class LibrarySystem extends JFrame implements LibWindow {
	ControllerInterface ci = new SystemController();
	public final static LibrarySystem INSTANCE =new LibrarySystem();
	JPanel mainPanel;
	JMenuBar menuBar;
    JMenu options;
    JMenuItem login, allBookIds, allMemberIds, addMember, addBook, checkoutBook, addBookCopy, logOut;
    String pathToImage;
    private boolean isInitialized = false;
    
    private static LibWindow[] allWindows = {
    	LibrarySystem.INSTANCE,
		guid.LoginForm.INSTANCE,
		AllMemberIdsWindow.INSTANCE,	
		AllBookIdsWindow.INSTANCE,
		AddMemberForm.INSTANCE,
		AddBookForm.INSTANCE,
		AddBookCopyForm.INSTANCE,
		CheckoutForm.INSTANCE
	};
    	
	public static void hideAllWindows() {	
		for(LibWindow frame: allWindows) {
			frame.setVisible(false);			
		}
	}
     
    private LibrarySystem() {}
    
    public void init() {
    	if(isInitialized)
    		return;
    	formatContentPane();
    	setPathToImage();
    	insertSplashImage();
    	
		createMenus();
		//pack();
		setSize(660,500);
		isInitialized = true;
    }
    
    private void formatContentPane() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,1));
		getContentPane().add(mainPanel);	
	}
    
    private void setPathToImage() {
    	String currDirectory = System.getProperty("user.dir");
    	pathToImage = currDirectory+"\\src\\librarysystem\\library.jpg";
    }
    
    private void insertSplashImage() {
        ImageIcon image = new ImageIcon(pathToImage);
		mainPanel.add(new JLabel(image));	
    }
    private void createMenus() {
    	menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
		addMenuItems();
		setJMenuBar(menuBar);		
    }
    
    private void addMenuItems() {
    	options = new JMenu("Options");  
    	menuBar.add(options);

    	if( SystemController.currentAuth == null) {
    		login = new JMenuItem("Login");
    		login.addActionListener(new LoginListener());
    		options.add(login);
    	} else {

    		allBookIds = new JMenuItem("All Books");
    		allBookIds.addActionListener(new AllBookIdsListener());
    		allMemberIds = new JMenuItem("All Member Ids");
    		allMemberIds.addActionListener(new AllMemberIdsListener());

    		addMember = new JMenuItem("Add Member");
    		addMember.addActionListener(new AddMemberListener());

    		addBook = new JMenuItem("Add Book");
    		addBook.addActionListener(new AddBookListener());

    		addBookCopy = new JMenuItem("Add Book Copy");
    		addBookCopy.addActionListener(new AddBookCopyListener());

    		checkoutBook = new JMenuItem("Checkout Book");
    		checkoutBook.addActionListener(new CheckoutBookListener());

    		logOut = new JMenuItem("Logout");
    		logOut.addActionListener(new LogoutListener());
    		
    		switch (SystemController.currentAuth) {
    		case LIBRARIAN:
    			options.add(checkoutBook);
    			options.add(allBookIds);
    			break;
    		case ADMIN:
    			options.add(allBookIds);
    			options.add(allMemberIds);
    			options.add(addMember);
    			options.add(addBook);
    			break;
    		case BOTH:
    			options.add(checkoutBook);
    			options.add(addMember);
    			options.add(addBook);
    			options.add(allBookIds);
    			options.add(allMemberIds);
    			break;
    		default:
    			options.add(logOut);
    		}
    		
    		options.add(logOut);
    	}
    	


/*
    	options.add(allBookIds);
    	options.add(allMemberIds);
    	options.add(addMember);
    	options.add(addBook);
    	options.add(addBookCopy);
    	options.add(checkoutBook);
*/
    }
    
    class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			LoginForm.INSTANCE.init();
			Util.centerFrameOnDesktop(LoginForm.INSTANCE);
			LoginForm.INSTANCE.setVisible(true);
			
		}
    	
    }
    class AllBookIdsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();
			
			List<String> ids = ci.allBookIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for(String s: ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllBookIdsWindow.INSTANCE.setData(sb.toString());
			AllBookIdsWindow.INSTANCE.pack();
			//AllBookIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
			AllBookIdsWindow.INSTANCE.setVisible(true);
			
		}
    	
    }
    
    class AllMemberIdsListener implements ActionListener {

    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllMemberIdsWindow.INSTANCE.init();
//			AllMemberIdsWindow.INSTANCE.pack();
//			AllMemberIdsWindow.INSTANCE.setVisible(true);
			
			
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();
			
			List<String> ids = ci.allMemberIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for(String s: ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllMemberIdsWindow.INSTANCE.setData(sb.toString());
			AllMemberIdsWindow.INSTANCE.pack();
			AllMemberIdsWindow.INSTANCE.repaint();
			//AllMemberIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
			AllMemberIdsWindow.INSTANCE.setVisible(true);
			
			
		}
    	
    }
    
    class AddMemberListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AddMemberForm.INSTANCE.init();
			Util.centerFrameOnDesktop(AddMemberForm.INSTANCE);
			AddMemberForm.INSTANCE.setVisible(true);
			
		}
    	
    }

    class AddBookListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AddBookForm.INSTANCE.init();
			Util.centerFrameOnDesktop(AddBookForm.INSTANCE);
			AddBookForm.INSTANCE.setVisible(true);
		}
	}

	class AddBookCopyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AddBookCopyForm.INSTANCE.init();
			Util.centerFrameOnDesktop(AddBookCopyForm.INSTANCE);
			AddBookCopyForm.INSTANCE.setVisible(true);
		}
	}

	class CheckoutBookListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			CheckoutForm.INSTANCE.init();
			Util.centerFrameOnDesktop(CheckoutForm.INSTANCE);
			CheckoutForm.INSTANCE.setVisible(true);
		}
	}
	
	class LogoutListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			ci.logout();
			LibrarySystem.INSTANCE.dispose();
			LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.init();
            Util.centerFrameOnDesktop(LibrarySystem.INSTANCE);
            LibrarySystem.INSTANCE.setVisible(true);
		}		
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}


	@Override
	public void isInitialized(boolean val) {
		isInitialized =val;
		
	}
    
}
