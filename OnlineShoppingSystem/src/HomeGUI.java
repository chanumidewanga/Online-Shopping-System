import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


//Implement GUI home from ActionListener
public class HomeGUI implements ActionListener {
    JFrame homeFrame = new JFrame("Westminster Shopping Manager");
    JLabel title1 = new JLabel("Westminster Shopping Manager");
    JLabel userIdLabel = new JLabel("User Name: ");
    JLabel passwordLabel = new JLabel("ID number: ");
    JButton loginBtn = new JButton("LOGIN");
    JTextField userIdField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    HashMap<String,String> loginData = new HashMap<String,String>(); //Store and retrieve key-value pairs
    ArrayList<User> userData = new ArrayList<>();
    ImageIcon bgImage = new ImageIcon("BG_img/WSC_bg.png"); //Add a background image to the hme page.
    JLabel bgImgPanel = new JLabel(bgImage);

    public HomeGUI(){
    }
    public HomeGUI(ArrayList<User> loginInformation, boolean showGUI){
        userData = loginInformation;
        homeFrame.setSize(800,600);
        homeFrame.setVisible(true);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setLocationRelativeTo(null);
        homeFrame.setLayout(null);
        homeFrame.setResizable(true);
        if (showGUI) {
            showGUI();
        }

        bgImgPanel.setSize(800,600);
        bgImgPanel.setLocation(0,0);

        title1.setBounds(160,80,500,40);
        title1.setFont(new Font("Arial",Font.BOLD,32));
        homeFrame.add(title1);

        userIdLabel.setBounds(470,220,80,30);
        homeFrame.add(userIdLabel);

        passwordLabel.setBounds(470,290,80,30);
        homeFrame.add(passwordLabel);

        loginBtn.setBounds(600,370,100,30);
        loginBtn.addActionListener(this);
        homeFrame.add(loginBtn);

        userIdField.setBounds(550,220,200,30);
        homeFrame.add(userIdField);

        passwordField.setBounds(550,290,200,30);
        homeFrame.add(passwordField);
        homeFrame.add(bgImgPanel);

    }
    public void showGUI(){
        homeFrame.setVisible(true);
    }
    private String NowLogged;
    public String getNowLogged() {
        return NowLogged;
    }
    public void setNowLogged(String nowLogged) {
        NowLogged = nowLogged;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        if(e.getSource()==loginBtn){
            String userName = userIdField.getText();
            String IDnumber = String.valueOf(passwordField.getPassword());

            boolean userNameStatus = false;
            boolean IDnumberStatus = false;
            int selected = -1;

            for(int i = 0; i < userData.size(); i++){
                if(userData.get(i).getUsername().equals(userName)){
                    if(userData.get(i).getPassword().equals(IDnumber)) {
                        userNameStatus=true;
                        IDnumberStatus=true;
                        selected = i;
                        break;
                    }
                }
            }

            if(userNameStatus && IDnumberStatus){
                userData.get(selected).addLoginCount();
                System.out.println("Selected: " +selected);

                try (ObjectOutputStream savedFile = new ObjectOutputStream(new FileOutputStream("userDetails.txt"))) {
                    savedFile.writeObject(userData);
                    System.out.println("Login count updated.");
                } catch (IOException ex){
                    System.err.println("Update failed!" + ex.getMessage());
                }

                homeFrame.dispose();
                setNowLogged(userName);
                ProductPageGUI productPage = new ProductPageGUI(true,selected);
            } else{
                JOptionPane.showMessageDialog(homeFrame, "Invalid UserName or IDnumber", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
