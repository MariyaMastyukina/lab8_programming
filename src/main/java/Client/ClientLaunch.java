package Client;

import Client.GUI.ConnectWindow;
import IO.IOInterfaceStream;
import IO.IOTerminal;
import Server.Launch.CommandLaunch;
import Server.Launch.ControlUnit;
import Utils.ConnectionUtils.ServerConnectionUtils;
import Utils.DataUtils.CommandUtils;
import Utils.DataUtils.TransferUtils;
import Utils.UserUtils;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientLaunch {
    private static PipedWriter cmdWriter;
    private static PipedReader cmdReader;
    private static BufferedReader bufferedReaderCmd;
    private static PipedReader resultReader;
    private static PipedWriter resultWriter;
    private static BufferedReader bufferedReader;
    private static Socket socket;
    private static ServerConnectionUtils serverConnectionUtils;
    private static UserUtils userUtils;
    private static TransferUtils transferUtils;
    private static IOInterfaceStream ioServer;
    private static ConnectWindow connectWindow;

    private static void init() throws IOException, ClassNotFoundException {
        new CommandLaunch(null, null, new ControlUnit());
        cmdWriter = new PipedWriter();
        cmdReader = new PipedReader(cmdWriter, 1024 * 1024);
        bufferedReaderCmd = new BufferedReader(cmdReader);
        resultReader = new PipedReader();
        resultWriter = new PipedWriter(resultReader);
        bufferedReader = new BufferedReader(resultReader);
        connectWindow = new ConnectWindow(resultWriter);
        try {
            connectWindow.setVisible(true);
        } catch (HeadlessException e) {
            System.out.println("Не нашел дисплей(");
            System.exit(0);
        }
        serverConnectionUtils = new ServerConnectionUtils();
        socket = serverConnectionUtils.connection(connectWindow, bufferedReader);
        ioServer = new IOTerminal(serverConnectionUtils.getOutputStream(), serverConnectionUtils.getInputStream());
        connectWindow.setVisible(false);
        userUtils = UserUtils.createUser(ioServer, connectWindow, cmdWriter);
        transferUtils = new TransferUtils(ioServer, serverConnectionUtils, UserUtils.getMainWindow());
    }

    public static void startClient() throws IOException, ClassNotFoundException {
        init();
        while (true) {
            while (!bufferedReaderCmd.ready()) {
            }
            CommandUtils commandUtils;
            ArrayList<String> argsAdd = null;
            String line = bufferedReaderCmd.readLine();
            if (line.equals("exit ")) {
                UserUtils.getMainWindow().setVisible(false);
                userUtils = UserUtils.createUser(ioServer, connectWindow, cmdWriter);
                transferUtils = new TransferUtils(ioServer, serverConnectionUtils, UserUtils.getMainWindow());
                continue;
            }
            if (line.equals("add") || line.contains("update")) {
                argsAdd = new ArrayList<>();
                while (!bufferedReaderCmd.ready()) {
                }
                while (argsAdd.size() != 10) {
                    argsAdd.add(bufferedReaderCmd.readLine());
                }
            }
            commandUtils = new CommandUtils(line);
            if (argsAdd != null) {
                commandUtils.setArgs(argsAdd);
            }
            commandUtils.setLogin(userUtils.getLogin());
            commandUtils.setPassword(userUtils.getPassword());
            if (commandUtils.getChecker()) {
                try {
                    transferUtils.transfer(commandUtils);
                } catch (StackOverflowError e) {
                    JOptionPane.showMessageDialog(UserUtils.getMainWindow(), "Команда execute_script зациклилась:(", "ОШИБКА", JOptionPane.ERROR_MESSAGE);

                }
            }
        }
    }
}
