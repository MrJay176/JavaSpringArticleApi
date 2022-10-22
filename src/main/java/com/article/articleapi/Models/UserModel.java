package com.article.articleapi.Models;
import javax.persistence.*;

@Entity
@Table(name = "user_model")
public class UserModel {

    public UserModel() {

    }

    public UserModel(Long id, String name, String email, String password, Long profilePicture, String coverPhoto, Boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
        this.coverPhoto = coverPhoto;
        this.isAdmin = isAdmin;
    }

    public UserModel(String name,
                     String email,
                     String password,
                     Long profilePicture,
                     String coverPhoto,
                     Boolean isAdmin) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
        this.coverPhoto = coverPhoto;
        this.isAdmin = isAdmin;
    }


    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 2
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;

    @Column
    private Long profilePicture;
    @Column
    private String coverPhoto;
    @Column
    private Boolean isAdmin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Long profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", coverPhoto='" + coverPhoto + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
