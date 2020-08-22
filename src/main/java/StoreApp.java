import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class StoreApp {

    // w metodach przekujemy argument połączenia z sql
    static void createStoreTable(Connection connection) throws SQLException {
        // obowiązkowy tworzymy ststement
        Statement create = connection.createStatement();
        create.execute("create table store" +
                "(" +
                "id integer primary key, " +
                "name varchar (25), " +
                "category enum (beer, whisky, vodka, wine), " +
                "voltage decimal (4,1))" +
                "capacity decimal (4,3));" +
                "");
        create.close();
    }

    static void insertRowsIntoStoreTable(Connection connection) throws SQLException {
        Statement insert = connection.createStatement();
        insert.executeUpdate("insert into store values (1, 'Jack Daniels', 'whisky', 40.0, 0,7)");
        insert.close();
    }

    static void deleteStoreTable (Connection connection) throws SQLException {
        Statement drop = connection.createStatement();
        drop.execute("drop table store ");
        drop.close();
    }



    public static void main(String[] args) {


    }

}
