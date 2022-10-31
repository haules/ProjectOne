package Departments;

import Components.Styles;
import DatabaseConnection.DBConnection;
import LoginAndMenu.MainMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ListBorrowedBooks {

    private static JLabel title;

    private static JFrame frame;
    private static JPanel panel;

    private static JButton backButton;
    private static JButton returnBookButton;

    private static JTable table;
    private static JScrollPane jScrollPane;

    public static void initialize() {
        panel = new JPanel();
        panel.setLayout(null);

        title = new JLabel("List with all your borrowed books");
        title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        title.setBounds(200, 50, 300, 30);
        panel.add(title);


        table = new JTable();
        try {
            retrieveFromDB(table);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(50, 100, 600, 500);
        panel.add(jScrollPane);


        backButton = new JButton("Back");
        backButton.setBounds(120, 620, 150, 50);
        Styles.buttonStyles(backButton);
        panel.add(backButton);
        backToMainMenu(backButton);

        returnBookButton = new JButton("Return Book");
        returnBookButton.setBounds(450, 620, 150, 50);
        Styles.buttonStyles(returnBookButton);
        panel.add(returnBookButton);
        returnBook(returnBookButton);

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

    public static void returnBook(JButton button) {
        button.addActionListener(e -> {
            System.out.println("Book returned");
        });
    }

    public static void retrieveFromDB(JTable table) throws SQLException {
        DBConnection.connect();

        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.YELLOW);

        String query = "SELECT * FROM borrowedbooks";

        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        DefaultTableModel defaultTableModel = new DefaultTableModel(new String[]{"Author", "Name of the Book", "Reserved - Start", "Reserved - End"}, 0);

        while (resultSet.next()) {
            String author = resultSet.getString("author");
            String bookName = resultSet.getString("bookName");
            String reservedStart = resultSet.getString("borrowedStart");
            String reservedEnd = resultSet.getString("borrowedEnd");

            defaultTableModel.addRow(new Object[]{author, bookName, reservedStart, reservedEnd});
        }
        table.setModel(defaultTableModel);
    }
}
