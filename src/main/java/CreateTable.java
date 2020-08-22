import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    public static void main(String[] args) throws SQLException {

        // odpowiada za połączenie z bazą danych
        Connection connection = JdbcConnection.MYSQL_JAVA6.getConnection();

        //Statement - to jest interfejs
        Statement createTable = connection.createStatement();
        createTable.execute("drop table kursanci;");
        //tworznie tabeli w sql - polecenie w sql'u dajemy
        // execute daje true jezeli zwraca jakis wynik,
        // false jezeli nie mamy bledu
        boolean result = createTable.execute("create table kursanci (email varchar(25) primary key, nick varchar(15));");
        System.out.println("Polecenie poprawnie wykonane ?"+result);

        // wstawiamy dane w tablei
        Statement insertRow = connection.createStatement();
        int count = insertRow.executeUpdate("insert into kursanci values('karol@op.pl','karole')," +
                "('ewa@gmail.com','ewa'),('marek@sda.pl','marek');");
        System.out.println("Liczba wstawionych rekordów: " + count);
    }
}
