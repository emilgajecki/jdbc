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
                "capacity decimal (2,1));" +
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
    // z poprawiona funkcjonalnoscia
    static void insertRowIntoStoreTable(Connection connection) throws SQLException {
        System.out.println("Wpisz nazwę:");
        String name = scanner.nextLine();
        System.out.println("Wpisz kategorę");
        String category = scanner.nextLine();
        System.out.println("Wpisz zawartość alkoholu");
        float voltage = scanner.nextFloat();
        System.out.println("Wprowadź pojemność");
        float capacity = scanner.nextFloat();


        PreparedStatement insert = connection.prepareStatement("insert into "+
                "store(`name`,`category`,`voltage`,`capacity`)" + "values(?, ?, ?, ?);");
        insert.setString(1,name);
        insert.setString(2,category);
        insert.setBigDecimal(4,new BigDecimal(capacity));
        insert.setBigDecimal(3, new BigDecimal(voltage));
        int count= insert.executeUpdate();
        System.out.println(count ==1 ? "Sukces" : "Błąd");
        insert.close();
    }

    //dodamy towar pod warunkiem, ze cos juz jest takiego w bazie
    //pokretna logika - chce dodac 2 razy wiecej
    static void insertIntoResultSet(Connection connection) throws SQLException {
        Statement select = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
        ResultSet set = select.executeQuery("SELECT * from store");
        while(set.next()){
            String category = set.getString("category");
            if ("wine".equals(category)){
                System.out.println("Wstaw nowy rekord");
                //wstawiamy nowy rekord
                set.moveToInsertRow();
                System.out.println("Podaj nazwe");
                String name = scanner.nextLine();
                System.out.println("Podaj zawartosc");
                float voltage= scanner.nextFloat();
                System.out.println("Podaj objętość");
                float capacity= scanner.nextFloat();
                scanner.nextLine();// czyszczenie klawiatury
                set.updateString("name", name);
                set.updateBigDecimal("voltage", new BigDecimal(voltage));
                set.updateBigDecimal("capacity", new BigDecimal(capacity));
                set.updateString("category", "wine");
                set.insertRow();
                set.next();
            }
        }
    }
    // usuniecie konkretnego wiesza - podczas przegladania
    static void deleteFromRowStoreTable (Connection connection) throws SQLException {
        System.out.println("Podaj id usuwanego wiersza:");
        int id = scanner.nextInt();
        scanner.nextLine();
        Statement delete = connection.createStatement();
        ResultSet set = delete.executeQuery("select * from store");
        if(id==set.getInt("id")){
            set.deleteRow();
        }
    }


    static int menu() {
        System.out.print("1. Utwórz tabelę ");
        System.out.print("2. Dodaj kilka rekordów ");
        System.out.print("3. Usuń tabele ");
        System.out.print("4. Zobacz co mamy w tabeli ");
        System.out.print("5. Dodoaj produkt ");
        System.out.print("6. Znajdz wiersz o nr ");
        System.out.print("7. Dodaj nowy produkt - wino jezeli jest w tabeli wino ");
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
                case 7:
                    insertIntoResultSet(connection);
                    break;
                case 0:
                    return;
            }
        }
    }

}
