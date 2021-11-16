package Client;

import Client.ConnectionUtils.ServerConnection;
import Client.DataUtils.CommandObject;
import Client.DataUtils.TransferObject;
import Client.DataUtils.User;
import Client.GUI.ConnectWindow;
import Client.IOClient.IOInterfaceStream;
import Client.IOClient.IOTerminal;
import Server.Launch.ControlUnit;
import Server.Launch.Launch;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author Мастюкина Мария
 * Класс, работающий в качестве клиента
 */
public class Client {
    static Socket socket;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Launch(null, null, new ControlUnit());
        PipedWriter cmdWriter = new PipedWriter();
        PipedReader cmdReader = new PipedReader(cmdWriter, 1024 * 1024);
        BufferedReader brcmd = new BufferedReader(cmdReader);
        PipedReader resultReader = new PipedReader();
        PipedWriter resultWriter = new PipedWriter(resultReader);
        BufferedReader br = new BufferedReader(resultReader);
        ConnectWindow app = new ConnectWindow(resultWriter);
        try {
            app.setVisible(true);
        } catch (HeadlessException e) {
            System.out.println("Не нашел дисплей(");
            System.exit(0);
        }
        while (!br.ready()) {
        }
        ServerConnection serverConnection = new ServerConnection();
        boolean checker = false;
        while (!checker) {
            try {
                socket = serverConnection.connection(br.readLine(), br.readLine(), app);
                checker = true;
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(app, "Надо бы ввести данные для подключение, а вы это не сделали", "ОШИБКА", JOptionPane.ERROR_MESSAGE);

            }
        }
        while (socket == null) {
            socket = serverConnection.connection(br.readLine(), br.readLine(), app);
        }
        IOInterfaceStream ioServer = new IOTerminal(serverConnection.getOutputStream(), serverConnection.getInputStream());
        app.setVisible(false);
        User user = User.createUser(ioServer, app, cmdWriter);
        TransferObject transferObject = new TransferObject(ioServer, serverConnection, user.getMain());
        while (true) {
            while (!brcmd.ready()) {
            }
            CommandObject command;
            ArrayList<String> argsAdd = null;
            String line = brcmd.readLine();
            if (line.equals("exit ")) {
                user.getMain().setVisible(false);
                user = User.createUser(ioServer, app, cmdWriter);
                transferObject = new TransferObject(ioServer, serverConnection, user.getMain());
                continue;
            }
            if (line.equals("add") || line.contains("update")) {
                argsAdd = new ArrayList<>();
                while (!brcmd.ready()) {
                }
                while (argsAdd.size() != 10) {
                    argsAdd.add(brcmd.readLine());
                }
            }
            command = new CommandObject(line);
            if (argsAdd != null) {
                command.setArgs(argsAdd);
            }
            command.setLogin(User.getLogin());
            command.setPassword(User.getPassword());
            if (command.getChecker()) {
                try {
                    transferObject.transfer(command);
                } catch (StackOverflowError e) {
                    JOptionPane.showMessageDialog(user.getMain(), "Команда execute_script зациклилась:(", "ОШИБКА", JOptionPane.ERROR_MESSAGE);

                }
            }
        }
    }
}