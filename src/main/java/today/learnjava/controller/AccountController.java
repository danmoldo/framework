package today.learnjava.controller;

import java.security.Principal;

import today.learnjava.form.SignupForm;
import today.learnjava.repository.AccountRepository;
import today.learnjava.model.Account;
import today.learnjava.service.EmailNotificationService;
import today.learnjava.support.web.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;

@Controller
@Secured("ROLE_USER")
class AccountController {

    private static final String FORGOT_PASSWORD_VIEW_NAME = "signin/forgotPassword";
    private static final String CHANGE_PASSWORD_VIEW_NAME = "signin/changePassword";

    private AccountRepository accountRepository;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @RequestMapping(value = "account/current", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Account accounts(Principal principal) {
        Assert.notNull(principal);
        return accountRepository.findByEmail(principal.getName());
    }

    @RequestMapping(value = "confirmAccount/{validationString}", method = RequestMethod.GET)
    public String confirmAccount(Model model, @PathVariable("validationString") String validationString, RedirectAttributes ra) {
        String pass = validationString.substring(0,10);
        String id = validationString.substring(10);
        Account account = accountRepository.findById(Long.parseLong(id));
        if (account.getPassword().startsWith(pass)) {
            account.setStatus(Account.STATUS_CONFIRMED);
        }
        accountRepository.save(account);
        MessageHelper.addSuccessAttribute(ra, "your.account.is.confirmed");
        return "redirect:/";
    }

    @RequestMapping(value = "forgotPassword")
    public String forgotPassword(Model model) {
        model.addAttribute(new SignupForm());
        return "signin/forgotPassword";
    }

    @RequestMapping(value = "forgotPassword", method = RequestMethod.POST)
    public String forgotPassword(@ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra) {
        errors = validateForgotPasswordForm(signupForm, errors);
        if (errors.hasErrors()) {
            return FORGOT_PASSWORD_VIEW_NAME;
        }

        Account account = accountRepository.findByEmail(signupForm.getEmail());
        emailNotificationService.sendForgotPasswordMail(account);
        MessageHelper.addSuccessAttribute(ra, "forgot.password.email.sent");
        return "redirect:/";
    }

    @RequestMapping(value = "changePassword/{identificationString}", method = RequestMethod.GET)
    public String changePassword(Model model, @PathVariable("identificationString") String identificationString, RedirectAttributes ra) {
        String pass = identificationString.substring(0,35);
        String id = identificationString.substring(35);
        Account account = accountRepository.findById(Long.parseLong(id));
        if (account.getPassword().startsWith(pass)) {
            SignupForm signupForm = new SignupForm();
            signupForm.setEmail(account.getEmail());
            model.addAttribute(signupForm);
            return CHANGE_PASSWORD_VIEW_NAME;
        }
        return "redirect:/";
    }

    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    public String changePassword(@ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra) {
        errors = validateChangePasswordForm(signupForm,errors);
        if (errors.hasErrors()) {
            return CHANGE_PASSWORD_VIEW_NAME;
        }
        Account account = accountRepository.findByEmail(signupForm.getEmail());
        account.setPassword(passwordEncoder.encode(signupForm.getPassword()));
        accountRepository.save(account);
        MessageHelper.addSuccessAttribute(ra, "password.recovered");
        return "redirect:/";
    }

    private Errors validateForgotPasswordForm(SignupForm signupForm, Errors errors) {

        if (accountRepository.findByEmail(signupForm.getEmail()) == null) {
            errors.rejectValue("email","email.not.registered");
        }
        return errors;
    }

    private Errors validateChangePasswordForm(SignupForm signupForm, Errors errors) {

        if (signupForm.getPassword().length() < 8) {
            errors.rejectValue("password","password.too.short");
        }

        if (!signupForm.getPassword().equals(signupForm.getPasswordConfirmation())) {
            errors.rejectValue("passwordConfirmation","password.confirmation.no.match");
        }

        return errors;
    }

}
