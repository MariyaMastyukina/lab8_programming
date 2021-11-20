package Client.GUI;

import Server.Model.City;
import Utils.DataUtils.ValidationUtils;
import Utils.GUIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EditWindow extends JDialog {
    private JPanel editPanel;
    private JPanel buttonsPanel;
    private JButton applyChangesButton;
    private JButton removeElementButton;
    private List<JTextField> fields;
    private List<JComboBox> comboBoxes;
    private List<JLabel> labels;
    private JLabel idField;
    private JTextField nameField;
    private JTextField xField;
    private JTextField yField;
    private JLabel dateCreationField;
    private JTextField areaField;
    private JTextField populationField;
    private JTextField metersField;
    private JComboBox climateBox;
    private JComboBox capitalBox;
    private JComboBox governmentBox;
    private JTextField governorField;
    private JLabel ownerField;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel xLabel;
    private JLabel yLabel;
    private JLabel dateCreationLabel;
    private JLabel areaLabel;
    private JLabel populationLabel;
    private JLabel metersLabel;
    private JLabel climateLabel;
    private JLabel capitalLabel;
    private JLabel governmentLabel;
    private JLabel governorLabel;
    private JLabel ownerLabel;
    private PipedWriter commandWriter;
    private ResourceBundle resourceBundle;

    public EditWindow(HashMap<String, Object> defaultValues, PipedWriter commandWriter, ResourceBundle resourceBundle) {
        initWindow(defaultValues, commandWriter, resourceBundle);
    }

    EditWindow(City city, PipedWriter commandWriter, ResourceBundle resourceBundle) {
        initWindow(GUIUtils.readDefaultValues(city), commandWriter, resourceBundle);
    }

    private void initWindow(Map<String, Object> defaultValues, PipedWriter commandWriter, ResourceBundle resourceBundle) {
        this.commandWriter = commandWriter;
        this.resourceBundle = resourceBundle;
        initComponents(defaultValues);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(editPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screen = toolkit.getScreenSize();
        setSize(screen.width / 3, screen.height / 2);
        setVisible(true);
    }

    private void initComponents(Map<String, Object> defaultValues) {
        editPanel = new JPanel();
        editPanel.setLayout(new GridLayout(14, 1, 3, 5));
        idField = new JLabel();
        nameField = new JTextField();
        xField = new JTextField();
        yField = new JTextField();
        dateCreationField = new JLabel();
        areaField = new JTextField();
        populationField = new JTextField();
        metersField = new JTextField();
        climateBox = new JComboBox();
        capitalBox = new JComboBox();
        governmentBox = new JComboBox();
        governorField = new JTextField();
        comboBoxes = List.of(capitalBox, climateBox, governmentBox);
        fields = List.of(nameField, xField, yField, areaField, populationField, metersField, governorField);
        idLabel = new JLabel();
        nameLabel = new JLabel();
        xLabel = new JLabel();
        yLabel = new JLabel();
        dateCreationLabel = new JLabel();
        areaLabel = new JLabel();
        populationLabel = new JLabel();
        metersLabel = new JLabel();
        climateLabel = new JLabel();
        capitalLabel = new JLabel();
        governmentLabel = new JLabel();
        governorLabel = new JLabel();
        labels = List.of(idLabel, nameLabel, xLabel, yLabel, dateCreationLabel, areaLabel, populationLabel, metersLabel, capitalLabel, climateLabel, governmentLabel, governorLabel);

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
        editPanel.add(idLabel);
        editPanel.add(idField);
        editPanel.add(nameLabel);
        editPanel.add(nameField);
        editPanel.add(xLabel);
        editPanel.add(xField);
        editPanel.add(yLabel);
        editPanel.add(yField);
        editPanel.add(dateCreationLabel);
        editPanel.add(dateCreationField);
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
        editPanel.add(ownerLabel);
        editPanel.add(ownerField);
        fillFields(defaultValues);

        buttonsPanel = new JPanel(new GridLayout(1, 2));
        applyChangesButton = new JButton();
        removeElementButton = new JButton();

        removeElementButton.setText(resourceBundle.getString("removeElement"));
        applyChangesButton.setText(resourceBundle.getString("applyChanges"));
        removeElementButton.addActionListener(new ClearListener());
        applyChangesButton.addActionListener(new UpdateListener(this));

        buttonsPanel.add(removeElementButton);
        buttonsPanel.add(applyChangesButton);
    }

    private void fillFields(Map<String, Object> defaultValues) {
        idField.setText(defaultValues.get("id").toString());
        nameField.setText(defaultValues.get("name").toString());
        xField.setText(defaultValues.get("x").toString());
        yField.setText(defaultValues.get("y").toString());
        dateCreationField.setText(defaultValues.get("date").toString());
        areaField.setText(defaultValues.get("area").toString());
        populationField.setText(defaultValues.get("population").toString());
        metersField.setText(defaultValues.get("meters").toString());
        governorField.setText(defaultValues.get("governor").toString());
        ownerField.setText(defaultValues.get("user").toString());
        GUIUtils.fillFields(defaultValues, resourceBundle, labels, comboBoxes);
    }

    private class ClearListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                commandWriter.write("remove_by_id " + idLabel.getText() + "\n");
                commandWriter.flush();
                setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class UpdateListener implements ActionListener {
        Component component;

        UpdateListener(Component component) {
            this.component = component;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (ValidationUtils.checkFields(fields, component, comboBoxes)) {
                    commandWriter.write("update " + idLabel.getText() + "\n");
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
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(component, ex.getMessage(), "ОШИБКА", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
