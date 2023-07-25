package Departments;

import Components.MenuBar;
import Components.ReserveBook;
import Components.Styles;
import DatabaseConnection.DBConnection;
import LoginAndMenu.MainMenu;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Arrays;
import java.util.Vector;

public class BorrowBooks {

    private static JLabel searchBookResultLabel;
    private static JLabel title;
    private static JLabel bookNameLabel;
    private static JLabel author;
    private static JLabel authorLabel;
    private static JLabel reservedDate;
    private static JLabel reservedDateLabel;
    private static JLabel isReserved;
    private static JLabel isReservedLabel;
    private static JLabel bottomLabel;

    private static JTextField searchBooks;

    private static JButton reserveBook;
    private static JButton backButton;
    private static JButton submitButton;

    private static JFrame frame;
    private static JPanel panel;

    private static JTable table;
    private static JScrollPane jScrollPane;

    private static DefaultTableModel tableModel;

    public static void initialize() {
        panel = new JPanel();
        panel.setLayout(null);

        title = new JLabel("Book Database - Reserve a Book");
        title.setBounds(200, 30, 300, 35);
        title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panel.add(title);

        searchBooks = new JTextField();
        searchBooks.setBounds(50, 70, 500, 30);
        searchBooks.setToolTipText("Type a name for the desired book");
        panel.add(searchBooks);

        bookNameLabel = new JLabel("Name:");
        bookNameLabel.setBounds(50, 150, 200, 30);
        panel.add(bookNameLabel);

        searchBookResultLabel = new JLabel("-");
        searchBookResultLabel.setBounds(130, 150, 200, 30);
        panel.add(searchBookResultLabel);

        authorLabel = new JLabel("Author:");
        authorLabel.setBounds(50, 175, 100, 30);
        panel.add(authorLabel);

        author = new JLabel("-");
        author.setBounds(130, 175, 250, 30);
        panel.add(author);

        isReservedLabel = new JLabel("Reserved?");
        isReservedLabel.setBounds(50, 205, 100, 30);
        panel.add(isReservedLabel);

        isReserved = new JLabel("-");
        isReserved.setBounds(130, 205, 200, 30);
        panel.add(isReserved);

        reservedDateLabel = new JLabel("Until:");
        reservedDateLabel.setBounds(50, 235, 100, 30);
        panel.add(reservedDateLabel);

        reservedDate = new JLabel("-");
        reservedDate.setBounds(130, 235, 150, 30);
        panel.add(reservedDate);

        table = new JTable();
        try {
            resultSetToTableModel(table);
        } catch (Exception e) {
            e.printStackTrace();
        }

        jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(50, 300, 600, 300);
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

        reserveBook = new JButton("Reserve");
        reserveBook.setBounds(100, 630, 100, 40);
        Styles.buttonStyles(reserveBook);
        reserveButton(reserveBook);

        panel.add(reserveBook);

        submitButton = new JButton("Find");
        submitButton.setBounds(560, 70, 100, 28);
        submitButton.setFont(new Font("Arial", Font.BOLD, 10));
        Styles.buttonStyles(submitButton);
        panel.add(submitButton);
        findBook(submitButton);

        bottomLabel = new JLabel("© 2022 Library App");
        bottomLabel.setFont(new Font("Calibri", Font.ITALIC, 10));
        bottomLabel.setBounds(310, 750, 100, 10);
        panel.add(bottomLabel);

        frame = new JFrame("Book Department");
        frame.getContentPane().setBackground(Color.lightGray);
        frame.add(panel);

        frame.setJMenuBar(MenuBar.getMenuBar());
        frame.getContentPane().setBackground(Color.BLUE);
        frame.setSize(700, 800);

        frame.setLocationRelativeTo(null); //set the frame to the center of the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    public static void findBook(JButton button) {
        DBConnection.connect();
        System.out.println("Borrow a Book department");

        button.addActionListener(e -> {
            try {
                String name = searchBooks.getText();
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE name=?");
                preparedStatement.setString(1, name);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String labelName = resultSet.getString(2);
                    String labelAuthor = resultSet.getString(3);
                    String labelReserved = resultSet.getString(4);
                    String labelUntil = resultSet.getString(5);
                    searchBookResultLabel.setText(labelName);
                    author.setText(labelAuthor);
                    isReserved.setText(labelReserved);
                    reservedDate.setText(labelUntil);
                } else {
                    JOptionPane.showMessageDialog(null, "Book not found");
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                System.out.println("Issues in findBook method");
            }
        });
    }

    public static void resultSetToTableModel(JTable table) throws SQLException {
        table.setDefaultEditor(Object.class, null);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // only one row is selected
//        table.getTableHeader().setResizingAllowed(false); //this line of code is stopping the user to resize the columns
        table.getTableHeader().setEnabled(false); // stops the user to sort the column order

        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.YELLOW);

        DBConnection.connect();
        String query = "SELECT * FROM books";

        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

//        //Create new table model
        tableModel = new DefaultTableModel(new String[]{"Name of the Book", "Name of the Author", "Reserved?", "Until"}, 0);

        while (resultSet.next()) {
            String bookName = resultSet.getString("name");
            String bookAuthor = resultSet.getString("author");
            String bookReservation = resultSet.getString("reserved");
            String untilDate = resultSet.getString("theDate");

            tableModel.addRow(new Object[]{bookName, bookAuthor, bookReservation, untilDate});

        }

        table.setModel(tableModel);

        table.getSelectionModel().addListSelectionListener(e -> {
//            table.setCellSelectionEnabled(false);
//            table.setRowSelectionAllowed(true);

            String nameGetText = table.getValueAt(table.getSelectedRow(), 0).toString();
            String authorGetText = table.getValueAt(table.getSelectedRow(), 1).toString();
            String reservedGetText = table.getValueAt(table.getSelectedRow(), 2).toString();
            String untilGetText = table.getValueAt(table.getSelectedRow(), 3).toString();

            searchBookResultLabel.setText(nameGetText);
            author.setText(authorGetText);
            isReserved.setText(reservedGetText);
            reservedDate.setText(untilGetText);

            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        });
    }

    public static void reserveButton(JButton button) {
        frame = new JFrame();
        button.setEnabled(false);

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int rowIndex = table.getSelectedRow();
                button.setEnabled(rowIndex != -1);
                frame.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        frame.requestFocusInWindow();
                        button.setEnabled(false);
                    }
                });


                button.addActionListener(event -> {
                    button.setEnabled(true);
                    ReserveBook.initialize();
                });
            }


        });


        frame.setEnabled(true);
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static JTable getTable() {
        return table;
    }
}
