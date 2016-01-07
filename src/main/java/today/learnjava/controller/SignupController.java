package today.learnjava.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import today.learnjava.repository.AccountRepository;
import today.learnjava.service.EmailNotificationService;
import today.learnjava.service.UserService;
import today.learnjava.model.Account;
import today.learnjava.form.SignupForm;
import today.learnjava.support.web.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignupController {

    private static final String SIGNUP_VIEW_NAME = "signup/signup";

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserService userService;

	@Inject
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailNotificationService emailNotificationService;
	
	@RequestMapping(value = "signup")
	public String signup(Model model) {
		model.addAttribute(new SignupForm());
        return SIGNUP_VIEW_NAME;
	}
	
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra) {
        errors = validateForm(signupForm,errors);
		if (errors.hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}
		Account account = signupForm.createAccount();
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		account = accountRepository.save(account);
		emailNotificationService.sendAccountConfirmationMail(account);
		userService.signin(account);
        MessageHelper.addSuccessAttribute(ra, "signup.success");
		return "redirect:/";
	}

    private Errors validateForm(SignupForm signupForm, Errors errors) {
		if (signupForm.getPassword().length() < 8) {
			errors.rejectValue("password","password.too.short");
		}

		if (!signupForm.getPassword().equals(signupForm.getPasswordConfirmation())) {
            errors.rejectValue("passwordConfirmation","password.confirmation.no.match");
        }

        if (accountRepository.findByEmail(signupForm.getEmail())!= null) {
            errors.rejectValue("email","email.already.registered");
        }
        return errors;
    }
}
