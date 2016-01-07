package today.learnjava.model;

import javax.persistence.*;

import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "account")
@NamedQuery(name = Account.FIND_BY_EMAIL, query = "select a from Account a where a.email = :email")
public class Account implements java.io.Serializable {

    public static final Long STATUS_NEW = 0l;
    public static final Long STATUS_CONFIRMED = 1l;

	public static final String FIND_BY_EMAIL = "Account.findByEmail";

	@Id
	@GeneratedValue
	private Long id;

    @Version
    private Long version;

    private String firstName;

    private String lastName;

    private Long status;

	@Column(unique = true)
	private String email;
	
	@JsonIgnore
	private String password;

    @Transient
    private String passwordConfirmation;

	private String role = "ROLE_USER";

    private String photoLink;

    private Date birthDate;

    private Date joinDate;

    protected Account() {

	}

	public Account(String firstName, String lastName, String email, String password, String passwordConfirmation, String role) {
		this.firstName = StringEscapeUtils.escapeHtml(firstName);
        this.lastName = StringEscapeUtils.escapeHtml(lastName);
        this.email = StringEscapeUtils.escapeHtml(email);
		this.password = password;
        this.passwordConfirmation = passwordConfirmation;
		this.role = role;
        this.status = STATUS_NEW;
        this.photoLink = "http://d92gfn9b4ppd1.cloudfront.net/defaultProfilePic.jpeg";
        this.joinDate = new Date();
	}

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

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

}
