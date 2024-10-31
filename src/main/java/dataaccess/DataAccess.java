/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import msureda.fitmanagerpro.dto.User;

/**
 * 
 * @author Marc Sureda
 */
public class DataAccess {
    private Connection getConnection() throws SQLException {
        Connection connection = null;
        
        String connectionString = "jdbc:sqlserver://localhost;database=simulapdb;user=sa;"
                + "password=1234;";
        
        connection = DriverManager.getConnection(connectionString);
        
        return connection;
    }
    
    public ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        
        String sql = "SELECT * FROM Usuaris";
        
        Connection conn = getConnection();
        
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        
        while (result.next()) {
            User user = new User();
            
            user.setId(result.getInt("Id"));
            user.setName(result.getString("Nom"));
            user.setEmail(result.getString("Email"));
            user.setPasswordHash(result.getString("PasswordHash"));
            //user.setPhoto(result.getBytes("Foto"));
            user.setInstructor(result.getBoolean("Instructor"));

            users.add(user);
        }
        
        statement.close();
        conn.close();
        
        return users;
    }
}
