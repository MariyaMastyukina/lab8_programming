package Client.GUI;

import Server.Collection.City;
import Server.Request;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.PipedWriter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainWindow extends JFrame {
    JComboBox<Locale> localCombo;
    ResourceBundle res;
    static AddWindow readCity;
    static UserInfoPanel infopanel;
    ButtonPanel buttonPanel;
    static JTextArea textArea;
    JPanel cardLayout;
    VisualObjectPanel visualPanel;
    public MainWindow(Locale[] locales, PipedWriter cmdWriter, Request table,ResourceBundle res){
        super("LABA 8");
        int localIndex=0;
        for (int i=0;i<locales.length;i++){
            if (Locale.getDefault().equals(locales[i])) localIndex=i;
        }
        this.res=res;
        readCity=new AddWindow(cmdWriter,this,res);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        visualPanel=new VisualObjectPanel(table,cmdWriter,res);
        buttonPanel=new ButtonPanel(this,cmdWriter,res);
        localCombo=new JComboBox<>(locales);
        infopanel=new UserInfoPanel(cmdWriter,res, this,localCombo);
        setCurrentlocale(locales[localIndex]);
        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension screen=kit.getScreenSize();
        setBounds(screen.width/5,screen.height/5,screen.width*3/5,screen.height*3/5);
        setLayout(new BorderLayout());
        textArea=new JTextArea(10,50);
        JScrollPane scroll=new JScrollPane(textArea);
        add(scroll,BorderLayout.SOUTH);
        add(infopanel,BorderLayout.NORTH);
        cardLayout=new JPanel(new CardLayout());
        cardLayout.getLayout();
        cardLayout.add(visualPanel,"Визуализация");
        add(cardLayout, BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.WEST);
        localCombo.addActionListener(e -> {
            setCurrentlocale((Locale)localCombo.getSelectedItem());
            validate();
        });

    }
    public void readCity(){
        readCity.prepare();
    }
    public static void addAnswer(String message){
        textArea.setText(message);
    }
    public void setUserInfo(String user){
        infopanel.setUserLabel(user);
    }
    public void setCurrentlocale(Locale locale){
        res = ResourceBundle.getBundle("Client.Resources.ProgramResources", locale);
//        tableModel.fireTableStructureChanged();
        infopanel.updateText(res);
        localCombo.setSelectedItem(locale);
        buttonPanel.updateText(res);
    }
}
