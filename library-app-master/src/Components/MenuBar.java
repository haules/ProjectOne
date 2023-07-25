package Components;

import Departments.BorrowBooks;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MenuBar {

    private static JMenuBar menuBar;

    private static JMenu fileMenu;
    private static JMenu helpMenu;

    private static JMenuItem saveItem;
    private static JMenuItem exitItem;

    private static JMenuItem version;

//abc
    public static JMenuBar getMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.BLUE);

        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");

        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");
        version = new JMenuItem("Version");

        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        helpMenu.add(version);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setExitItem(exitItem);
        return menuBar;
    }


    public static void setExitItem(JMenuItem exit) {
        exit.addActionListener(e -> {
            System.exit(1);
        });
    }
}
