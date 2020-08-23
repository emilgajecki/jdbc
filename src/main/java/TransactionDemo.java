import java.sql.*;

public class TransactionDemo {

    static void createTableAccount(Connection connection) throws SQLException {
        Statement create = connection.createStatement();
        create.execute("Create table account (int id primary key , int points)");
        create.close();
    }
    static int getPointsFromAcount(Connection connection, int id) throws SQLException {
        PreparedStatement select = connection.prepareStatement("select points from account where id=?");
        select.setInt(1,id);
        ResultSet set = select.executeQuery();
        set.next();
        return set.getInt(1);
    }

    public static void main(String[] args) {

    }
}
