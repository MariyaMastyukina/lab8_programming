package Server.Commands;

import Client.DataUtils.CommandObject;
import Server.Launch.ControlUnit;
import Server.ConnectionUtils.Request;
import Server.DBUtils.UserDB;

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
