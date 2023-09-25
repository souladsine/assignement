package ma.octo.assignement.domain;

import ma.octo.assignement.domain.util.Generators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "UTILISATEUR")
public class Utilisateur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 10, nullable = false, unique = true)
    private String username;

    @Column(length = 10, nullable = false)
    private String gender;

    @Column(length = 60, nullable = false)
    private String lastname;

    @Column(length = 60, nullable = false)
    private String firstname;

    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utilisateur that = (Utilisateur) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(username, that.username)) return false;
        if (!Objects.equals(gender, that.gender)) return false;
        if (!Objects.equals(lastname, that.lastname)) return false;
        if (!Objects.equals(firstname, that.firstname)) return false;
        return Objects.equals(birthdate, that.birthdate);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (birthdate != null ? birthdate.hashCode() : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFullName(){
        return firstname + " " + lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Utilisateur() {
    }

    public static class Builder {
        private Long id;
        private String username;
        private String gender;
        private String lastname;
        private String firstname;
        private Date birthdate;

        public Builder() {
        }

        // Setter methods
        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder setFirstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder setBirthdate(Date birthdate) {
            this.birthdate = birthdate;
            return this;
        }

        public Utilisateur build() {
            if (this.birthdate == null) {
                this.birthdate = new Date();
            }
            if (this.lastname == null) {
                this.lastname = "";
            }
            if (this.firstname == null) {
                this.firstname = "";
            }
            if (this.username == null) {
                this.username = Generators.randomUsername();
            }
            if (this.gender == null) {
                this.gender = "undefined";
            }
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.firstname = this.firstname;
            utilisateur.lastname = this.lastname;
            utilisateur.birthdate = this.birthdate;
            utilisateur.username = this.username;
            utilisateur.gender = this.gender;
            return utilisateur;
        }
    }
}