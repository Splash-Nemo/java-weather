import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGUI extends JFrame{
    public WeatherAppGUI(){
        super("Weather-App");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 650);

        setLocationRelativeTo(null);
        setLayout(null);

        setResizable(false);

        addGUIComponents();
    }

    private void addGUIComponents(){
        //search field
        JTextField searchTextField= new JTextField();
        searchTextField.setBounds(15,25,350,30);

        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 20));

        add(searchTextField);

        //search button
        JButton searchButton= new JButton(loadImage("imgs/search.png"));

        //cursor appearance
         searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
         searchButton.setBounds(375, 25, 47, 30);

         add(searchButton);
         
    }

    // Images for GUI
    private ImageIcon loadImage(String resourcePath){
        try{
            BufferedImage image= ImageIO.read(new File(resourcePath));

            return new ImageIcon(image);
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("Could not find image path");
        return null;
    }
}
