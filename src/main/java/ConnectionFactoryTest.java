import java.sql.Connection;

public class ConnectionFactoryTest {

    public static void main(String[] args) {
        Connection connection = ConnectionFactory.getConnection();
        System.out.println(connection);
    }

}
