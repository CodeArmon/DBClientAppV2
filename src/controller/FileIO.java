package controller;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class FileIO {
    public void initialize(URL url, ResourceBundle resourceBundle) throws FileNotFoundException {
        //create variable, name file to write entry logs into
        String filename = "entrylogs.txt";
        String logEntry;

        //creates scanner object to read
        Scanner keyboard = new Scanner(System.in);
        //get stuff in
        System.out.println("How many? ");
        int numitems = keyboard.nextInt();
        //clear keyboard buffer
        keyboard.nextLine();

        //create and Open File
        PrintWriter outputFile = new PrintWriter(filename);
        //get items and write to file
        for(int i =0; i<numitems; i++)
        {
            System.out.println("Enter item: " + (i+1));
            logEntry = keyboard.nextLine();
            outputFile.println(logEntry);
        }
        //close file
        outputFile.close();
        System.out.println("File Written");

    }
}
