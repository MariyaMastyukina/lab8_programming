package Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.ResourceBundle;

public class SortPanel extends JPanel {
    public SortPanel(ResourceBundle res, PipedWriter writer) {
        setLayout(new GridLayout(1, 13));
        JButton sort1 = new JButton(res.getString("sort"));
        sort1.addActionListener(e -> {
            try {
                writer.write("sort id\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(sort1);
        JButton sort2 = new JButton(res.getString("sort"));
        sort2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writer.write("sort name\n");
                    writer.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(sort2);
        JButton sort3 = new JButton(res.getString("sort"));
        sort3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writer.write("sort x\n");
                    writer.flush();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(sort3);
        JButton sort4 = new JButton(res.getString("sort"));
        sort4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writer.write("sort y\n");
                    writer.flush();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(sort4);
        JButton sort5 = new JButton(res.getString("sort"));
        sort5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writer.write("sort date\n");
                    writer.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(sort5);
        JButton sort6 = new JButton(res.getString("sort"));
        sort6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writer.write("sort area\n");
                    writer.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(sort6);
        JButton sort7 = new JButton(res.getString("sort"));
        sort7.addActionListener(e -> {
            try {
                writer.write("sort population\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(sort7);
        JButton sort8 = new JButton(res.getString("sort"));
        sort8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writer.write("sort meters\n");
                    writer.flush();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(sort8);
        JButton sort9 = new JButton(res.getString("sort"));
        sort9.addActionListener(e -> {
            try {
                writer.write("sort capital\n");
                writer.flush();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(sort9);
        JButton sort10 = new JButton(res.getString("sort"));
        sort10.addActionListener(e -> {
            try {
                writer.write("sort climate\n");
                writer.flush();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(sort10);
        JButton sort11 = new JButton(res.getString("sort"));
        sort11.addActionListener(e -> {
            try {
                writer.write("sort government\n");
                writer.flush();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(sort11);
        JButton sort12 = new JButton(res.getString("sort"));
        sort12.addActionListener(e -> {
            try {
                writer.write("sort governor\n");
                writer.flush();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(sort12);
        JButton sort13 = new JButton(res.getString("sort"));
        sort13.addActionListener(e -> {
            try {
                writer.write("sort user\n");
                writer.flush();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(sort13);
    }

    public void updateSort(ResourceBundle res) {
        for (int i = 0; i < getComponentCount(); i++) {
            JButton button = (JButton) getComponent(i);
            button.setText(res.getString("sort"));
        }
    }
}
