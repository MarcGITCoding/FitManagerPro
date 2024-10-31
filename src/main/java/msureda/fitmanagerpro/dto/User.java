/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package msureda.fitmanagerpro.dto;

/**
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
     * @return the Id
     */
    public int getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(int Id) {
        this.Id = Id;
    }

    /**
     * @return the Nom
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @return the Email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * @param Email the Email to set
     */
    public void setEmail(String Email) {
        this.Email = Email;
    }

    /**
     * @return the PasswordHash
     */
    public String getPasswordHash() {
        return PasswordHash;
    }

    /**
     * @param PasswordHash the PasswordHash to set
     */
    public void setPasswordHash(String PasswordHash) {
        this.PasswordHash = PasswordHash;
    }

    /**
     * @return the Photo
     */
    public byte[] getPhoto() {
        return Photo;
    }

    /**
     * @param Photo the Photo to set
     */
    public void setPhoto(byte[] Photo) {
        this.Photo = Photo;
    }

    /**
     * @return the Instructor
     */
    public boolean isInstructor() {
        return Instructor;
    }

    /**
     * @param Instructor the Instructor to set
     */
    public void setInstructor(boolean Instructor) {
        this.Instructor = Instructor;
    }
}
