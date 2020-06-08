package Client;

import Client.GUI.AutorizationWindow;
import Client.GUI.LoginWindow;
import Client.GUI.RegisterWindow;
import Server.Request;

import javax.swing.*;
import java.io.*;

public class User {
    private String login;
    private char[] password;
    private static AutorizationWindow autorization=null;
    public User(String login, char[] password){
        this.login=login;
        this.password=password;
    }
    public static User createUser(IOInterfaceStream ioServer) throws IOException, ClassNotFoundException {
        PipedReader reader=new PipedReader();
        BufferedReader br=new BufferedReader(reader);
        PipedWriter writer=new PipedWriter(reader);
        LoginWindow loginWindow = null;
        RegisterWindow register = null;
        boolean permission=false;
        if (autorization==null){
            autorization=new AutorizationWindow(writer);
        }
        String login="";
        char[]password=null;
        synchronized (User.class) {
                autorization.setVisible(true);
                while(!br.ready()){

                }
            autorization.setVisible(false);
            String action=br.readLine();
            System.out.println(action);
            while (!permission) {
                if (action.equals("check_in")) {
                     register = new RegisterWindow(writer);
                    register.setVisible(true);
                    while (!br.ready()) {
                    }
                    login = br.readLine();
                    password = br.readLine().toCharArray();
                    if (login.isEmpty()) throw new IOException("Имя пользователя не может быть пустым");
                    CommandObject commandObject = new CommandObject("check_in", null);
                    commandObject.setLogin(login);
                    commandObject.setPassword(password);
                    ioServer.writeObj(commandObject);
                }
                    else if (action.equals("sign_in")) {
                         loginWindow=new LoginWindow(writer);
                        loginWindow.setVisible(true);
                        login=br.readLine();
                        password=br.readLine().toCharArray();
                    CommandObject commandObject = new CommandObject("sign_in", null);
                    commandObject.setLogin(login);
                    commandObject.setPassword(password);
                    ioServer.writeObj(commandObject);
                }
                while (!ioServer.ready()) {

                }
                StringBuilder sb = new StringBuilder();
                while (ioServer.ready()) {
                    Request request=(Request)ioServer.readObj();
                    sb.append(request.getAnswer());
                }
                System.out.println(sb);
                if (sb.toString().equals("Вход произошел успешно")) {
                    permission = true;
                    loginWindow.setVisible(false);
                }else if (sb.toString().equals("Пользователь "+login+" зарегистрирован")){
                    action="sign_in";
                    register.setVisible(false);
                }
            }
            return new User(login,password);
        }
    }

    public char[] getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}
