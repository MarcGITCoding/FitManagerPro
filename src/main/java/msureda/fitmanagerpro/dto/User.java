package msureda.fitmanagerpro.dto;

/**
 * Clase DTO que representa un usuario en la tabla Usuaris.
 * Contiene información sobre el identificador, nombre, email, contraseña, foto y si es instructor.
 * 
 * @author Marc Sureda
 */
public class User {
    private int Id;
    private String Name;
    private String Email;
    private String PasswordHash;
    private byte[] Photo;
    private boolean Instructor;

    /**
     * Obtiene el identificador del usuario.
     * @return el Id del usuario.
     */
    public int getId() {
        return Id;
    }

    /**
     * Establece el identificador del usuario.
     * @param Id el Id a asignar.
     */
    public void setId(int Id) {
        this.Id = Id;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return el nombre del usuario.
     */
    public String getName() {
        return Name;
    }

    /**
     * Establece el nombre del usuario.
     * @param Name el nombre a asignar.
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return el email del usuario.
     */
    public String getEmail() {
        return Email;
    }

    /**
     * Establece el correo electrónico del usuario.
     * @param Email el email a asignar.
     */
    public void setEmail(String Email) {
        this.Email = Email;
    }

    /**
     * Obtiene el hash de la contraseña del usuario.
     * @return el hash de la contraseña.
     */
    public String getPasswordHash() {
        return PasswordHash;
    }

    /**
     * Establece el hash de la contraseña del usuario.
     * @param PasswordHash el hash de la contraseña a asignar.
     */
    public void setPasswordHash(String PasswordHash) {
        this.PasswordHash = PasswordHash;
    }

    /**
     * Obtiene la foto de perfil del usuario.
     * @return la foto de perfil como array de bytes.
     */
    public byte[] getPhoto() {
        return Photo;
    }

    /**
     * Establece la foto de perfil del usuario.
     * @param Photo la foto a asignar.
     */
    public void setPhoto(byte[] Photo) {
        this.Photo = Photo;
    }

    /**
     * Verifica si el usuario es un instructor.
     * @return true si es instructor, false en caso contrario.
     */
    public boolean isInstructor() {
        return Instructor;
    }

    /**
     * Establece si el usuario es un instructor.
     * @param Instructor true si es instructor, false en caso contrario.
     */
    public void setInstructor(boolean Instructor) {
        this.Instructor = Instructor;
    }
}
