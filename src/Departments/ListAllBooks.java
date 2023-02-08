package Departments;

import Components.MenuBar;
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

public class ListAllBooks {

    private static JFrame frame;
    private static JPanel panel;

    private static JButton backButton;

    private static JLabel title;

    private static JLabel bottomLabel;

    private static JTable table;
    private static JScrollPane jScrollPane;


    public static void initialize() {
        panel = new JPanel();
        panel.setLayout(null);

        backButton = new JButton("Back");
        backButton.setBounds(500, 630, 100, 40);
        Styles.buttonStyles(backButton);
        panel.add(backButton);

        backButton.addActionListener(e -> {
            MainMenu.initialize();
            frame.dispose();
        });

        title = new JLabel("Book Database - List of all books");
        title.setBounds(200, 30, 300, 35);
        title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panel.add(title);

        table = new JTable();
        try {
            resultSetToTableModel(table);
        } catch (Exception e) {
            e.printStackTrace();
        }

        jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(50, 100, 600, 500);
//        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //this doesn't allow the columns to resize to the table width
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        panel.add(jScrollPane);

        backButton = new JButton("Back");
        backButton.setBounds(500, 630, 100, 40);
        Styles.buttonStyles(backButton);
        panel.add(backButton);

        backButton.addActionListener(e -> {
            MainMenu.initialize();
            frame.dispose();
        });

        bottomLabel = new JLabel("© 2022 Library App");
        bottomLabel.setFont(new Font("Calibri", Font.ITALIC, 10));
        bottomLabel.setBounds(310, 750, 100, 10);
        panel.add(bottomLabel);

        frame = new JFrame("List All books Department");
        frame.getContentPane().setBackground(Color.lightGray);
        frame.add(panel);

        frame.setJMenuBar(MenuBar.getMenuBar());
        frame.setSize(700, 800);

        frame.setLocationRelativeTo(null); //set the frame to the center of the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void resultSetToTableModel(JTable table) throws SQLException {
        table.setDefaultEditor(Object.class, null);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // only one row is selected
        table.getTableHeader().setResizingAllowed(false); //this line of code is stopping the user to resize the columns
        table.getTableHeader().setEnabled(false); // stops the user to sort the column order

        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.YELLOW);

        DBConnection.connect();
        String query = "SELECT * FROM books";

        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

//        //Create new table model
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Name of the Book", "Name of the Author", "Reserved?", "Until"}, 0);

        while (resultSet.next()) {
            String bookName = resultSet.getString("name");
            String bookAuthor = resultSet.getString("author");
            String bookReservation = resultSet.getString("reserved");
            String untilDate = resultSet.getString("theDate");

            tableModel.addRow(new Object[]{bookName, bookAuthor, bookReservation, untilDate});
        }
        table.setModel(tableModel);

    }
}
