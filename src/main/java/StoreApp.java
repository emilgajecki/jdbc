import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class StoreApp {

    // w metodach przekujemy argument połączenia z sql
    static void createTable(Connection connection) throws SQLException {
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
    }



    public static void main(String[] args) {


    }

}
