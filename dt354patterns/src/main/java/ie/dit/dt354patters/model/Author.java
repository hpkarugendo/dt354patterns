package ie.dit.dt354patters.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Author {
    @Id
    @GeneratedValue
    private int id;
    private String firstName, lastName;
    @Transient
    private static Author instance = new Author();
    
    private Author() {
	super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static Author getInstance() {
        return instance;
    }
    
    
}
