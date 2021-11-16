package Client.GUI;

import Server.Collection.City;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EditWindow extends JDialog {
    private JTextField nameField = new JTextField();
    private JTextField xField = new JTextField();
    private JTextField yField = new JTextField();
    private JTextField areaField = new JTextField();
    private JTextField populationField = new JTextField();
    private JTextField metersField = new JTextField();
    private JTextField governorField = new JTextField();
    private JPanel editPanel = new JPanel();
    private JComboBox climateBox = new JComboBox();
    private JComboBox governmentBox = new JComboBox();
    private JComboBox capitalBox = new JComboBox();
    private JLabel ownerLabel = new JLabel();
    private JLabel timeLabel = new JLabel();
    private JButton applyChangesButton = new JButton();
    private JButton removeElementButton = new JButton();
    private JLabel idLabel = new JLabel();
    private JLabel ownerLocLabel = new JLabel();
    private JLabel timeLocLabel = new JLabel();
    private JLabel idLocLabel = new JLabel();
    private JLabel nameLabel = new JLabel();
    private JLabel xLabel = new JLabel();
    private JLabel yLabel = new JLabel();
    private JLabel areaLabel = new JLabel();
    private JLabel populationLabel = new JLabel();
    private JLabel metersLabel = new JLabel();
    private JLabel capitalLabel = new JLabel();
    private JLabel climateLabel = new JLabel();
    private JLabel governmentLabel = new JLabel();
    private JLabel governorLabel = new JLabel();
    private PipedWriter cmdWriter;

    public EditWindow(HashMap<String, Object> defaultValues, PipedWriter cmdWriter, ResourceBundle res) {
        editPanel.setLayout(new GridLayout(14, 1, 3, 5));
        climateBox.addItem("");
        climateBox.addItem("HUMIDCONTINENTAL");
        climateBox.addItem("SUBARCTIC");
        climateBox.addItem("TUNDRA");
        capitalBox.addItem("");
        capitalBox.addItem("true");
        capitalBox.addItem("false");
        governmentBox.addItem("");
        governmentBox.addItem("CORPORATOCRACY");
        governmentBox.addItem("MERITOCRACY");
        governmentBox.addItem("OLIGARCHY");
        editPanel.add(idLocLabel);
        editPanel.add(idLabel);
        editPanel.add(nameLabel);
        editPanel.add(nameField);
        editPanel.add(xLabel);
        editPanel.add(xField);
        editPanel.add(yLabel);
        editPanel.add(yField);
        editPanel.add(timeLocLabel);
        editPanel.add(timeLabel);
        editPanel.add(areaLabel);
        editPanel.add(areaField);
        editPanel.add(populationLabel);
        editPanel.add(populationField);
        editPanel.add(metersLabel);
        editPanel.add(metersField);
        editPanel.add(capitalLabel);
        editPanel.add(capitalBox);
        editPanel.add(climateLabel);
        editPanel.add(climateBox);
        editPanel.add(governmentLabel);
        editPanel.add(governmentBox);
        editPanel.add(governorLabel);
        editPanel.add(governorField);
        editPanel.add(ownerLocLabel);
        editPanel.add(ownerLabel);
        this.cmdWriter = cmdWriter;
        if (defaultValues.get("climate") != null) {
            climateBox.setSelectedItem(defaultValues.get("climate").toString());
        } else {
            climateBox.setSelectedItem("");
        }
        if (defaultValues.get(res.getString("government")) != null) {
            governmentBox.setSelectedItem(defaultValues.get("government").toString());
        } else {
            governmentBox.setSelectedItem("");
        }
        if (defaultValues.get("capital") != null) {
            capitalBox.setSelectedItem(defaultValues.get("capital").toString());
        } else {
            capitalBox.setSelectedItem("");
        }
        nameField.setText(defaultValues.get("name").toString());
        ownerLabel.setText(defaultValues.get("user").toString());
        timeLabel.setText(defaultValues.get("date").toString());
        idLabel.setText(defaultValues.get("id").toString());
        xField.setText(defaultValues.get("x").toString());
        yField.setText(defaultValues.get("y").toString());
        areaField.setText(defaultValues.get("area").toString());
        populationField.setText(defaultValues.get("population").toString());
        metersField.setText(defaultValues.get("meters").toString());
        governorField.setText(defaultValues.get("governor").toString());
        ownerLocLabel.setText(res.getString("owner"));
        timeLocLabel.setText(res.getString("time"));
        idLocLabel.setText(res.getString("id"));
        nameLabel.setText(res.getString("name"));
        xLabel.setText(res.getString("x"));
        yLabel.setText(res.getString("y"));
        areaLabel.setText(res.getString("area"));
        populationLabel.setText(res.getString("population"));
        metersLabel.setText(res.getString("meters"));
        capitalLabel.setText(res.getString("capital"));
        climateLabel.setText(res.getString("climate"));
        governmentLabel.setText(res.getString("government"));
        governorLabel.setText(res.getString("governor"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ClearListener clearListener = new ClearListener();
        UpdateListener updateListener = new UpdateListener(this);
        removeElementButton.setText(res.getString("removeElement"));
        applyChangesButton.setText(res.getString("applyChanges"));
        removeElementButton.addActionListener(clearListener);
        applyChangesButton.addActionListener(updateListener);
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(removeElementButton);
        panel.add(applyChangesButton);
        add(editPanel, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screen = toolkit.getScreenSize();
        setSize(screen.width / 3, screen.height / 2);
        setVisible(true);
    }

    EditWindow(City city, PipedWriter cmdWriter, ResourceBundle res) {
        editPanel.setLayout(new GridLayout(14, 1, 3, 5));
        climateBox.addItem("");
        climateBox.addItem("HUMIDCONTINENTAL");
        climateBox.addItem("SUBARCTIC");
        climateBox.addItem("TUNDRA");
        capitalBox.addItem("");
        capitalBox.addItem("true");
        capitalBox.addItem("false");
        governmentBox.addItem("");
        governmentBox.addItem("CORPORATOCRACY");
        governmentBox.addItem("MERITOCRACY");
        governmentBox.addItem("OLIGARCHY");
        editPanel.add(idLocLabel);
        editPanel.add(idLabel);
        editPanel.add(nameLabel);
        editPanel.add(nameField);
        editPanel.add(xLabel);
        editPanel.add(xField);
        editPanel.add(yLabel);
        editPanel.add(yField);
        editPanel.add(timeLocLabel);
        editPanel.add(timeLabel);
        editPanel.add(areaLabel);
        editPanel.add(areaField);
        editPanel.add(populationLabel);
        editPanel.add(populationField);
        editPanel.add(metersLabel);
        editPanel.add(metersField);
        editPanel.add(capitalLabel);
        editPanel.add(capitalBox);
        editPanel.add(climateLabel);
        editPanel.add(climateBox);
        editPanel.add(governmentLabel);
        editPanel.add(governmentBox);
        editPanel.add(governorLabel);
        editPanel.add(governorField);
        editPanel.add(ownerLocLabel);
        editPanel.add(ownerLabel);
        this.cmdWriter = cmdWriter;
        if (city.getClimate() != null) {
            climateBox.setSelectedItem(city.getClimate().toString());
        } else {
            climateBox.setSelectedItem("");
        }
        if (city.getGovernment() != null) {
            governmentBox.setSelectedItem(city.getGovernment().toString());
        } else {
            governmentBox.setSelectedItem("");
        }
        if (city.getCapital() != null) {
            capitalBox.setSelectedItem(city.getCapital().toString());
        } else {
            capitalBox.setSelectedItem("");
        }
        nameField.setText(city.getNameCity());
        ownerLabel.setText(city.getUser());
        timeLabel.setText(city.getCreationDate().toString());
        idLabel.setText(String.valueOf(city.getIdOfCity()));
        xField.setText(String.valueOf(city.getCoordinates().getX()));
        yField.setText(String.valueOf(city.getCoordinates().getY()));
        areaField.setText(String.valueOf(city.getArea()));
        populationField.setText(String.valueOf(city.getPopulation()));
        metersField.setText(String.valueOf(city.getMetersAboveSeaLevel()));
        governorField.setText(city.getGovernor().toString());
        ownerLocLabel.setText(res.getString("owner"));
        timeLocLabel.setText(res.getString("time"));
        idLocLabel.setText(res.getString("id"));
        nameLabel.setText(res.getString("name"));
        xLabel.setText(res.getString("x"));
        yLabel.setText(res.getString("y"));
        areaLabel.setText(res.getString("area"));
        populationLabel.setText(res.getString("population"));
        metersLabel.setText(res.getString("meters"));
        capitalLabel.setText(res.getString("capital"));
        climateLabel.setText(res.getString("climate"));
        governmentLabel.setText(res.getString("government"));
        governorLabel.setText(res.getString("governor"));
        ClearListener clearListener = new ClearListener();
        UpdateListener updateListener = new UpdateListener(this);
        removeElementButton.addActionListener(clearListener);
        applyChangesButton.addActionListener(updateListener);
        removeElementButton.setText(res.getString("removeElement"));
        applyChangesButton.setText(res.getString("applyChanges"));
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(removeElementButton);
        panel.add(applyChangesButton);
        add(editPanel, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screen = toolkit.getScreenSize();
        setSize(screen.width / 3, screen.height / 2);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public class ClearListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                cmdWriter.write("remove_by_id " + idLabel.getText() + "\n");
                cmdWriter.flush();
                setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class UpdateListener implements ActionListener {
        Component component;

        UpdateListener(Component component) {
            this.component = component;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (checkFields(component)) {
                    cmdWriter.write("update " + idLabel.getText() + "\n");
                    cmdWriter.write(nameField.getText() + "\n");
                    cmdWriter.write(xField.getText() + "\n");
                    cmdWriter.write(yField.getText() + "\n");
                    cmdWriter.write(areaField.getText() + "\n");
                    cmdWriter.write(populationField.getText() + "\n");
                    cmdWriter.write(metersField.getText() + "\n");
                    cmdWriter.write(capitalBox.getSelectedItem().toString() + "\n");
                    cmdWriter.write(climateBox.getSelectedItem().toString() + "\n");
                    cmdWriter.write(governmentBox.getSelectedItem().toString() + "\n");
                    cmdWriter.write(governorField.getText() + "\n");
                    cmdWriter.flush();
                    setVisible(false);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(component, ex.getMessage(), "ОШИБКА", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public boolean checkFields(Component component) {
        boolean checker;
        if (nameField.getText().isEmpty()) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Имя города не может быть пустым", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (xField.getText().isEmpty()) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Координата Х не может быть пустой", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!checkX(xField.getText(), component)) {
            checker = false;
        } else if (yField.getText().isEmpty()) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Координата Y не может быть пустой", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!checkY(yField.getText(), component)) {
            checker = false;
        } else if (areaField.getText().isEmpty()) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Площадь не может быть пустой", "Error", JOptionPane.ERROR_MESSAGE);

        } else if (!checkArea(areaField.getText(), component)) {
            checker = false;
        } else if (populationField.getText().isEmpty()) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Население не может быть пустым", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!checkPopulation(populationField.getText(), component)) {
            checker = false;
        } else if (!checkMeters(metersField.getText(), component)) {
            checker = false;
        } else if (capitalBox.getSelectedItem().equals("")) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Выберите столицу", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (climateBox.getSelectedItem().equals("")) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Выберите климат", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (governmentBox.getSelectedItem().equals("")) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Выберите правительство", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (governorField.getText().isEmpty()) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Имя губернатора не может быть пустым", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            checker = checkName(governorField.getText(), component);
        }
        return checker;
    }

    public boolean checkX(String x, Component component) {
        boolean checker = true;
        try {
            Float X = Float.parseFloat(x);
            if (X <= -375F) {
                checker = false;
                JOptionPane.showMessageDialog(component, "Указано неверное значение координаты X. Значение не может быть меньше -375", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Указан неверный формат координаты Х", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }

    public boolean checkY(String y, Component component) {
        boolean checker = true;
        try {
            Integer Y = Integer.parseInt(y);
            if (Y <= -966) {
                checker = false;
                JOptionPane.showMessageDialog(component, "Указан неверное значение координаты Y. Значение не может быть меньше -966", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Указан неверный формат координаты Y", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }

    public boolean checkArea(String area, Component component) {
        boolean checker = true;
        try {
            Double AREA = Double.parseDouble(area);
            if (AREA <= 0) {
                checker = false;
                JOptionPane.showMessageDialog(component, "Указано неверное значение площади города. Значение не может быть отрицательным или равным 0", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Указан неверный формат площади города", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }

    public boolean checkPopulation(String population, Component component) {
        boolean checker = true;
        try {
            Integer Population = Integer.parseInt(population);
            if (Population <= 0) {
                checker = false;
                JOptionPane.showMessageDialog(component, "Указано неверное значение населения города. Значение не может быть отрицательным или равным 0", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Указан неверный формат населения", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }

    public boolean checkName(String name, Component component) {
        boolean checker = true;
        char[] symbols = name.toLowerCase().toCharArray();
        Boolean checkername1 = true;
        String validationName = "abcdefghijklmnopqrstuvwxyzабвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        for (char c : symbols) {
            if (validationName.indexOf(c) == -1) {
                checkername1 = false;
            }
        }
        if (!checkername1) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Указано неверное имя губернатора. Используйте только латинские символы", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }

    public boolean checkMeters(String name, Component component) {
        boolean checker = true;
        try {
            Integer.parseInt(name);
        } catch (NumberFormatException e) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Указан неверный формат метров", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }
}
