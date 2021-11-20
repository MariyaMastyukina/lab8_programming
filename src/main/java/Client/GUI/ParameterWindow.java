package Client.GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.ResourceBundle;

public class ParameterWindow extends JDialog {
    private JLabel parameterLabel = new JLabel();
    private JButton confirmButton;
    private JTextField parameterField = new JTextField();
    private PipedWriter commandWriter;
    private String command = "";
    private ResourceBundle resourceBundle;
    private AddWindow addWindow;

    public ParameterWindow(JFrame frame, PipedWriter commandWriter, ResourceBundle resourceBundle, AddWindow addWindow) {
        super(frame, resourceBundle.getString("input"), true);
        this.resourceBundle = resourceBundle;
        this.addWindow = addWindow;
        this.commandWriter = commandWriter;
        initComponents();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        setBounds(screen.width / 5, screen.height / 5, screen.width / 3, screen.width / 3);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(parameterLabel, BorderLayout.NORTH);
        add(parameterField, BorderLayout.CENTER);
        add(confirmButton, BorderLayout.SOUTH);
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    private void initComponents() {
        confirmButton = new JButton(resourceBundle.getString("confirm"));
        parameterLabel.setText(resourceBundle.getString("requireSimple") + command);
        confirmButton.addActionListener(new confirmButtonListener(this));

    }
    private class confirmButtonListener implements ActionListener {
        Component component;

        confirmButtonListener(Component component) {
            this.component = component;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                if (checkParameter(parameterField.getText(), component)) {
                    if (command.equals("update")) {
                        setVisible(false);
                        addWindow.setCommandName(command + " " + parameterField.getText());
                        addWindow.prepare();
                    } else {
                        String line = command + " " + parameterField.getText();
                        commandWriter.write(line + "\n");
                        commandWriter.flush();
                        setVisible(false);
                    }
                    parameterField.setText("");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean checkParameter(String par, Component component) {
        boolean checker = true;
        if (par.isEmpty()) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Параметр не может быть пустым", "F", JOptionPane.ERROR_MESSAGE);
        } else if (command.equals("remove_by_id") || command.equals("update")) {
            try {
                Long.parseLong(par);
                checker = true;
            } catch (NumberFormatException e) {
                checker = false;
                JOptionPane.showMessageDialog(component, "Выполнение команды невозможно. Введен некорректный id, повторите ввод команды", "FFF", JOptionPane.ERROR_MESSAGE);
            }
        } else if (command.equals("remove_all_by_meters_above_sea_level")) {
            try {
                Integer.parseInt(par);
                checker = true;
            } catch (NumberFormatException e) {
                checker = false;
                JOptionPane.showMessageDialog(component, "Выполнение команды невозможно. Введен некорректный meters_above_sea_level, повторите ввод команды", "FF", JOptionPane.ERROR_MESSAGE);
            }
        }
        return checker;
    }
}
