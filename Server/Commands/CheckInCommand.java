package Server.Commands;

import Client.CommandObject;
import Server.Collection.ControlUnit;
import Server.Request;
import Server.UserDB;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class CheckInCommand implements Command{
    static int argsSize;
    public CheckInCommand(ControlUnit pusk){
        argsSize=0;
        pusk.addCommand("check_in",this);
    }
    @Override
    public Request execute(CommandObject CO) throws IOException, SQLException, NoSuchAlgorithmException {
        return new Request(UserDB.check_in(CO.getLogin(),CO.getPassword()),null,null);
    }

    @Override
    public String getName() {
        return "check_in";
    }

    @Override
    public int getargsSize() {
        return argsSize;
    }
}
