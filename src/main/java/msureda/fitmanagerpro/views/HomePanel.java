package msureda.fitmanagerpro.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import msureda.fitmanagerpro.dataaccess.DataAccess;
import msureda.fitmanagerpro.Main;
import msureda.fitmanagerpro.dataaccess.DatabaseConfig;
import msureda.fitmanagerpro.dto.User;
import msureda.fitmanagerpro.utils.ErrorHandler;
import msureda.fitmanagerpro.utils.StyleUtils;
import msureda.workoutscalendarpanel.WorkoutsCalendarPanel;
import msureda.workoutscalendarpanel.dto.Workout;

/**
 * Panel principal de inicio que se muestra tras realizar el login de un instructor.
 * Esta clase muestra un saludo al instructor, una lista de usuarios asociados a él,
 * y un calendario con los entrenamientos de los usuarios. Además, permite al instructor
 * navegar a los paneles de usuario y gestionar los entrenamientos.
 * 
 * @author Marc Sureda
 */
public class HomePanel extends javax.swing.JPanel {
    private Main main;
    private JList<User> userList;
    private DefaultListModel<User> listModel;
    private ArrayList<User> users;
    private WorkoutsCalendarPanel calendarPanel;
    private JLabel statusBar;
    private JTable statusTable;
    private JScrollPane statusScrollPane;
    private Timer fadeOutTimer;

    /**
     * Constructor que inicializa el panel de inicio con la información del instructor.
     * 
     * @param main El objeto principal de la aplicación.
     * @param instructor El instructor que ha iniciado sesión.
     */
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
        StyleUtils.styleButton(logoutButton, StyleUtils.ButtonStyle.PRIMARY);

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
        
        calendarPanel = new WorkoutsCalendarPanel();
        calendarPanel.setConnectionString(DatabaseConfig.CONNECTION_STRING);
        calendarPanel.setInstructorId(instructor.getId());
        calendarPanel.setActiveButtonColor(StyleUtils.PRIMARY_COLOR);
        calendarPanel.setBackground(StyleUtils.BACKGROUND_COLOR);
        calendarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        calendarPanel.addCalendarEventListener(event -> updateStatusBar(event.getWorkouts()));
        
        statusBar = new JLabel("Selecciona un día para ver los workouts", SwingConstants.CENTER);
        StyleUtils.styleLabel(statusBar);
        statusBar.setOpaque(true);
        statusBar.setBackground(StyleUtils.BACKGROUND_COLOR);
        
        JPanel calendarContainer = new JPanel(new BorderLayout());
        calendarContainer.setBackground(StyleUtils.BACKGROUND_COLOR);
        calendarContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        calendarContainer.add(calendarPanel, BorderLayout.CENTER);
        calendarContainer.add(statusBar, BorderLayout.SOUTH);

        // Agregar el calendario en la parte inferior del bodyPanel
        bodyPanel.add(calendarContainer, BorderLayout.SOUTH);

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

    /**
     * Maneja el evento de clic en el botón de logout.
     * 
     * @param evt El evento de acción generado por el clic en el botón.
     */
    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // En logout, volver a cargar la aplicación desde el main
        SwingUtilities.getWindowAncestor(HomePanel.this).dispose();
        new Main().setVisible(true);
    }//GEN-LAST:event_logoutButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton logoutButton;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables

    /**
     * Actualiza la barra de estado con los entrenamientos para el día seleccionado.
     * 
     * @param workouts La lista de entrenamientos para el día.
     */
    private void updateStatusBar(ArrayList<Workout> workouts) {
        if (statusScrollPane != null) {
            remove(statusScrollPane);
        }

        if (workouts.isEmpty()) {
            statusBar.setText("No hay workouts para este día");
            return;
        }

        String[] columnNames = {"Usuario", "Comentario", "Ejercicios"};
        Object[][] data = new Object[workouts.size()][3];

        for (int i = 0; i < workouts.size(); i++) {
            Workout workout = workouts.get(i);
            data[i][0] = workout.getUser().getName();
            data[i][1] = workout.getComments();
            data[i][2] = workout.getExercises().size();
        }

        statusTable = new JTable(data, columnNames);
        statusTable.setFillsViewportHeight(true);
        statusTable.setPreferredScrollableViewportSize(new Dimension(getWidth(), 100));
        StyleUtils.styleTable(statusTable);

        statusScrollPane = new JScrollPane(statusTable);
        statusScrollPane.setBackground(StyleUtils.BACKGROUND_COLOR);
        statusScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(statusScrollPane, BorderLayout.SOUTH);
        revalidate();
        repaint();

        startFadeOutAnimation(statusScrollPane);
    }
    
    /**
     * Inicia una animación de desvanecimiento para el componente dado.
     * 
     * @param component El componente al que se le aplicará el desvanecimiento.
     */
    private void startFadeOutAnimation(JComponent component) {
        if (fadeOutTimer != null && fadeOutTimer.isRunning()) {
            fadeOutTimer.stop();
        }

        Timer fadeTimer = new Timer(50, new ActionListener() {
            float opacity = 1.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f;
                component.setOpaque(false);
                component.repaint();
                if (opacity <= 0) {
                    ((Timer) e.getSource()).stop();
                    remove(component);
                    revalidate();
                    repaint();
                }
            }
        });

        fadeOutTimer = new Timer(10000, e -> fadeTimer.start());
        fadeOutTimer.setRepeats(false);
        fadeOutTimer.start();
    }
    
    /**
     * Renderizador personalizado para la lista de usuarios.
     * Muestra el nombre y el correo electrónico del usuario en una fila de la lista.
     */
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
