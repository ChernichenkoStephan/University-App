package org.university.View;

import org.university.Controller.ViewController;

import javax.swing.*;
import java.awt.*;

public class View {
    protected JPanel mainPanel;

    protected ViewController viewController = ViewController.defaultController;

    public View() {
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(800, 550));
        mainPanel.setSize(800, 550);
    }

    public JPanel panel() {
        return mainPanel;
    }

    public String name;

    protected void prepareGUI(){ }

    protected Integer open(String text) {
        if (!text.equalsIgnoreCase("")) {
            try {
                Integer res = Integer.parseInt(text);
                return res;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                viewController.warning("Error in number field");
                return -1;
            }
        }
        return -1;
    }
}
