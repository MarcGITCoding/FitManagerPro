package msureda.fitmanagerpro.views;

import javax.swing.*;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.regex.Pattern;
import msureda.fitmanagerpro.Main;

import msureda.fitmanagerpro.dataaccess.DataAccess;
import msureda.fitmanagerpro.dto.User;
import msureda.fitmanagerpro.utils.ErrorHandler;
import msureda.fitmanagerpro.utils.StyleUtils;

/**
 * Diálogo modal para el register
 * @author Marc Sureda
 */
public class RegisterDialog extends JDialog {
    private User registeredUser = null;

    public RegisterDialog(JFrame parent, Main mainFrame) {
        super(mainFrame, "Login", true);
        initComponents();

        // Configuración de estilos básica
        setSize(600, 250);
        setResizable(false);
        setLayout(new GridBagLayout());
        this.getContentPane().setBackground(StyleUtils.BACKGROUND_COLOR);

        StyleUtils.styleLabel(nameLabel);
        StyleUtils.styleLabel(emailLabel);
        StyleUtils.styleLabel(passwordLabel);
        StyleUtils.styleTextField(nameField);
        StyleUtils.styleTextField(emailField);
        StyleUtils.stylePasswordField(passwordField);
        StyleUtils.styleButton(registerButton, StyleUtils.ButtonStyle.SECONDARY);

        // Estructuración de componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(registerButton, gbc);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        registerButton = new javax.swing.JButton();
        emailField = new javax.swing.JTextField();
        emailLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        registerButton.setText("Registrar");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });
        getContentPane().add(registerButton);
        registerButton.setBounds(150, 150, 75, 23);

        emailField.setToolTipText("");
        getContentPane().add(emailField);
        emailField.setBounds(270, 40, 64, 22);

        emailLabel.setText("Email: ");
        getContentPane().add(emailLabel);
        emailLabel.setBounds(60, 50, 90, 16);

        passwordLabel.setText("Contraseña: ");
        getContentPane().add(passwordLabel);
        passwordLabel.setBounds(60, 90, 90, 16);

        passwordField.setToolTipText("");
        getContentPane().add(passwordField);
        passwordField.setBounds(270, 80, 90, 22);

        nameLabel.setText("Nombre: ");
        getContentPane().add(nameLabel);
        nameLabel.setBounds(60, 20, 90, 16);

        nameField.setToolTipText("");
        getContentPane().add(nameField);
        nameField.setBounds(270, 10, 64, 22);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        String name = nameField.getText();
        String email = emailField.getText();
        char[] password = passwordField.getPassword();

        if (name.isEmpty() || email.isEmpty() || password.length == 0) {
            ErrorHandler.showCustomError("Todos los campos son obligatorios", this);
            return;
        }

        // Validaciones de longitud
        if (name.length() > 50) {
            ErrorHandler.showCustomError("El nombre no puede tener más de 50 caracteres", this);
            return;
        }
        if (email.length() > 100) {
            ErrorHandler.showCustomError("El email no puede tener más de 100 caracteres", this);
            return;
        }

        // Validación de formato de email
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!Pattern.matches(emailRegex, email)) {
            ErrorHandler.showCustomError("Formato de email inválido", this);
            return;
        }

        // Validación de longitud de la contraseña
        String passwordHash = BCrypt.withDefaults().hashToString(12, password);
        if (passwordHash.length() > 60) {
            ErrorHandler.showCustomError("Error al generar el hash de la contraseña", this);
            return;
        }
                
        try {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPasswordHash(passwordHash);
            
            DataAccess.saveUser(user);

            registeredUser = user;
            dispose();
        } catch (SQLException e) {
            ErrorHandler.showSQLError(e, this);
        }
    }//GEN-LAST:event_registerButtonActionPerformed

    public User getRegisteredUser() {
        return registeredUser;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JButton registerButton;
    // End of variables declaration//GEN-END:variables
}
