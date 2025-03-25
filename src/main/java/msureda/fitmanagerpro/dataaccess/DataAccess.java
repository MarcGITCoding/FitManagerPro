package msureda.fitmanagerpro.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import msureda.fitmanagerpro.dto.User;
import java.sql.Date;
import java.sql.Statement;
import msureda.fitmanagerpro.dto.Exercise;
import msureda.fitmanagerpro.dto.Workout;

/**
 * Clase que gestiona el acceso a la base de datos.
 * Permite realizar operaciones CRUD sobre usuarios, entrenamientos y ejercicios.
 * 
 * Conexión a la base de datos mediante JDBC.
 * 
 * @author Marc Sureda
 */
public class DataAccess {

    /**
     * Establece una conexión con la base de datos.
     * 
     * @return una conexión a la base de datos.
     * @throws SQLException si hay un error al conectarse.
     */
    private static Connection getConnection() throws SQLException {
        Connection connection = null;

        //OLD LOCAL DB STRING
        //String connectionString = "jdbc:sqlserver://localhost;database=simulapdb;user=sa;"
        //        + "password=Pwd1234;encrypt=false;";
        String connectionString = "jdbc:sqlserver://simulapsqlserver.database.windows.net:1433;database=simulapdb25;user=simulapdbadmin@simulapsqlserver;"
                + "password=Pwd1234.;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";

        connection = DriverManager.getConnection(connectionString);

        return connection;
    }

