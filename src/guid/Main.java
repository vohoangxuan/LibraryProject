package guid;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import librarysystem.Util;



public class Main {

	public static void main(String[] args) {
<<<<<<< HEAD
		/*
		 * EventQueue.invokeLater(() -> { LoginForm.INSTANCE.init();
		 * Util.centerFrameOnDesktop(LoginForm.INSTANCE);
		 * LoginForm.INSTANCE.setVisible(true); });
		 * 
		 * 
		 */
		
		//   AddMemberForm.INSTANCE.init();
		//   Util.centerFrameOnDesktop(AddMemberForm.INSTANCE); 
		//   AddMemberForm.INSTANCE.setVisible(true);

		
		  EventQueue.invokeLater(() -> {
		  LibrarySystem.INSTANCE.setTitle("Sample Library Application");
		  LibrarySystem.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  
		  LibrarySystem.INSTANCE.init(); centerFrameOnDesktop(LibrarySystem.INSTANCE);
		  LibrarySystem.INSTANCE.setVisible(true); });
		  
		  
		 
=======
		EventQueue.invokeLater(() ->
		{
			LibrarySystem.INSTANCE.setTitle("Banned Animals Library Application");
			LibrarySystem.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			LibrarySystem.INSTANCE.init();
			centerFrameOnDesktop(LibrarySystem.INSTANCE);
			LibrarySystem.INSTANCE.setVisible(true);
		});

>>>>>>> a75d8fd82884b4b189c06317253bdfc6cbb99e27
	}

	public static void centerFrameOnDesktop(Component f) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int height = toolkit.getScreenSize().height;
		int width = toolkit.getScreenSize().width;
		int frameHeight = f.getSize().height;
		int frameWidth = f.getSize().width;
		f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
	}
}