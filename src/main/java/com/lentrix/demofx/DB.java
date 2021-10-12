package com.lentrix.demofx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if(connection==null) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String conString = "jdbc:sqlserver://DESKTOP-JKEV4AP;Database=lntrxdb;IntegratedSecurity=true";
                connection = DriverManager.getConnection(conString);
            }catch(SQLException ex) {
                ex.printStackTrace();
            }catch(ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }

        return connection;
    }
}
