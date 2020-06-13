package Server.Commands;

import Client.CommandObject;
import Server.Collection.ControlUnit;
import Server.Request;
import Server.UserDB;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class SignInCommand implements Command {
    static int argsSize;
    public SignInCommand(ControlUnit pusk){
        argsSize=0;
        pusk.addCommand("sign_in",this);
    }
    @Override
    public Request execute(CommandObject user) throws IOException, SQLException, NoSuchAlgorithmException {
        return new Request(UserDB.sign_in(user.getLogin(),user.getPassword()),null,null);
    }
    @Override
    public String getName() {
        return "sign_in";
    }

    @Override
    public int getargsSize() {
        return argsSize;
    }
}
