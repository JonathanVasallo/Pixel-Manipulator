/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasallo2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class handles all operations dealing with the GUI, from displaying it to
 * modifying and closing it.
 *
 * @author Jonathan Vasallo
 */
public final class GUIDemo extends JFrame implements ItemListener {

    //ArrayList<BufferedImage> originalIMG;
    private final JButton load;
    private final JButton save;
    private final JButton quit;
   
    private JPanel cardLayout, option0, option1, option2, option3, option4, option5;
// a panel that uses card Layout and all the options that follow 
    private JLabel pointerLabel;
    private final String NORMAL = "Normal Filter";
    private final String GREYSCALE = "Grey Scaled";
    private final String INVERTED = "Inverted Colors";
    private final String MIRRORED = "Mirrored Image";
    private final String BLACKANDWHITE = "Black & White";
    private final String BLURRED = "Blurred Image";
    private final String[] effectsName = {NORMAL, GREYSCALE, INVERTED,
        MIRRORED, BLACKANDWHITE, BLURRED};
    
    private ArrayList<BufferedImage> images; // keep separated from others

    private ArrayList<Image> imageGreySafe;
    private ArrayList<Image> blackWhiteSafe;
    private ArrayList<Image> invertedSafe;
    private ArrayList<Image> mirroredSafe;
    private ArrayList<Image> blurredSafe;
    private BufferedImage blurredI;

    private ArrayList<String> pathway;
    private JComboBox options;

