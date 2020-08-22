import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum JdbcConnection {

    //wzorzec singleton
    // tworzymy enum poniewż chcemy tylko, aby istniał jeden obiekt tej klasy.
    //Tworzymy konstruktor do połączeń z bazą
    //
    MYSQL_JAVA6("jdbc:mysql://localhost:3306/java6?serverTimezone=UTC",
              "root", "Kamczactiusz10!","com.mysql.cj.jdbc.Driver");

    private final String url;
    private final String user;
    private final String password;
    private final String driverClassName;


    // konstruktor
    JdbcConnection(String url, String user, String password, String driverClassName) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.driverClassName = driverClassName;
        try {
            Class.forName(driverClassName).getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //metoda łącząca się z bazą
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
