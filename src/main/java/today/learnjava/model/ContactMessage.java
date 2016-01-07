package today.learnjava.model;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "contact_message")
public class ContactMessage implements java.io.Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @Lob
    private String message;

    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
