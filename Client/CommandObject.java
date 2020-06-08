package Client;

import Server.Collection.Climate;
import Server.Collection.Government;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private transient Scanner scanner=new Scanner(System.in);
    /**
     * Конструктор - создания команды и проверка валидности ее ввода
     * @param line -строка с консоли
     * @param filename -имя файла execute_script
     */
    public CommandObject(String line, String filename) {
        if (!line.trim().equals("")) {
            if (line.contains(" ")) {
                String[] lineParts = line.split(" ", 2);
                Validation validator = new Validation(lineParts[0], lineParts[1]);
                checker = validator.isValidation();
                if (checker) {
                    nameCommand = lineParts[0];
                    option = lineParts[1];
                }
            } else {
                Validation validator = new Validation(line, null);
                checker = validator.isValidation();
                if (checker) {
                    nameCommand = line;
                    option = null;
                }
            }
        }
        if (nameCommand!=null && (filename==null)){
                if (nameCommand.equals("add") || nameCommand.equals("update")) {
                    setListArgs();
                }
        }

    }

    /**
     * Функция создания списка аргументов для команд add и update
     */
    private void setListArgs() {
        System.out.println("Введите название города. Пример: Москва");
        args.add(scanner.nextLine());
        System.out.println("Введите координату города X(число должно быть больше -375). Пример: 9000000.75");
        Boolean checker1 = true;
        Float x = 0F;
        while (checker1) {
            try {
                x = Float.parseFloat(scanner.nextLine());
                if (x > -375F) {
                    checker1 = false;
                } else {
                    System.out.println("Значение не может быть меньше или равно -375, введите координату города X еще раз");
                }
            } catch (NumberFormatException e) {
                System.out.println("Формат введенного значения неверный, введите значение еще раз");
            }
        }
        args.add(x.toString());
        System.out.println("Введите координату города Y(число должно быть больше -966). Пример: 8000000");
        Boolean checker2 = true;
        Integer y = 0;
        while (checker2) {
            try {
                y = Integer.parseInt(scanner.nextLine());
                if (y > -966) {
                    checker2 = false;
                } else {
                    System.out.println("Значение не может быть меньше или равно -966, введите координату города Y еще раз");
                }
            } catch (NumberFormatException e) {
                System.out.println("Формат введенного значения неверный, введите значение еще раз");
            }
        }
        args.add(y.toString());
        System.out.println("Введите площадь города(число должно быть больше 0). Пример: 900.8");
        Boolean checker3 = true;
        Double area = 0.0;
        while (checker3) {
            try {
                area = Double.parseDouble(scanner.nextLine());
                if (area > 0) {
                    checker3 = false;
                } else {
                    System.out.println("Значение не может быть отрицательным или равным 0, введите площадь города еще раз");
                }
            } catch (NumberFormatException e) {
                System.out.println("Формат введенного значения неверный, введите значение еще раз");
            }
        }
        args.add(area.toString());
        System.out.println("Введите население города(число должно быть больше 0). Пример: 10000000");
        Boolean checker4 = true;
        Integer population = 0;
        while (checker4) {
            try {
                population = Integer.parseInt(scanner.nextLine());
                if (population > 0) {
                    checker4 = false;
                } else {
                    System.out.println("Значение не может быть отрицательным или равным 0, введите население города еще раз");
                }
            } catch (NumberFormatException e) {
                System.out.println("Формат введенного значения неверный, введите значение еще раз");
            }
        }
        args.add(population.toString());
        System.out.println("Введите на скольких метрах над уровнем моря находится город. Пример: -180");
        Boolean checker5 = true;
        Integer metersAboveSeaLevel = 0;
        while (checker5) {
            try {
                metersAboveSeaLevel = Integer.parseInt(scanner.nextLine());
                checker5 = false;
            } catch (NumberFormatException e) {
                System.out.println("Формат введенного значения неверный, введите значение еще раз");
            }
        }
        args.add(metersAboveSeaLevel.toString());
        System.out.println("Введите true, если город является столицей, иначе введите false");
        Boolean checker6 = true;
        String capital = null;
        while (checker6) {
            try {
                capital = scanner.nextLine();
                if (capital.equals("true") || capital.equals("false")) {
                    checker6 = false;
                } else System.out.println("Введенное значение некорректно, введите значение еще раз");
            } catch (NumberFormatException e) {
                System.out.println("Формат введенного значения неверный, введите значение еще раз");
            }
        }
        args.add(capital);
        System.out.println("Введите климат города. Выберете из этого: \n" + "HUMIDCONTINENTAL\n" + "SUBARCTIC\n" + "TUNDRA");
        Boolean checker7 = true;
        Climate climate = null;
        while (checker7) {
            String clim = scanner.nextLine();
            if (clim.toUpperCase().equals("HUMIDCONTINENTAL")) {
                climate = Climate.HUMIDCONTINENTAL;
                checker7 = false;
            } else if (clim.toUpperCase().equals("SUBARCTIC")) {
                climate = Climate.SUBARCTIC;
                checker7 = false;
            } else if (clim.toUpperCase().equals("TUNDRA")) {
                climate = Climate.TUNDRA;
                checker7 = false;
            } else System.out.println("Введенное значение некорректно, введите климат города еще раз");
        }
        args.add(climate.toString());
        System.out.println("Введите правительство города. Выберите из этого: \n" + "CORPORATOCRACY\n" + "MERITOCRACY\n" + "OLIGARCHY");
        Boolean checker8 = true;
        Government government = null;
        while (checker8) {
            String gov = scanner.nextLine();
            if (gov.toUpperCase().equals("CORPORATOCRACY")) {
                government = Government.CORPORATOCRACY;
                checker8 = false;
            } else if (gov.toUpperCase().equals("MERITOCRACY")) {
                government = Government.MERITOCRACY;
                checker8 = false;
            } else if (gov.toUpperCase().equals("OLIGARCHY")) {
                government = Government.OLIGARCHY;
                checker8 = false;
            } else System.out.println("Введенное значение некорректно, введите правительство города еще раз");
        }
        args.add(government.toString());
        System.out.println("Введите фамилию губернатора города. Пример: Собянин");
        Boolean checker9 = true;
        String nameGovernor = null;
        while (checker9) {
            String line = scanner.nextLine();
            char[] symbols = line.toLowerCase().toCharArray();
            Boolean checkernamt1 = true;
            String validationName = "abcdefghijklmnopqrstuvwxyzабвгдеёжзийклмнопрстуфхцчшщъыьэюя";
            for (char c : symbols) {
                if (validationName.indexOf(c) == -1) {
                    checkernamt1 = false;
                }
            }
            if (checkernamt1) {
                checker9 = false;
                nameGovernor = line;
            } else System.out.println("Введенное имя записано некорректно. Введите имя еще раз");
        }
        args.add(nameGovernor);
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
