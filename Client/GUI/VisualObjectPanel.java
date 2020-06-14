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
    private boolean checker=true;

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
            panelSize = new Dimension(200, 200);
            setLayout(layout = new SpringLayout());
            ArrayList<Long> ID = getdeletedID(list);
            LinkedList<City> newCities = getnewElements(list);
        System.out.println(list.size());
        System.out.println(localList.size());
                    for (Long remCity : ID) {
                        System.out.println("A");
                        removeByID(remCity);
                    }
                    for (City e : newCities) {
                        System.out.println("FFFF");
                        addPoint(e);
                    }
                    checker=false;

            localList = list;
            revalidate();
            for (int i=0;i<getComponentCount();i++){
                ObjectButton button=(ObjectButton) getComponent(i);
                layout.putConstraint(SpringLayout.WEST, button,button.getXButton(), SpringLayout.WEST, this);
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
        ObjectButton button;
        if (checker) {
             button= new ObjectButton(city, city.getIdOfCity(), diam, x, y,diam);
        }
        else{
            button= new ObjectButton(city, city.getIdOfCity(), diam, x, y,0);
        }
        button.addActionListener(e-> {
            if (city.getUser().equals(User.getLogin())){
                new EditWindow(city, cmdWriter, res);
            }
            else new InfoWindow(city, res);
        });
        add(button);
//        layout.putConstraint(SpringLayout.WEST, button, button.getXButton(), SpringLayout.WEST, this);
//        layout.putConstraint(SpringLayout.NORTH, button, button.getYButton(), SpringLayout.NORTH, this);
    }

    public ArrayList<Long> getdeletedID(CopyOnWriteArrayList<City> newList){
        ArrayList<Long> deletedID = new ArrayList<>();
        for(City oldList : localList){
            boolean checker=false;
            Long id=oldList.getIdOfCity();
            for (City newL: newList){
                if(oldList.equals(newL)) checker=true;
            }
            if (!checker) deletedID.add(id);
        }
        return deletedID;
    }
    public LinkedList<City> getnewElements(CopyOnWriteArrayList<City> newList){
        LinkedList<City> newElements = new LinkedList<>();
        for(City newL:newList){
            boolean checker=false;
            for (City oldL: localList){
                if(oldL.equals(newL)) checker=true;
            }
            if (!checker) newElements.add(newL);
        }
        return newElements;
    }
}