    /**
     * Obtiene un instructor por su correo electrónico.
     * 
     * @param email el correo del instructor.
     * @return el usuario instructor si existe, de lo contrario null.
     * @throws SQLException si hay un error en la consulta.
     */
    public static User getInstructorByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Usuaris WHERE Email = ? AND Instructor = 1";
        try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                User instructor = new User();
                instructor.setId(result.getInt("Id"));
                instructor.setName(result.getString("Nom"));
                instructor.setEmail(email);
                instructor.setPasswordHash(result.getString("PasswordHash"));
                return instructor;
            }
        }
        return null;
    }

    /**
     * Obtiene una lista de usuarios asignados a un instructor.
     * 
     * @param instructorId el identificador del instructor.
     * @return una lista de usuarios.
     * @throws SQLException si hay un error en la consulta.
     */
    public static ArrayList<User> getUsersByInstructor(int instructorId) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Usuaris WHERE AssignedInstructor = ?";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {
            selectStatement.setInt(1, instructorId);
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("Id"));
                user.setName(resultSet.getString("Nom"));
                user.setEmail(resultSet.getString("Email"));
                user.setPasswordHash(resultSet.getString("PasswordHash"));
                user.setInstructor(resultSet.getBoolean("Instructor"));
                users.add(user);
            }
        }

        return users;
    }
    
    /**
     * Guarda un nuevo usuario en la base de datos.
     * 
     * @param user el usuario a guardar.
     * @throws SQLException si hay un error en la operación.
     */
    public static void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO Usuaris (Nom, Email, PasswordHash, Instructor) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement insertStatement = connection.prepareStatement(sql)) {
                insertStatement.setString(1, user.getName());
                insertStatement.setString(2, user.getEmail());
                insertStatement.setString(3, user.getPasswordHash());
                insertStatement.setInt(4, 1);
                insertStatement.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    /**
     * Obtiene una lista de entrenamientos de un usuario.
     *
     * @param user el usuario cuyos entrenamientos se desean obtener.
     * @return una lista de entrenamientos.
     * @throws SQLException si ocurre un error en la consulta.
     */
    public static ArrayList<Workout> getWorkoutsByUser(User user) throws SQLException {
        ArrayList<Workout> workouts = new ArrayList<>();
        String sql = "SELECT Workouts.Id, Workouts.ForDate, Workouts.UserId, Workouts.Comments"
                + " FROM Workouts"
                + " WHERE Workouts.UserId = ?"
                + " ORDER BY Workouts.ForDate";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {
            selectStatement.setInt(1, user.getId());
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Workout workout = new Workout();
                workout.setId(resultSet.getInt("Id"));
                workout.setForDate(resultSet.getDate("ForDate"));
                workout.setUserId(resultSet.getInt("UserId"));
                workout.setComments(resultSet.getString("Comments"));

                ArrayList<Exercise> exercises = getExercisesByWorkout(workout);
                workout.setExercises(exercises);

                workouts.add(workout);
            }
        }

        return workouts;

    }

    /**
     * Obtiene los ejercicios de un entrenamiento.
     *
     * @param workout el entrenamiento cuyos ejercicios se desean obtener.
     * @return una lista de ejercicios.
     * @throws SQLException si ocurre un error en la consulta.
     */
    public static ArrayList<Exercise> getExercisesByWorkout(Workout workout) throws SQLException {
        ArrayList<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT ExercicisWorkouts.IdExercici,"
                + " Exercicis.NomExercici, Exercicis.Descripcio, Exercicis.DemoFoto"
                + " FROM ExercicisWorkouts INNER JOIN Exercicis ON ExercicisWorkouts.IdExercici=Exercicis.Id"
                + " WHERE ExercicisWorkouts.IdWorkout=?";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {
            selectStatement.setInt(1, workout.getId());
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("IdExercici"));
                exercise.setName(resultSet.getString("NomExercici"));
                exercise.setDescription(resultSet.getString("Descripcio"));
                exercise.setDemoPhoto(resultSet.getString("DemoFoto"));

                exercises.add(exercise);
            }
        }

        return exercises;
    }

    /**
     * Obtiene todos los ejercicios disponibles en la base de datos.
     *
     * @return una lista de ejercicios.
     * @throws SQLException si ocurre un error en la consulta.
     */
    public static ArrayList<Exercise> getAllExercises() throws SQLException {
        ArrayList<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT Id, Exercicis.NomExercici, Exercicis.Descripcio, Exercicis.DemoFoto"
                + " FROM Exercicis";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {

            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("Id"));
                exercise.setName(resultSet.getString("NomExercici"));
                exercise.setDescription(resultSet.getString("Descripcio"));
                exercise.setDemoPhoto(resultSet.getString("DemoFoto"));

                exercises.add(exercise);
            }
        }

        return exercises;
    }

    /**
     * Guarda un nuevo entrenamiento en la base de datos junto con los ejercicios seleccionados.
     *
     * @param date              La fecha del entrenamiento.
     * @param userId            El ID del usuario al que pertenece el entrenamiento.
     * @param comments          Comentarios opcionales sobre el entrenamiento.
     * @param selectedExercises Lista de ejercicios asociados al entrenamiento.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public static void saveWorkout(Date date, int userId, String comments, ArrayList<Exercise> selectedExercises) throws SQLException {
        String sql = "INSERT INTO Workouts (ForDate, UserId, Comments) VALUES (?, ?, ?)";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                insertStatement.setDate(1, date);
                insertStatement.setInt(2, userId);
                insertStatement.setString(3, comments);
                insertStatement.executeUpdate();

                ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int workoutId = generatedKeys.getInt(1);

                    String exerciseSql = "INSERT INTO ExercicisWorkouts (IdWorkout, IdExercici) VALUES (?, ?)";
                    try (PreparedStatement exerciseStatement = connection.prepareStatement(exerciseSql)) {
                        for (Exercise exercise : selectedExercises) {
                            exerciseStatement.setInt(1, workoutId);
                            exerciseStatement.setInt(2, exercise.getId());
                            exerciseStatement.addBatch();
                        }
                        exerciseStatement.executeBatch();
                    }
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }
    
    /**
     * Actualiza un entrenamiento existente en la base de datos y reemplaza los ejercicios asociados.
     *
     * @param workoutId         El ID del entrenamiento a actualizar.
     * @param date              La nueva fecha del entrenamiento.
     * @param comments          Los nuevos comentarios sobre el entrenamiento.
     * @param selectedExercises La nueva lista de ejercicios asociados al entrenamiento.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public static void updateWorkout(int workoutId, Date date, String comments, ArrayList<Exercise> selectedExercises) throws SQLException {
        String sql = "UPDATE Workouts SET ForDate = ?, Comments = ? WHERE Id = ?";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement updateStatement = connection.prepareStatement(sql)) {
                updateStatement.setDate(1, date);
                updateStatement.setString(2, comments);
                updateStatement.setInt(3, workoutId);
                updateStatement.executeUpdate();

                String deleteSql = "DELETE FROM ExercicisWorkouts WHERE IdWorkout = ?";
                try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                    deleteStatement.setInt(1, workoutId);
                    deleteStatement.executeUpdate();
                }

                String insertSql = "INSERT INTO ExercicisWorkouts (IdWorkout, IdExercici) VALUES (?, ?)";
                try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                    for (Exercise exercise : selectedExercises) {
                        insertStatement.setInt(1, workoutId);
                        insertStatement.setInt(2, exercise.getId());
                        insertStatement.addBatch();
                    }
                    insertStatement.executeBatch();
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    /**
     * Elimina un entrenamiento de la base de datos junto con sus ejercicios asociados.
     *
     * @param workoutId El ID del entrenamiento a eliminar.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public static void deleteWorkout(int workoutId) throws SQLException {
        String sql = "DELETE FROM ExercicisWorkouts WHERE IdWorkout = ?";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement deleteExercisesStatement = connection.prepareStatement(sql)) {
                deleteExercisesStatement.setInt(1, workoutId);
                deleteExercisesStatement.executeUpdate();

                String deleteWorkoutSql = "DELETE FROM Workouts WHERE Id = ?";
                try (PreparedStatement deleteWorkoutStatement = connection.prepareStatement(deleteWorkoutSql)) {
                    deleteWorkoutStatement.setInt(1, workoutId);
                    deleteWorkoutStatement.executeUpdate();
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

}
