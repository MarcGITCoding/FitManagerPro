/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package msureda.fitmanagerpro.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import msureda.fitmanagerpro.dto.User;
import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * 
 * @author Marc Sureda
 */
public class DataAccess {
    private Connection getConnection() throws SQLException {
        Connection connection = null;
        
        String connectionString = "jdbc:sqlserver://localhost;database=simulapdb;user=sa;"
                + "password=1234;encrypt=false;";
        
        connection = DriverManager.getConnection(connectionString);
        
        return connection;
    }
    
    public User validateUser(String email, String password) throws SQLException {
        String sql = "SELECT * FROM Usuaris WHERE Email = ?";
        try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            
            if (result.next()) {
                String storedHash = result.getString("PasswordHash");
                BCrypt.Result  bcryptResult = BCrypt.verifyer().verify(password.toCharArray(), storedHash);
                if (bcryptResult.verified) {
                    User user = new User();
                    user.setId(result.getInt("Id"));
                    user.setName(result.getString("Nom"));
                    user.setEmail(email);
                    return user;
                }
            }
        }
        return null;
    }
    
    public ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        
        String sql = "SELECT * FROM Usuaris";
        
        try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
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
            
        }
        
        return users;
    }
}
