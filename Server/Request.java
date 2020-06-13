package Server;

import Server.Collection.City;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class Request implements Serializable {
    private String answer;
    private CopyOnWriteArrayList<City> new_map;
    private CopyOnWriteArrayList<City> list;
    public Request(String answer, CopyOnWriteArrayList<City> new_map, CopyOnWriteArrayList<City> list){
        this.list=list;
        this.answer=answer;
        this.new_map=new_map;
    }

    public CopyOnWriteArrayList<City> getNew_map() {
        return new_map;
    }

    public CopyOnWriteArrayList<City> getList() {
        return list;
    }

    public String getAnswer() {
        return answer;
    }
}
