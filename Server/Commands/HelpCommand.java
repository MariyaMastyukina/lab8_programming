package Server.Commands;

import Client.CommandObject;
import Server.Collection.ControlUnit;
import Server.Request;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды help-список команд
 */
public class HelpCommand implements Command {
    static Logger LOGGER;
    TypeCommand type;
    static int argsSize;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param p- переменная для управления командами
     */
    public HelpCommand(ControlUnit p){
        p.addCommand("help",this);
        LOGGER=Logger.getLogger(HelpCommand.class.getName());
        type=TypeCommand.INFORM;
        argsSize=0;
    }
    /**
     * Функция выполнения команды
     */
    @Override
    public Request execute( CommandObject user) throws IOException {
        LOGGER.log(Level.INFO,"Отправка результата выполнения команды на сервер");
        StringBuilder sb=new StringBuilder();
        sb.append("check_in:регистрация");
        sb.append("sign_in:вход");
        sb.append("help:вывести справку по доступным командам").append("\n");
        sb.append("info:вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)").append("\n");
        sb.append("show:вывести в стандартный поток вывода все элементы коллекции в строковом представлении").append("\n");
        sb.append("add:добавить новый элемент в коллекцию").append("\n");
        sb.append("update id :обновить значение элемента коллекции, id которого равен заданному").append("\n");
        sb.append("remove_by_id id:удалить элемент из коллекции по его id").append("\n");
        sb.append("execute_script file_name:считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.").append("\n");
        sb.append("exit:завершить программу (без сохранения в файл)").append("\n");
        sb.append("remove_last:удалить последний элемент из коллекции").append("\n");
        sb.append("sort:отсортировать коллекцию в естественном порядке").append("\n");
        sb.append("history:вывести последние 8 команд (без их аргументов)").append("\n");
        sb.append("remove_all_by_meters_above_sea_level metersAboutSeaLevel:удалить из коллекции все элементы, значение поля metersAboveSeaLevel которого эквивалентно заданному").append("\n");
        sb.append("group_counting_by_population:сгруппировать элементы коллекции по значению поля population, вывести количество элементов в каждой группе").append("\n");
        sb.append("print_ascending:вывести элементы коллекции в порядке возрастания");
        return new Request(sb.toString(),null,null);
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public int getargsSize() {
        return argsSize;
    }

}
