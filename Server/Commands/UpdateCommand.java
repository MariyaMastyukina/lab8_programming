package Server.Commands;

import Client.CommandObject;
import Server.Collection.City;
import Server.Collection.CollectWorker;
import Server.Collection.ControlUnit;
import Server.CollectionDB;
import Server.Request;

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
    }

    /**
     * Функция выполнения команды
     */
    @Override
    public Request execute(CommandObject CO) throws IOException, SQLException {
        System.out.println(CO.getArgs().size());
        String checker = null;
        long id = Long.parseLong(CO.getOption());
        for (City city : coll.getCollection()) {
            if (city.getIdOfCity() == id) {
                City newcity = new City(CO.getArgs());
                newcity.setId(id);
                newcity.setUser(CO.getLogin());
                checker = CollectionDB.UpdateIDDB(id, newcity);
                if (checker == null) {
                    coll.update(newcity, CO.getLogin());
                    return new Request( "Команда update выполнена. Значение элемента коллекции с id " + Integer.parseInt(CO.getOption()) + " обновлено, введите команду \"show\", чтобы увидеть содержимое коллекции",coll.getCollection());
                }
            }
            else{
                return new Request(checker,coll.getCollection());
            }
        }
        return new Request("Элемента с таким id нет. Введите команду \"show\", чтобы увидеть элементы коллекции и их id.",coll.getCollection());
    }
}