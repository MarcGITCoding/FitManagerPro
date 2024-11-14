package msureda.fitmanagerpro.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import msureda.fitmanagerpro.dataaccess.DataAccess;
import msureda.fitmanagerpro.Main;
import msureda.fitmanagerpro.dto.User;
import msureda.fitmanagerpro.utils.ErrorHandler;
import msureda.fitmanagerpro.utils.StyleUtils;

/**
 * HomePage mostrada tras hacer el login
 * @author Marc Sureda
 */
public class HomePanel extends javax.swing.JPanel {
    private Main main;
    private JList<User> userList;
    private DefaultListModel<User> listModel;
    private ArrayList<User> users;

    public HomePanel(Main main, User instructor) {
        initComponents();
        
        this.main = main;

        // Configuración de estilos básica
        setLayout(new BorderLayout());
        StyleUtils.stylePanel(this);

        welcomeLabel.setText("Bienvenido, " + instructor.getName());
        StyleUtils.styleLabel(welcomeLabel);
        welcomeLabel.setHorizontalAlignment(SwingConstants.LEFT);

        logoutButton.setText("Cerrar Sesión");
        StyleUtils.styleButton(logoutButton);

        // Panel superior para organizar el saludo y el botón de logout
        JPanel topPanel = new JPanel(new BorderLayout());
        StyleUtils.stylePanel(topPanel);
        topPanel.add(welcomeLabel, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);

        // Lista de usuarios del instructor
        try {
            users = DataAccess.getUsersByInstructor(instructor.getId());
        } catch (SQLException e) {
            ErrorHandler.showSQLError(e, this);
        }
        
        listModel = new DefaultListModel<>();
        for (User user : users) {
            listModel.addElement(user);
        }
        
        userList = new JList<>(listModel);
        StyleUtils.styleList(userList);
        userList.setCellRenderer(new UserListCellRenderer());
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Panel de encabezado para la lista
        JPanel headerPanel = new JPanel(new BorderLayout(5, 5));
        headerPanel.setBackground(StyleUtils.BACKGROUND_COLOR.brighter());
        JLabel dateHeader = new JLabel("Nombre del usuario");
        JLabel commentHeader = new JLabel("E-Mail");
        StyleUtils.styleLabel(dateHeader);
        StyleUtils.styleLabel(commentHeader);

        headerPanel.add(dateHeader, BorderLayout.WEST);
        headerPanel.add(commentHeader, BorderLayout.EAST);
        
        // Handler de doble-click sobre un usuario de la lista
        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() < 2) return;

                int index = userList.locationToIndex(e.getPoint());
                if (index == -1) return;
                
                User selectedUser = userList.getModel().getElementAt(index);
                if (selectedUser == null) return;

                // Abrir su listado de workouts
                main.showUserPanel(selectedUser);
            }
        });

        JScrollPane userScrollPane = new JScrollPane(userList);
        userScrollPane.setPreferredSize(StyleUtils.LIST_SIZE);
        
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.add(headerPanel, BorderLayout.NORTH);
        bodyPanel.add(userScrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(bodyPanel, BorderLayout.CENTER);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        welcomeLabel = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(800, 800));
        setLayout(null);

        welcomeLabel.setText("Bienvenido");
        welcomeLabel.setToolTipText("");
        add(welcomeLabel);
        welcomeLabel.setBounds(6, 6, 190, 30);

        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        add(logoutButton);
        logoutButton.setBounds(570, 10, 72, 23);
    }// </editor-fold>//GEN-END:initComponents

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // En logout, volver a cargar la aplicación desde el main
        SwingUtilities.getWindowAncestor(HomePanel.this).dispose();
        new Main().setVisible(true);
    }//GEN-LAST:event_logoutButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton logoutButton;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables

    // Render personalizado para la lista de usuarios
    private static class UserListCellRenderer extends JPanel implements ListCellRenderer<User> {
        private JLabel nameLabel;
        private JLabel emailLabel;

        public UserListCellRenderer() {
            setLayout(new BorderLayout(5, 5));
            nameLabel = new JLabel();
            emailLabel = new JLabel();
            StyleUtils.styleLabel(nameLabel);
            StyleUtils.styleLabel(emailLabel);
            add(nameLabel, BorderLayout.WEST);
            add(emailLabel, BorderLayout.EAST);
            setBackground(StyleUtils.BACKGROUND_COLOR);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends User> list, User user, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            nameLabel.setText(user.getName());
            emailLabel.setText(user.getEmail());
            setBackground(isSelected ? StyleUtils.PRIMARY_COLOR : StyleUtils.BACKGROUND_COLOR);
            return this;
        }
    }
}
