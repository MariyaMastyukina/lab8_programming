package Server.Commands;

import Server.Launch.ControlUnit;
import Utils.ConnectionUtils.Request;
import Utils.DataUtils.CommandUtils;

import java.io.IOException;

public class HelpCommand implements Command {
    private final int argsSize = 0;
    private final String name = "help";
    private final boolean isButton = true;

    public HelpCommand(ControlUnit controlUnit) {
        controlUnit.addCommand(name, this);
    }

    @Override
    public Request execute(CommandUtils commandUtils) throws IOException {
        String sb = "check_in:регистрация\n" +
                "sign_in:вход\n" +
                "help:вывести справку по доступным командам" + "\n" +
                "show:вывести в стандартный поток вывода все элементы коллекции в строковом представлении" + "\n" +
                "add:добавить новый элемент в коллекцию" + "\n" +
                "update id :обновить значение элемента коллекции, id которого равен заданному" + "\n" +
                "remove_by_id id:удалить элемент из коллекции по его id" + "\n" +
                "execute_script file_name:считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме." + "\n" +
                "exit:завершить программу (без сохранения в файл)" + "\n" +
                "remove_last:удалить последний элемент из коллекции" + "\n" +
                "sort:отсортировать коллекцию в естественном порядке" + "\n" +
                "history:вывести последние 8 команд (без их аргументов)" + "\n" +
                "remove_all_by_meters_above_sea_level metersAboutSeaLevel:удалить из коллекции все элементы, значение поля metersAboveSeaLevel которого эквивалентно заданному" + "\n" +
                "group_counting_by_population:сгруппировать элементы коллекции по значению поля population, вывести количество элементов в каждой группе" + "\n" +
                "print_ascending:вывести элементы коллекции в порядке возрастания";
        return new Request(sb, null, "Success");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getArgsSize() {
        return argsSize;
    }

    @Override
    public boolean isButton() {
        return isButton;
    }

}
