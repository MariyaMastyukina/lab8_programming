package Client.GUI;

import sun.jvm.hotspot.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.ResourceBundle;

public class ParameterWindow extends JDialog {
    private JLabel label = new JLabel();
    private JButton button;
    private JTextField textField = new JTextField();
    private PipedWriter writer;
    private String command = "";
    private ResourceBundle res;
    private AddWindow read;

    public ParameterWindow(JFrame frame, PipedWriter writer, ResourceBundle res, AddWindow read) {
        super(frame, res.getString("input"), true);
        this.res = res;
//        read=new AddWindow(writer,frame,res);
        this.read=read;
        button = new JButton(res.getString("confirm"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.writer = writer;
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        setBounds(screen.width / 5, screen.height / 5, screen.width / 3, screen.width / 3);
        setLayout(new BorderLayout());
        label.setText(res.getString("requireSimple") + command);
        add(label, BorderLayout.NORTH);
        add(textField, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
        button.addActionListener(new buttonListener(this));
    }

    public void setRes(ResourceBundle res) {
        this.res = res;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public class buttonListener implements ActionListener {
        Component component;

        buttonListener(Component component) {
            this.component = component;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

                try {
                    if (checkParameter(textField.getText(),component)) {
                        if (command.equals("update")) {
                            setVisible(false);
                            read.setCommand(command+" "+textField.getText());
                            read.prepare();
                        }
                        else {
                            String line = command + " " + textField.getText();
                            writer.write(line + "\n");
                            writer.flush();
                            setVisible(false);
                        }
                        textField.setText("");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        public boolean checkParameter(String par, Component component) {
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
