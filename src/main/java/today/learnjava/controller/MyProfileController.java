package today.learnjava.controller;

import today.learnjava.repository.AccountRepository;
import today.learnjava.form.MyProfileForm;
import today.learnjava.model.Account;
import today.learnjava.service.AmazonS3Service;
import today.learnjava.support.web.MessageHelper;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: danmoldovan
 * Date: 06/04/14
 * Time: 15:00
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class MyProfileController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AmazonS3Service amazonS3Service;

    @Inject
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "myProfile")
    public String myProfile(Model model) {
        Account account = accountRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        MyProfileForm myProfileForm = new MyProfileForm();
        myProfileForm.setEmail(account.getEmail());
        myProfileForm.setFirstName(account.getFirstName());
        myProfileForm.setLastName(account.getLastName());

        myProfileForm.setPassword(account.getPassword());
        myProfileForm.setPasswordConfirmation(account.getPassword());

        if (account.getBirthDate() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(account.getBirthDate());
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);

            myProfileForm.setBirthd("" + day);
            myProfileForm.setBirthm("" + (month + 1));
            myProfileForm.setBirthy("" + year);
        }

        model.addAttribute(myProfileForm);
        if (account.getPhotoLink() != null && !account.getPhotoLink().equals("")) {
            model.addAttribute("profilePhotoLink", account.getPhotoLink());
        }
        else {
            model.addAttribute("profilePhotoLink", "http://d92gfn9b4ppd1.cloudfront.net/defaultProfilePic.jpeg");
        }
        return "myProfile/myProfile";
    }

    @RequestMapping(value = "profileChangePassword", method = RequestMethod.GET)
    public String profileChangePassword(Model model) {
        MyProfileForm myProfileForm = new MyProfileForm();
        model.addAttribute(myProfileForm);
        return "myProfile/changePassword";
    }

    @Transactional
    @RequestMapping(value = "profileChangePassword", method = RequestMethod.POST)
    public String profileChangePassword(Model model,@Valid @ModelAttribute MyProfileForm myProfileForm, Errors errors, RedirectAttributes ra) throws ParseException {
        Account account = accountRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (!passwordEncoder.matches(myProfileForm.getOldPassword(), account.getPassword())) {
            errors.rejectValue("oldPassword", "password.old.no.match");
        }

        if (myProfileForm.getPassword().length() < 8) {
            errors.rejectValue("password","password.too.short");
        }

        if (!myProfileForm.getPassword().equals(myProfileForm.getPasswordConfirmation())) {
            errors.rejectValue("passwordConfirmation","password.confirmation.no.match");
        }
        if (errors.hasErrors()) {
            return "myProfile/changePassword";
        }

        account.setPassword(passwordEncoder.encode(myProfileForm.getPassword()));
        accountRepository.save(account);

        MessageHelper.addSuccessAttribute(ra, "your.password.was.changed");

        return "redirect:/myProfile";
    }

    @RequestMapping(value = "profileUpdate", method = RequestMethod.POST)
    public String profileUpdate(Model model,@Valid @ModelAttribute MyProfileForm myProfileForm, Errors errors, RedirectAttributes ra) throws ParseException {
        Account account = accountRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (myProfileForm.getPhoto()!= null && !myProfileForm.getPhoto().getOriginalFilename().equals("") && !myProfileForm.getPhoto().getContentType().equals("image/jpeg") && !myProfileForm.getPhoto().getContentType().equals("image/jpg") && !myProfileForm.getPhoto().getContentType().equals("image/png")) {
            errors.rejectValue("photo", "photo.unsupported.format");
        }
        if (myProfileForm.getPhoto()!= null && myProfileForm.getPhoto().getSize() > 3 * 1024 * 1024) {
            errors.rejectValue("photo", "photo.too.large");
        }
        if (myProfileForm.getLastName() == null || myProfileForm.getLastName().equals("")) {
            errors.rejectValue("lastName", "myProfile.last.name.null");
        }
        if (myProfileForm.getFirstName() == null || myProfileForm.getFirstName().equals("")) {
            errors.rejectValue("firstName", "myProfile.first.name.null");
        }

        String dateStr = myProfileForm.getBirthd() + "/" + myProfileForm.getBirthm() + "/" + myProfileForm.getBirthy();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if (!dateStr.equals("//")) {
            try {
                account.setBirthDate(sdf.parse(dateStr));
            } catch (ParseException e) {
                errors.rejectValue("birthday", "invalid.birthday");
            }
        }

        if (errors.hasErrors()) {
            return "myProfile/myProfile";
        }

        account.setFirstName(StringEscapeUtils.escapeHtml(myProfileForm.getFirstName()));
        account.setLastName(StringEscapeUtils.escapeHtml(myProfileForm.getLastName()));

        if (myProfileForm.getPassword() != null && myProfileForm.getPassword() != "") {
            account.setPassword(myProfileForm.getPassword());
            account.setPasswordConfirmation(myProfileForm.getPasswordConfirmation());
        }

        if (myProfileForm.getPhoto() != null) {
            String filename = "profile" + account.getId();
            switch (myProfileForm.getPhoto().getContentType()) {
                case "image/jpeg": filename = filename + ".jpeg"; break;
                case "image/jpg": filename = filename + ".jpg"; break;
                case "image/png": filename = filename + ".png"; break;
                default: filename = null; break;
            }
            if (filename != null) {
                File profilePhoto = new File(filename);
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(profilePhoto);
                    fos.write(myProfileForm.getPhoto().getBytes());
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                account.setPhotoLink("http://d92gfn9b4ppd1.cloudfront.net/" + filename);
                amazonS3Service.storeFile(profilePhoto);
            }
        }

        //Updating account
        if (account.getPhotoLink() != null && !account.getPhotoLink().equals("")) {
            model.addAttribute("profilePhotoLink", account.getPhotoLink());
        }
        else {
            model.addAttribute("profilePhotoLink", "http://d92gfn9b4ppd1.cloudfront.net/defaultProfilePic.jpeg");
        }

        MessageHelper.addSuccessAttribute(ra, "myProfile.update.success");
        accountRepository.save(account);
        return "myProfile/myProfile";
    }

}
