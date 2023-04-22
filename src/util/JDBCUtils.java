package util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author MagicBook
 */
public class JDBCUtils {
    /**
     * 获取数据库的连接
     *
     * @return Connection
     * @throws Exception
     */
    public static Connection getConnection() throws Exception{
       InputStream is=ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties pr=new Properties();
        pr.load(is);

        String user=pr.getProperty("user");
        String password=pr.getProperty("password");
        String url=pr.getProperty("url");
        String driverClass=pr.getProperty("driverClass");

        Class.forName(driverClass);

        return DriverManager.getConnection(url,user,password);

    }
    /**
     * 获取数据库的连接
     *
     * @return Connection
     * @throws Exception
     */
    public static Connection getConnection(String dbName) throws Exception{
        InputStream is=ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties pr=new Properties();
        pr.load(is);

        String user=pr.getProperty("user");
        String password=pr.getProperty("password");
        String url=pr.getProperty("url");
        String driverClass=pr.getProperty("driverClass");

        Class.forName(driverClass);

        return DriverManager.getConnection(url+dbName,user,password);

    }

    /**
     *
     * 关闭资源的操作
     * @param conn 连接
     * @param ps SQl语句
     * @param rs 结果集
     */
    public static void closeResource(Connection conn, Statement ps, ResultSet rs){
            try {
                if(conn!=null) {
                    conn.close();
                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }

            try {
                if(ps!=null) {
                    ps.close();
                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }

            try {
                if(rs!=null){
                rs.close();
            }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }

    }

    /**
     * 关闭资源
     *
     * @param conn 连接
     * @param ps 操作Sql语句的实例
     */
    public static void closeResource(Connection conn, Statement ps){
            try {
                if(conn!=null) {
                    conn.close();
                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }

            try {
                if(ps!=null) {
                    ps.close();
                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }

    }

}
