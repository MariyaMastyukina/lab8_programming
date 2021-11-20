package Client.GUI;

import Server.Model.City;
import Utils.ConnectionUtils.Request;
import Utils.UserUtils;

import javax.swing.*;
import java.awt.*;
import java.io.PipedWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public class VisualObjectPanel extends JPanel {
    private Dimension panelSize;
    private SpringLayout layout;
    private CopyOnWriteArrayList<City> localList = new CopyOnWriteArrayList<>();
    private PipedWriter commandWriter;
    private ResourceBundle resourceBundle;
    private boolean checker = true;
    private UserUtils userUtils;

    public VisualObjectPanel(Request table, PipedWriter commandWriter, ResourceBundle resourceBundle, UserUtils userUtils) {
        this.commandWriter = commandWriter;
        this.userUtils = userUtils;
        this.resourceBundle = resourceBundle;
        setBackground(new Color(214, 255, 231));
        updateVisual(table.getNewTable());
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void updateVisual(CopyOnWriteArrayList<City> list) {
        panelSize = new Dimension(200, 200);
        setLayout(layout = new SpringLayout());
        ArrayList<Long> ID = getDeletedID(list);
        LinkedList<City> newCities = getNewElements(list);
        for (Long remCity : ID) {
            removeByID(remCity);
        }
        for (City e : newCities) {
            addPoint(e);
        }
        checker = false;

        localList = list;
        revalidate();
        for (int i = 0; i < getComponentCount(); i++) {
            ObjectButton button = (ObjectButton) getComponent(i);
            layout.putConstraint(SpringLayout.WEST, button, button.getXButton(), SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, button, button.getYButton(), SpringLayout.NORTH, this);
        }
    }

    private void removeByID(Long id) {
        for (int i = 0; i < getComponentCount(); i++) {
            ObjectButton button = (ObjectButton) getComponent(i);
            if (button.getID() == id) {
                button.remove();
                break;
            }
        }
    }

    private void addPoint(City city) {
        int diam;
        if (city.getArea() <= 100) {
            diam = 10;
        } else if (city.getArea() <= 1000) {
            diam = 50;
        } else diam = 100;
        Integer x = city.getCoordinates().getX().intValue() % panelSize.width + panelSize.width;
        Integer y = city.getCoordinates().getY() % panelSize.height + panelSize.height;
        ObjectButton button;
        if (checker) {
            button = new ObjectButton(city, city.getId(), diam, x, y);
        } else {
            button = new ObjectButton(city, city.getId(), diam, x, y);
        }
        button.addActionListener(e -> {
            if (city.getUser().equals(userUtils.getLogin())) {
                new EditWindow(city, commandWriter, resourceBundle);
            } else new InfoWindow(city, resourceBundle);
        });
        add(button);
    }

    private ArrayList<Long> getDeletedID(CopyOnWriteArrayList<City> newList) {
        ArrayList<Long> deletedID = new ArrayList<>();
        for (City oldList : localList) {
            boolean checker = false;
            Long id = oldList.getId();
            for (City newL : newList) {
                if (oldList.equals(newL)) checker = true;
            }
            if (!checker) deletedID.add(id);
        }
        return deletedID;
    }

    private LinkedList<City> getNewElements(CopyOnWriteArrayList<City> newList) {
        LinkedList<City> newElements = new LinkedList<>();
        for (City newL : newList) {
            boolean checker = false;
            for (City oldL : localList) {
                if (oldL.equals(newL)) checker = true;
            }
            if (!checker) newElements.add(newL);
        }
        return newElements;
    }
}
