package Server.Commands;

import Client.DataUtils.CommandObject;
import Server.Collection.City;
import Server.Launch.CollectWorker;
import Server.Launch.ControlUnit;
import Server.DBUtils.CollectionDB;
import Server.ConnectionUtils.Request;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Класс команды update-обновление элемента по id
 */
public class UpdateCommand implements Command {
    CollectWorker coll;
    static Logger LOGGER;
    TypeCommand type;
    static int argsSize;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param p-          переменная для управления командами
     * @param collection- переменнаяи для работы с коллекцией
     */
    public UpdateCommand(ControlUnit p, CollectWorker collection) {
        p.addCommand("update", this);
        this.coll = collection;
        type=TypeCommand.EDIT;
        argsSize=1;
    }

    /**
     * Функция выполнения команды
     */
    @Override
    public Request execute(CommandObject CO) throws IOException, SQLException {
        System.out.println(CO.getArgs().size());
        String checker;
        City newcity;
        long id = Long.parseLong(CO.getOption());
        for (City city : coll.getCollection()) {
            if (city.getIdOfCity() == id) {
                 newcity= new City(CO.getArgs());
                newcity.setId(id);
                newcity.setUser(CO.getLogin());
                checker = CollectionDB.UpdateIDDB(id, newcity);
                if (checker == null) {
                    coll.update(newcity, CO.getLogin());
                    return new Request("Команда update выполнена. Значение элемента коллекции с id " + Integer.parseInt(CO.getOption()), coll.getCollection(), null);
                } else {
                    return new Request(checker, null, null);
                }
            }
        }
        return new Request("Элемента с таким id нет.",null,null);
    }
    @Override
    public String getName() {
        return "update";
    }

    @Override
    public int getargsSize() {
        return argsSize;
    }
}