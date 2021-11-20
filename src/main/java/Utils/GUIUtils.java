package Utils;

import Server.Model.City;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class GUIUtils {
    public static Map<String, Object> readDefaultValues(City city) {
        Map<String, Object> defaultValues = new HashMap<>();
        defaultValues.put("id", city.getId());
        defaultValues.put("name", city.getName());
        defaultValues.put("x", city.getCoordinates().getX());
        defaultValues.put("y", city.getCoordinates().getY());
        defaultValues.put("date", city.getCreationDate());
        defaultValues.put("area", city.getName());
        defaultValues.put("population", city.getPopulation());
        defaultValues.put("meters", city.getMetersAboveSeaLevel());
        defaultValues.put("capital", city.getCapital());
        defaultValues.put("climate", city.getClimate());
        defaultValues.put("government", city.getGovernment());
        defaultValues.put("governor", city.getGovernor());
        defaultValues.put("user", city.getUser());
        return defaultValues;
    }

    public static void fillFields(Map<String, Object> defaultValues, ResourceBundle resourceBundle, List<JLabel> labels, List<JComboBox> comboBoxes) {
        labels.get(0).setText(resourceBundle.getString("id"));
        labels.get(1).setText(resourceBundle.getString("name"));
        labels.get(2).setText(resourceBundle.getString("x"));
        labels.get(3).setText(resourceBundle.getString("y"));
        labels.get(4).setText(resourceBundle.getString("time"));
        labels.get(5).setText(resourceBundle.getString("area"));
        labels.get(6).setText(resourceBundle.getString("population"));
        labels.get(7).setText(resourceBundle.getString("meters"));
        labels.get(8).setText(resourceBundle.getString("capital"));
        labels.get(9).setText(resourceBundle.getString("climate"));
        labels.get(10).setText(resourceBundle.getString("government"));
        labels.get(11).setText(resourceBundle.getString("governor"));
        labels.get(12).setText(resourceBundle.getString("owner"));
        if (comboBoxes == null) {
            return;
        }
        System.out.println(defaultValues.get("climate"));
        System.out.println(defaultValues.get("capital"));
        System.out.println(defaultValues.get("government"));
        if (defaultValues.get("climate") != null) {
            comboBoxes.get(1).setSelectedItem(defaultValues.get("climate").toString());
            System.out.println("CLIMATE");
        } else {
            comboBoxes.get(1).setSelectedItem("");
        }
        if (defaultValues.get("government") != null) {
            comboBoxes.get(2).setSelectedItem(defaultValues.get("government").toString());
            System.out.println("GOVER");
        } else {
            comboBoxes.get(2).setSelectedItem("");
        }
        if (defaultValues.get("capital") != null) {
            comboBoxes.get(0).setSelectedItem(defaultValues.get("capital").toString());
            System.out.println("CAP");
        } else {
            comboBoxes.get(0).setSelectedItem("");
        }
    }

}
