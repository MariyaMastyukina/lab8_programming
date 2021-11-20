package Server.Launch;

import Server.Commands.Command;
import Utils.ConnectionUtils.Request;
import Utils.DataUtils.CommandUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ControlUnit {

    private static Map<String, Command> commands = new HashMap<>();
    private final List<String> lastCommands = new ArrayList<>();
    private int numberCommand = 0;
    private boolean checker = false;

    public void addCommand(String key, Command command) {
        commands.put(key, command);
    }

    public static Map<String, Command> getCommands() {
        return commands;
    }

    public Request executeCommand(String key, CommandUtils commandUtils) throws IOException {
        try {
            if (numberCommand == 8) {
                numberCommand = 0;
                checker = true;
            }
            Request answer = commands.get(key).execute(commandUtils);
            lastCommands.add(key);
            if (checker) {
                lastCommands.remove(numberCommand);
            }
            numberCommand++;
            return answer;

        } catch (IndexOutOfBoundsException e) {
            return null;
        } catch (NoSuchAlgorithmException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getListCommand() {
        StringBuilder stringBuilder = new StringBuilder();
        if (lastCommands.size() < 8) {
            return null;
        } else {
            lastCommands.forEach(commands -> stringBuilder.append(commands + "\n"));
        }
        return "Команда history выполнена.\n" + "Вывод последних восьми использованных команд:\n" + stringBuilder.toString();
    }

    public void clearListCommand() {
        lastCommands.clear();
    }
}
