package LoginAndMenu;

import Components.Styles;
import DatabaseConnection.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;


public class LoginPage {

    private static JLabel title;
    private static JTextField usernameTF;
    private static JPasswordField passwordPF;
    private static JButton loginButton;
    private static JLabel newUserLabel;
    private static JLabel bottomLabel;

    private static JFrame frame;
    private static JPanel pane;

    public static void initialize() {

        pane = new JPanel();
        pane.setLayout(null);

        title = new JLabel("Login");
        title.setBounds(200, 50, 100, 40);
        title.setFont(new Font("Times New Roman", Font.BOLD, 35));
        pane.add(title);

        usernameTF = new JTextField();
        usernameTF.setBounds(100, 150, 300, 30);
        usernameTF.setToolTipText("Insert your username");
        pane.add(usernameTF);

        passwordPF = new JPasswordField();
        passwordPF.setBounds(100, 200, 300, 30);
        passwordPF.setToolTipText("Insert your password");
        pane.add(passwordPF);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 300, 300, 40);
        Styles.buttonStyles(loginButton);
        handleLogin(loginButton);
        pane.add(loginButton);

        newUserLabel = new JLabel("Register here");
        newUserLabel.setBounds(200, 350, 83, 20);
        newUserLabel.setForeground(Color.blue);
        pane.add(newUserLabel);
        newUser(newUserLabel);

        bottomLabel = new JLabel("© 2022 Library App");
        bottomLabel.setFont(new Font("Calibri", Font.ITALIC, 10));
        bottomLabel.setBounds(200, 550, 100, 10);
        pane.add(bottomLabel);

        frame = new JFrame("Login");
        frame.getContentPane().setBackground(Color.lightGray);
        frame.add(pane);

//        frame.getContentPane().setBackground(Color.BLUE);
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null); //set the frame to the center of the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    public static void handleLogin(JButton button) {
        DBConnection.connect(); //always connect first to the database, otherwise you'll get a '... "connection" is null'

        button.addActionListener(e -> {
            if (usernameTF.getText().isEmpty() && passwordPF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Boxes are empty, please fill in with you credentials");
                usernameTF.setText("");
                passwordPF.setText("");
            } else {
                try {
                    String query = "SELECT users, password from users where users='" + usernameTF.getText() + "' and password='" + String.valueOf(passwordPF.getPassword()) + "';";
                    Connection connection = DBConnection.getConnection();

                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    if (resultSet.next()) {

                        Thread.sleep(500);
                        MainMenu.initialize();
                        System.out.println("login successful ");
                        frame.dispose();

                    } else {
                        JOptionPane.showMessageDialog(null, "Username or password are incorrect, please try again");
                        usernameTF.setText("");
                        passwordPF.setText("");
                    }
                } catch (Exception exception) {
                    System.out.println("error in LoginPage.loginController()");
            throw new RuntimeException("unhandled", exception);

                }
            }
        });
    }

    public static void newUser(JLabel label){
       label.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               RegisterPage.initialize();
               frame.dispose();
           }
       });
    }




}
