package Client.GUI;

import Client.DataUtils.User;
import javafx.scene.control.ComboBox;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserInfoPanel extends JPanel{
    MainWindow frame;
    JButton user=new JButton();
    JButton table=new JButton();
    JButton visual=new JButton();
    static JLabel label=new JLabel();
    static JLabel labelUser=new JLabel();
    PipedWriter writer;
    public UserInfoPanel(PipedWriter writer, ResourceBundle res, MainWindow frame, JComboBox<Locale> localCombo){
        this.writer=writer;
        this.frame=frame;
        setLayout(new BorderLayout());
        label.setText(res.getString("enteredAs"));
        JPanel panel1=new JPanel(new GridLayout(2,1));
        panel1.add(label);
        panel1.add(labelUser);
        user.setText(res.getString("changeUser"));
        user.addActionListener(e->{
            User.permission=false;
            try {
                writer.write("exit \n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        panel1.add(user);
        add(panel1,BorderLayout.WEST);
        JPanel panel2=new JPanel(new GridLayout(1,2));
        table.setText(res.getString("table"));
        table.addActionListener(e -> {
            CardLayout layout=(CardLayout)(frame.cardLayout.getLayout());
            layout.show(frame.cardLayout,"Таблица");
        });
        visual.setText(res.getString("visual"));
        visual.addActionListener(e -> {
            CardLayout layout=(CardLayout)(frame.cardLayout.getLayout());
            layout.show(frame.cardLayout,"Визуализация");
        });
        panel2.add(table,BorderLayout.WEST);
        panel2.add(visual,BorderLayout.CENTER);
        panel2.add(localCombo,BorderLayout.EAST);
        add(panel2,BorderLayout.CENTER);
    }
    public static void setUserLabel (String user){
        labelUser.setText(user);
    }
    public void updateText(ResourceBundle res){
        label.setText(res.getString("enteredAs"));
        user.setText(res.getString("changeUser"));
        visual.setText(res.getString("visual"));
        table.setText(res.getString("table"));
    }
}
