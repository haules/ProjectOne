package LoginAndMenu;

import Components.Styles;
import Departments.BorrowBooks;
import Departments.RegisterABook;
import Departments.ListBorrowedBooks;

import javax.swing.*;
import java.awt.*;

public class MainMenu {

    private static JFrame frame;
    private static JPanel panel;
    private static JLabel title;

    private static JButton borrowBookButton;
    private static JButton viewBorrowedButton;
    private static JButton registerABook;
    private static JButton logOut;

    private static JLabel bottomLabel;

    public static void initialize(){
        panel = new JPanel();
        panel.setLayout(null);

        title = new JLabel("Please choose a department");
        title.setFont(new Font("Times New Roman", Font.BOLD, 30));
        title.setBounds(110, 40, 400, 40);
        panel.add(title);

        borrowBookButton = new JButton("Borrow a Book");
        borrowBookButton.setBounds(170, 200, 250, 30);
        Styles.buttonStyles(borrowBookButton);
        changeDepartment(borrowBookButton);
        panel.add(borrowBookButton);

        viewBorrowedButton = new JButton("List Borrowed Books");
        viewBorrowedButton.setBounds(170, 250, 250, 30);
        Styles.buttonStyles(viewBorrowedButton);
        changeDepartment(viewBorrowedButton);
        panel.add(viewBorrowedButton);

        registerABook = new JButton("List All Books");
        registerABook.setBounds(170, 300, 250, 30);
        Styles.buttonStyles(registerABook);
        changeDepartment(registerABook);
        panel.add(registerABook);

        logOut = new JButton("Log out");
        logOut.setBounds(170, 350, 250, 30);
        Styles.buttonStyles(logOut);
        changeDepartment(logOut);
        panel.add(logOut);

        bottomLabel = new JLabel("© 2022 Library App");
        bottomLabel.setFont(new Font("Calibri", Font.ITALIC, 10));
        bottomLabel.setBounds(250, 550, 100, 10 );
        panel.add(bottomLabel);

        frame = new JFrame();
        frame.add(panel);
        frame.getContentPane().setBackground(Color.lightGray);
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    public static void changeDepartment(JButton button){

        button.addActionListener(e -> {
            if(button == borrowBookButton){
                BorrowBooks.initialize();
                frame.dispose();
            }else if(button == viewBorrowedButton){
                ListBorrowedBooks.initialize();
                frame.dispose();
            }else if(button == registerABook) {
                RegisterABook.initialize();
                frame.dispose();
            }else{
                LoginPage.initialize();
                frame.dispose();
            }
        });
    }
}
