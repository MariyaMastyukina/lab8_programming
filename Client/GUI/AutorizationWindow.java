package Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PipedWriter;

public class AutorizationWindow extends JFrame{
    PipedWriter writer;
    JLabel label;
    public AutorizationWindow(PipedWriter writer){
        super("Авторизация");
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        setBounds(toolkit.getScreenSize().width/3,toolkit.getScreenSize().height/3,toolkit.getScreenSize().width/3,toolkit.getScreenSize().height/3);
        this.writer=writer;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(4,1));
        label=new JLabel("Приветствую, дорогой пользователь!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        label=new JLabel("Если ты хочешь зарегаться, жми кнопку check_in");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        label=new JLabel("Если ты хочешь зайти в свой аккаунт, жми кнопку sign_in");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        label=new JLabel("Если же ты хочешь покинуть нас, придется нажать кнопку exit");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        add(panel,BorderLayout.CENTER);
        JPanel panelButton=new JPanel(new GridLayout(1,3));
        JButton check_in=new JButton("check_in");
        check_in.addActionListener(e->{
            try {
                writer.write("check_in\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        panelButton.add(check_in);
        JButton sign_in=new JButton("sign_in");
        sign_in.addActionListener(e->{
            try {
                writer.write("sign_in\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        panelButton.add(sign_in);
        JButton exit=new JButton("exit");
        exit.addActionListener(e->System.exit(1));
        panelButton.add(exit);
        add(panelButton,BorderLayout.SOUTH);
}

}
