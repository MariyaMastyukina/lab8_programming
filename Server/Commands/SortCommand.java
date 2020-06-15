package Server.Commands;

import Client.CommandObject;
import Server.Collection.CollectWorker;
import Server.Collection.ControlUnit;
import Server.Request;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды sort-сортировка коллекции по возрастанию поля population
 */
public class SortCommand implements Command {
    CollectWorker coll;
    static Logger LOGGER;
    static int argsSize;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param p- переменная для управления командами
     * @param collection- переменнаяи для работы с коллекцией
     */
    public SortCommand(ControlUnit p, CollectWorker collection){
        p.addCommand("sort",this);
        this.coll=collection;
        argsSize=0;

        LOGGER=Logger.getLogger(SortCommand.class.getName());
    }
    /**
     * Функция выполнения команды
     */
    @Override
    public Request execute(CommandObject user) throws IOException {
        if (user.getOption().equals("id")){
             return new Request("", coll.sort_id(),null);
        }
        else if (user.getOption().equals("name")){
            return new Request(null, coll.sort_name(),null);
        }
        else if (user.getOption().equals("x")){
            return new Request("", coll.sort_X(),null);
        }
        else if (user.getOption().equals("y")){
            return new Request("", coll.sort_Y(),null);
        }
        else if (user.getOption().equals("date")){
            return new Request("", coll.sort_date(),null);
        }
        else if (user.getOption().equals("area")){
            return new Request("", coll.sort_area(),null);
        }
        else if (user.getOption().equals("population")){
            return new Request("", coll.sort_population(),null);
        }else if (user.getOption().equals("meters")){
            return new Request("", coll.sort_meters(),null);
        }else if (user.getOption().equals("capital")){
            return new Request("", coll.sort_capital(),null);
        }
        else if (user.getOption().equals("climate")){
            return new Request("", coll.sort_climate(),null);
        }else if (user.getOption().equals("government")){
            return new Request("", coll.sort_government(),null);
        }else if (user.getOption().equals("governor")){
            return new Request("", coll.sort_governor(),null);
        }
        else {
            return new Request("sort", coll.sort_user(),null);
        }
    }
    @Override
    public String getName() {
        return "sort";
    }

    @Override
    public int getargsSize() {
        return argsSize;
    }

}
