package Server;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;

public class UserDB {
    private static Connection connection;
    private static String userTable;
    public UserDB(Connection connection,String userTable){
        this.connection=connection;
        this.userTable=userTable;
    }
    public void closeConnection() throws SQLException {
        connection.close();
    }

    public static String check_in(String login, char[] password) throws SQLException, NoSuchAlgorithmException {
        PreparedStatement ps=connection.prepareStatement("SELECT login FROM "+userTable);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            if(login.equals(rs.getString("login"))) return "Пользователен с таким логином уже зарегистрирован";
        }
        ps=connection.prepareStatement("INSERT INTO "+userTable+" VALUES (?,?,?) ");
        String salt=getSalt();
        String hashPassword=hash(new String(password),salt);
        ps.setString(1,login);
        ps.setString(2,hashPassword);
        ps.setString(3,salt);
        ps.execute();
        ps.close();
        return "Пользователь "+login+" зарегистрирован";
    }
    public static String getSalt(){
        SecureRandom random = new SecureRandom();
        byte[]salt = new byte[16];
        random.nextBytes(salt);
        return new String(salt, StandardCharsets.UTF_8);
    }
    public static String hash(String password, String salt) throws NoSuchAlgorithmException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            byte[] hashbytes = md.digest((password + salt).getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashbytes);
        }
        catch(NoSuchAlgorithmException e){
            return password;
        }
    }
    public static String sign_in(String login, char[] password) throws SQLException, NoSuchAlgorithmException {
        PreparedStatement pr=connection.prepareStatement("SELECT * FROM "+userTable+" WHERE login = ?");
        pr.setString(1,login);
        ResultSet rs=pr.executeQuery();
        if(rs.next()&&hash(new String(password),rs.getString("salt")).equals(rs.getString("password"))){
            return "Вход произошел успешно";
        }
        else{
            return "Неверное имя пользователя или пароль";
        }
    }

}
