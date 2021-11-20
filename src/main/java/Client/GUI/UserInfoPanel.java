package Client.GUI;


import Utils.UserUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserInfoPanel extends JPanel {
    private MainWindow mainWindow;
    private JButton logOut;
    private JButton tableButton;
    private JButton visualButton;
    private JLabel label;
    private JLabel labelUser;
    private PipedWriter commandWriter;
    private ResourceBundle resourceBundle;
    private JPanel userInfoPanel;
    private JPanel buttonPanel;

    public UserInfoPanel(PipedWriter commandWriter, ResourceBundle resourceBundle, MainWindow mainWindow, JComboBox<Locale> localCombo, UserUtils userUtils) {
        this.commandWriter = commandWriter;
        this.resourceBundle = resourceBundle;
        this.mainWindow = mainWindow;
        initComponents(localCombo, userUtils);
        setLayout(new BorderLayout());
        add(userInfoPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private void initComponents(JComboBox<Locale> localCombo, UserUtils userUtils) {
        userInfoPanel = new JPanel(new GridLayout(2, 1));
        labelUser = new JLabel();
        labelUser.setText(userUtils.getLogin());
        label = new JLabel();
        label.setText(resourceBundle.getString("enteredAs"));
        logOut = new JButton();
        logOut.setText(resourceBundle.getString("changeUser"));
        logOut.addActionListener(e -> {
            UserUtils.setPermission(false);
            try {
                commandWriter.write("exit \n");
                commandWriter.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        tableButton = new JButton();
        buttonPanel = new JPanel(new GridLayout(1, 2));
        tableButton.setText(resourceBundle.getString("table"));
        tableButton.addActionListener(e -> {
            CardLayout layout = (CardLayout) (mainWindow.getCardLayout().getLayout());
            layout.show(mainWindow.getCardLayout(), "Таблица");
        });

        visualButton = new JButton();
        visualButton.setText(resourceBundle.getString("visual"));
        visualButton.addActionListener(e -> {
            CardLayout layout = (CardLayout) (mainWindow.getCardLayout().getLayout());
            layout.show(mainWindow.getCardLayout(), "Визуализация");
        });
        buttonPanel.add(tableButton, BorderLayout.WEST);
        buttonPanel.add(visualButton, BorderLayout.CENTER);
        buttonPanel.add(localCombo, BorderLayout.EAST);
        userInfoPanel.add(label);
        userInfoPanel.add(labelUser);
        userInfoPanel.add(logOut);
    }

    public void setUserLabel(String user) {
        labelUser.setText(user);
    }

    public void updateText(ResourceBundle res) {
        label.setText(res.getString("enteredAs"));
        logOut.setText(res.getString("changeUser"));
        visualButton.setText(res.getString("visual"));
        tableButton.setText(res.getString("table"));
    }
}
