import javax.swing.*;
import java.awt.*;

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
        searchTextField.setBounds(15,25,400,30);

        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 20));

        add(searchTextField);

        //search button
        
    }
}
