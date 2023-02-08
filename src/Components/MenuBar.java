package Components;

import Departments.BorrowBooks;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MenuBar {

    private static JMenuBar menuBar;

    private static JMenu fileMenu;
    private static JMenu editMenu;
    private static JMenu helpMenu;

    private static JMenuItem refreshItem;
    private static JMenuItem saveItem;
    private static JMenuItem exitItem;


    public static JMenuBar getMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.PINK);

        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        helpMenu = new JMenu("Help");

        refreshItem = new JMenuItem("Refresh");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        fileMenu.add(refreshItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        refreshTable(refreshItem);
        setExitItem(exitItem);
        return menuBar;
    }


    public static void setExitItem(JMenuItem exit) {
        exit.addActionListener(e -> {
            System.exit(1);
        });
    }

    public static void refreshTable(JMenuItem refresh) {
        refresh.addActionListener(e -> {

            BorrowBooks.initialize(); //not working
        });
    }
}
