/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Israel
 */
public class MySql {

    /**
     * @param args the command line arguments
     */
    public static Connection conexion;
    
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "ejemplo", "ejemplo");
            ConsultasBaseDeDatos.Consultas(conexion);
        } catch (Exception e) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
}
