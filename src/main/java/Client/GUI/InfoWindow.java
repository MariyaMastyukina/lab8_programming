package Client.GUI;

import Server.Collection.City;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.ResourceBundle;


class InfoWindow extends JDialog {
    private JPanel infoPanel = new JPanel();
    private JLabel timeLabel = new JLabel();
    private JLabel idLabel = new JLabel();
    private JLabel nameLabel = new JLabel();
    private JLabel xLabel = new JLabel();
    private JLabel yLabel = new JLabel();
    private JLabel areaLabel = new JLabel();
    private JLabel populationLabel = new JLabel();
    private JLabel metersLabel = new JLabel();
    private JLabel climateLabel = new JLabel();
    private JLabel governmentLabel = new JLabel();
    private JLabel capitalLocLabel = new JLabel();
    private JLabel capitalLabel = new JLabel();
    private JLabel governorLabel = new JLabel();
    private JLabel ownerLabel = new JLabel();
    private JLabel ownerLocLabel = new JLabel();
    private JLabel timeLocLabel = new JLabel();
    private JLabel idLocLabel = new JLabel();
    private JLabel nameLocLabel = new JLabel();
    private JLabel xLocLabel = new JLabel();
    private JLabel yLocLabel = new JLabel();
    private JLabel areaLocLabel = new JLabel();
    private JLabel populationLocLabel = new JLabel();
    private JLabel metersLocLabel = new JLabel();
    private JLabel climateLocLabel = new JLabel();
    private JLabel governmentLocLabel = new JLabel();
    private JLabel governorLocLabel = new JLabel();


    public InfoWindow(HashMap<String, Object> defaultValues, ResourceBundle res) {
        infoPanel.setLayout(new GridLayout(14, 2, 3, 5));
        infoPanel.add(idLocLabel);
        infoPanel.add(idLabel);
        infoPanel.add(nameLocLabel);
        infoPanel.add(nameLabel);
        infoPanel.add(xLocLabel);
        infoPanel.add(xLabel);
        infoPanel.add(yLocLabel);
        infoPanel.add(yLabel);
        infoPanel.add(timeLocLabel);
        infoPanel.add(timeLabel);
        infoPanel.add(areaLocLabel);
        infoPanel.add(areaLabel);
        infoPanel.add(populationLocLabel);
        infoPanel.add(populationLabel);
        infoPanel.add(metersLocLabel);
        infoPanel.add(metersLabel);
        infoPanel.add(capitalLocLabel);
        infoPanel.add(capitalLabel);
        infoPanel.add(climateLocLabel);
        infoPanel.add(climateLabel);
        infoPanel.add(governmentLocLabel);
        infoPanel.add(governmentLabel);
        infoPanel.add(governorLocLabel);
        infoPanel.add(governorLabel);
        infoPanel.add(ownerLocLabel);
        infoPanel.add(ownerLabel);
        capitalLabel.setText(defaultValues.get("capital").toString());
        timeLabel.setText(defaultValues.get("date").toString());
        nameLabel.setText(defaultValues.get("name").toString());
        idLabel.setText(defaultValues.get("id").toString());
        xLabel.setText(defaultValues.get("x").toString());
        yLabel.setText(defaultValues.get("y").toString());
        areaLabel.setText(defaultValues.get("area").toString());
        populationLabel.setText(defaultValues.get("population").toString());
        metersLabel.setText(defaultValues.get("meters").toString());
        if (defaultValues.get("climate") != null) {
            climateLabel.setText(defaultValues.get("climate").toString());
        } else climateLabel.setText("");
        governmentLabel.setText(defaultValues.get("government").toString());
        governorLabel.setText(defaultValues.get("governor").toString());
        ownerLabel.setText(defaultValues.get("user").toString());
        ownerLocLabel.setText(res.getString("owner"));
        timeLocLabel.setText(res.getString("time"));
        idLocLabel.setText(res.getString("id"));
        nameLocLabel.setText(res.getString("name"));
        xLocLabel.setText(res.getString("x"));
        yLocLabel.setText(res.getString("y"));
        areaLocLabel.setText(res.getString("area"));
        populationLocLabel.setText(res.getString("population"));
        metersLocLabel.setText(res.getString("meters"));
        capitalLocLabel.setText(res.getString("capital"));
        climateLocLabel.setText(res.getString("climate"));
        governmentLocLabel.setText(res.getString("government"));
        governorLocLabel.setText(res.getString("governor"));
        add(infoPanel, BorderLayout.CENTER);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screen = toolkit.getScreenSize();
        setSize(screen.width / 3, screen.height / 2);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);

    }

    public InfoWindow(City city, ResourceBundle res) {
        infoPanel.setLayout(new GridLayout(13, 2, 3, 5));
        infoPanel.add(idLocLabel);
        infoPanel.add(idLabel);
        infoPanel.add(nameLocLabel);
        infoPanel.add(nameLabel);
        infoPanel.add(xLocLabel);
        infoPanel.add(xLabel);
        infoPanel.add(yLocLabel);
        infoPanel.add(yLabel);
        infoPanel.add(timeLocLabel);
        infoPanel.add(timeLabel);
        infoPanel.add(areaLocLabel);
        infoPanel.add(areaLabel);
        infoPanel.add(populationLocLabel);
        infoPanel.add(populationLabel);
        infoPanel.add(metersLocLabel);
        infoPanel.add(metersLabel);
        infoPanel.add(capitalLocLabel);
        infoPanel.add(capitalLabel);
        infoPanel.add(climateLocLabel);
        infoPanel.add(climateLabel);
        infoPanel.add(governmentLocLabel);
        infoPanel.add(governmentLabel);
        infoPanel.add(governorLocLabel);
        infoPanel.add(governorLabel);
        infoPanel.add(ownerLocLabel);
        infoPanel.add(ownerLabel);
        timeLabel.setText(city.getCreationDate().toString());
        nameLabel.setText(city.getNameCity());
        idLabel.setText(String.valueOf(city.getIdOfCity()));
        xLabel.setText(String.valueOf(city.getCoordinates().getX()));
        yLabel.setText(String.valueOf(city.getCoordinates().getY()));
        areaLabel.setText(String.valueOf(city.getArea()));
        populationLabel.setText(String.valueOf(city.getPopulation()));
        metersLabel.setText(String.valueOf(city.getMetersAboveSeaLevel()));
        if (city.getClimate() != null) {
            climateLabel.setText(city.getClimate().toString());
        } else climateLabel.setText("");
        governmentLabel.setText(city.getGovernment().toString());
        governorLabel.setText(city.getGovernor().toString());
        ownerLabel.setText(city.getUser());
        capitalLabel.setText(String.valueOf(city.getCapital()));
        ownerLocLabel.setText(res.getString("owner"));
        timeLocLabel.setText(res.getString("time"));
        idLocLabel.setText(res.getString("id"));
        nameLocLabel.setText(res.getString("name"));
        xLocLabel.setText(res.getString("x"));
        yLocLabel.setText(res.getString("y"));
        areaLocLabel.setText(res.getString("area"));
        populationLocLabel.setText(res.getString("population"));
        metersLocLabel.setText(res.getString("meters"));
        climateLocLabel.setText(res.getString("climate"));
        governmentLocLabel.setText(res.getString("government"));
        governorLocLabel.setText(res.getString("governor"));
        capitalLocLabel.setText(res.getString("capital"));
        add(infoPanel, BorderLayout.CENTER);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screen = toolkit.getScreenSize();
        setSize(screen.width / 3, screen.height / 2);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);
    }

}
