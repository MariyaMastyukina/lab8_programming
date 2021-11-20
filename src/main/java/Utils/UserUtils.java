package Utils;

import Client.GUI.AuthorizationWindow;
import Client.GUI.LoginWindow;
import Client.GUI.MainWindow;
import Client.GUI.RegisterWindow;
import IO.IOInterfaceStream;
import Utils.ConnectionUtils.Request;
import Utils.DataUtils.CommandUtils;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserUtils {
    private static ResourceBundle res;
    private String login;
    private static Request table;
    private char[] password;
    private static boolean permission;
    private static MainWindow mainWindow;
    private static AuthorizationWindow authorization = null;
    private static PipedReader reader = new PipedReader();
    private static BufferedReader br = new BufferedReader(reader);
    private static PipedWriter writer;
    private static LoginWindow loginWindow;
    private static RegisterWindow registerWindow;

    static {
        try {
            writer = new PipedWriter(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MainWindow getMainWindow() {
        return mainWindow;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public static UserUtils createUser(IOInterfaceStream ioServer, Component component, PipedWriter cmdWriter) throws IOException, ClassNotFoundException {
        UserUtils userUtils = new UserUtils();
        Request request = null;
        permission = false;
        if (authorization == null) {
            authorization = new AuthorizationWindow(writer);
        }
        synchronized (UserUtils.class) {
            authorization.setVisible(true);
            while (!br.ready()) {

            }
            authorization.setVisible(false);
            String action = br.readLine();
            while (!permission) {
                if (action.equals("check_in")) {
                    checkIn(ioServer, userUtils);
                } else if (action.equals("sign_in")) {
                    signIn(ioServer, userUtils);
                }
                while (!ioServer.ready()) {

                }
                while (ioServer.ready()) {
                    request = (Request) ioServer.readObj();
                }
                if (request.getStatus().equals("Success")) {
                    permission = true;
                    loginWindow.setVisible(false);
                    registerWindow.setVisible(false);
                    Locale[] locales = {new Locale("ru"), new Locale("bg"), new Locale("fi"), new Locale("es", "MX")};
                    CommandUtils currentCommand = new CommandUtils("getTable");
                    ioServer.writeObj(currentCommand);
                    while (!ioServer.ready()) {
                    }
                    table = (Request) ioServer.readObj();
                    res = ResourceBundle.getBundle("Client.Resources.ProgramResources", locales[0]);
                    mainWindow = new MainWindow(locales, cmdWriter, table, res, userUtils);
                    mainWindow.setUserInfo(userUtils.getLogin());
                    mainWindow.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(component, request.getAnswer(), "ОШИБКА", JOptionPane.ERROR_MESSAGE);
                }
            }
            return userUtils;
        }
    }

    private static void signIn(IOInterfaceStream ioServer, UserUtils userUtils) throws IOException {
        loginWindow = new LoginWindow(writer);
        registerWindow = new RegisterWindow(writer);
        loginWindow.setVisible(true);
        String login = br.readLine();
        char[] password = br.readLine().toCharArray();
        while (login.isEmpty()) {
            JOptionPane.showMessageDialog(loginWindow, "Имя пользователя не может быть пустым", "ОШИБКА", JOptionPane.ERROR_MESSAGE);
            while (!br.ready()) {
            }
            login = br.readLine();
        }
        CommandUtils commandUtils = new CommandUtils("sign_in");
        commandUtils.setLogin(login);
        commandUtils.setPassword(password);
        ioServer.writeObj(commandUtils);
        userUtils.setLogin(login);
        userUtils.setPassword(password);
    }


    private static void checkIn(IOInterfaceStream ioServer, UserUtils userUtils) throws IOException {
        registerWindow = new RegisterWindow(writer);
        loginWindow = new LoginWindow(writer);
        registerWindow.setVisible(true);
        while (!br.ready()) {
        }
        String login = br.readLine();
        char[] password = br.readLine().toCharArray();
        while (login.isEmpty()) {
            JOptionPane.showMessageDialog(registerWindow, "Имя пользователя не может быть пустым", "ОШИБКА", JOptionPane.ERROR_MESSAGE);
            while (!br.ready()) {
            }
            login = br.readLine();
        }
        CommandUtils commandUtils = new CommandUtils("check_in");
        commandUtils.setLogin(login);
        commandUtils.setPassword(password);
        ioServer.writeObj(commandUtils);
        userUtils.setLogin(login);
        userUtils.setPassword(password);
    }

    public char[] getPassword() {
        return password;
    }

    public static void setPermission(boolean permission) {
        UserUtils.permission = permission;
    }

    public String getLogin() {
        return login;
    }
}
