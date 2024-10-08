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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ControllerInterface ci = new SystemController();
	public final static LibrarySystem INSTANCE =new LibrarySystem();
	JPanel mainPanel;
	JMenuBar menuBar;
    JMenu options;
    JMenuItem allBookIds, allMemberIds, addMember, checkoutBook, logOut, addBook, addBookCopy, searchOverDueEntries, searchMember, exit;
    String pathToImage;
    private boolean isInitialized = false;
    
    
    JDesktopPane desktop;
    private static LibWindow[] allWindows = {
    	LibrarySystem.INSTANCE,
		guid.LoginForm.INSTANCE,
		AllMemberIdsWindow.INSTANCE,	
		AllBookIdsWindow.INSTANCE,
		AddMemberForm.INSTANCE,
		AddBookForm.INSTANCE,
		AddBookCopyForm.INSTANCE,
		CheckoutForm.INSTANCE,
		SearchOverDueBookForm.INSTANCE,
		SearchMemberForm.INSTANCE
		
	};
    	
	public static void hideAllWindows() {	
		for(LibWindow frame: allWindows) {
			frame.setVisible(false);			
		}
	}
     
    private LibrarySystem() {}
    
    public void init() {
    	if(isInitialized && SystemController.currentUser !=null)
    		return;
    	desktop = new JDesktopPane();
    	LoginForm.INSTANCE.init();
		LoginForm.INSTANCE.setVisible(SystemController.currentUser==null);
		desktop.add(LoginForm.INSTANCE);
		setContentPane(desktop);
		setJMenuBar(createMenus());
		setSize(660,500);
		isInitialized = true;
    }
    
    public void setImage() {
    	formatContentPane();
    	setPathToImage();
    	insertSplashImage();
    }
    
    private void formatContentPane() {
    	
    	getContentPane().removeAll();
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,1));
		setContentPane(mainPanel);
	}
    
    private void setPathToImage() {
    	String currDirectory = System.getProperty("user.dir");
    	pathToImage = currDirectory+"\\src\\librarysystem\\library.jpg";
    }
    
    private void insertSplashImage() {
        ImageIcon image = new ImageIcon(pathToImage);
		mainPanel.add(new JLabel(image));	
    }
    private JMenuBar createMenus() {
    	menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
		addMenuItems();
//		setJMenuBar(menuBar);	
		return menuBar;
    }
    
    public void addMenuItems() {
    	menuBar.removeAll();
    	options = new JMenu("Options");  
    	menuBar.add(options);

    	if( SystemController.currentUser != null) {
    		allMemberIds = new JMenuItem("All Member Ids");
    		allMemberIds.addActionListener(new AllMemberIdsListener());
    		addMember = new JMenuItem("Add Member");
    		addMember.addActionListener(new AddMemberListener());
			allBookIds = new JMenuItem("All Books");
			allBookIds.addActionListener(new AllBookIdsListener());
			addBook = new JMenuItem("Add Book");
			addBook.addActionListener(new AddBookListener());
			addBookCopy = new JMenuItem("Add Book Copy");
			addBookCopy.addActionListener(new AddBookCopyListener());
    		checkoutBook = new JMenuItem("Checkout Book");
    		checkoutBook.addActionListener(new CheckoutBookListener());
			searchOverDueEntries = new JMenuItem("Overdue Entries");
			searchOverDueEntries.addActionListener(new OverDueEntriesListener());
			searchMember = new JMenuItem("Search Member");
			searchMember.addActionListener(new SearchMemberListener());
    		logOut = new JMenuItem("Logout");
    		logOut.addActionListener(new LogoutListener());
    		
    		switch (SystemController.currentAuth) {
    		case LIBRARIAN:
    			options.add(checkoutBook);
				options.add(allBookIds);
    			options.add(searchMember);
    			options.add(searchOverDueEntries);
    			break;
    		case ADMIN:
    			options.add(addBook);
    			options.add(addBookCopy);
				options.add(allMemberIds);
				options.add(addMember);
    			options.add(allBookIds);
				options.add(searchMember);
    			break;
    		case BOTH:
    			options.add(addMember);
    			options.add(addBook);
				options.add(addBookCopy);
				options.add(checkoutBook);
				options.add(allMemberIds);
    			options.add(allBookIds);
				options.add(searchMember);
    			options.add(searchOverDueEntries);
    			break;
    		default:
    		}
    		
    		options.add(logOut);
    	}
    	
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

    class AddBookListener implements  ActionListener {
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
            LibrarySystem.INSTANCE.addMenuItems();
            Util.centerFrameOnDesktop(LibrarySystem.INSTANCE);
            LibrarySystem.INSTANCE.setVisible(true);
            LoginForm.INSTANCE.setVisible(true);
		}		
	}

	class OverDueEntriesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			SearchOverDueBookForm.INSTANCE.init();
			Util.centerFrameOnDesktop(SearchOverDueBookForm.INSTANCE);
			SearchOverDueBookForm.INSTANCE.setVisible(true);
		}
	}
	
	class SearchMemberListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			SearchMemberForm.INSTANCE.init();
			Util.centerFrameOnDesktop(SearchMemberForm.INSTANCE);
			SearchMemberForm.INSTANCE.setVisible(true);
		}
	}

	class ExitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			ci.logout();
			LibrarySystem.INSTANCE.dispose();
			LibrarySystem.INSTANCE.setVisible(false);
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
