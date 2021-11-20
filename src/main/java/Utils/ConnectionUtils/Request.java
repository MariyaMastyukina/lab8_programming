package Utils.ConnectionUtils;

import Server.Model.City;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class Request implements Serializable {
    private String answer;
    private String status;
    private CopyOnWriteArrayList<City> newTable;

    public Request(String answer, CopyOnWriteArrayList<City> newTable, String status) {
        this.answer = answer;
        this.newTable = newTable;
        this.status = status;
    }

    public CopyOnWriteArrayList<City> getNewTable() {
        return newTable;
    }

    public String getAnswer() {
        return answer;
    }

    public String getStatus() {
        return status;
    }
}
