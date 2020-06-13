package Client.GUI;

import Server.Collection.City;

import javax.swing.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;


class InfoWindow extends JDialog {
        private JPanel infoPanel;
        private JLabel timeLabel;
        private JLabel idLabel;
        private JLabel nameLabel;
        private JLabel xLabel;
        private JLabel yLabel;
        private JLabel areaLabel;
        private JLabel populationLabel;
        private JLabel metersLabel;
        private JLabel climateLabel;
        private JLabel governmentLabel;
        private JLabel governorLabel;
        private JLabel ownerLabel;
        private JLabel ownerLocLabel;
        private JLabel timeLocLabel;
        private JLabel idLocLabel;
        private JLabel nameLocLabel;
        private JLabel xLocLabel;
        private JLabel yLocLabel;
        private JLabel areaLocLabel;
        private JLabel populationLocLabel;
        private JLabel metersLocLabel;
        private JLabel climateLocLabel;
        private JLabel governmentLocLabel;
        private JLabel governorLocLabel;


        public InfoWindow(HashMap<String, Object> defaultValues, ResourceBundle res) {
            setModal(true);
            setContentPane(infoPanel);
            timeLabel.setText(defaultValues.get(res.getString("time")).toString());
            nameLabel.setText(defaultValues.get(res.getString("name")).toString());
            idLabel.setText(defaultValues.get(res.getString("id")).toString());
            xLabel.setText(defaultValues.get(res.getString("x")).toString());
            yLabel.setText(defaultValues.get(res.getString("y")).toString());
            areaLabel.setText(defaultValues.get(res.getString("area")).toString());
            populationLabel.setText(defaultValues.get(res.getString("population")).toString());
            metersLabel.setText(defaultValues.get(res.getString("meters")).toString());
            if (defaultValues.get(res.getString("climate"))!=null) {
                climateLabel.setText(defaultValues.get(res.getString("climate")).toString());
            } else climateLabel.setText("");
            governmentLabel.setText(defaultValues.get(res.getString("government")).toString());
            governorLabel.setText(defaultValues.get(res.getString("governor")).toString());

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

            pack();
            setVisible(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }
    public InfoWindow(City city, ResourceBundle res){
        setModal(true);
        setContentPane(infoPanel);
        timeLabel.setText(city.getCreationDate().toString());
        nameLabel.setText(city.getNameCity());
        idLabel.setText(String.valueOf(city.getIdOfCity()));
        xLabel.setText(String.valueOf(city.getCoordinates().getX()));
        yLabel.setText(String.valueOf(city.getCoordinates().getY()));
        areaLabel.setText(String.valueOf(city.getArea()));
        populationLabel.setText(String.valueOf(city.getPopulation()));
        metersLabel.setText(String.valueOf(city.getMetersAboveSeaLevel()));
        if (city.getClimate()!=null) {
            climateLabel.setText(city.getClimate().toString());
        } else climateLabel.setText("");
        governmentLabel.setText(city.getGovernment().toString());
        governorLabel.setText(city.getGovernor().toString());
        ownerLabel.setText(city.getUser());

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

        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

}
