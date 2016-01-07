package today.learnjava.form;

import org.hibernate.validator.constraints.Email;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: danmoldovan
 * Date: 06/04/14
 * Time: 15:24
 * To change this template use File | Settings | File Templates.
 */
public class MyProfileForm {

    private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
    private static final String EMAIL_MESSAGE = "{email.message}";

    @Email(message = MyProfileForm.EMAIL_MESSAGE)
    private String email;

    private String oldPassword;

    private String password;

    private String passwordConfirmation;

    private String firstName;

    private String lastName;

    private MultipartFile photo;

    private Date birthday;

    private String birthd;

    private String birthm;

    private String birthy;

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

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBirthd() {
        return birthd;
    }

    public void setBirthd(String birthd) {
        this.birthd = birthd;
    }

    public String getBirthm() {
        return birthm;
    }

    public void setBirthm(String birthm) {
        this.birthm = birthm;
    }

    public String getBirthy() {
        return birthy;
    }

    public void setBirthy(String birthy) {
        this.birthy = birthy;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

}
