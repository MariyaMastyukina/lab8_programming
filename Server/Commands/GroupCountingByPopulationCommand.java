package Server.Commands;

import Client.DataUtils.CommandObject;
import Server.Launch.CollectWorker;
import Server.Launch.ControlUnit;
import Server.ConnectionUtils.Request;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды group_counting_by_population-группировка коллекции
 */
public class GroupCountingByPopulationCommand implements Command {
    CollectWorker coll;
    static Logger LOGGER;
    TypeCommand type;
    static int argsSize;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param p- переменная для управления командами
     * @param collection- переменнаяи для работы с коллекцией
     */
    public GroupCountingByPopulationCommand(CollectWorker collection, ControlUnit p){
        p.addCommand("group_counting_by_population",this);
        this.coll=collection;
        LOGGER=Logger.getLogger(GroupCountingByPopulationCommand.class.getName());
        type=TypeCommand.INFORM;
        argsSize=0;

    }
    /**
     * Функция выполнения команды
     */
    @Override
    public Request execute(CommandObject user) throws IOException {
        LOGGER.log(Level.INFO,"Отправка результата выполнения команды на сервер");
        return new Request(coll.group_counting_by_population(),null,null);
    }
    @Override
    public String getName() {
        return "group_counting_by_population";
    }

    @Override
    public int getargsSize() {
        return argsSize;
    }
}
