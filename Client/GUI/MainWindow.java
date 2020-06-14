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
    Request table;
    CityTableModel tableModel;
    ResourceBundle res;
    static AddWindow readCity;
    static UserInfoPanel infopanel;
    ButtonPanel buttonPanel;
    static JTextArea textArea;
    JPanel cardLayout;
    TablePanel tablePanel;
    VisualObjectPanel visualPanel;
    public MainWindow(Locale[] locales, PipedWriter cmdWriter, Request table,ResourceBundle res){
        super("LABA 8");
        this.table=table;
        int localIndex=0;
        for (int i=0;i<locales.length;i++){
            if (Locale.getDefault().equals(locales[i])) localIndex=i;
        }
        System.out.println(table);
        tableModel=new CityTableModel(res,table);
        this.res=res;
        readCity=new AddWindow(cmdWriter,this,res);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tablePanel=new TablePanel(tableModel,cmdWriter,res,table);
        visualPanel=new VisualObjectPanel(table,cmdWriter,res);
        buttonPanel=new ButtonPanel(this,cmdWriter,res);
        localCombo=new JComboBox<>(locales);
        infopanel=new UserInfoPanel(cmdWriter,res, this,localCombo);
        setCurrentlocale(locales[localIndex]);
        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension screen=kit.getScreenSize();
        setBounds(screen.width/5,screen.height/5,screen.width*4/5,screen.height*4/5);
        setLayout(new BorderLayout());
        textArea=new JTextArea(5,50);
        JScrollPane scroll=new JScrollPane(textArea);
        add(scroll,BorderLayout.SOUTH);
        add(infopanel,BorderLayout.NORTH);
        cardLayout=new JPanel(new CardLayout());
        cardLayout.add(tablePanel,"Таблица");
        cardLayout.add(visualPanel,"Визуализация");
        cardLayout.getLayout();
        add(cardLayout, BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.WEST);
        localCombo.addActionListener(e -> {
            setCurrentlocale((Locale)localCombo.getSelectedItem());
            validate();
        });
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                visualPanel.updateVisual(table.getNew_map());
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }
    public void readCity(){
        readCity.prepare();
    }
    public static void addAnswer(String message){
        textArea.append(message+"\n");
    }
    public void setUserInfo(String user){
        infopanel.setUserLabel(user);
    }
    public void setCurrentlocale(Locale locale){
        res = ResourceBundle.getBundle("Client.Resources.ProgramResources", locale);
        tablePanel.setRes(res);
        tablePanel.updateTime();
        infopanel.updateText(res);
        localCombo.setSelectedItem(locale);
        buttonPanel.updateText(res);
        visualPanel.setRes(res);
        readCity.updateText(res);
    }

    public CityTableModel getTableModel() {
        return tableModel;
    }

    public VisualObjectPanel getVisualPanel() {
        return visualPanel;
    }

    public void setTableK(Request table) {
        this.table = table;
    }

    public void updateVisual(Request table){
        visualPanel.updateVisual(table.getNew_map());
    }
}
