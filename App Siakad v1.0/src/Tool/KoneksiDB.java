/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tool;

import java.sql.*;
/**
 *
 * @author Bangbay
 */
public class KoneksiDB {
    
    public Connection getConnection() throws SQLException{
        Connection cnn;
        try{
            String server = "jdbc:mysql://localhost/dbsiakad_171530023";
            String drever = "com.mysql.jdbc.Driver";
            Class.forName(drever);
            cnn = DriverManager.getConnection(server, "root", "");
            return cnn;
        }catch(SQLException | ClassNotFoundException se){
            System.out.println(se);
            return null;
        }
    }
}
