package Client.GUI;

import Server.Collection.ControlUnit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AddWindow extends JDialog {
    boolean checker=true;
    String command;
    private JTextField nameField1=new JTextField();
    private JTextField xField2=new JTextField();
    private JTextField yField3=new JTextField();
    private JTextField areaField4=new JTextField();
    private JTextField populationField5=new JTextField();
    private JTextField metersField6=new JTextField();
    private JComboBox climateBox1=new JComboBox();
    private JComboBox capitalBox2=new JComboBox();
    private JComboBox governmentBox3=new JComboBox();
    private JTextField governorField7=new JTextField();
    private JButton confirmButton=new JButton();
    private JPanel panel=new JPanel();
    private JLabel nameLabel=new JLabel();
    private JLabel xLabel=new JLabel();
    private JLabel yLabel=new JLabel();
    private JLabel areaLabel=new JLabel();
    private JLabel popLabel=new JLabel();
    private JLabel metersLabel=new JLabel();
    private JLabel climateLabel=new JLabel();
    private JLabel capitalLabel=new JLabel();
    private JLabel governmentLabel=new JLabel();
    private JLabel governorLabel=new JLabel();
    private PipedWriter cmdWriter;
    private ResourceBundle res;
    public AddWindow(PipedWriter writer, JFrame owner, ResourceBundle res){
        super(owner,res.getString("input"),true);
        cmdWriter=writer;
        this.res=res;
        Toolkit toolkit=Toolkit.getDefaultToolkit();
//        Dimension screen = toolkit.getScreenSize();
//        setBounds(0 ,0, 900000000 , 900000000);
        panel.setLayout(new GridLayout(10,1,3,5));
        ConfirmListener confirmListener=new ConfirmListener(this);
        confirmButton.setText(res.getString("confirm"));
        confirmButton.addActionListener(confirmListener);
        setLayout(new BorderLayout());
        climateBox1.addItem("");
        climateBox1.addItem("HUMIDCONTINENTAL");
        climateBox1.addItem("SUBARCTIC");
        climateBox1.addItem("TUNDRA");
        capitalBox2.addItem("");
        capitalBox2.addItem("true");
        capitalBox2.addItem("false");
        governmentBox3.addItem("");
        governmentBox3.addItem("CORPORATOCRACY");
        governmentBox3.addItem("MERITOCRACY");
        governmentBox3.addItem("OLIGARCHY");
        panel.add(nameLabel);
        panel.add(nameField1);
        panel.add(xLabel);
        panel.add(xField2);
        panel.add(yLabel);
        panel.add(yField3);
        panel.add(areaLabel);
        panel.add(areaField4);
        panel.add(popLabel);
        panel.add(populationField5);
        panel.add(metersLabel);
        panel.add(metersField6);
        panel.add(capitalLabel);
        panel.add(capitalBox2);
        panel.add(climateLabel);
        panel.add(climateBox1);
        panel.add(governmentLabel);
        panel.add(governmentBox3);
        panel.add(governorLabel);
        panel.add(governorField7);
        add(panel,BorderLayout.CENTER);
        add(confirmButton,BorderLayout.SOUTH);
        updateText(res);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(toolkit.getScreenSize().width/3,toolkit.getScreenSize().height/2);
        System.out.println(this.getSize());
    }
    public void setCommand(String command){
        this.command=command;
    }
    public void prepare() {
        nameField1.setText("");
        xField2.setText("");
        yField3.setText("");
        areaField4.setText("");
        populationField5.setText("");
        metersField6.setText("");
        governorField7.setText("");
        setVisible(true);
    }
    public class ConfirmListener implements ActionListener{
        Component component;
        ConfirmListener(Component component){
            this.component=component;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                checker=checkFields(component);
                if (checker) {
                    cmdWriter.write(command+"\n");
                    cmdWriter.write(nameField1.getText() + "\n");
                    cmdWriter.write(xField2.getText() + "\n");
                    cmdWriter.write(yField3.getText() + "\n");
                    cmdWriter.write(areaField4.getText() + "\n");
                    cmdWriter.write(populationField5.getText() + "\n");
                    cmdWriter.write(metersField6.getText() + "\n");
                    cmdWriter.write(capitalBox2.getSelectedItem().toString() + "\n");
                    cmdWriter.write(climateBox1.getSelectedItem().toString() + "\n");
                    cmdWriter.write(governmentBox3.getSelectedItem().toString() + "\n");
                    cmdWriter.write(governorField7.getText() + "\n");
                    cmdWriter.flush();
                    setVisible(false);
                }
            }  catch (IllegalArgumentException ex) {
                checker=false;
                JOptionPane.showMessageDialog(component, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (IOException ex){
                ex.printStackTrace();
            }

        }
    }
    public void updateText(ResourceBundle res) {
        this.res = res;
        nameLabel.setText(res.getString("name"));
        xLabel.setText(res.getString("x"));
        yLabel.setText(res.getString("y"));
        areaLabel.setText(res.getString("area"));
        popLabel.setText(res.getString("population"));
        metersLabel.setText(res.getString("meters"));
        climateLabel.setText(res.getString("climate"));
        capitalLabel.setText(res.getString("capital"));
        governmentLabel.setText(res.getString("government"));
        governorLabel.setText(res.getString("governor"));
        confirmButton.setText(res.getString("confirm"));
        setTitle(res.getString("input"));
        pack();
    }
    public boolean checkFields(Component component) {
        boolean checker;
        if (nameField1.getText().isEmpty()){
            checker=false;
            JOptionPane.showMessageDialog(component, "Имя города не может быть пустым", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if (xField2.getText().isEmpty()){
            checker=false;
            JOptionPane.showMessageDialog(component,"Координата Х не может быть пустой" , "Error", JOptionPane.ERROR_MESSAGE);
        }else if (!checkX(xField2.getText(),component)){
            checker=false;
        }
        else if (yField3.getText().isEmpty()){
            checker=false;
            JOptionPane.showMessageDialog(component,"Координата Y не может быть пустой" , "Error", JOptionPane.ERROR_MESSAGE);
        }else if (!checkY(yField3.getText(),component)){
            checker= false;
        }
        else if (areaField4.getText().isEmpty()){
            checker=false;
            JOptionPane.showMessageDialog(component,"Площадь не может быть пустой" , "Error", JOptionPane.ERROR_MESSAGE);

        }else if (!checkArea(areaField4.getText(),component)){
            checker=false;
        }
        else if (populationField5.getText().isEmpty()){
            checker=false;
            JOptionPane.showMessageDialog(component,"Население не может быть пустым" , "Error", JOptionPane.ERROR_MESSAGE);
        }else if (!checkPopulation(populationField5.getText(),component)){
            checker=false;
        }
        else if(!checkMeters(metersField6.getText(),component)){
            checker=false;
        }
        else if (capitalBox2.getSelectedItem().equals("")){
            checker=false;
            JOptionPane.showMessageDialog(component,"Выберите столицу" , "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if (climateBox1.getSelectedItem().equals("")){
            checker=false;
            JOptionPane.showMessageDialog(component,"Выберите климат", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if (governmentBox3.getSelectedItem().equals("")){
            checker=false;
            JOptionPane.showMessageDialog(component,"Выберите правительство", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if (governorField7.getText().isEmpty()){
            checker=false;
            JOptionPane.showMessageDialog(component,"Имя губернатора не может быть пустым", "Error", JOptionPane.ERROR_MESSAGE);
        }else {
            checker = checkName(governorField7.getText(), component);
        }
    return checker;
    }

    public boolean checkX(String x,Component component) {
        boolean checker=true;
        try {
            Float X = Float.parseFloat(x);
            if (X <= -375F) {
                checker=false;
                JOptionPane.showMessageDialog(component,"Указано неверное значение координаты X. Значение не может быть меньше -375", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            checker=false;
            JOptionPane.showMessageDialog(component,"Указан неверный формат координаты Х", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }
    public boolean checkY(String y,Component component) {
        boolean checker=true;
        try{
            Integer Y=Integer.parseInt(y);
            if (Y <= -966) {
                checker=false;
                JOptionPane.showMessageDialog(component,"Указан неверное значение координаты Y. Значение не может быть меньше -966", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            checker=false;
            JOptionPane.showMessageDialog(component,"Указан неверный формат координаты Y", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }
    public boolean checkArea(String area,Component component){
        boolean checker=true;
        try {
            Double AREA = Double.parseDouble(area);
            if (AREA <= 0) {
                checker=false;
                JOptionPane.showMessageDialog(component,"Указано неверное значение площади города. Значение не может быть отрицательным или равным 0", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            checker=false;
            JOptionPane.showMessageDialog(component,"Указан неверный формат площади города", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }
    public boolean checkPopulation(String population,Component component){
        boolean checker=true;
        try {
            Integer Population = Integer.parseInt(population);
            if (Population <= 0) {
                checker=false;
                JOptionPane.showMessageDialog(component,"Указано неверное значение населения города. Значение не может быть отрицательным или равным 0", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            checker=false;
            JOptionPane.showMessageDialog(component,"Указан неверный формат населения", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }
    public boolean checkName(String name,Component component){
        boolean checker=true;
        char[] symbols = name.toLowerCase().toCharArray();
        Boolean checkername1 = true;
        String validationName = "abcdefghijklmnopqrstuvwxyzабвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        for (char c : symbols) {
            if (validationName.indexOf(c) == -1) {
                checkername1 = false;
            }
        }
        if (!checkername1) {
            checker=false;
            JOptionPane.showMessageDialog(component,"Указано неверное имя губернатора. Используйте только латинские символы", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }
    public boolean checkMeters(String name,Component component){
        boolean checker= true;
        try {
            Integer.parseInt(name);
        }
        catch (NumberFormatException e){
            checker=false;
            JOptionPane.showMessageDialog(component,"Указан неверный формат метров", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }
}
