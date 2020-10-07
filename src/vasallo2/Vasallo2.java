/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasallo2;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * This class sets the gui to visible and sets the size of the gui window
 * @author Jonathan Vasallo
 */
public class Vasallo2 {

    /**
     * This main class sets the gui to visible and sets the size of the gui.
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        
        
        
        //    fc.getSelectedFile().getAbsolutePath();
        //System.out.println("somehow that worked");
        
        GUIDemo guiDemo = new GUIDemo();

        guiDemo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        guiDemo.setSize(1280, 720);

        guiDemo.setVisible(true);

    }

    public String promptForFolder(Component parent) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getAbsolutePath();
        }

        return null;
    }

}
