package Client.GUI;

import Server.Collection.City;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class ObjectButton extends JButton implements ActionListener {
    City city;
    long id;
    Timer timer=new Timer(10,this);
    Integer currentDiam=0;
    int diam,red,green,blue,x,y;
    boolean grow=true;
    boolean decrease=false;
    Color color;
    ObjectButton(City city, long id,Integer diam, Integer x, Integer y,Integer currentDiam){
        this.city=city;
        this.id=id;
        this.diam=diam;
        this.x=x;
        this.y=y;
        setContentAreaFilled(false);
        setOpaque(true);
        Dimension size = new Dimension(diam+1, diam+1);
        setBackground(new Color(214, 255, 231));
        setPreferredSize(size);
        red = city.getUser().hashCode()*3%255;
        green = city.getUser().hashCode()*2%255;
        blue = city.getUser().hashCode()*7%255;
        timer.start();
    }
    public void remove(){
        grow=false;
        decrease=true;
    }
    public long getID(){
        return id;
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2=(Graphics2D)g;
        g2.setColor(new Color(red,green,blue));
        if (grow && currentDiam<=diam) currentDiam+=1;
        if (currentDiam==0){
            decrease=false;
            if (!grow)setVisible(false);
        }
        if (decrease && !grow) currentDiam-=1;
        g2.setStroke(new BasicStroke(3f));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D ellipse= new Ellipse2D.Double(0,0,currentDiam,currentDiam);
        g2.fill(ellipse);
//        super.paintComponent(g2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public Integer getXButton() {
        return x;
    }
    public Integer getYButton(){
        return y;
    }

}
