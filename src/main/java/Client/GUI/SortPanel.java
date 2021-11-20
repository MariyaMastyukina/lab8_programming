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
        JButton sortId = new JButton(res.getString("sort"));
        sortId.addActionListener(e -> {
            try {
                writer.write("sort id\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(sortId);
        JButton sortName = new JButton(res.getString("sort"));
        sortName.addActionListener(new ActionListener() {
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
        add(sortName);
        JButton sortX = new JButton(res.getString("sort"));
        sortX.addActionListener(new ActionListener() {
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
        add(sortX);
        JButton sortY = new JButton(res.getString("sort"));
        sortY.addActionListener(new ActionListener() {
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
        add(sortY);
        JButton sortDate = new JButton(res.getString("sort"));
        sortDate.addActionListener(new ActionListener() {
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
        add(sortDate);
        JButton sortArea = new JButton(res.getString("sort"));
        sortArea.addActionListener(new ActionListener() {
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
        add(sortArea);
        JButton sortPopulation = new JButton(res.getString("sort"));
        sortPopulation.addActionListener(e -> {
            try {
                writer.write("sort population\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(sortPopulation);
        JButton sortMetersAboveSeaLevel = new JButton(res.getString("sort"));
        sortMetersAboveSeaLevel.addActionListener(new ActionListener() {
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
        add(sortMetersAboveSeaLevel);
        JButton sortCapital = new JButton(res.getString("sort"));
        sortCapital.addActionListener(e -> {
            try {
                writer.write("sort capital\n");
                writer.flush();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(sortCapital);
        JButton sortClimate = new JButton(res.getString("sort"));
        sortClimate.addActionListener(e -> {
            try {
                writer.write("sort climate\n");
                writer.flush();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(sortClimate);
        JButton sortGovernment = new JButton(res.getString("sort"));
        sortGovernment.addActionListener(e -> {
            try {
                writer.write("sort government\n");
                writer.flush();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(sortGovernment);
        JButton sortGovernor = new JButton(res.getString("sort"));
        sortGovernor.addActionListener(e -> {
            try {
                writer.write("sort governor\n");
                writer.flush();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(sortGovernor);
        JButton sortUser = new JButton(res.getString("sort"));
        sortUser.addActionListener(e -> {
            try {
                writer.write("sort user\n");
                writer.flush();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(sortUser);
    }

    public void updateSort(ResourceBundle res) {
        for (int i = 0; i < getComponentCount(); i++) {
            JButton button = (JButton) getComponent(i);
            button.setText(res.getString("sort"));
        }
    }
}
