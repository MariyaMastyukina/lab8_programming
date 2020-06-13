package Client;

import Client.GUI.AddWindow;
import Client.GUI.EditWindow;
import Server.Collection.Climate;
import Server.Collection.Government;

import java.io.PipedWriter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Класс-обертка для команды
 */
public class CommandObject implements Serializable {
    private String login;
    private char[] password;
    /** Поле имя команды*/
    private String nameCommand;
    /** Поле аргумент команды*/
    private String option;
    /** Поле валидности введенной команды*/
    private Boolean checker=false;
    /** Поле список аргументов для команд add и update*/
    private List<String> args = new ArrayList<>();
    /**
     * Конструктор - создания команды и проверка валидности ее ввода
     * @param line -строка с консоли
     * @param filename -имя файла execute_script
     */
    public CommandObject(String line, String filename) {
        if (!line.trim().equals("")) {
            if (line.contains(" ")) {
                String[] lineParts = line.split(" ", 2);
                Validation validator = new Validation(lineParts[0], lineParts[1],User.mainWindow);
                checker = validator.isValidation();
                if (checker) {
                    nameCommand = lineParts[0];
                    option = lineParts[1];
                }
            } else {
                System.out.println(line);
                Validation validator = new Validation(line, null,User.mainWindow);
                checker = validator.isValidation();
                if (checker) {
                    nameCommand = line;
                    option = null;
                }
            }
        }

    }

    /**
     * Функция создания списка аргументов для команд add и update
     */
    private void setListArgs() {

    }
    /**
     * Функция заполнения списка аргументов для команд add и update существующим
     */
    public void setArgs(List<String> args){
        this.args=args;
    }
    /**
     * Функция получения значения поля {@link CommandObject#nameCommand}
     * @return  имя команды
     */
    public String getNameCommand() {
        return nameCommand;
    }
    /**
     * Функция получения значения поля {@link CommandObject#args}
     * @return  список аргументов для команд add и update
     */
    public List<String> getArgs() {
        return args;
    }
    /**
     * Функция получения значения поля {@link CommandObject#option}
     * @return  аргумент команды
     */
    public String getOption() {
        return option;
    }
    /**
     * Функция получения значения поля {@link CommandObject#checker}
     * @return  валидность команды
     */
    public Boolean getChecker(){
        return checker;
    }
    public char[] getPassword(){
        return password;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
