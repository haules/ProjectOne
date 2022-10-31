package LoginAndMenu;

import Components.Styles;
import DatabaseConnection.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class RegisterPage {

    private static JFrame frame;
    private static JPanel panel;

    private static JLabel firstNameLabel;
    private static JLabel lastNameLabel;
    private static JLabel emailLabel;
    private static JLabel title;
    private static JLabel passwordLabel;
    private static JLabel confirmPasswordLabel;
    private static JLabel bottomLabel;

    private static JTextField firstNameTF;
    private static JTextField lastNameTF;
    private static JTextField emailTF;
    private static JPasswordField passwordTF;
    private static JPasswordField confirmPassTF;

    private static JButton registerButton;
    private static JButton backToLogin;


    public static void initialize() {

        panel = new JPanel();
        panel.setLayout(null);

        title = new JLabel("Register a new user");
        firstNameLabel = new JLabel("First Name");
        lastNameLabel = new JLabel("Last Name");
        emailLabel = new JLabel("Email");
        passwordLabel = new JLabel("Password");
        confirmPasswordLabel = new JLabel("Confirm Password");
        registerButton = new JButton("Register User");
        firstNameTF = new JTextField();
        lastNameTF = new JTextField();
        emailTF = new JTextField();
        passwordTF = new JPasswordField();
        confirmPassTF = new JPasswordField();
        backToLogin = new JButton("Back");

        title.setFont(new Font("Times New Roman", Font.BOLD, 30));
        title.setBounds(220, 20, 260, 40);
        panel.add(title);


        firstNameLabel.setBounds(30, 100, 90, 20);
        firstNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        firstNameLabel.setBackground(Color.red);
        firstNameLabel.setOpaque(true);
        panel.add(firstNameLabel);


        lastNameLabel.setBounds(30, 150, 90, 20);
        lastNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(lastNameLabel);


        emailLabel.setBounds(30, 200, 90, 20);
        emailLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(emailLabel);


        passwordLabel.setBounds(30, 250, 90, 20);
        passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(passwordLabel);


        confirmPasswordLabel.setBounds(30, 300, 150, 20);
        confirmPasswordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(confirmPasswordLabel);


        registerButton.setBounds(260, 400, 200, 50);
        back(registerButton);


        firstNameTF.setBounds(350, 96, 250, 30);
        panel.add(firstNameTF);


        lastNameTF.setBounds(350, 146, 250, 30);
        panel.add(lastNameTF);


        emailTF.setBounds(350, 196, 250, 30);
        panel.add(emailTF);


        passwordTF.setBounds(350, 246, 250, 30);
        panel.add(passwordTF);


        confirmPassTF.setBounds(350, 296, 250, 30);
        panel.add(confirmPassTF);

        backToLogin.setBounds(260, 470, 200, 50);
        Styles.buttonStyles(backToLogin);
        panel.add(backToLogin);
        backToLoginPage(backToLogin);

        bottomLabel = new JLabel("© 2022 Library App");
        bottomLabel.setFont(new Font("Calibri", Font.ITALIC, 10));
        bottomLabel.setBounds(300, 650, 100, 10);
        panel.add(bottomLabel);


        frame = new JFrame("Register new user");
        frame.add(panel);

        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null); //set the frame to the center of the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);


    }

    public static void back(JButton button) {
        DBConnection.connect();


        button.addActionListener(e -> {
            if (firstNameTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insert your First Name");
                firstNameTF.requestFocus();

            } else if (lastNameTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insert your Last Name");
                lastNameTF.requestFocus();

            } else if (emailTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insert your email address");
                emailTF.requestFocus();

            } else if (passwordTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insert a password");
                passwordTF.requestFocus();

            } else if (confirmPassTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Confirm the password");
                confirmPassTF.requestFocus();
            } else {
                if (Arrays.equals(passwordTF.getPassword(), confirmPassTF.getPassword())) {
                    String user = emailTF.getText();
                    String password = new String(confirmPassTF.getPassword());
                    String query = "INSERT INTO users(users, password) VALUES ('" + user + "', '" + password + "');";
                    try {
                        Connection connection = DBConnection.getConnection();
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(query);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println("user created");
                    LoginPage.initialize();
                    frame.dispose();


                } else {
                    JOptionPane.showMessageDialog(null, "Passwords do not match. Please insert again!");
                }
            }
        });
    }

    public static void backToLoginPage(JButton button) {
        button.addActionListener(e -> {
            LoginPage.initialize();
            frame.dispose();
        });
    }
}


