import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StoreApp {

    static Scanner scanner = new Scanner(System.in);

    // w metodach przekujemy argument połączenia z sql
    static void createStoreTable(Connection connection) throws SQLException {
        // obowiązkowy tworzymy ststement
        Statement create = connection.createStatement();
        create.execute("create table store" +
                "(" +
                "id integer primary key, " +
                "name varchar (25), " +
                "category enum ('beer', 'whisky', 'vodka', 'wine'), " +
                "voltage decimal (4,1)," +
                "capacity decimal (4,3));" +
                "");
        create.close();
    }

    // metoda wstawiajace dane z odpowiednimi wartosciami
    static void insertRowsIntoStoreTable(Connection connection) throws SQLException {
        Statement insert = connection.createStatement();
        insert.executeUpdate("insert into store values (1, 'Jack Daniels', 'whisky', 40.0, 0.7)");
        insert.close();
    }

    //usuniecie danych z tabeli
    static void deleteStoreTable(Connection connection) throws SQLException {
        Statement drop = connection.createStatement();
        drop.execute("drop table store ");
        drop.close();
    }

    static int menu() {
        System.out.print("1. Utwórz tabelę ");
        System.out.print("2. Dodaj kilka rekordów ");
        System.out.print("3. Usuń tabele ");
        System.out.print("0. Koniec ");

        while (!scanner.hasNextInt()) {
            System.out.println("Wpisz nr polecenia z menu!!!");
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

    public static void main(String[] args) throws SQLException {

        Connection connection = JdbcConnection.MYSQL_JAVA6.getConnection();
        while (true){
            final int option = menu();
            switch (option){
                case 1 :
                    createStoreTable(connection);
                    break;
                case 2:
                    insertRowsIntoStoreTable(connection);
                    break;
                case 3:
                    deleteStoreTable(connection);
                    break;
                case 0:
                    return;
            }
        }
    }

}
