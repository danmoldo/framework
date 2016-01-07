package today.learnjava.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class ContactMessageForm {

    private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
    private static final String EMAIL_MESSAGE = "{email.message}";

    @NotBlank(message = ContactMessageForm.NOT_BLANK_MESSAGE)
    private String message;

    @Email(message = ContactMessageForm.EMAIL_MESSAGE)
    @NotBlank(message = ContactMessageForm.NOT_BLANK_MESSAGE)
    private String email;

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
