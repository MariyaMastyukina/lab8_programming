package Client.GUI;

import Utils.ConnectionUtils.Request;
import Utils.UserUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PipedWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.*;
import java.util.regex.PatternSyntaxException;

public class TablePanel extends JPanel {
    private JTextField idFilter;
    private JTextField xFilter;
    private JTextField governmentFilter;
    private JTextField areaFilter;
    private JTextField governorFilter;
    private JTextField timeFilter;
    private JTextField yFilter;
    private JTextField metersFilter;
    private JTextField populationFilter;
    private JTextField climateFilter;
    private JTextField capitalFilter;
    private JTextField ownerFilter;
    private JTextField nameFilter;
    private LinkedHashMap<String, JTextField> map = new LinkedHashMap<>();
    private JTable table;
    private PipedWriter commandWriter;
    private ResourceBundle resourceBundle;
    private TableRowSorter<CityTableModel> sorter;
    private List<String> columnsName;
    private CityTableModel tableModel;
    private SortPanel sortPanel;
    private UserUtils userUtils;
    private JPanel mainPanel;
    private JPanel filterPanel;

    public TablePanel(CityTableModel tableModel, PipedWriter commandWriter, ResourceBundle resourceBundle, Request list, UserUtils userUtils) {
        this.userUtils = userUtils;
        this.commandWriter = commandWriter;
        this.resourceBundle = resourceBundle;
        this.tableModel = tableModel;
        initComponents();
        setLayout(new BorderLayout());
        setTable(tableModel);
        add(mainPanel, BorderLayout.NORTH);
    }

    private void initComponents() {
        columnsName = List.of("id", "name", "x", "y", "date", "area", "population", "meters", "capital", "climate", "government", "governor", "user");
        idFilter = new JTextField();
        xFilter = new JTextField();
        governmentFilter = new JTextField();
        areaFilter = new JTextField();
        governorFilter = new JTextField();
        timeFilter = new JTextField();
        yFilter = new JTextField();
        metersFilter = new JTextField();
        populationFilter = new JTextField();
        climateFilter = new JTextField();
        capitalFilter = new JTextField();
        ownerFilter = new JTextField();
        nameFilter = new JTextField();
        map.put("id", idFilter);
        map.put("name", nameFilter);
        map.put("x", xFilter);
        map.put("y", yFilter);
        map.put("time", timeFilter);
        map.put("area", areaFilter);
        map.put("population", populationFilter);
        map.put("meters", metersFilter);
        map.put("capital", capitalFilter);
        map.put("climate", climateFilter);
        map.put("government", governmentFilter);
        map.put("governor", governorFilter);
        map.put("owner", ownerFilter);

        idFilter = new JTextField();
        mainPanel = new JPanel(new GridLayout(2, 1));
        sortPanel = new SortPanel(resourceBundle, commandWriter);
        filterPanel = new JPanel(new GridLayout(1, 13));
        int i = 0;
        for (Map.Entry<String, JTextField> element : map.entrySet()) {
            int finalI = i;
            filterPanel.add(element.getValue());
            element.getValue().getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    textFilter(element.getKey(), finalI);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    textFilter(element.getKey(), finalI);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    textFilter(element.getKey(), finalI);
                }
            });
            i++;
        }
        mainPanel.add(sortPanel);
        mainPanel.add(filterPanel);
    }

    private void textFilter(String field, int index) {
        RowFilter<CityTableModel, Object> rowFilter = null;
        try {
            rowFilter = RowFilter.regexFilter(map.get(field).getText(), index);
        } catch (PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rowFilter);
    }

    private class ClickHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Point p = e.getPoint();
            int row = table.rowAtPoint(p);
            if (e.getClickCount() == 1) {
                if (row != -1) {
                    HashMap<String, Object> result = new HashMap<>();
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        result.put(columnsName.get(i), table.getValueAt(row, i));
                    }
                    for (String tableName : result.keySet()) {
                        System.out.println(tableName + ":" + result.get(tableName));
                    }
                    if (result.get("user").equals(userUtils.getLogin())) {
                        new EditWindow(result, commandWriter, resourceBundle);
                    } else {

                        new InfoWindow(result, resourceBundle);
                    }
                }
            }
        }
    }

    private class DateRender extends DefaultTableCellRenderer {
        DateTimeFormatter formatter;

        public DateRender(Locale locale) {
            super();
            formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale);
        }
    }

    private void setTable(CityTableModel tableModel) {

        table = new JTable(tableModel);
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            table.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(tableModel.getColumns().get(i));
        }
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        sorter = new TableRowSorter<>(tableModel);
        table.addMouseListener(new ClickHandler());
        table.setRowSorter(sorter);
        table.setColumnSelectionAllowed(true);
        table.setDefaultRenderer(LocalDateTime.class, new DateRender(resourceBundle.getLocale()));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void updateSort() {
        sortPanel.updateSort(resourceBundle);
    }

    public void updateTime() {
        table.setDefaultRenderer(LocalDateTime.class, new DateRender(resourceBundle.getLocale()));
    }


    public void updateColumns() {
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            table.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(tableModel.getColumns().get(i));
        }
        repaint();
    }

}
