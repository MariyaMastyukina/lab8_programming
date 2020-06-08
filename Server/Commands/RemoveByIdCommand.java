package Server.Commands;

import Client.CommandObject;
import Server.Collection.City;
import Server.Collection.CollectWorker;
import Server.Collection.ControlUnit;
import Server.CollectionDB;
import Server.Request;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды remove_by_id-Удаление элементов по id
 */

public class RemoveByIdCommand implements Command {
    CollectWorker coll;
    static Logger LOGGER;
    TypeCommand type;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param p- переменная для управления командами
     * @param collection- переменнаяи для работы с коллекцией
     */
    public RemoveByIdCommand(ControlUnit p, CollectWorker collection){
        p.addCommand("remove_by_id",this);
        this.coll=collection;
        LOGGER=Logger.getLogger(RemoveByIdCommand.class.getName());
        type=TypeCommand.EDIT;
    }
    /**
     * Функция выполнения команды
     */
    @Override
    public Request execute(CommandObject CO) throws IOException, SQLException {
        LOGGER.log(Level.INFO, "Отправка результата выполнения команды на сервер");
        String checker = null;
        long id = Long.parseLong(CO.getOption());
        if (coll.getSize() != 0) {
            for (City city : coll.getCollection()) {
                if (city.getIdOfCity() == id) {
                    checker = CollectionDB.removeIDBD(id, CO.getLogin());
                    if (checker == null) {
                        coll.remove_by_id(id, CO.getLogin());
                        return new Request("Команда remove_by_id выполнена. Элемент из коллекции с id " + id + " удален",coll.getCollection());
                    } else return new Request(checker,coll.getCollection());
                }
            }
            return new Request("Элемента с таким id нет. Введите команду \"show\", чтобы увидеть элементы коллекции и их id.",coll.getCollection());
        } else {
            return new Request("Команда remove_by_id не выполнена. Коллекция пуста",coll.getCollection());
        }
    }
}