    /**
     * This is the constructor of the GUI which is responsible for actually
     * initializing and displaying all the elements of the GUI. This constructor
     * handles the layouts, the file input and saving, and it manages the cards.
     */
    public GUIDemo() throws FileNotFoundException, IOException {
        super("Pixel Manipulator");

        images = new ArrayList();
        pathway = new ArrayList();
        imageGreySafe = new ArrayList();
        blackWhiteSafe = new ArrayList();
        invertedSafe = new ArrayList();
        mirroredSafe = new ArrayList();
        blurredSafe = new ArrayList();
        //imageGrey = new ArrayList();
        //setLayout(new FlowLayout());
        JPanel visible = new JPanel();
// visible everywhere it navigates user between guis 

        cardLayout = new JPanel(new CardLayout()); // where we switch our panels 

        pointerLabel = new JLabel("Once Image is loaded, Select an option in the combobox to change the picture Above");

        options = new JComboBox(effectsName); // stores each option to choose 
        //from inside the combobox where our user will make a selection 
        options.setMaximumRowCount(6);
        options.addItemListener(this);
        // KeyListener keylis = new MyKeyListener();

        JPanel paneldisplay = new JPanel(); // the screen where we start off at
        visible = new JPanel();
        JPanel buttonplace = new JPanel();
        load = new JButton("Load Picture");
        save = new JButton("  Save Image  ");
        quit = new JButton("End Program");

        option0 = new JPanel();

        option1 = new JPanel(); // this is  the first card 
        //option1.add(quit);
        option2 = new JPanel(); // this is the second card
        // option2.add(new JButton("Yo"));
        option3 = new JPanel(); // this is  the third card 

        option4 = new JPanel(); // this is the fourth card

        option5 = new JPanel();

        cardLayout.add(option0, NORMAL);
        cardLayout.add(option1, GREYSCALE); // adds the first card as being  
        //chosen by greyscales name 
        cardLayout.add(option2, INVERTED);
        cardLayout.add(option3, MIRRORED);
        cardLayout.add(option4, BLACKANDWHITE);
        cardLayout.add(option5, BLURRED);

        buttonplace.add(load);
        buttonplace.add(save);
        buttonplace.add(quit);

        buttonplace.add(options);

        //need a try catch block here somewhere 
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    //visible.removeAll(); //this will get us to remove 
                    //the picture off the screen and keep the buttons in display screen

                    loadIMG();
                    validate();
                } catch (IOException ex) {
                    Logger.getLogger(GUIDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // greyImage();
                    //makeInvertedColors();
                    System.out.println("Inside save try statement");
                    saveIMG();
                } catch (IOException ex) {
                    Logger.getLogger(GUIDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
                validate();
            }
        });

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });

        paneldisplay.add(buttonplace);
        paneldisplay.add(visible);

        this.add(pointerLabel, BorderLayout.SOUTH);
        this.add(paneldisplay, BorderLayout.PAGE_START);
        this.add(cardLayout, BorderLayout.CENTER);

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (images.size() > 0) {

            if (options.getSelectedIndex() == 0) {
                option0.removeAll();
                option0.validate();
                option0.repaint();
                //reverseGrey.get(0).flush();
                JLabel image = new JLabel(new ImageIcon(images.get(0)));
                //reverseGrey.get(0).flush();
                option0.add(image);
                //option0.add(new JButton("YOOOO"));
                option0.repaint();
                option0.validate();
            }
            if (options.getSelectedIndex() == 1) { // grey image 
                try {
                    greyImage();
                } catch (IOException ex) {
                    Logger.getLogger(GUIDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (options.getSelectedIndex() == 2) { // inverted Colors
                try {
                    makeInvertedColors();
                } catch (IOException ex) {
                    Logger.getLogger(GUIDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (options.getSelectedIndex() == 3) { // mirrored image
                try {
                    makeMirrored();
                } catch (IOException ex) {
                    Logger.getLogger(GUIDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (options.getSelectedIndex() == 4) { // black and white
                try {
                    blackandwhite();
                } catch (IOException ex) {
                    Logger.getLogger(GUIDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (options.getSelectedIndex() == 5) { // black and white
                try {
                    blurImage();
                } catch (IOException ex) {
                    Logger.getLogger(GUIDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            //System.out.println((String) e.getItem());
            CardLayout c1 = (CardLayout) (cardLayout.getLayout());
            c1.show(cardLayout, (String) e.getItem());
        }
    }
    /**
     * This method takes an image in by accessing the images arrayList and
     * checks what option the user is on to save the exact effect of the 
     * picture to a file of the users choice and sends out a notice to confirm
     * overriding a file.
     * @param img the original image as a buffered image
     */
    public void saveIMG() throws IOException {
        if (images.isEmpty()) {
            JOptionPane.showMessageDialog(option0, "You must load an image "
                    + "into the program before saving!");
            System.exit(0);
        }
        
        //JOptionPane.showMessageDialog(option0,"Save the picture by typing in a 'Name' with '.jpg' as the extension");
        String s = JOptionPane.showInputDialog(option0, "Save the picture"
                + " by typing in a \n 'Name' with '.jpg' as the extension"); // this will be the file name = s
        
        File f = null;

        if (option1.isShowing()) { // this is how I  will determine what img the user is trying to save 
            try {

                f = new File(s);
                if (f.exists()) {
                    //System.out.println(s + " exists");
                    int option = JOptionPane.showConfirmDialog(option0, "The "
                            + "file " + s + " already exists\n"
                            + " Select YES if you want to override it");
                    //System.out.println(option); // 0 means yes 1 means no 
                    if (option == 0) {
                        ImageIO.write((RenderedImage) imageGreySafe.get(0), ""
                                + "jpg", f);   // override the file 
                    } else {
                        // do nothing    
                    }
                }
                if (f.exists() == false) {
                    
                    ImageIO.write((RenderedImage) imageGreySafe.get(0), ""
                            + "jpg", f);
                }
            } catch (IOException e) {
                System.out.println("error");
            }
            //System.out.println("Grey has the focus");
        }
        if (option2.isShowing()) { // this is how I  will determine what img the user is trying to save 
            try {

                f = new File(s);
                if (f.exists()) {
                    //System.out.println(s + " exists");
                    int option = JOptionPane.showConfirmDialog(option0, ""
                            + "The file " + s + " already exists\n"
                            + " Select YES if you want to override it");
                    //System.out.println(option); // 0 means yes 1 means no 
                    if (option == 0) {
                        ImageIO.write((RenderedImage) invertedSafe.get(0), ""
                                + "jpg", f);   // override the file 
                    } else {
                        // do nothing    
                    }
                }
                if (f.exists() == false) {
                    
                    ImageIO.write((RenderedImage) invertedSafe.get(0), "jpg", f);
                }
            } catch (IOException e) {
                System.out.println("error");
            }
        }
        if (option3.isShowing()) {// this is how I  will determine what img the user is trying to save 
            try {

                f = new File(s);
                if (f.exists()) {
                    //System.out.println(s + " exists");
                    int option = JOptionPane.showConfirmDialog(option0, "The"
                            + " file " + s + " already exists\n"
                            + " Select YES if you want to override it");
                    //System.out.println(option); // 0 means yes 1 means no 
                    if (option == 0) {
                        ImageIO.write((RenderedImage) mirroredSafe.get(0), ""
                                + "jpg", f);   // override the file 
                    } else {
                        // do nothing    
                    }
                }
                if (f.exists() == false) {
                    
                    ImageIO.write((RenderedImage) mirroredSafe.get(0), ""
                            + "jpg", f);
                }
            } catch (IOException e) {
                System.out.println("error");
            }
        }
        if (option4.isShowing()) {// this is how I  will determine what img the user is trying to save 
            try {

                f = new File(s);
                if (f.exists()) {
                    //System.out.println(s + " exists");
                    int option = JOptionPane.showConfirmDialog(option0, "The"
                            + " file " + s + " already exists\n"
                            + " Select YES if you want to override it");
                    //System.out.println(option); // 0 means yes 1 means no 
                    if (option == 0) {
                        ImageIO.write((RenderedImage) blackWhiteSafe.get(0), 
                                "jpg", f);   // override the file 
                    } else {
                        // do nothing    
                    }
                }
                if (f.exists() == false) {
                    
                    ImageIO.write((RenderedImage) blackWhiteSafe.get(0), 
                            "jpg", f);
                }
            } catch (IOException e) {
                System.out.println("error");
            }
        }

        if (option5.isShowing()) {// this is how I  will determine 
            //what img the user is trying to save 
            try {

                f = new File(s);
                if (f.exists()) {
                    //System.out.println(s + " exists");
                    int option = JOptionPane.showConfirmDialog(option0, ""
                            + "The file " + s + " already exists\n"
                            + " Select YES if you want to override it");
                    //System.out.println(option); // 0 means yes 1 means no 
                    if (option == 0) {
                        ImageIO.write((RenderedImage) blurredSafe.get(0), ""
                                + "jpg", f);   // override the file 
                    } else {
                        // do nothing    
                    }
                }
                if (f.exists() == false) {
                    
                    ImageIO.write((RenderedImage) blurredSafe.get(0), 
                            "jpg", f);
                }
            } catch (IOException e) {
                System.out.println("error");
            }
        }
         // needed a string to access file

    }
/**
     * This method takes an image in by accessing the images the user selects
     * in the filechooser and stores the image and saves the path it comes from
     * to access later than is display for the user to see in panel Option0.
     * 
     * @param img the original image as a buffered image
     */
    public void loadIMG() throws IOException {
        JFileChooser fc = new JFileChooser();
        fc.showDialog(fc, "Import");
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        File file = fc.getSelectedFile();
        String t = (file.getAbsolutePath()); // needed a string to access file

        if (pathway.isEmpty()) {
            pathway.add(t);
        } else if (pathway.isEmpty() == false) {
            pathway.remove(0);
            pathway.add(t);
        }
        //we need to fins a way to get a clean file to plug into 
        //the necessity for a buffered image 

        BufferedImage img = null;
        img = ImageIO.read(new File(t));

        if (images.isEmpty()) {
             // saves it so we can come back later  for it
            images.add(img); // adds our image we loaded into our program 
            //into an arraylist if its the first
            

            JLabel image = new JLabel(new ImageIcon(images.get(0)));
            option0.add(image);

            option0.validate();
        } else if (images.isEmpty() == false) { // theres an img loaded already 
            // replace it with the new one cause the user messed up 
            
            images.remove(0);
            images.add(img);

            option0.removeAll(); // removes the previous loaded img and replaces it w new one
            option0.validate();
            option0.repaint();

            JLabel image = new JLabel(new ImageIcon(images.get(0)));

            option0.add(image);
            option0.validate();
            //option0.add(image);
        }
        
    } // end of LOAD image 

    /**
     * This method takes in an image and converts it to a black and white image.
     * this is done through the use of the Graphics2D class, and essentially
     * sets all the colors to either white or black. This method also sets up
     * the info that will go in the black and white text area.
     *
     * @param image the original image in the form of a BufferedImage
     */
    public void blackandwhite() throws IOException {
        BufferedImage img = null;
        img = ImageIO.read(new File(pathway.get(0)));

        BufferedImage result = new BufferedImage(img.getWidth(), 
                img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D graphic = result.createGraphics();
        graphic.drawImage(img, 0, 0, Color.WHITE, null);
        graphic.dispose();
        //set new RGB value

        //replace RGB value with avg
        JLabel imageBWColor = new JLabel(new ImageIcon(result));
        option4.removeAll();
        option4.validate();
        option4.add(imageBWColor); 
// this should add the greyScaled image to the card that is matched to this setting 
        option4.repaint();
        if (blackWhiteSafe.isEmpty()) { // only keep one 
            blackWhiteSafe.add(result);
        } else if (blackWhiteSafe.isEmpty() == false) {
            blackWhiteSafe.remove(0);
            blackWhiteSafe.add(result); 
// this will store the img we want to eventually save to 
        }
    } // end of Black and white method 

    /**
     * This method takes an image and makes converts it to a gray scale image.
     * This method makes use of the grabbing the rgb values to make all the
     * colors an avg on one another. Creating different shades of gray.
     *
     * @param image the original image as a buffered image
     */
    private void greyImage() throws IOException {

        //BufferedImage img4 =imageGrey.get(0);
        //File f = null;
        BufferedImage img = null;
        img = ImageIO.read(new File(pathway.get(0)));
        //BufferedImage bufImg= new BufferedImage(images.get(0));

        int width = img.getWidth();
        int height = img.getHeight();

        //convert to grayscale
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                //calculate average
                int avg = ((r) + (g) + (b)) / 3;

                //replace RGB value with avg
                p = (a << 24) | (avg << 16) | (avg << 8) | avg;

                img.setRGB(x, y, p);

            }

        }

        JLabel imageGrey = new JLabel(new ImageIcon(img));
        option1.removeAll();
        option1.validate();
        option1.add(imageGrey); // this should add the greyScaled 
        //image to the card that is matched to this setting 
        option1.repaint();
        if (imageGreySafe.isEmpty()) { // only keep one 
            imageGreySafe.add(img);
        } else if (imageGreySafe.isEmpty() == false) {
            imageGreySafe.remove(0);
            imageGreySafe.add(img); // this will store the img 
            //we want to eventually save to 
        }

    }

    /**
     * this method takes an image and flips the orientation horizontally. The
     * method makes use of the BufferedImage class redrawing to flip the
     * orientation of the image.
     *
     * @param image the original image as as bufferedImage
     */
    public void makeMirrored() throws IOException {

        BufferedImage img = null;
        img = ImageIO.read(new File(pathway.get(0)));
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage mimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //create mirror image pixel by pixel
        for (int y = 0; y < height; y++) {
            for (int lx = 0, rx = width - 1; lx < width; lx++, rx--) {
                //lx starts from the left side of the image
                //rx starts from the right side of the image
                //get source pixel value
                int p = img.getRGB(lx, y);
                //set mirror image pixel value - both left and right
                //mimg.setRGB(lx, y, p);
                mimg.setRGB(rx, y, p);
            }
        }
        JLabel imageMirror = new JLabel(new ImageIcon(mimg));
        option3.removeAll();
        option3.validate();
        option3.add(imageMirror); // this should add the greyScaled 
        //image to the card that is matched to this setting 
        option3.repaint();
        if (mirroredSafe.isEmpty()) { // only keep one 
            mirroredSafe.add(mimg);
        } else if (mirroredSafe.isEmpty() == false) {
            mirroredSafe.remove(0);
            mirroredSafe.add(mimg); // this will store the img we 
            //want to eventually save to 
        }

    }

    /**
     * This method inverts the colors of an image, and in turn makes the it a
     * negative image. This is done by subtracting the rgb values from 255 in
     * order to get the opposite of the original colors.
     *
     * @param image the original image as a BufferedImage
     */
    public void makeInvertedColors() throws IOException {
        BufferedImage img = null;
        img = ImageIO.read(new File(pathway.get(0)));

        int width = img.getWidth();
        int height = img.getHeight();
        //convert to inverted img
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                //subtract RGB from 255
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                //set new RGB value
                p = (a << 24) | (r << 16) | (g << 8) | b;
                img.setRGB(x, y, p);

                //replace RGB value with avg
            }

        }
        JLabel imageInvertColor = new JLabel(new ImageIcon(img));
        option2.removeAll();
        option2.validate();
        option2.add(imageInvertColor); // this should add the greyScaled 
        //image to the card that is matched to this setting 
        option2.repaint();
        if (invertedSafe.isEmpty()) { // only keep one 
            invertedSafe.add(img);
        } else if (invertedSafe.isEmpty() == false) {
            invertedSafe.remove(0);
            invertedSafe.add(img); // this will store the img we want to eventually save to 
        }
    }

    /**
     * This method blurs an image by making use of the radius and size as well
     * as creating a new kernel and using the ConvolveOp class.
     *
     * @param img the original image as a buffered image
     */
    public void blurImage() throws IOException {

        int radius = 11;
        int size = radius * 2 + 1;
        float weight = 1.0f / (size * size);
        float[] data = new float[size * size];

        for (int i = 0; i < data.length; i++) {
            data[i] = weight;
        }

        Kernel kernel = new Kernel(size, size, data);// create a new kernel object
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null); 
// actually apply the blur
        //tbi is BufferedImage
        BufferedImage img = null;
        img = ImageIO.read(new File(pathway.get(0)));
        blurredI = op.filter(img, null);

        JLabel imageInvertColor = new JLabel(new ImageIcon(blurredI));
        option5.removeAll();
        option5.validate();
        option5.add(imageInvertColor); // this should add the greyScaled
        //image to the card that is matched to this setting 
        option5.repaint();
        if (blurredSafe.isEmpty()) { // only keep one 
            blurredSafe.add(blurredI);
        } else if (blurredSafe.isEmpty() == false) {
            blurredSafe.remove(0);
            blurredSafe.add(blurredI); // this will store the img we want 
            //to eventually save to 
        }

    }// end of blur image

}
