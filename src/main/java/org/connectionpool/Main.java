package org.connectionpool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;
import java.time.LocalTime;

public class Main {
    private final static String url = "jdbc:postgresql://localhost:5432/test";
    private final static String password = "ashdhaker";
    private final static String userName = "postgres";

    private final static Object lock = new Object();

    public static void main(String[] args) throws SQLException, InterruptedException {

//        A Simple Implementation
        ConnectionPool connectionPool = BasicConnectionPool.getConnectionPool(url, userName, password);
        MyThread[] myThreads = new MyThread[15];
        for(int i = 0; i < myThreads.length; i++) {
            myThreads[i] = new MyThread((BasicConnectionPool) connectionPool);
        }
        LocalTime start = LocalTime.now();
        for(MyThread myThread : myThreads) {
            myThread.start();
        }
        for(MyThread myThread: myThreads) {
            myThread.join();
        }
        long seconds = LocalTime.now().toSecondOfDay() - start.toSecondOfDay();
        System.out.println(seconds);
        connectionPool.shutdown();

// HikariCP
//        BuiltInConnectionPool builtInConnectionPool = new BuiltInConnectionPool(new HikariDataSource());
//        builtInConnectionPool.init(url);
//        MyThreadModified[] myThreadModifiers = new MyThreadModified[15];
//        for(int i = 0; i < myThreadModifiers.length; i++) {
//            myThreadModifiers[i] = new MyThreadModified(builtInConnectionPool);
//        }
//        LocalTime localTime = LocalTime.now();
//        for(MyThreadModified myThreadModified: myThreadModifiers) {
//            myThreadModified.start();
//        }
//        for(MyThreadModified myThreadModified : myThreadModifiers) {
//            myThreadModified.join();
//        }
//        System.out.println(LocalTime.now().toSecondOfDay() - localTime.toSecondOfDay());


// C3P0
//        BuiltInConnectionPool builtInConnectionPool = new BuiltInConnectionPool(new ComboPooledDataSource());
//        builtInConnectionPool.init(url);
//        MyThreadModified[] myThreadModifiers = new MyThreadModified[15];
//        for(int i = 0; i < myThreadModifiers.length; i++) {
//            myThreadModifiers[i] = new MyThreadModified(builtInConnectionPool);
//        }
//        LocalTime localTime = LocalTime.now();
//        for(MyThreadModified myThreadModified: myThreadModifiers) {
//            myThreadModified.start();
//        }
//        for(MyThreadModified myThreadModified : myThreadModifiers) {
//            myThreadModified.join();
//        }
//        System.out.println(LocalTime.now().toSecondOfDay() - localTime.toSecondOfDay());

//  Apache Commons DBCP
//        BuiltInConnectionPool builtInConnectionPool = new BuiltInConnectionPool(new BasicDataSource());
//        builtInConnectionPool.init(url);
//        MyThreadModified[] myThreadModifiers = new MyThreadModified[15];
//        for(int i = 0; i < myThreadModifiers.length; i++) {
//            myThreadModifiers[i] = new MyThreadModified(builtInConnectionPool);
//        }
//        LocalTime localTime = LocalTime.now();
//        for(MyThreadModified myThreadModified: myThreadModifiers) {
//            myThreadModified.start();
//        }
//        for(MyThreadModified myThreadModified : myThreadModifiers) {
//            myThreadModified.join();
//        }
//        System.out.println(LocalTime.now().toSecondOfDay() - localTime.toSecondOfDay());
    }
}