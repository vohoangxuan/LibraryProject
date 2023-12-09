package dataaccess;
import java.io.*;
import java.util.HashMap;

import business.Author;
import business.Book;
import business.LibraryMember;

class Test
{
    public static void main(String[] args)
    {   
        String filename = "C:\\quang\\project\\MPP_Project\\LibraryProject\\src\\dataaccess\\storage\\AUTHORS";
 
        HashMap<String, Author> object1 = null;
 
        // Deserialization
        try
        {   
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
             
            // Method for deserialization of object
            object1 = (HashMap<String, Author>)in.readObject();
             
            in.close();
            file.close();
             
            System.out.println("Object has been deserialized ");
            for (String k: object1.keySet()){
                System.out.println(k);
                System.out.println(object1.get(k));
            }
        }
         
        catch(IOException ex)
        {
            System.out.println(ex);
        }
         
        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }
 
    }
}