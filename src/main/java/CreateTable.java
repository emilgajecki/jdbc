import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    public static void main(String[] args) throws SQLException {

        Connection connection = JdbcConnection.MYSQL_JAVA6.getConnection();
        Statement createTable = connection.createStatement();
        //tworznie tabeli w sql
        // execute daje true jezeli zwraca jakis wynik,
        // false jezeli nie mamy bledu
        boolean result = createTable.execute("create table kursanci (email varchar(25) primary key, nick varchar(15));");
        System.out.println("Polecenie poprawnie wykonane ?"+result);
    }
}
