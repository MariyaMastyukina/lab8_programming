package Client.GUI;

import Server.Launch.ControlUnit;
import Server.Commands.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.*;

public class ButtonPanel extends JPanel{
    private PipedWriter writer;
    private JFrame frame;
    private ParameterWindow parameterWindow;
    private SimpleListener simpleListener;
    private NotSimpleListener notSimpleListener;
    private HashMap<String,JButton> buttons=new HashMap<>();
    AddWindow read;
    ButtonPanel(JFrame frame, PipedWriter writer, ResourceBundle res){
        this.frame=frame;
        read=new AddWindow(writer,frame,res);
        this.writer=writer;
        parameterWindow=new ParameterWindow(frame, writer,res,read);
        simpleListener=new SimpleListener();
        notSimpleListener=new NotSimpleListener();
        setLayout(new GridLayout(ControlUnit.getCommands().size()-1,1,3,5));
        Map<String,Command> commands=ControlUnit.getCommands();
        commands.remove("sign_in");
        commands.remove("check_in");
        for (Command command:commands.values()){
                if (command.getargsSize()==1){
                    System.out.println(command.getName());
                    addButton(command.getName(),notSimpleListener);
                }
                else{
                    addButton(command.getName(),simpleListener);
                }
        }
        addButton("execute_script",notSimpleListener);
    }
    public void addButton(String name, ActionListener listener){
        JButton button=new JButton(name);
        button.addActionListener(listener);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setPreferredSize(new Dimension(200,500));
        buttons.put(name,button);
        add(button);
    }
    void updateText(ResourceBundle res){
        for (Map.Entry<String,JButton> button:buttons.entrySet()){
            button.getValue().setText(res.getString(button.getKey()));
        }
        parameterWindow.setRes(res);
    }
    public class SimpleListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
            ResourceBundle res=ResourceBundle.getBundle("Client.Resources.ButtonResources");
            if (res.getString(e.getActionCommand()).equals("add")){
                read.setCommand("add");
                read.prepare();
            }
            else {
                writer.write(res.getString(e.getActionCommand()) + "\n");
                writer.flush();
            }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class NotSimpleListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
                ResourceBundle res=ResourceBundle.getBundle("Client.Resources.ButtonResources");
                parameterWindow.setCommand(res.getString(e.getActionCommand()));
                parameterWindow.setVisible(true);
        }
    }
}
