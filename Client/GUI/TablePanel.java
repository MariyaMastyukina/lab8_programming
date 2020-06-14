package Client.GUI;

import Client.User;
import Server.Request;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import javafx.scene.control.TableColumn;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.PipedWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.regex.PatternSyntaxException;

public class TablePanel extends JPanel {
    private final JTextField idFilter=new JTextField();
    private final JTextField xFilter=new JTextField();
    private final JTextField governmentFilter=new JTextField();
    private final JTextField areaFilter=new JTextField();
    private final JTextField governorFilter=new JTextField();
    private final JTextField timeFilter=new JTextField();
    private final JTextField yFilter=new JTextField();
    private final JTextField metersFilter=new JTextField();
    private final JTextField populationFilter=new JTextField();
    private final JTextField climateFilter=new JTextField();
    private final JTextField capitalFilter=new JTextField();
    private LinkedHashMap<String, JTextField> map = new LinkedHashMap<>();
    private JTable table;
    PipedWriter cmdWriter;
    private ResourceBundle res;
    private TableRowSorter<CityTableModel> sorter;
    private JTextField ownerFilter=new JTextField();
    private JTextField nameFilter=new JTextField();
    CityTableModel tableModel;

    public TablePanel(CityTableModel tableModel, PipedWriter cmdWriter, ResourceBundle res,Request list) {
        this.cmdWriter=cmdWriter;
        this.res=res;
        setLayout(new BorderLayout());
        setTable(list,tableModel);
        this.tableModel=tableModel;
//        table=new JTable(tableModel.updateTable();tableModel.changeColumns(res););
//        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
//        table.setFillsViewportHeight(true);
//        sorter=new TableRowSorter<>(tableModel);
//        table.setRowSorter(sorter);
//        table.addMouseListener(new ClickHandler());
//        table.setDefaultRenderer(LocalDateTime.class,new DateRender(res.getLocale()));
//        JScrollPane scrollPane=new JScrollPane(table);
//        add(scrollPane,BorderLayout.CENTER);
        JPanel filterPanel=new JPanel(new GridLayout(1,13));
        map.put("id",idFilter);
        map.put("name",nameFilter);
        map.put("x",xFilter);
        map.put("y",yFilter);
        map.put("time",timeFilter);
        map.put("area",areaFilter);
        map.put("population",populationFilter);
        map.put("meters",metersFilter);
        map.put("capital",capitalFilter);
        map.put("climate",climateFilter);
        map.put("government",governmentFilter);
        map.put("governor",governorFilter);
        map.put("owner",ownerFilter);
        int i=0;
        for (Map.Entry<String,JTextField> element:map.entrySet()){
            int finalI=i;
            filterPanel.add(element.getValue());
            element.getValue().getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    textFilter(element.getKey(),finalI);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    textFilter(element.getKey(),finalI);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    textFilter(element.getKey(),finalI);
                }
            });
            i++;
        }
        add(filterPanel,BorderLayout.NORTH);
    }

    public void setRes(ResourceBundle res) {
        this.res = res;
    }
    private void textFilter(String field, int index){
        RowFilter<CityTableModel,Object> rowFilter=null;
        try{
            rowFilter= RowFilter.regexFilter(map.get(field).getText(),index);
        }catch (PatternSyntaxException e){
            return;
        }
        sorter.setRowFilter(rowFilter);
    }
    class ClickHandler extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            Point p=e.getPoint();
            int row=table.rowAtPoint(p);
            if (e.getClickCount()==1){
                if (row!=-1) {
                    HashMap<String, Object> result = new HashMap<>();
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        result.put(table.getColumnName(i), table.getValueAt(row, i));
                    }
                    if (result.get("user").equals(User.getLogin())) {
                        new EditWindow(result, cmdWriter, res);
                    } else {
                        new InfoWindow(result, res);
                    }
                }
            }
        }
    }
    static class DateRender extends DefaultTableCellRenderer{
        DateTimeFormatter formatter;
        public DateRender(Locale locale){
            super();
            formatter=DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale);
        }
    }
    public void updateTime(){
        table.setDefaultRenderer(LocalDateTime.class,new DateRender(res.getLocale()));
    }
    public JTable setTable(Request list,CityTableModel tableModel) {
        for (int i=0;i<tableModel.getColumnCount();i++){
            tableModel.addColumn(tableModel.getColumns().get(i));
        }
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        sorter = new TableRowSorter<>(tableModel);
        table.addMouseListener(new ClickHandler());
        table.setRowSorter(sorter);
        table.setColumnSelectionAllowed(true);
        table.setDefaultRenderer(LocalDateTime.class, new DateRender(res.getLocale()));
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p=e.getPoint();
                int collumn=table.rowAtPoint(p);
                if (e.getClickCount()==1){
                    if (collumn!=-1) {
                        try {
                            cmdWriter.write("sort "+table.getColumnName(collumn));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        return table;
    }

}
