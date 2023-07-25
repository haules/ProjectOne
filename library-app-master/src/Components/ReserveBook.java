package Components;

import DatabaseConnection.DBConnection;
import Departments.BorrowBooks;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class ReserveBook {

    private static JFrame frame;
    private static JPanel panel;

    private static JButton cancelButton;
    private static JButton confirmButton;

    private static JLabel title;
    private static JLabel labelSubTitle;

    private static JRadioButton radioButton1;
    private static JRadioButton radioButton2;
    private static JRadioButton radioButton3;

    private static ButtonGroup buttonGroup;

    public static void initialize() {
        panel = new JPanel();
        panel.setLayout(null);

        title = new JLabel("Choose the booking time period");
        title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        title.setBounds(70, 20, 300, 50);
        panel.add(title);

        labelSubTitle = new JLabel("-");
        labelSubTitle.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        labelSubTitle.setBounds(20, 100, 350, 20);
        panel.add(labelSubTitle);

        buttonGroup = new ButtonGroup();
        radioButton1 = new JRadioButton("2 weeks");
        buttonGroup.add(radioButton1); //always add the button to the group and then add the same button to the panel
        radioButton1.setBounds(30, 130, 100, 20);
        radioButton1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel.add(radioButton1);

        radioButton2 = new JRadioButton("1 month");
        buttonGroup.add(radioButton2);
        radioButton2.setBounds(30, 160, 100, 20);
        radioButton2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel.add(radioButton2);

        radioButton3 = new JRadioButton("2 months");
        buttonGroup.add(radioButton3);
        radioButton3.setBounds(30, 190, 100, 20);
        radioButton3.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel.add(radioButton3);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(40, 300, 100, 50);
        Styles.buttonStyles(cancelButton);
        panel.add(cancelButton);
        returnToPage(cancelButton);

        confirmButton = new JButton("Confirm Booking");
        confirmButton.setBounds(200, 300, 150, 50);
        Styles.buttonStyles(confirmButton);
        panel.add(confirmButton);
        setConfirmButton(confirmButton);

        frame = new JFrame("Reserve book");
        frame.getContentPane().setBackground(Color.lightGray);
        frame.add(panel);

        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null); //set the frame to the center of the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void returnToPage(JButton button) {

        button.addActionListener(e -> {
            frame.dispose();
            BorrowBooks.getFrame().setEnabled(true);

        });

    }

    public static void setConfirmButton(JButton button) {
        DBConnection.connect();
        JTable table = BorrowBooks.getTable();
        int row = table.getSelectedRow();

        //setting the label with the table selection row
        String bookTitle = BorrowBooks.getTable().getModel().getValueAt(row, 0).toString();
        String authorTitle = BorrowBooks.getTable().getModel().getValueAt(row, 1).toString();
        String resultString = "Book Title: " + bookTitle + " by " + authorTitle;
        labelSubTitle.setText(resultString);

        String isReserved = BorrowBooks.getTable().getModel().getValueAt(row, 2).toString();
        String until = BorrowBooks.getTable().getModel().getValueAt(row, 3).toString();

        String updateQuery =
                "INSERT INTO " + "borrowedbooks(author, bookName, borrowedStart, borrowedEnd) VALUES ("
                        + "'" + authorTitle + "',"
                        + "'" + bookTitle + "',"
                        + "'" + isReserved + "',"
                        + "'" + until + "');";

        String deleteQuery = "DELETE FROM books WHERE name='" + bookTitle + "';";

        button.addActionListener(e -> {
            if (radioButton1.isSelected()) {
                reserveBook(updateQuery, deleteQuery);
            } else if (radioButton2.isSelected()) {
                reserveBook(updateQuery, deleteQuery);
                System.out.println("You borrowed the book for 1 month");
            } else if (radioButton3.isSelected()) {
                reserveBook(updateQuery, deleteQuery);
                System.out.println("You borrowed the book for 2 month");
            } else {
                System.out.println("Problem in time period selection: BorrowBooks.setConfirmButton()");
            }

            frame.dispose();
        });
    }

    private static void reserveBook(String updateQuery, String deleteQuery) {
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.executeUpdate();

            Statement statement = DBConnection.getConnection().createStatement();
            statement.executeUpdate(deleteQuery);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}




