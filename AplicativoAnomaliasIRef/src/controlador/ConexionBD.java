/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PAVILLION
 */
public class ConexionBD {
    private Connection cnx;

    public ConexionBD() {
    }
    
     public Connection getCnx() {
        return cnx;
    }

    public void setCnx(Connection cnx) {
        this.cnx = cnx;
    }
    
    
    public Connection obtener(String server,String database, String user, String password){
         try {
            String dbURL = "jdbc:sqlserver://"+server+";databaseName=" + database ;
            cnx = DriverManager.getConnection(dbURL, user, password);
            if (cnx != null) {
                DatabaseMetaData dm = (DatabaseMetaData) cnx.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
 
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
        return cnx;
    }
    
    public void cerrar(){
        if (cnx != null){
            try {
                cnx.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
