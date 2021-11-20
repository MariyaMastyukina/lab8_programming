package Client.GUI;

import Server.Commands.Command;
import Server.Launch.ControlUnit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ButtonPanel extends JPanel {
    private PipedWriter commandWriter;
    private JFrame frame;
    private ParameterWindow parameterWindow;
    private Map<String, JButton> buttons = new HashMap<>();
    private AddWindow addWindow;
    private ResourceBundle resourceBundle;

    ButtonPanel(JFrame frame, PipedWriter commandWriter, ResourceBundle resourceBundle) {
        this.frame = frame;
        this.commandWriter = commandWriter;
        this.resourceBundle = resourceBundle;
        initComponents();
        setLayout(new GridLayout(buttons.size(), 1, 3, 5));
    }

    private void initComponents() {
        addWindow = new AddWindow(commandWriter, frame, resourceBundle);
        parameterWindow = new ParameterWindow(frame, commandWriter, resourceBundle, addWindow);
        initButtons();
    }

    private void initButtons() {
        Map<String, Command> commands = new HashMap<>();
        for (String nameCommand : ControlUnit.getCommands().keySet()) {
            if (ControlUnit.getCommands().get(nameCommand).isButton()) {
                commands.put(nameCommand, ControlUnit.getCommands().get(nameCommand));
            }
        }
        for (Command command : commands.values()) {
            if (command.getArgsSize() == 1) {
                addButton(command.getName(), new NotSimpleListener());
            } else {
                addButton(command.getName(), new SimpleListener());
            }
        }
        addButton("execute_script", new NotSimpleListener());
    }

    private void addButton(String name, ActionListener listener) {
        JButton button = new JButton(name);
        button.addActionListener(listener);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setPreferredSize(new Dimension(200, 500));
        buttons.put(name, button);
        add(button);
    }

    public void updateText(ResourceBundle res) {
        for (String name : buttons.keySet()) {
            buttons.get(name).setText(res.getString(name));
            buttons.get(name).setName(name);
        }
        parameterWindow.setResourceBundle(res);
        repaint();
    }

    private class SimpleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("Client.Resources.ButtonResources");
                if (resourceBundle.getString(e.getActionCommand()).equals("add")) {
                    addWindow.setCommandName("add");
                    addWindow.prepare();
                } else {
                    commandWriter.write(resourceBundle.getString(e.getActionCommand()) + "\n");
                    commandWriter.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class NotSimpleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Client.Resources.ButtonResources");
            parameterWindow.setCommand(resourceBundle.getString(e.getActionCommand()));
            parameterWindow.setVisible(true);
        }
    }
}
