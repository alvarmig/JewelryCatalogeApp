/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel
 */
public class JDBCMySQL {

    //public String
    private static Connection conn;

    public Connection connectionDB() {
        try {
            //register the driver
            //String ruta = "jdbc:mysql://";
            //String servidor = nomservidor + "/";

            Class.forName("com.mysql.jdbc.Driver");

            try {
                // create conection
                conn = DriverManager.getConnection("jdbc:mysql://localhost/silmara", "root", "oracle");
            } catch (SQLException ex) {
                Logger.getLogger(JDBCMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }

            /*  if (conn != null) {
                conn.close();
            } else*/
            if (conn == null) {
                return null;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBCMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    /**
     * Miguel Alvarado Método que genera una desconexión a BD 09-08-2019
     *
     * @param conn
     * @return
     */
    public void disconnection(Connection conn) {

        try {
            //conn = null;
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
