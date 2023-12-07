package guid;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import librarysystem.Util;



public class Main {

	public static void main(String[] args) {
		/*
	      EventQueue.invokeLater(() -> 
	         {
	        	 LoginForm.INSTANCE.init();
	 			 Util.centerFrameOnDesktop(LoginForm.INSTANCE);
	 			LoginForm.INSTANCE.setVisible(true);
	         });
	      


		CheckoutForm.INSTANCE.init();
			 Util.centerFrameOnDesktop(CheckoutForm.INSTANCE);
			 CheckoutForm.INSTANCE.setVisible(true);
			 
			  */
		
	      EventQueue.invokeLater(() -> 
	         {
	            LibrarySystem.INSTANCE.setTitle("Sample Library Application");
	            LibrarySystem.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            
	            LibrarySystem.INSTANCE.init();
	            centerFrameOnDesktop(LibrarySystem.INSTANCE);
	            LibrarySystem.INSTANCE.setVisible(true);
	         });
	      
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
