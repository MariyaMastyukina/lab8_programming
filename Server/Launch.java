package Server;

import Client.*;
import Server.Collection.*;
import Server.Collection.CollectWorker;
import Server.Collection.ControlUnit;
import Server.Commands.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.RecursiveTask;

/**
 * Класс, который обрабатывает ввод команд с консоли
 */
public class Launch implements Callable<Request> {
    private CommandObject command;
    private ControlUnit pusk;
    private CollectWorker collection;
    /**
     * Конструктор создания команд
     */
    public Launch(CollectWorker collection,CommandObject command,ControlUnit pusk) {
        this.collection=collection;
        this.pusk=pusk;
        this.command=command;
        Command group_counting_by_population = new GroupCountingByPopulationCommand(collection, pusk);
//        Command help = new HelpCommand(pusk);
//        Command sort = new SortCommand(pusk, collection);
        Command remove_by_id = new RemoveByIdCommand(pusk, collection);
        Command remove_all_by_meters_above_sea_level = new RemoveAllBYMetersAboveSeaLevelCommand(collection, pusk);
        Command clear = new ClearCommand(pusk, collection);
        Command info = new InfoCommand(pusk, collection);
//        Command show = new ShowCommand(pusk, collection);
        Command history = new HistoryCommand(pusk,collection);
        Command remove_last = new RemoveLastCommand(pusk, collection);
//        Command print_ascending = new PrintAscendingCommand(pusk, collection);
        Command add = new AddCommand(pusk, collection);
        Command update = new UpdateCommand(pusk, collection);
        Command sign_in=new SignInCommand(pusk);
        Command check_in=new CheckInCommand(pusk);
    }

    @Override
    public Request call() throws Exception {
        Request answer= null;
        try {
            if (command.getNameCommand().equals("getTable")) return new Request(null,collection.getCollection(),null);
            answer = pusk.executeCommand(command.getNameCommand(), command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;    }
}


