package Client.GUI;

import Server.Collection.City;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;

public class ObjectButton extends JButton implements ActionListener {
    City city;
    long id;
    Timer timer=new Timer(10,this);
    Integer currentDiam=0;
    Integer diam,red,green,blue,x,y;
    boolean grow=true;
    boolean decrease=false;
    ObjectButton(City city, long id,Integer diam, Integer x, Integer y){
        this.city=city;
        this.id=id;
        this.diam=diam;
        this.x=x;
        this.y=y;
        Dimension size=new Dimension(diam,diam);
        setPreferredSize(size);
        setContentAreaFilled(false);
        red = city.getUser().hashCode()*30%255;
        green = city.getUser().hashCode()*20%255;
        blue = city.getUser().hashCode()*70%255;
        timer.start();
    }
    public void remove(){
        decrease=true;
//        grow=true;
    }
    public long getID(){
        return id;
    }


    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()){
            g.setColor(Color.yellow);
        }
        else{
            g.setColor(Color.red);
        }
        g.setColor(new Color(red,green,blue));
        g.fillOval(0,0,currentDiam,currentDiam);
        super.paintComponent(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (currentDiam>=diam) grow=false;
        if (currentDiam==0){
            decrease=false;
            if (!grow)setVisible(false);
        }
        if (grow) currentDiam=+1;
        if (decrease) currentDiam=-1;
        repaint();
    }

    public Integer getXButton() {
        return x;
    }
    public Integer getYButton(){
        return y;
    }

}
