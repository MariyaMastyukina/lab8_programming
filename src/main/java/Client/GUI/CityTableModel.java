package Client.GUI;

import Server.Collection.City;
import Server.ConnectionUtils.Request;

import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class CityTableModel extends AbstractTableModel {
    private Vector<String> columns;
    private Vector<Vector<Object>> data = new Vector<>();

    CityTableModel(ResourceBundle res, Request table) {
        changeColumns(res);
        updateTable(table.getNew_map());
    }

    public void changeColumns(ResourceBundle res) {
        columns = new Vector<>(Arrays.asList(
                res.getString("id"),
                res.getString("name"),
                res.getString("x"),
                res.getString("y"),
                res.getString("time"),
                res.getString("area"),
                res.getString("population"),
                res.getString("meters"),
                res.getString("capital"),
                res.getString("climate"),
                res.getString("government"),
                res.getString("governor"),
                res.getString("owner")
        ));
    }

    public Vector<String> getColumns() {
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
        return data.get(rowIndex).get(columnIndex);
    }

    public Vector<Vector<Object>> updateTable(CopyOnWriteArrayList<City> list) {
        data = new Vector<>();
        for (City e : list) {
            Vector<Object> row = new Vector<>(Arrays.asList(e.getDataRow()));
            data.add(row);
        }
        fireTableDataChanged();
        return data;
    }

}
