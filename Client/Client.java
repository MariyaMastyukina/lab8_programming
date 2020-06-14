package Client;

import Client.GUI.ConnectWindow;
import Client.GUI.MainWindow;
import Server.Collection.CollectWorker;
import Server.Collection.ControlUnit;
import Server.Launch;
import Server.Request;
import com.sun.jdi.connect.Connector;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Мастюкина Мария
 * Класс, работающий в качестве клиента
 */
public class Client {
    static Logger LOGGER;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Launch(null,null,new ControlUnit());
        PipedWriter cmdWriter = new PipedWriter();
        PipedReader cmdReader = new PipedReader(cmdWriter);
        BufferedReader brcmd=new BufferedReader(cmdReader);
        PipedReader resultReader = new PipedReader();
        PipedWriter resultWriter = new PipedWriter(resultReader);
        BufferedReader br=new BufferedReader(resultReader);
        ConnectWindow app = new ConnectWindow(resultWriter);
        app.setVisible(true);
        while(!br.ready()){}
        ServerConnection serverConnection=new ServerConnection();
        Socket socket=serverConnection.connection(br.readLine(),br.readLine(),app);
        while(socket==null){
            socket=serverConnection.connection(br.readLine(),br.readLine(),app);
        }
        IOInterfaceStream ioServer = new IOTerminal(serverConnection.getOutputStream(),serverConnection.getInputStream());
        app.setVisible(false);
        User user=User.createUser(ioServer,app,cmdWriter);
        TransferObject transferObject = new TransferObject(ioServer,serverConnection,user.getMain());
        while(true) {
            while (!brcmd.ready()){}
            CommandObject command;
            ArrayList<String> argsAdd = null;
            String line = brcmd.readLine();
                    if (line.equals("exit ")){
                        user.getMain().setVisible(false);
                        user=User.createUser(ioServer,app,cmdWriter);
                        transferObject=new TransferObject(ioServer,serverConnection,user.getMain());
                        continue;
                    }
                    if (line.equals("add") || line.contains("update")){
                        argsAdd = new ArrayList<>();
                        while (brcmd.ready()){
                            argsAdd.add(brcmd.readLine());
                        }
                    }
            System.out.println(argsAdd);
                    command = new CommandObject(line, null);
            if (argsAdd != null) {
                command.setArgs(argsAdd);
            }
                    command.setLogin(User.getLogin());
                    command.setPassword(User.getPassword());
                    if (command.getChecker()) {
                        try {
                            transferObject.transfer(command);
                        } catch (StackOverflowError e) {
                            JOptionPane.showMessageDialog(user.getMain(),"Команда execute_script зациклилась:(","ОШИБКА", JOptionPane.ERROR_MESSAGE);

                        }
                    }
        }
    }
    }

//
//
//
//        IOInterfaceStream ioServer = new IOTerminal(serverConnection.getInputStream(), serverConnection.getOutputStream());
//        LOGGER.log(Level.INFO,"Ждем готовности сервера");
//        LOGGER.log(Level.INFO,"Получаем спсиок команд с сервера");
//        TransferObject transferObject = new TransferObject(ioServer, ioClient, serverConnection);
//        User user= User.createUser(ioServer);
//        ioClient.writeln("Здравстуйте! Введите \"help\", чтобы увидеть список доступных команд");
//        ioClient.writeln("Введите команду");
//        LOGGER.log(Level.INFO,"Считываем команду с консоли");
//
//}