package Utils.DataUtils;

import java.io.Serializable;
import java.util.List;

public class CommandUtils implements Serializable {
    private String login;
    private char[] password;
    private String nameCommand;
    private String option;
    private Boolean checker = false;
    private List<String> args;

    public CommandUtils(String line) {
        if (!line.trim().equals("")) {
            if (line.contains(" ")) {
                String[] lineParts = line.split(" ", 2);
                checker = ValidationUtils.isValidCommand(lineParts[0], lineParts[1]);
                if (checker) {
                    nameCommand = lineParts[0];
                    option = lineParts[1];
                }
            } else {
                checker = ValidationUtils.isValidCommand(line, null);
                if (checker) {
                    nameCommand = line;
                    option = null;
                }
            }
        }
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public String getNameCommand() {
        return nameCommand;
    }

    public List<String> getArgs() {
        return args;
    }

    public String getOption() {
        return option;
    }

    public Boolean getChecker() {
        return checker;
    }

    public char[] getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
