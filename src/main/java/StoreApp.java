import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

public class StoreApp {

    static Scanner scanner = new Scanner(System.in);

    // w metodach przekujemy argument połączenia z sql
    static void createStoreTable(Connection connection) throws SQLException {
        // obowiązkowy tworzymy ststement
        Statement create = connection.createStatement();
        create.execute("create table store" +
                "(" +
                "id integer primary key auto_increment, " +
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

    //tworzymy select - pobieranie danych i wyswietlanie danych
    static void showRowsFromStoreTable(Connection connection) throws SQLException {
        Statement select = connection.createStatement();
        ResultSet set = select.executeQuery("select * from store");

        //set w tej petki to nasz przegladany wiersz
        while (set.next()) {
            //pobranie kolumny z nazwa kolumny
            //konwersja typu bazodanowego na java
            // w bazie danych jest varchar a w javie odpowiada to Sting
            int id = set.getInt("id");
            String name = set.getString("name");
            String category = set.getString("category");
            BigDecimal voltage = set.getBigDecimal("voltage");
            BigDecimal capacity = set.getBigDecimal("capacity");
            System.out.println(id + " " + name + " " + category + " " + voltage + " " + capacity);
        }
        set.close();
        select.close();
    }

    // dodoanei mechanizmy do recznego dodwania danych w bazie
    static void insertRowIntoStoreTable(Connection connection) throws SQLException {
        System.out.println("Wpisz nazwę:");
        String name = scanner.nextLine();
        System.out.println("Wpisz kategorę");
        String category = scanner.nextLine();
        System.out.println("Wpisz zawartość alkoholu");
        float voltage = scanner.nextFloat();
        System.out.println("Wprowadź pojemność");
        float capacity = scanner.nextFloat();

        Statement insert = connection.createStatement();
        int count = insert.executeUpdate("insert into store (`name`, `category`,`voltage`,`capacity`) " +
                "values ('" + name + "','" + category + "'," + voltage + "," + capacity + ");"
        );
        System.out.println(count ==1 ? "Sukces" : "Błąd");
        insert.close();
    }


    static int menu() {
        System.out.print("1. Utwórz tabelę ");
        System.out.print("2. Dodaj kilka rekordów ");
        System.out.print("3. Usuń tabele ");
        System.out.print("4. Zobacz co mamy w tabeli ");
        System.out.print("5. Dodoaj produkt ");
        System.out.print("6. Znajdz wiersz o nr ");
        System.out.print("0. Koniec ");

        while (!scanner.hasNextInt()) {
            System.out.println("Wpisz nr polecenia z menu!!!");
            scanner.nextLine();
        }
        // scaner czyta wszystko z inta - enter zostawał wiec jest blad
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    // utworzenie wyszukiwania dla jednego id
    static void selectIdFromStoreTableByID(Connection connection) throws SQLException {
        System.out.println("Wpisz ID:");
        String id = scanner.nextLine();

        // z wada dostepu do bazy 1 or 1=1
        //Statement select = connection.createStatement();
        // ? na koncu zapytania oznacza, ze w tym miejcu należy wstawić parametr
        PreparedStatement select = connection.prepareStatement("select * from store where id =?");

        //zwraca resulset
        //parsujemy z stinga id na int
        select.setInt(1, Integer.parseInt(id));
        ResultSet set = select.executeQuery();

        //z wada dostepu do bazy 1 or 1=1
        //ResultSet set = select.executeQuery("select * from store where id = "+id);
        while (set.next()){
            int id1 = set.getInt("id");
            String name1 = set.getString("name");
            System.out.println(id1+ " "+ name1);
        }
    }

    public static void main(String[] args) throws SQLException {

        Connection connection = JdbcConnection.MYSQL_JAVA6.getConnection();
        while (true) {
            final int option = menu();
            switch (option) {
                case 1:
                    createStoreTable(connection);
                    break;
                case 2:
                    insertRowsIntoStoreTable(connection);
                    break;
                case 3:
                    deleteStoreTable(connection);
                    break;
                case 4:
                    showRowsFromStoreTable(connection);
                    break;
                case 5:
                    insertRowIntoStoreTable(connection);
                    break;
                case 6:
                    selectIdFromStoreTableByID(connection);
                    break;
                case 0:
                    return;
            }
        }
    }

}
