package Client;

import Server.Collection.Climate;
import Server.Collection.Government;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс для отправки на сервер объекта и получения ответа
 */
public class TransferObject {
    static Logger LOGGER;
    String answer = null;

    private IOInterfaceStream ioServer;
    private IOInterfaceStream ioClient;
    private ServerConnection serverConnection;
    public TransferObject(IOInterfaceStream ioServer, IOInterfaceStream ioClient, ServerConnection serverConnection){
        this.ioServer=ioServer;
        this.ioClient=ioClient;
        this.serverConnection=serverConnection;
        LOGGER=Logger.getLogger(TransferObject.class.getName());
    }
    /**
     * Функция для отправки на сервер объекта и получения ответа
     * @param command-команда
     */
    public String transfer(CommandObject command) throws IOException, ClassNotFoundException {
        LOGGER.log(Level.INFO,"Проверяем команду на execute_script");
        if (command.getNameCommand().equals("execute_script")){
            File file = new File(command.getOption());
            if(file.exists()) {
                if (file.canRead()) {
                    FileReader fileReader = new FileReader(file);
                    Scanner scanner = new Scanner(fileReader);
                    while (scanner.hasNextLine()) {
                        String fileLine = scanner.nextLine();
                        CommandObject commandObject = new CommandObject(fileLine, command.getOption());
                        if (commandObject.getNameCommand().equals("add") || commandObject.getNameCommand().equals("update")) {
                            List<String> args = new ArrayList<>();
                            String name = scanner.nextLine();
                            args.add(name);
                            Boolean checker1 = true;
                            Float x = 0F;
                            while (checker1) {
                                try {
                                    x = Float.parseFloat(scanner.nextLine());
                                    if (x > -375F) {
                                        checker1 = false;
                                    } else {
                                        ioClient.writeln("В файле указано неверное значение. Значение не может быть меньше -375, координата города Х по умолчанию устанавливается 0");
                                        x = 0F;
                                        checker1 = false;
                                    }
                                } catch (NumberFormatException e) {
                                    ioClient.writeln("В файле указан неверный формат.Координата города Х по умолчанию устанавливается 0");
                                    x = 0F;
                                    checker1 = false;
                                }
                            }
                            args.add(x.toString());
                            Boolean checker2 = true;
                            Integer y = 0;
                            while (checker2) {
                                try {
                                    y = Integer.parseInt(scanner.nextLine());
                                    if (y > -966) {
                                        checker2 = false;
                                    } else {
                                        ioClient.writeln("В файле указан неверное значение. Значение не может быть меньше -966, координата города Y по умолчанию устанавливается 0");
                                        y = 0;
                                        checker2 = false;
                                    }
                                } catch (NumberFormatException e) {
                                    ioClient.writeln("В файле указан неверный формат.Координата города Y по умолчанию устанавливается 0");
                                    y = 0;
                                    checker2 = false;
                                }
                            }
                            args.add(y.toString());
                            Boolean checker3 = true;
                            Double area = 0.0;
                            while (checker3) {
                                try {
                                    area = Double.parseDouble(scanner.nextLine());
                                    if (area > 0) {
                                        checker3 = false;
                                    } else {
                                        ioClient.writeln("В файле указано неверное значение. Значение не может быть отрицательным или равным 0, площадь города по умолчанию устанавливается 1.0");
                                        area = 1.0;
                                        checker3 = false;
                                    }
                                } catch (NumberFormatException e) {
                                    ioClient.writeln("В файле указан неверный формат.Площадь города по умолчанию устанавливается 1.0");
                                    area = 1.0;
                                    checker3 = false;
                                }
                            }
                            args.add(area.toString());
                            Boolean checker4 = true;
                            Integer population = 0;
                            while (checker4) {
                                try {
                                    population = Integer.parseInt(scanner.nextLine());
                                    if (population > 0) {
                                        checker4 = false;
                                    } else {
                                        ioClient.writeln("В файле указано неверное значение. Значение не может быть отрицательным или равным 0, население города по умолчанию устанавливается 1");
                                        population = 1;
                                        checker4 = false;
                                    }
                                } catch (NumberFormatException e) {
                                    ioClient.writeln("В файле указан неверный формат.Население города по умолчанию устанавливается 1");
                                    population = 1;
                                    checker4 = false;
                                }
                            }
                            args.add(population.toString());
                            Boolean checker5 = true;
                            Integer metersAboveSeaLevel = 0;
                            while (checker5) {
                                try {
                                    metersAboveSeaLevel = Integer.parseInt(scanner.nextLine());
                                    checker5 = false;
                                } catch (NumberFormatException e) {
                                    ioClient.writeln("В файле указан неверный формат.Уровень города над морем по умолчанию устанавливается 0");
                                    metersAboveSeaLevel = 0;
                                    checker5 = false;
                                }
                            }
                            args.add(metersAboveSeaLevel.toString());
                            Boolean checker6 = true;
                            Boolean capital = null;
                            while (checker6) {
                                String str = scanner.nextLine();
                                if (str.equals("true") || str.equals("false")) {
                                    capital = Boolean.parseBoolean(str);
                                    checker6 = false;
                                } else {
                                    ioClient.writeln("В файле указано неверное значение. По умолчанию город не является столицей");
                                    capital = false;
                                    checker6 = false;
                                }
                            }
                            args.add(capital.toString());
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
                                } else {
                                    ioClient.writeln("В файле указано неверное значение. По умолчанию климат города HUMIDCONTINENTAL");
                                    climate = Climate.HUMIDCONTINENTAL;
                                    checker7 = false;
                                }
                            }
                            args.add(climate.toString());
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
                                } else {
                                    ioClient.writeln("В файле указано неверное значение. По умолчанию правительство города CORPORATOCRACY");
                                    government = Government.CORPORATOCRACY;
                                    checker8 = false;
                                }
                            }
                            args.add(government.toString());
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
                                } else {
                                    ioClient.writeln("В файле указано неверное имя губернатора. Имя губернатора не вводится");
                                    nameGovernor = "";
                                    checker9 = false;
                                }
                                args.add(nameGovernor);
                            }
                            commandObject.setArgs(args);
                        }
                        if (commandObject.getChecker()) {
                            transfer(commandObject);
                        }
                    }
                } else {
                    ioClient.writeln("Чтение из файлa " + command.getOption() + " невозможно, выполнение команды execute_script невозможно. Измените права.");
                    return answer ;
                }
            }
            else{
                ioClient.writeln("Файл не существует, выполнение команды execute_script невозможно");
                return answer;
            }
        }
        else{
            if (command.getChecker()) {
                LOGGER.log(Level.INFO, "Отправляем команду на сервер");
                ioServer.writeObj(command);

        long startTime=System.currentTimeMillis();
        LOGGER.log(Level.INFO,"Ждем готовности сервера");
        while (!ioServer.ready()){
            long endTime=System.currentTimeMillis();
            if (endTime-startTime>20000){
                ioClient.writeln("Сервер недоступен, завершение работы программы");
                serverConnection.close();
                System.exit(0);
            }
        }
        LOGGER.log(Level.INFO,"Получаем ответ сервера на отправленную команду");
        LOGGER.log(Level.INFO, "Выводим ответ сервера на консоль");
        while (ioServer.ready()) {
                    answer = ioServer.readLine();
                    if (answer.equals("exit")) {
                        ioServer.close();
                        serverConnection.close();
                        LOGGER.log(Level.INFO, "Завершение работы приложения");
                        System.exit(1);
                    }
                    ioClient.writeln(answer);
                }

            }
        }
        return answer;
    }
}
