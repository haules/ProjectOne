package Departments;

import Components.Styles;
import LoginAndMenu.MainMenu;

import javax.swing.*;
import java.awt.*;

public class ListBorrowedBooks {

    private static JFrame frame;
    private static JPanel panel;

    private static JButton backButton;


    public static void initialize(){
        panel = new JPanel();
        panel.setLayout(null);


        backButton = new JButton("Back");
        backButton.setBounds(100, 500, 150, 50);
        Styles.buttonStyles(backButton);
        panel.add(backButton);
        backToMainMenu(backButton);


        frame = new JFrame("All book borrowed");
        frame.getContentPane().setBackground(Color.lightGray);
        frame.add(panel);

        frame.setSize(700, 800);
        frame.setLocationRelativeTo(null); //set the frame to the center of the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void backToMainMenu(JButton button) {
        button.addActionListener(e -> {
            MainMenu.initialize();
            frame.dispose();
        });
    }
}
