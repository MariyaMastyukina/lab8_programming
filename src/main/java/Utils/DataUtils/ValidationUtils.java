
package Utils.DataUtils;

import Client.GUI.AddWindow;
import Client.GUI.EditWindow;
import Client.GUI.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ValidationUtils {
    private static List<String> list = Arrays.asList("help", "add", "check_in", "clear", "exit", "group_counting_by_population", "history", "info", "execute_script", "print_ascending", "remove_all_by_meters_above_sea_level", "remove_by_id", "remove_last", "show", "sign_in", "sort", "update", "getTable");
    private static List<String> errors;

    public static Boolean isValidCommand(String command, String option) {
        boolean checker = false;
        for (String c : list) {
            if (c.equals(command)) checker = true;
        }
        if (!checker) {
            System.out.println("Такой команды не существует или она записана некорректно. Введите \"help\", чтобы получить список действующих команд");
            return false;
        }
        if (command.equals("remove_by_id") || command.equals("update")) {
            try {
                Long.parseLong(option);
            } catch (NumberFormatException e) {
                System.out.println("Выполнение команды невозможно. Введен некорректный id, повторите ввод команды");
                checker = false;
            }
        }
        if (command.equals("remove_all_by_meters_above_sea_level")) {
            try {
                Integer.parseInt(option);
            } catch (NumberFormatException e) {
                System.out.println("Выполнение команды невозможно. Введен некорректный meters_above_sea_level, повторите ввод команды");
                checker = false;
            }
        }
        return checker;
    }


    public static List<String> readFileArgs(Scanner scanner, MainWindow mainWindow) {
        List<String> args = new ArrayList<>();
        errors = new ArrayList<>();
        args.add(scanner.nextLine());
        args.add(ValidationUtils.readFileX(scanner));
        args.add(ValidationUtils.readFileY(scanner));
        args.add(ValidationUtils.readFileArea(scanner));
        args.add(ValidationUtils.readFilePopulation(scanner));
        args.add(ValidationUtils.readFileMetersAboveSeaLevel(scanner));
        args.add(ValidationUtils.readFileCapital(scanner));
        args.add(ValidationUtils.raedFileClimate(scanner));
        args.add(ValidationUtils.readFileGovernment(scanner));
        args.add(ValidationUtils.readFileGovernor(scanner));
        if (!errors.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < errors.size(); i++) {
                sb.append(i + 1).append(". ").append(errors.get(i)).append("\n");
            }
            JOptionPane.showMessageDialog(mainWindow, sb.toString(), "Неверные данные в файле", JOptionPane.INFORMATION_MESSAGE);
        }

        return args;
    }

    private static String isValidX(Scanner scanner) {

        try {
            float x = Float.parseFloat(scanner.nextLine());
            if (x > -375F) {
                return Float.toString(x);
            } else {
                return "-1000";
            }
        } catch (NumberFormatException e) {
            return "-1001";
        }
    }


    private static String isValidY(Scanner scanner) {
        try {
            int y = Integer.parseInt(scanner.nextLine());
            if (y > -966) {
                return Integer.toString(y);
            } else {
                return "-1000";
            }
        } catch (NumberFormatException e) {
            return "-1001";
        }
    }


    private static String isValidArea(Scanner scanner) {
        try {
            double area = Double.parseDouble(scanner.nextLine());
            if (area > 0) {
                return Double.toString(area);
            } else {
                return "-1000";
            }
        } catch (NumberFormatException e) {
            return "-1001";
        }
    }

    private static String isValidPopulation(Scanner scanner) {
        try {
            int population = Integer.parseInt(scanner.nextLine());
            if (population > 0) {
                return Integer.toString(population);
            } else {
                return "-1000";
            }
        } catch (NumberFormatException e) {
            return "-1001";
        }
    }

    private static String isValidMetersAboveSeaLevel(Scanner scanner) {
        try {
            int metersAboveSeaLevel = Integer.parseInt(scanner.nextLine());
            return Integer.toString(metersAboveSeaLevel);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String isValidCapital(Scanner scanner) {
        String capital;
        capital = scanner.nextLine();
        if (capital.equals("true") || capital.equals("false"))
            return capital;
        else
            return null;
    }


    private static String isValidClimate(Scanner scanner) {
        boolean checker = false;
        String clim = scanner.nextLine();
        if (clim.equalsIgnoreCase("HUMIDCONTINENTAL")) {
            checker = true;
        } else if (clim.equalsIgnoreCase("SUBARCTIC")) {
            checker = true;
        } else if (clim.equalsIgnoreCase("TUNDRA")) {
            checker = true;
        }
        return checker ? clim.toUpperCase() : null;
    }

    private static String isValidGovernment(Scanner scanner) {
        boolean checker = false;
        String gov = scanner.nextLine();
        if (gov.equalsIgnoreCase("CORPORATOCRACY")) {
            checker = true;
        } else if (gov.equalsIgnoreCase("MERITOCRACY")) {
            checker = true;
        } else if (gov.equalsIgnoreCase("OLIGARCHY")) {
            checker = true;
        }
        return checker ? gov.toUpperCase() : null;
    }

    private static String isValidGovernor(Scanner scanner) {
        String nameGovernor = scanner.nextLine();
        char[] symbols = nameGovernor.toLowerCase().toCharArray();
        String validationName = "abcdefghijklmnopqrstuvwxyzабвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        for (char c : symbols) {
            if (validationName.indexOf(c) == -1) {
                return null;
            }
        }
        return nameGovernor;
    }

    private static String readFileX(Scanner scanner) {
        String x = isValidX(scanner);
        if (x.equals("-1000") || x.equals("-1001")) {
            if (x.equals("-1000"))
                errors.add("В файле указано неверное значение. Значение не может быть меньше -375, координата города Х по умолчанию устанавливается 0");
            else
                errors.add("В файле указан неверный формат.Координата города Х по умолчанию устанавливается 0");
            x = "0.0";
        }
        return x;
    }

    private static String readFileY(Scanner scanner) {
        String y = isValidY(scanner);
        if (y.equals("-1000") || y.equals("-1001")) {
            if (y.equals("-1000"))
                errors.add("В файле указан неверное значение. Значение не может быть меньше -966, координата города Y по умолчанию устанавливается 0");
            else
                errors.add("В файле указан неверный формат.Координата города Y по умолчанию устанавливается 0");
            y = "0";
        }
        return y;
    }

    private static String readFileArea(Scanner scanner) {
        String area = isValidArea(scanner);
        if (area.equals("-1000") || area.equals("-1001")) {
            if (area.equals("-1000"))
                errors.add("В файле указано неверное значение. Значение не может быть отрицательным или равным 0, площадь города по умолчанию устанавливается 1.0");
            else
                errors.add("В файле указан неверный формат.Площадь города по умолчанию устанавливается 1.0");
            area = "1.0";
        }
        return area;
    }

    private static String readFilePopulation(Scanner scanner) {
        String population = isValidPopulation(scanner);
        if (population.equals("-1000") || population.equals("-1001")) {
            if (population.equals("-1000"))
                errors.add("В файле указано неверное значение. Значение не может быть отрицательным или равным 0, введите население города еще раз, население города по умолчанию устанавливается 1");
            else
                errors.add("В файле указан неверный формат. Население города по умолчанию устанавливается 1");
            population = "1";
        }
        return population;
    }

    private static String readFileMetersAboveSeaLevel(Scanner scanner) {
        String metersAboveSeaLevel = isValidMetersAboveSeaLevel(scanner);
        if (metersAboveSeaLevel == null) {
            errors.add("В файле указан неверный формат.Уровень города над морем по умолчанию устанавливается 0");
            metersAboveSeaLevel = "0";
        }
        return metersAboveSeaLevel;
    }

    private static String readFileCapital(Scanner scanner) {
        String capital = isValidCapital(scanner);
        if (capital == null) {
            errors.add("В файле указано неверное значение. По умолчанию город не является столицей");
            capital = "false";
        }
        return capital;
    }

    private static String raedFileClimate(Scanner scanner) {
        String climate = isValidClimate(scanner);
        if (climate == null) {
            errors.add("В файле указано неверное значение. По умолчанию климат города HUMIDCONTINENTAL");
            climate = "HUMIDCONTINENTAL";
        }
        return climate;
    }

    private static String readFileGovernment(Scanner scanner) {
        String government = isValidGovernment(scanner);
        if (government == null) {
            errors.add("В файле указано неверное значение. По умолчанию правительство города CORPORATOCRACY");
            government = "CORPORATOCRACY";
        }
        return government;
    }

    private static String readFileGovernor(Scanner scanner) {
        String nameGovernor = isValidGovernor(scanner);
        if (nameGovernor == null) {
            errors.add("В файле указано неверное имя губернатора. Имя губернатора не вводится");
            nameGovernor = "";
        }
        return nameGovernor;
    }

    public static boolean checkFields(List<JTextField> fields, Component component, List<JComboBox> checkboxes) {
        boolean checker;
        if (fields.get(0).getText().isEmpty()) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Имя города не может быть пустым", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (fields.get(1).getText().isEmpty()) {
            checker = false;
            JOptionPane.showMessageDialog(fields.get(1), "Координата Х не может быть пустой", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!checkXField(fields.get(1).getText(), component)) {
            checker = false;
        } else if (fields.get(2).getText().isEmpty()) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Координата Y не может быть пустой", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!checkYField(fields.get(2).getText(), component)) {
            checker = false;
        } else if (fields.get(3).getText().isEmpty()) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Площадь не может быть пустой", "Error", JOptionPane.ERROR_MESSAGE);

        } else if (!checkAreaField(fields.get(3).getText(), component)) {
            checker = false;
        } else if (fields.get(4).getText().isEmpty()) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Население не может быть пустым", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!checkPopulationField(fields.get(4).getText(), component)) {
            checker = false;
        } else if (fields.get(5).getText().isEmpty()) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Уровень над морем не может быть пустым", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!checkMetersField(fields.get(5).getText(), component)) {
            checker = false;
        } else if (Objects.equals(checkboxes.get(0).getSelectedItem(), "")) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Выберите столицу", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Objects.equals(checkboxes.get(1).getSelectedItem(), "")) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Выберите климат", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Objects.equals(checkboxes.get(2).getSelectedItem(), "")) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Выберите правительство", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (fields.get(6).getText().isEmpty()) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Имя губернатора не может быть пустым", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            checker = checkNameField(fields.get(6).getText(), component);
        }
        return checker;
    }

    private static boolean checkXField(String x, Component component) {
        boolean checker = true;
        try {
            Float X = Float.parseFloat(x);
            if (X <= -375F) {
                checker = false;
                JOptionPane.showMessageDialog(component, "Указано неверное значение координаты X. Значение не может быть меньше -375", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Указан неверный формат координаты Х", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }

    private static boolean checkYField(String y, Component component) {
        boolean checker = true;
        try {
            Integer Y = Integer.parseInt(y);
            if (Y <= -966) {
                checker = false;
                JOptionPane.showMessageDialog(component, "Указан неверное значение координаты Y. Значение не может быть меньше -966", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Указан неверный формат координаты Y", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }

    private static boolean checkAreaField(String area, Component component) {
        boolean checker = true;
        try {
            Double AREA = Double.parseDouble(area);
            if (AREA <= 0) {
                checker = false;
                JOptionPane.showMessageDialog(component, "Указано неверное значение площади города. Значение не может быть отрицательным или равным 0", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Указан неверный формат площади города", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }

    private static boolean checkPopulationField(String population, Component component) {
        boolean checker = true;
        try {
            Integer Population = Integer.parseInt(population);
            if (Population <= 0) {
                checker = false;
                JOptionPane.showMessageDialog(component, "Указано неверное значение населения города. Значение не может быть отрицательным или равным 0", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Указан неверный формат населения", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }

    private static boolean checkNameField(String name, Component component) {
        boolean checker = true;
        char[] symbols = name.toLowerCase().toCharArray();
        Boolean checkerName = true;
        String validationName = "abcdefghijklmnopqrstuvwxyzабвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        for (char c : symbols) {
            if (validationName.indexOf(c) == -1) {
                checkerName = false;
            }
        }
        if (!checkerName) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Указано неверное имя губернатора. Используйте только латинские символы", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }

    private static boolean checkMetersField(String name, Component component) {
        boolean checker = true;
        try {
            Integer.parseInt(name);
        } catch (NumberFormatException e) {
            checker = false;
            JOptionPane.showMessageDialog(component, "Указан неверный формат метров", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return checker;
    }
}
