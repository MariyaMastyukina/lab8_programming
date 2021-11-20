package Client.GUI;

import Server.Model.City;
import Utils.ConnectionUtils.Request;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public class CityTableModel extends AbstractTableModel {
    private List<String> columns;
    private List<List<String>> data = new ArrayList<>();

    public CityTableModel(ResourceBundle resourceBundle, Request request) {
        changeColumns(resourceBundle);
        updateTable(request.getNewTable());
    }

    public void changeColumns(ResourceBundle resourceBundle) {
        columns = new ArrayList<>(Arrays.asList(
                resourceBundle.getString("id"),
                resourceBundle.getString("name"),
                resourceBundle.getString("x"),
                resourceBundle.getString("y"),
                resourceBundle.getString("time"),
                resourceBundle.getString("area"),
                resourceBundle.getString("population"),
                resourceBundle.getString("meters"),
                resourceBundle.getString("capital"),
                resourceBundle.getString("climate"),
                resourceBundle.getString("government"),
                resourceBundle.getString("governor"),
                resourceBundle.getString("owner")
        ));
    }

    public List<String> getColumns() {
        return columns;
    }

    @Override
    public int getRowCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        System.out.println();
        return data.get(rowIndex).get(columnIndex);
    }

    public List<List<String>> updateTable(CopyOnWriteArrayList<City> cities) {
        data = new ArrayList<>();
        for (City e : cities) {
            List<String> row = new ArrayList<>(e.getRow());
            data.add(row);
        }
        fireTableDataChanged();
        return data;
    }

}
