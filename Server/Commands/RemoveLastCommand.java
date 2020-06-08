package Server.Commands;

import Client.CommandObject;
import Server.Collection.CollectWorker;
import Server.Collection.ControlUnit;
import Server.CollectionDB;
import Server.Request;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды remove_last-Удаление последнего элемента
 */
public class RemoveLastCommand implements Command {
    private CollectWorker coll;
    static Logger LOGGER;
    TypeCommand type;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param p- переменная для управления командами
     * @param collection- переменнаяи для работы с коллекцией
     */
    public RemoveLastCommand(ControlUnit p, CollectWorker collection){
        p.addCommand("remove_last",this);
        this.coll=collection;
        LOGGER=Logger.getLogger(RemoveByIdCommand.class.getName());
        type=TypeCommand.EDIT;

    }
    /**
     * Функция выполнения команды
     */
    @Override
    public Request execute(CommandObject user) throws IOException, SQLException {
        LOGGER.log(Level.INFO,"Отправка результата выполнения команды на сервер");
        if (coll.getSize()!=0){
            long id=coll.get_id_last(user.getLogin());
            if (id!=0) {
                CollectionDB.removeIDBD(id, user.getLogin());
                coll.remove_last(user.getLogin());
                return new Request("Команда remove_last выполнена. Последний элемент коллекции удален",coll.getCollection());
            }
            else{
                return new Request("Команда remove_last не выполнена, так как вы не создали ни один объект",coll.getCollection());
            }
    }
        else {
            return new Request("Команда remove_last не выполнена. Коллекция пуста, удаление невозможно",coll.getCollection());
        }
        }
}
