
package data;


import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class ConnectionPool {
    private static ConnectionPool pool = null;
    private static DataSource dataSource = null;

    private ConnectionPool() throws NamingException {

        InitialContext ic = new InitialContext();
        dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/flashcard");

    }
    public static synchronized ConnectionPool getInstance() throws NamingException {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }
    public Connection getConnection() throws SQLException {

        return dataSource.getConnection();

    }
    public void freeConnection(Connection c) throws SQLException {

        c.close();

    }
}
