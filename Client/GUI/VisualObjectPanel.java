package Client.GUI;

import Client.User;
import Server.Collection.City;
import Server.Request;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.io.PipedWriter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class VisualObjectPanel extends JPanel{
    private Dimension panelSize;
    private SpringLayout layout;
    private CopyOnWriteArrayList<City> localList = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<City> buffMap;
    private PipedWriter cmdWriter;
    private ResourceBundle res;

    public VisualObjectPanel(Request table, PipedWriter cmdWriter, ResourceBundle res ){
        this.cmdWriter = cmdWriter;
        this.res= res;
        setBackground(new Color(214, 255, 231));
        updateVisual(table.getNew_map());
    }

    public void setRes(ResourceBundle res){
        this.res = res;
    }

    public void updateVisual(CopyOnWriteArrayList<City> list) {
            panelSize = new Dimension(500/2, 500/2);
            setLayout(layout = new SpringLayout());
            Pair<ArrayList<Long>, LinkedList<City>> pair = compareLists(list);
            ArrayList<Long> ID = pair.getKey();
            LinkedList<City> newCities = pair.getValue();
            if(list.size()==localList.size()) {
                removeAll();
                for (City e : list) {
                    addPoint(e);
                    System.out.println("F");
                }
            }
            else{
                    for (Long remCity : ID) {
                        System.out.println("A");
                        removeByID(remCity);
                    }
                    for (City e : newCities) {
                        System.out.println("FFFF");
                        addPoint(e);
                    }
                }

            localList = list;
            revalidate();
            for (int i=0;i<getComponentCount();i++){
                ObjectButton button=(ObjectButton)getComponent(i);
                layout.putConstraint(SpringLayout.WEST, button, button.getXButton(), SpringLayout.WEST, this);
                layout.putConstraint(SpringLayout.NORTH, button, button.getYButton(), SpringLayout.NORTH, this);
            }
    }
    public void removeByID(Long id) {
        for (int i = 0; i < getComponentCount(); i++) {
            ObjectButton button = (ObjectButton) getComponent(i);
            if (button.getID()==id) {
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
        Integer x=city.getCoordinates().getX().intValue() % panelSize.width+panelSize.width;
        Integer y=city.getCoordinates().getY() % panelSize.height +panelSize.height;
        ObjectButton button = new ObjectButton(city,city.getIdOfCity(), diam,x,y);
        button.addActionListener(e-> {
            if (city.getUser().equals(User.getLogin())){
                new EditWindow(city, cmdWriter, res);
            }
            else new InfoWindow(city, res);
        });
        add(button);
        layout.putConstraint(SpringLayout.WEST, button, x, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, button, y, SpringLayout.NORTH, this);
    }

    public Pair<ArrayList<Long>, LinkedList<City>> compareLists(CopyOnWriteArrayList<City> newList){
        System.out.println("CompareMApsCheck");
        ArrayList<Long> deletedID = new ArrayList<>();
        LinkedList<City> newElements = new LinkedList<>();
        Pair<ArrayList<Long>, LinkedList<City>> pair = new Pair<>(deletedID,newElements);
        for(City oldList : localList){
            boolean checker=false;
            Long id=oldList.getIdOfCity();
            for (City newL: newList){
                if(oldList.equals(newL)) checker=true;
            }
            if (!checker) deletedID.add(id);
        }
        for(City newL:newList){
            boolean checker=false;
            for (City oldL: localList){
                if(oldL.equals(newL)) checker=true;
            }
            if (!checker) newElements.add(newL);
        }
        return pair;
    }
}
