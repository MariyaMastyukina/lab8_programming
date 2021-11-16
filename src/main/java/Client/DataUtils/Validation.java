
package Client.DataUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Класс, проверяющий валидность вводимых команд
 */
public class Validation {
    String command;
    String option;
    Component component;

    /**
     * Конструктор класса, принимающий имя команды и ее аргумент
     */
    public Validation(String command, String option, Component component) {
        this.component = component;
        this.command = command;
        this.option = option;
    }

    /**
     * Функция проверки валидности вводимой команды
     */
    public Boolean isValidation() {
        boolean checker;
        if (command.equals("remove_by_id") || command.equals("update")) {
            try {
                Long.parseLong(option);
                checker = true;
            } catch (NumberFormatException e) {
                checker = false;
                JOptionPane.showMessageDialog(component, "Выполнение команды невозможно. Введен некорректный id, повторите ввод команды", "ОШИБКА", JOptionPane.ERROR_MESSAGE);
            }
        } else if (command.equals("remove_all_by_meters_above_sea_level")) {
            try {
                Integer.parseInt(option);
                checker = true;
            } catch (NumberFormatException e) {
                checker = false;
                JOptionPane.showMessageDialog(component, "Выполнение команды невозможно. Введен некорректный meters_above_sea_level, повторите ввод команды", "ОШИБКА", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            checker = true;
        }
        return checker;
    }
}
