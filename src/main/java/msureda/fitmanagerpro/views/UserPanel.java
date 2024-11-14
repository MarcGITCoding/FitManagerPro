package msureda.fitmanagerpro.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import msureda.fitmanagerpro.dataaccess.DataAccess;
import msureda.fitmanagerpro.Main;
import msureda.fitmanagerpro.dto.User;
import msureda.fitmanagerpro.dto.Workout;
import msureda.fitmanagerpro.utils.ErrorHandler;
import msureda.fitmanagerpro.utils.StyleUtils;

/**
 * Panel de usuario con su listado de entrenamientos
 * @author Marc Sureda
 */
public class UserPanel extends javax.swing.JPanel {
    private Main main;
    private User user;
    private JLabel backLabel;
    private JButton addWorkoutButton;
    private JList<Workout> workoutList;
    private DefaultListModel<Workout> listModel;
    private ArrayList<Workout> workouts;

    public UserPanel(Main main, User user) {
        initComponents();
        
        this.main = main;
        this.user = user;

        // Configuración de estilos básica
        setLayout(new BorderLayout());
        StyleUtils.stylePanel(this);

        // Flecha para regresar hacia atrás
        backLabel = new JLabel("←");
        StyleUtils.styleLabel(backLabel);
        backLabel.setFont(new Font("Arial", Font.BOLD, 24));
        backLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                main.showHomePanel();
            }
        });

        // Botón de añadir workout
        addWorkoutButton = new JButton("Añadir Workout");
        StyleUtils.styleButton(addWorkoutButton);
        addWorkoutButton.addActionListener(this::addWorkoutAction);

        // Panel superior para la flecha de regreso y el botón de añadir workout
        JPanel topPanel = new JPanel(new BorderLayout());
        StyleUtils.stylePanel(topPanel);
        topPanel.add(backLabel, BorderLayout.WEST);
        topPanel.add(addWorkoutButton, BorderLayout.EAST);

        // Lista de workouts
        try {
            workouts = DataAccess.getWorkoutsByUser(user);
        } catch (SQLException e) {
            ErrorHandler.showSQLError(e, this);
        }
        
        listModel = new DefaultListModel<>();
        for (Workout workout : workouts) {
            listModel.addElement(workout);
        }

        workoutList = new JList<>(listModel);
        StyleUtils.styleList(workoutList);
        workoutList.setCellRenderer(new WorkoutListCellRenderer());
        workoutList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        
        // Panel de encabezado para la lista
        JPanel headerPanel = new JPanel(new BorderLayout(5, 5));
        headerPanel.setBackground(StyleUtils.BACKGROUND_COLOR.brighter());
        JLabel dateHeader = new JLabel("Fecha");
        JLabel commentHeader = new JLabel("Comentario");
        StyleUtils.styleLabel(dateHeader);
        StyleUtils.styleLabel(commentHeader);

        headerPanel.add(dateHeader, BorderLayout.WEST);
        headerPanel.add(commentHeader, BorderLayout.EAST);
        
        // Handler de doble-click sobre un workout de la lista
        workoutList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() < 2) return;

                int index = workoutList.locationToIndex(e.getPoint());
                
                if (index == -1) return;
                
                Workout selectedWorkout = workoutList.getModel().getElementAt(index);
                if (selectedWorkout == null) return;

                ManageWorkoutDialog manageWorkoutDialog = new ManageWorkoutDialog(main, main, selectedWorkout, user);
                manageWorkoutDialog.setLocationRelativeTo(main);
                manageWorkoutDialog.setVisible(true);
                
                //Refrescar workout por si ha habido algún cambio en el workout
                refreshWorkoutList();
            }
        });

        JScrollPane workoutScrollPane = new JScrollPane(workoutList);
        workoutScrollPane.setPreferredSize(StyleUtils.LIST_SIZE);
        
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.add(headerPanel, BorderLayout.NORTH);
        bodyPanel.add(workoutScrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(bodyPanel, BorderLayout.CENTER);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(800, 800));
        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    
    private void refreshWorkoutList() {
        try {
            workouts = DataAccess.getWorkoutsByUser(user);
            
            listModel.clear();
            for (Workout workout : workouts) {
                listModel.addElement(workout);
            }
        } catch (SQLException e) {
            ErrorHandler.showSQLError(e, this);
        }
    }
    
    
    // Acción para abrir el diálogo de añadir workout
    private void addWorkoutAction(ActionEvent e) {
        ManageWorkoutDialog manageWorkoutDialog = new ManageWorkoutDialog(main, main, null, user);
        manageWorkoutDialog.setLocationRelativeTo(main);
        manageWorkoutDialog.setVisible(true);
        
        //Refrescar workouts por si se ha añadido un workout
        refreshWorkoutList();
    }

    // Render personalizado para la lista de workouts
    private static class WorkoutListCellRenderer extends JPanel implements ListCellRenderer<Workout> {
        private JLabel dateLabel;
        private JLabel commentLabel;

        /*
            TODO: Cuando se añada una función para hacer review de ejercicios,
            se podría mostrar si hay algún ejercicio pendiente por hacer review
            en otra columna.
        */
        public WorkoutListCellRenderer() {
            setLayout(new BorderLayout(5, 5));
            dateLabel = new JLabel();
            commentLabel = new JLabel();
            StyleUtils.styleLabel(dateLabel);
            StyleUtils.styleLabel(commentLabel);
            add(dateLabel, BorderLayout.WEST);
            add(commentLabel, BorderLayout.EAST);
            setBackground(StyleUtils.BACKGROUND_COLOR);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Workout> list, Workout workout, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            dateLabel.setText(workout.getForDate().toString()); // Mostramos la fecha
            commentLabel.setText(workout.getComments()); // Mostramos el comentario
            setBackground(isSelected ? StyleUtils.PRIMARY_COLOR : StyleUtils.BACKGROUND_COLOR);
            return this;
        }
    }
}
