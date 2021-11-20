package Client.GUI;

import Server.Model.City;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class ObjectButton extends JButton implements ActionListener {
    private City city;
    private long id;
    private Timer timer = new Timer(10, this);
    private Integer currentDiam = 0;
    private int diam, red, green, blue, x, y;
    private boolean grow = true;
    private boolean decrease = false;

    ObjectButton(City city, long id, Integer diam, Integer x, Integer y) {
        this.city = city;
        this.id = id;
        this.diam = diam;
        this.x = x;
        this.y = y;
        red = Math.abs(city.getUser().hashCode()) * 3 % 255;
        green = Math.abs(city.getUser().hashCode()) * 2 % 255;
        blue = Math.abs(city.getUser().hashCode()) % 255;
        setContentAreaFilled(false);
        setOpaque(true);
        Dimension size = new Dimension(diam + 1, diam + 1);
        setBackground(new Color(214, 255, 231));
        setPreferredSize(size);
        timer.start();
    }

    public void remove() {
        grow = false;
        decrease = true;
    }

    public long getID() {
        return id;
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(red, green, blue));
        if (grow && currentDiam <= diam) currentDiam += 1;
        if (currentDiam == 0) {
            decrease = false;
            if (!grow) setVisible(false);
        }
        if (decrease && !grow) currentDiam -= 1;
        g2.setStroke(new BasicStroke(3f));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D ellipse = new Ellipse2D.Double(0, 0, currentDiam, currentDiam);
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

    public Integer getYButton() {
        return y;
    }

}
