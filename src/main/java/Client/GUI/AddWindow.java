package Client.GUI;

import Utils.DataUtils.ValidationUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.List;
import java.util.ResourceBundle;

public class AddWindow extends JDialog {
    private boolean checker = true;
    private List<JTextField> fields;
    private List<JComboBox> comboBoxes;
    private String commandName;
    private JTextField nameField;
    private JTextField xField;
    private JTextField yField;
    private JTextField areaField;
    private JTextField populationField;
    private JTextField metersField;
    private JComboBox climateBox;
    private JComboBox capitalBox;
    private JComboBox governmentBox;
    private JTextField governorField;
    private JButton confirmButton;
    private JPanel addCityPanel;
    private JLabel nameLabel;
    private JLabel xLabel;
    private JLabel yLabel;
    private JLabel areaLabel;
    private JLabel populationLabel;
    private JLabel metersLabel;
    private JLabel climateLabel;
    private JLabel capitalLabel;
    private JLabel governmentLabel;
    private JLabel governorLabel;
    private PipedWriter commandWriter;
    private ResourceBundle resourceBundle;

    public AddWindow(PipedWriter commandWriter, JFrame owner, ResourceBundle resourceBundle) {
        super(owner, resourceBundle.getString("input"), true);
        this.commandWriter = commandWriter;
        this.resourceBundle = resourceBundle;
        initComponents();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setLayout(new BorderLayout());
        add(addCityPanel, BorderLayout.CENTER);
        add(confirmButton, BorderLayout.SOUTH);
        updateText(resourceBundle);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(toolkit.getScreenSize().width / 3, toolkit.getScreenSize().height / 2);
    }

    private void initComponents() {
        addCityPanel = new JPanel();
        nameField = new JTextField();
        xField = new JTextField();
        yField = new JTextField();
        areaField = new JTextField();
        populationField = new JTextField();
        metersField = new JTextField();
        climateBox = new JComboBox();
        capitalBox = new JComboBox();
        governmentBox = new JComboBox();
        governorField = new JTextField();
        confirmButton = new JButton();
        fields = List.of(nameField, xField, yField, areaField, populationField, metersField, governorField);
        comboBoxes = List.of(capitalBox, climateBox, governmentBox);

        nameLabel = new JLabel();
        xLabel = new JLabel();
        yLabel = new JLabel();
        areaLabel = new JLabel();
        populationLabel = new JLabel();
        metersLabel = new JLabel();
        climateLabel = new JLabel();
        capitalLabel = new JLabel();
        governmentLabel = new JLabel();
        governorLabel = new JLabel();

        addCityPanel.setLayout(new GridLayout(10, 1, 3, 5));

        confirmButton.setText(resourceBundle.getString("confirm"));
        confirmButton.addActionListener(new ConfirmListener(this));
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
        addCityPanel.add(nameLabel);
        addCityPanel.add(nameField);
        addCityPanel.add(xLabel);
        addCityPanel.add(xField);
        addCityPanel.add(yLabel);
        addCityPanel.add(yField);
        addCityPanel.add(areaLabel);
        addCityPanel.add(areaField);
        addCityPanel.add(populationLabel);
        addCityPanel.add(populationField);
        addCityPanel.add(metersLabel);
        addCityPanel.add(metersField);
        addCityPanel.add(capitalLabel);
        addCityPanel.add(capitalBox);
        addCityPanel.add(climateLabel);
        addCityPanel.add(climateBox);
        addCityPanel.add(governmentLabel);
        addCityPanel.add(governmentBox);
        addCityPanel.add(governorLabel);
        addCityPanel.add(governorField);
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public void prepare() {
        nameField.setText("");
        xField.setText("");
        yField.setText("");
        areaField.setText("");
        populationField.setText("");
        metersField.setText("");
        governorField.setText("");
        setVisible(true);
    }

    private class ConfirmListener implements ActionListener {
        Component component;

        ConfirmListener(Component component) {
            this.component = component;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                checker = ValidationUtils.checkFields(fields, component, comboBoxes);
                if (checker) {
                    commandWriter.write(commandName + "\n");
                    commandWriter.flush();
                    commandWriter.write(nameField.getText() + "\n");
                    commandWriter.write(xField.getText() + "\n");
                    commandWriter.write(yField.getText() + "\n");
                    commandWriter.write(areaField.getText() + "\n");
                    commandWriter.write(populationField.getText() + "\n");
                    commandWriter.write(metersField.getText() + "\n");
                    commandWriter.write(capitalBox.getSelectedItem().toString() + "\n");
                    commandWriter.write(climateBox.getSelectedItem().toString() + "\n");
                    commandWriter.write(governmentBox.getSelectedItem().toString() + "\n");
                    commandWriter.write(governorField.getText() + "\n");
                    commandWriter.flush();
                    setVisible(false);
                }
            } catch (IllegalArgumentException ex) {
                checker = false;
                JOptionPane.showMessageDialog(component, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public void updateText(ResourceBundle res) {
        this.resourceBundle = res;
        nameLabel.setText(res.getString("name"));
        xLabel.setText(res.getString("x"));
        yLabel.setText(res.getString("y"));
        areaLabel.setText(res.getString("area"));
        populationLabel.setText(res.getString("population"));
        metersLabel.setText(res.getString("meters"));
        climateLabel.setText(res.getString("climate"));
        capitalLabel.setText(res.getString("capital"));
        governmentLabel.setText(res.getString("government"));
        governorLabel.setText(res.getString("governor"));
        confirmButton.setText(res.getString("confirm"));
        setTitle(res.getString("input"));
        pack();
    }
}
