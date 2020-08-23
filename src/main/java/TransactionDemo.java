import java.sql.*;

public class TransactionDemo {

    static void createTableAccount(Connection connection) throws SQLException {
        Statement create = connection.createStatement();
        create.execute("Create table account ( id integer primary key ,  points integer)");
        create.executeUpdate("insert into account values (1,100), (2,50)");
        create.close();
    }
    static int getPointsFromAcount(Connection connection, int id) throws SQLException {
        PreparedStatement select = connection.prepareStatement("select points from account where id=?");
        select.setInt(1,id);
        ResultSet set = select.executeQuery();
        set.next();
        return set.getInt(1);
    }

    public static void main(String[] args) throws SQLException {

        //transfer srodków z jednego konta na drugie
        //Pobieramy z konta
        int pointsToTransfer = 20;
        Connection connection =JdbcConnection.MYSQL_JAVA6.getConnection();
        //createTableAccount(connection);

        PreparedStatement transfer = connection.prepareStatement("update account set points = " +
                "(select points - ? from account where id =?) where id =?");
       transfer.setInt(1,pointsToTransfer);
       transfer.setInt(2,1);
       transfer.setInt(3,1);
       transfer.executeUpdate();
       transfer.close();

       //dodajemy do drugiego konta
       PreparedStatement transferTo = connection.prepareStatement("update account set points = " +
               "(select points + ? from account where id =?) where id =?");
       transferTo.setInt(1,pointsToTransfer);
       transferTo.setInt(2,2);
       transferTo.setInt(3,2);
       transferTo.executeUpdate();
       transferTo.close();
    }

}
