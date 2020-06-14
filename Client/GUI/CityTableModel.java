package Client.GUI;

import Server.Collection.City;
import Server.Request;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class CityTableModel extends DefaultTableModel {
    private Vector<String>columns;
    private Vector<Vector<Object>> data=new Vector<>();
    CityTableModel(ResourceBundle res, Request table){
        changeColumns(res);
        updateTable(table.getNew_map());
//        System.out.println(columns.get(0));
    }
    public void changeColumns(ResourceBundle res){
        columns=new Vector<>(Arrays.asList(
                "id",
                "name",
                "x",
                "y",
                "time",
                "area",
                "population",
                "meters",
                "capital",
                "climate",
                "government",
                "governor",
                "owner"
                ));
    }

    public Vector<String> getColumns() {
        return columns;
    }

    @Override
    public int getRowCount() {
        if (data!=null){
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
    public Vector<Vector<Object>> updateTable(CopyOnWriteArrayList<City> list){
        data=new Vector<>();
        for (City e:list){
            Vector<Object> row=new Vector<>(Arrays.asList(e.getDataRow()));
            data.add(row);
        }
        fireTableDataChanged();
        return data;
    }

}
