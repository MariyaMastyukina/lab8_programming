package Client.DataUtils;

import Client.GUI.MainWindow;
import Client.IOClient.IOInterfaceStream;
import Server.Collection.Climate;
import Server.Collection.Government;
import Server.ConnectionUtils.Request;
import Client.ConnectionUtils.*;
import javax.swing.*;
import java.awt.*;
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
    private IOInterfaceStream ioServer;
    private ServerConnection serverConnection;
    private Component component;
    private MainWindow mainWindow;
    public TransferObject(IOInterfaceStream ioServer, ServerConnection serverConnection, Component component){
        this.component=component;
        this.ioServer=ioServer;
        this.serverConnection=serverConnection;
        LOGGER=Logger.getLogger(TransferObject.class.getName());
        mainWindow=(MainWindow) component;
    }
    /**
     * Функция для отправки на сервер объекта и получения ответа
     * @param command-команда
     */
    public void transfer(CommandObject command) throws IOException, ClassNotFoundException {
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
                                        MainWindow.addAnswer("В файле указано неверное значение. Значение не может быть меньше -375, координата города Х по умолчанию устанавливается 0");
                                        x = 0F;
                                        checker1 = false;
                                    }
                                } catch (NumberFormatException e) {
                                    MainWindow.addAnswer("В файле указан неверный формат.Координата города Х по умолчанию устанавливается 0");
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
                                        MainWindow.addAnswer("В файле указан неверное значение. Значение не может быть меньше -966, координата города Y по умолчанию устанавливается 0");
                                        y = 0;
                                        checker2 = false;
                                    }
                                } catch (NumberFormatException e) {
                                    MainWindow.addAnswer("В файле указан неверный формат.Координата города Y по умолчанию устанавливается 0");
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
                                        MainWindow.addAnswer("В файле указано неверное значение. Значение не может быть отрицательным или равным 0, площадь города по умолчанию устанавливается 1.0");
                                        area = 1.0;
                                        checker3 = false;
                                    }
                                } catch (NumberFormatException e) {
                                    MainWindow.addAnswer("В файле указан неверный формат.Площадь города по умолчанию устанавливается 1.0");
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
                                        MainWindow.addAnswer("В файле указано неверное значение. Значение не может быть отрицательным или равным 0, население города по умолчанию устанавливается 1");
                                        population = 1;
                                        checker4 = false;
                                    }
                                } catch (NumberFormatException e) {
                                    MainWindow.addAnswer("В файле указан неверный формат.Население города по умолчанию устанавливается 1");
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
                                    MainWindow.addAnswer("В файле указан неверный формат.Уровень города над морем по умолчанию устанавливается 0");
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
                                    MainWindow.addAnswer("В файле указано неверное значение. По умолчанию город не является столицей");
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
                                    MainWindow.addAnswer("В файле указано неверное значение. По умолчанию климат города HUMIDCONTINENTAL");
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
                                    MainWindow.addAnswer("В файле указано неверное значение. По умолчанию правительство города CORPORATOCRACY");
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
                                    MainWindow.addAnswer("В файле указано неверное имя губернатора. Имя губернатора не вводится");
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
                    JOptionPane.showMessageDialog(component,"Чтение из файлa " + command.getOption() + " невозможно, выполнение команды execute_script невозможно. Измените права.","ОШИБКА", JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(component,"Файл не существует, выполнение команды execute_script невозможно","ОШИБКА", JOptionPane.ERROR_MESSAGE);
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
            if (endTime-startTime>5000){
                JOptionPane.showMessageDialog(component,"Сервер недоступен, завершение работы программы","ОШИБКА", JOptionPane.ERROR_MESSAGE);
                serverConnection.close();
                System.exit(0);
            }
        }
        LOGGER.log(Level.INFO,"Получаем ответ сервера на отправленную команду");
        while (ioServer.ready()) {
            LOGGER.log(Level.INFO, "Выводим ответ сервера на консоль");
            Request request=(Request) ioServer.readObj();
            System.out.println(request.getAnswer());
                    MainWindow.addAnswer(request.getAnswer());
            System.out.println(mainWindow);
                    if (request.getNew_map()!=null){
                        mainWindow.getTableModel().updateTable(request.getNew_map());
                        mainWindow.getVisualPanel().updateVisual(request.getNew_map());
                    }
                }

            }
        }
    }
}
