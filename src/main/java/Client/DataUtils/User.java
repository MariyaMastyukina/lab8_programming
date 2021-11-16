package Client.DataUtils;

import Client.GUI.AutorizationWindow;
import Client.GUI.LoginWindow;
import Client.GUI.MainWindow;
import Client.GUI.RegisterWindow;
import Client.IOClient.IOInterfaceStream;
import Server.ConnectionUtils.Request;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Locale;
import java.util.ResourceBundle;

public class User {
    static ResourceBundle res;
    private static String login;
    private static Request table;
    private static char[] password;
    public static boolean permission;
    public static MainWindow mainWindow;
    private static AutorizationWindow autorization = null;
    static PipedReader reader = new PipedReader();
    static BufferedReader br = new BufferedReader(reader);
    static PipedWriter writer;

    static {
        try {
            writer = new PipedWriter(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User(String login, char[] password) throws IOException {
        User.login = login;
        User.password = password;
    }

    public static User createUser(IOInterfaceStream ioServer, Component component, PipedWriter cmdWriter) throws IOException, ClassNotFoundException {
        LoginWindow loginWindow = null;
        RegisterWindow register = null;
        permission = false;
        if (autorization == null) {
            autorization = new AutorizationWindow(writer);
        }
        String login = "";
        char[] password = null;
        synchronized (User.class) {
            autorization.setVisible(true);
            while (!br.ready()) {

            }
            autorization.setVisible(false);
            String action = br.readLine();
            while (!permission) {
                if (action.equals("check_in")) {
                    register = new RegisterWindow(writer);
                    register.setVisible(true);
                    while (!br.ready()) {
                    }
                    login = br.readLine();
                    password = br.readLine().toCharArray();
                    if (login.isEmpty()) throw new IOException("Имя пользователя не может быть пустым");
                    CommandObject commandObject = new CommandObject("check_in");
                    commandObject.setLogin(login);
                    commandObject.setPassword(password);
                    ioServer.writeObj(commandObject);
                } else if (action.equals("sign_in")) {
                    loginWindow = new LoginWindow(writer);
                    loginWindow.setVisible(true);
                    login = br.readLine();
                    password = br.readLine().toCharArray();
                    CommandObject commandObject = new CommandObject("sign_in");
                    commandObject.setLogin(login);
                    commandObject.setPassword(password);
                    ioServer.writeObj(commandObject);
                }
                while (!ioServer.ready()) {

                }
                StringBuilder sb = new StringBuilder();
                while (ioServer.ready()) {
                    Request request = (Request) ioServer.readObj();
                    sb.append(request.getAnswer());
                }
                if (sb.toString().equals("Вход произошел успешно")) {
                    permission = true;
                    loginWindow.setVisible(false);
                    Locale[] locales = {new Locale("ru"), new Locale("bg"), new Locale("fi"), new Locale("es", "MX")};
                    CommandObject currentCommand = new CommandObject("getTable");
                    ioServer.writeObj(currentCommand);
                    while (!ioServer.ready()) {
                    }
                    table = (Request) ioServer.readObj();
                    res = ResourceBundle.getBundle("Client.Resources.ProgramResources", locales[0]);
                    mainWindow = new MainWindow(locales, cmdWriter, table, res);
                    mainWindow.setUserInfo(login);
                    mainWindow.setVisible(true);
                } else if (sb.toString().equals("Неверное имя пользователя или пароль")) {
                    JOptionPane.showMessageDialog(component, sb.toString(), "ОШИБКА", JOptionPane.ERROR_MESSAGE);
                    loginWindow.setVisible(false);
                } else if (sb.toString().equals("Пользователь " + login + " зарегистрирован")) {
                    action = "sign_in";
                    register.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(component, sb.toString(), "ОШИБКА", JOptionPane.ERROR_MESSAGE);
                    register.setVisible(false);
                }
            }
            return new User(login, password);
        }
    }

    public static char[] getPassword() {
        return password;
    }

    public static String getLogin() {
        return login;
    }

    public static Request getTable() {
        return table;
    }

    public MainWindow getMain() {
        return mainWindow;
    }
}
