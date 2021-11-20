package Client.GUI;

import Server.Model.City;
import Utils.GUIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


class InfoWindow extends JDialog {
    private JPanel infoPanel = new JPanel();
    private List<JLabel> labels;
    private JLabel idField;
    private JLabel nameField;
    private JLabel xField;
    private JLabel yField;
    private JLabel dateCreationField;
    private JLabel areaField;
    private JLabel populationField;
    private JLabel metersField;
    private JLabel climateField;
    private JLabel capitalField;
    private JLabel governmentField;
    private JLabel governorField;
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
    private ResourceBundle resourceBundle;

    public InfoWindow(HashMap<String, Object> defaultValues, ResourceBundle resourceBundle) {
        initWindow(defaultValues, resourceBundle);
    }

    public InfoWindow(City city, ResourceBundle resourceBundle) {
        initWindow(GUIUtils.readDefaultValues(city), resourceBundle);
    }

    private void initWindow(Map<String, Object> defaultValues, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        initComponents(defaultValues);
        add(infoPanel, BorderLayout.CENTER);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screen = toolkit.getScreenSize();
        setSize(screen.width / 3, screen.height / 2);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents(Map<String, Object> defaultValues) {
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(13, 2, 3, 5));
        idField = new JLabel();
        nameField = new JLabel();
        xField = new JLabel();
        yField = new JLabel();
        dateCreationField = new JLabel();
        areaField = new JLabel();
        populationField = new JLabel();
        metersField = new JLabel();
        climateField = new JLabel();
        capitalField = new JLabel();
        governmentField = new JLabel();
        governorField = new JLabel();
        ownerField = new JLabel();

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
        ownerLabel = new JLabel();
        labels = List.of(idLabel, nameLabel, xLabel, yLabel, dateCreationLabel, areaLabel, populationLabel, metersLabel, capitalLabel, climateLabel, governmentLabel, governorLabel, ownerLabel);

        fillFields(defaultValues);
        infoPanel.add(idLabel);
        infoPanel.add(idField);
        infoPanel.add(nameLabel);
        infoPanel.add(nameField);
        infoPanel.add(xLabel);
        infoPanel.add(xField);
        infoPanel.add(yLabel);
        infoPanel.add(yField);
        infoPanel.add(dateCreationLabel);
        infoPanel.add(dateCreationField);
        infoPanel.add(areaLabel);
        infoPanel.add(areaField);
        infoPanel.add(populationLabel);
        infoPanel.add(populationField);
        infoPanel.add(metersLabel);
        infoPanel.add(metersField);
        infoPanel.add(capitalLabel);
        infoPanel.add(capitalField);
        infoPanel.add(climateLabel);
        infoPanel.add(climateField);
        infoPanel.add(governmentLabel);
        infoPanel.add(governmentField);
        infoPanel.add(governorLabel);
        infoPanel.add(governorField);
        infoPanel.add(ownerLabel);
        infoPanel.add(ownerField);
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
        capitalField.setText(defaultValues.get("capital").toString());
        climateField.setText(defaultValues.get("climate").toString());
        governmentField.setText(defaultValues.get("government").toString());
        governorField.setText(defaultValues.get("governor").toString());
        ownerField.setText(defaultValues.get("user").toString());
        GUIUtils.fillFields(defaultValues, resourceBundle, labels, null);
    }


}
