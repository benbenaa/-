package util;

import bean.Student;
import bean.User;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlUtils {

    /**
     * @param user 用户类-保存的MySQL数据
     * @return int
     */

    public static boolean login(User user) {
        String sql = "select account,password from user where account = ?;";
        User user1 = selectOneUser(User.class, sql, user.getAccount());
        if (user1 != null) {
            return user1.getPassword().equals(user.getPassword());
        }
        return false;
    }

    /**
     * @param user 用户类-保存的MySQL数据
     * @return boolean
     */
    public static boolean insert(User user) {
        String sql = "insert into user(name,account,password,phone,start) values(?,?,?,?,?);";
        return update(sql, user.getName(), user.getAccount(), user.getPassword(),user.getPhone(), user.getStart());

    }

    /**
     * 插入一条学生信息
     * @param student 保存的学生信息
     * @return boolean
     */
    public static boolean insertStudent(Student student){
        String sql ="insert into student(name,sex,age,grade,number) values(?,?,?,?,?);";
        return update(sql,student.getName(),student.getSex(),student.getAge(),student.getGrade(),student.getNumber());

    }
    /**
     * @param account 要删除的人
     * @return boolean
     */
    public static boolean delete(String account) {
        String sql="delete from user where account=?;";
        return update(sql,account);
    }

    /**
     *
     * @param number 要删除的学生的学号
     * @return boolean
     */
    public static boolean deleteStudent(String number){
        String sql="delete from student where number=?;";
        return update(sql,number);
    }
    /**
     * 通过账号修改姓名和密码
     * @param user 用户类-保存的MySQL数据
     * @return boolean
     */
    public static boolean update(User user) {
        String sql = "update user set name=?,password=? where account=?";
        return update(sql,user.getName(), user.getPassword(), user.getAccount());
    }
    public static boolean updateStudent(Student student){
        String sql="update student set name=?,sex=?,age=?,grade=? where number=?;";
        return update(sql,student.getName(),student.getSex(),student.getAge(),student.getGrade(),student.getNumber());

    }
    /**
     *修改一条记录
     * @param password 传入要修改的数据
     * @param str 传入要修改的数据的查询条件
     * @return boolean
     */
    public static boolean updateOne(String password,String str) {
        String sql = "update user set password=? where account=?";
        return update(sql,password,str);
    }
    /**
     * 查询一条记录
     * @param name 要查询的用户
     * @return User
     */
    public static User select(String name) {
        String sql="select * from user where name=?;";
        return selectOneUser(User.class,sql,name);
    }

    /**
     * 查询一条学生信息
     * @param number 要查询的学生的学号
     * @return Student
     */
    public static Student selectStudent(String number){
        String sql="select * from student where number=?;";
        return selectOneUser(Student.class,sql,number);
    }
    /**
     * 设置账户的状态
     * @param str 要修改的账户
     * @param a 状态：0-离线，1-在线
     */
    public static void setStart(String str,int a){
        String sql="update user set start =? where account=?;";
        update(sql,a,str);
    }
    /**
     * 查询所有用户
     *
     * @return 所有用户
     */
    public static List<User> select() {
        String sql = "select * from user;";
        return getAll(User.class, sql);
    }

    /**
     * 返回所有的学生信息
     * @return Student
     */
    public static List<Student> selectStudent(){
        String sql="select * from student;";
        return getAll(Student.class,sql);
    }
    public static String selectPassword(String phone){
        String sql="select password from user where phone=?;";
        return selectOneUser(User.class, sql, phone).getPassword();
    }

    /**
     *
     * @param clazz 用户类
     * @param sql SQL语句
     * @param args 可变形参
     * @param <T> 泛型-用户类
     * @return 用户类
     */
    private static <T> List<T> getAll(Class<T> clazz, String sql, Object... args) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //获取连接
            conn = JDBCUtils.getConnection("Manger");
            //预编译SQL语句
            ps = conn.prepareStatement(sql);
            //添加占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //执行
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            ArrayList<T> list = new ArrayList<>();
            while (rs.next()) {
                T t = clazz.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object value = rs.getObject(i + 1);
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, value);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }

    /**
     * 更新一条数据
     * @param sql SQL语句
     * @param args 可变形参
     * @return boolean
     */
    private static boolean update(String sql, Object... args) {
        int num = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //获取连接
            conn = JDBCUtils.getConnection("Manger");
            //sql预编译
            ps = conn.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //执行
            num = ps.executeUpdate();
            //关闭资源
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }

        return num != 0;
    }

    /**
     * 查询一条用户数据
     * @param clazz 用户类
     * @param sql sql语句
     * @param args 可变形参
     * @param <T> 泛型-用户类
     * @return 用户类
     */
    private static <T> T selectOneUser(Class<T> clazz, String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection("Manger");
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            if (rs.next()) {
                T t = clazz.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object value = rs.getObject(i + 1);
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, value);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }
    public static void createDb(String database) {
        // 1.数据库名
        String DBName = database;
        Connection conn = null;
        try {
            // 建表语句
            String tableSql1 = "create table if not exists user(name varchar(20) not null,"
                    + "account varchar(20) not null primary key, "+
                    "password varchar(20) not null,"+
                    "phone varchar(20),"+
                    "start int(2) default 0);";
            String tableSql2="create table if not exists test(num int(5) primary key AUTO_INCREMENT,mark varchar(20));";
            // 建库语句
            String databaseSql = "create database if not exists " + DBName;
            String tableSql3 = "create table if not exists student(name varchar(20) not null,"
                    + "sex varchar(20) not null, "+
                    "age int(11) not null,"+
                    "grade varchar(20) not null ,"+
                    "number varchar(20) not null primary key );";
            // 链接数据库
            conn = JDBCUtils.getConnection();
            // 用于执行静态SQL语句并返回其产生的结果的对象
            Statement smt = conn.createStatement();
            // 执行建库语句
            smt.executeUpdate(databaseSql);
            SqlUtils.createTable("Manger",tableSql3);
            SqlUtils.createTable("manger",tableSql2);
            createTrigger("student");
            // 链接新建的数据库
            smt.execute("use "+DBName+";");
            // 执行建表语句
            smt.executeUpdate(tableSql1);
            User user=new User("zgh","admin","1234","1238",0);
            insert(user);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn, null);
        }
    }
    public static void createTable(String database,String sql){
            Connection conn = null;
            Statement smt= null;
        try {
            conn =JDBCUtils.getConnection(database);
            smt=conn.createStatement();
            smt.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,smt);
        }
    }
    public static <T> List<T> callProcedure(Class<T> clazz,int x){
        CallableStatement cstmt=null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection("Manger");
            cstmt = conn.prepareCall("{call my_proc2(?)}");
            cstmt.setInt(1,x);
            rs = cstmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            ArrayList<T> list = new ArrayList<>();
            while (rs.next()) {
                T t = clazz.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object value = rs.getObject(i + 1);
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, value);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, cstmt, rs);
        }
        return null;
    }
    public static void createProcedure(String table){
        String sql1 = "CREATE PROCEDURE my_proc1(in sage int) BEGIN SELECT * FROM "+table+" where age<sage; END";
        String sql2 = "CREATE PROCEDURE my_proc2(in num varchar(20)) BEGIN SELECT * FROM "+table+" where number=num; END";
        Statement smt = null;
        Connection conn = null;
        try {
            //获取连接
            conn = JDBCUtils.getConnection("Manger");
            smt=conn.createStatement();
            smt.execute(sql1);
            smt.execute(sql2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.closeResource(conn, smt);
        }
    }
    public static void createTrigger(String table){
        // 创建触发器
        String sql = "CREATE TRIGGER my_trigger AFTER update ON "
                +table+" FOR EACH ROW INSERT INTO test(mark) VALUES ('after_update')";
        Statement smt = null;
        Connection conn = null;
        try {
            //获取连接
            System.out.println("sda");
            conn = JDBCUtils.getConnection("Manger");
            smt = conn.createStatement();
            smt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.closeResource(conn, smt);
        }
    }

}
