package Server.Collection;

import Client.CommandObject;
import Server.Commands.Command;
import Server.Request;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для хранения и запуска команд
 */
public class ControlUnit {
    /** Коллекция Map для хранения комманд*/
    private Map<String,Command>Commands=new HashMap<>();;
    /** Список последних восьми команд*/
    private List<String> lastCommands=new ArrayList<>();
    private int numberCommand=0;
    /**
     * Функция добавления комманды в Map
     * @param key- ключ комманды
     * @param command- комманда
     */

    public void addCommand(String key, Command command){
        Commands.put(key,command);
    }
    /**
     * Функция выполнения команды
     */
    private boolean checker=false;
    public Request executeCommand(String key,CommandObject CO) throws IOException {
        try {
            if (numberCommand==8) {
            numberCommand = 0;
                checker=true;
            }
            Request answer=Commands.get(key).execute(CO);
            lastCommands.add(key);
            if (checker){
                lastCommands.remove(numberCommand);
            }
            numberCommand++;
            return answer;

        } catch (IndexOutOfBoundsException e) {
            return null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Функция вывода последних восьми комманд
     */
    public String getListCommand() {
    StringBuilder stringBuilder=new StringBuilder();
    if (lastCommands.size() < 8) {
            return"Команда history не выполнена. Вы использовали меньше 8 команд";
        }
        else {
            lastCommands.forEach(commands->stringBuilder.append(commands+"\n"));
            }
            return "Команда history выполнена.\n"+"Вывод последних восьми использованных команд:\n"+stringBuilder.toString();
        }
        public void clearListCommand(){
        lastCommands.clear();
        }
    }
