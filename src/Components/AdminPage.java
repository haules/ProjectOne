package Components;

import DatabaseConnection.DBConnection;
import Departments.ListAllBooks;
import LoginAndMenu.LoginPage;
import LoginAndMenu.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminPage {

    private static JFrame frame;
    private static JPanel panel;

    private static JLabel title;

    private static JTextField username;
    private static JPasswordField password;

    private static JButton backButton;
    private static JButton loginButton;

    private static JLabel bottomLabel;

    public static void initialize() {
        panel = new JPanel();
        panel.setLayout(null);

        title = new JLabel("Only for Admins");
        title.setBounds(120, 50, 250, 50);
        title.setFont(new Font("Times New Roman", Font.BOLD, 35));
        panel.add(title);

        username = new JTextField();
        username.setBounds(100, 150, 300, 30);
        username.setToolTipText("Insert your username");
        panel.add(username);

        password = new JPasswordField();
        password.setBounds(100, 200, 300, 30);
        password.setToolTipText("Insert your password");
        panel.add(password);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 300, 130, 40);
        Styles.buttonStyles(loginButton);
        adminLogin(loginButton);
        panel.add(loginButton);

        backButton = new JButton("Back");
        backButton.setBounds(270, 300, 130, 40);
        Styles.buttonStyles(backButton);
        panel.add(backButton);

        backButton.addActionListener(e -> {
            LoginPage.initialize();
            frame.dispose();
        });

        bottomLabel = new JLabel("© 2022 Library App");
        bottomLabel.setFont(new Font("Calibri", Font.ITALIC, 10));
        bottomLabel.setBounds(220, 550, 100, 10);
        panel.add(bottomLabel);


        frame = new JFrame("Login");
        frame.getContentPane().setBackground(Color.lightGray);
        frame.add(panel);

        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null); //set the frame to the center of the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static void adminLogin(JButton button) {
        DBConnection.connect();

        button.addActionListener(e -> {
            if (username.getText().isEmpty() && password.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Boxes are empty, please fill in with you credentials");
                username.setText("");
                password.setText("");
            } else {
                try {
                    String query = "SELECT adminName, adminPassword from admins where adminName='" + username.getText() + "' and adminPassword='" + String.valueOf(password.getPassword()) + "';";
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
                        username.setText("");
                        password.setText("");
                    }
                } catch (Exception exception) {
                    System.out.println("error in AdminPage.loginController()");
                    throw new RuntimeException("unhandled", exception);

                }
            }
        });
    }
}
