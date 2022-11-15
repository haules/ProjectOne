package Departments;

import Components.Styles;
import DatabaseConnection.DBConnection;
import LoginAndMenu.MainMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;


public class ListBorrowedBooks {

    private static JLabel title;

    private static JFrame frame;
    private static JPanel panel;

    private static JButton backButton;
    private static JButton returnBookButton;

    private static JTable table;
    private static JScrollPane jScrollPane;

    private static JLabel bottomLabel;

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

        bottomLabel = new JLabel("© 2022 Library App");
        bottomLabel.setFont(new Font("Calibri", Font.ITALIC, 10));
        bottomLabel.setBounds(310, 750, 100, 10 );
        panel.add(bottomLabel);

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
        DBConnection.connect();


        button.addActionListener(e -> {
            int column = 1; //this is taking column (bookName) one and not 0 (if it was 0 then it was the ID column)
            int row = table.getSelectedRow();
            String value = table.getModel().getValueAt(row, column).toString(); //this helped me to delete database row
            String deleteQuery = "DELETE FROM borrowedbooks WHERE bookName='" + value + "';";

            int result = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to return this book?",
                    "Alert", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if(result == JOptionPane.YES_OPTION){
                try {

                    Statement statement = DBConnection.getConnection().createStatement();
                    statement.executeUpdate(deleteQuery);

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else if(result == JOptionPane.NO_OPTION) {
                return;
            }else{
                return;
            }

            try {
                retrieveFromDB(table); //this code updates the table live
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public static void retrieveFromDB(JTable functionTable) throws SQLException {

        DefaultTableModel model = (DefaultTableModel) functionTable.getModel();
        functionTable.setModel(model);

        functionTable.setDefaultEditor(Object.class, null);
        functionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //this enables the user to select ONLY ONE row at a time
        DBConnection.connect();

        JTableHeader header = functionTable.getTableHeader();
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
        functionTable.setModel(defaultTableModel);
    }
}
