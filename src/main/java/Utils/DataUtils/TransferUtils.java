package Utils.DataUtils;

import Client.GUI.MainWindow;
import IO.IOInterfaceStream;
import Utils.ConnectionUtils.Request;
import Utils.ConnectionUtils.ServerConnectionUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TransferUtils {
    private String answer = null;
    private MainWindow mainWindow;
    private IOInterfaceStream ioServer;
    private ServerConnectionUtils serverConnectionUtils;

    public TransferUtils(IOInterfaceStream ioServer, ServerConnectionUtils serverConnectionUtils, MainWindow mainWindow) throws IOException {
        this.ioServer = ioServer;
        this.serverConnectionUtils = serverConnectionUtils;
        this.mainWindow = mainWindow;
    }

    public void transfer(CommandUtils command) throws IOException, ClassNotFoundException {
        if (command.getNameCommand().equals("execute_script")) {
            File file = new File(command.getOption());
            if (file.exists()) {
                if (file.canRead()) {
                    FileReader fileReader = new FileReader(file);
                    Scanner scanner = new Scanner(fileReader);
                    while (scanner.hasNextLine()) {
                        String fileLine = scanner.nextLine();
                        CommandUtils commandUtils = new CommandUtils(fileLine);
                        command.setLogin(command.getLogin());
                        if (commandUtils.getNameCommand().equals("add") || commandUtils.getNameCommand().equals("update")) {
                            commandUtils.setArgs(ValidationUtils.readFileArgs(scanner, mainWindow));
                        }
                        if (commandUtils.getChecker()) {
                            transfer(commandUtils);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(mainWindow, "Чтение из файлa " + command.getOption() + " невозможно, выполнение команды execute_script невозможно. Измените права.", "ОШИБКА", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(mainWindow, "Файл не существует, выполнение команды execute_script невозможно", "ОШИБКА", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            if (command.getChecker()) {
                ioServer.writeObj(command);

                long startTime = System.currentTimeMillis();
                while (!ioServer.ready()) {
                    long endTime = System.currentTimeMillis();
                    if (endTime - startTime > 5000) {
                        JOptionPane.showMessageDialog(mainWindow, "Сервер недоступен, завершение работы программы", "ОШИБКА", JOptionPane.ERROR_MESSAGE);
                        serverConnectionUtils.close();
                        System.exit(0);
                    }
                }
                while (ioServer.ready()) {
                    Request request = (Request) ioServer.readObj();
                    if (request.getStatus().equals("ERROR")) {
                        JOptionPane.showMessageDialog(mainWindow, request.getAnswer(), "ОШИБКА", JOptionPane.ERROR_MESSAGE);
                    } else {
                        mainWindow.addAnswer(request.getAnswer());
                    }
                    if (request.getNewTable() != null) {
                        mainWindow.getCityTableModel().updateTable(request.getNewTable());
                        mainWindow.getVisualPanel().updateVisual(request.getNewTable());
                    }
                }

            }
        }
    }
}
