package today.learnjava.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import today.learnjava.form.ContactMessageForm;
import today.learnjava.model.ContactMessage;
import today.learnjava.repository.*;
import today.learnjava.support.web.MessageHelper;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private AccountRepository accountRepository;

	@Autowired
	private ContactMessageRepository contactMessageRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal, Model model) {

		if (principal != null) {
			model.addAttribute("loggedUser", accountRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
			return "home/homeNotSignedIn";
		}
		else {
			return "home/homeNotSignedIn";
		}
	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contact(Model model) {
		model.addAttribute("contactMessageForm", new ContactMessageForm());
		return "util/contactPage";
	}

	@RequestMapping(value = "/sendContactMessage", method = RequestMethod.POST)
	@Transactional
	public String sendContactMessage(@Valid @ModelAttribute ContactMessageForm contactMessageForm, Errors errors, RedirectAttributes ra) {

		ContactMessage contactMessage = new ContactMessage();
		contactMessage.setEmail(StringEscapeUtils.escapeHtml(contactMessageForm.getEmail()));
		contactMessage.setMessage(StringEscapeUtils.escapeHtml(contactMessageForm.getMessage()));
		contactMessageRepository.save(contactMessage);
		MessageHelper.addSuccessAttribute(ra, "contact.message.was.sent");
		return "redirect:/";
	}

}
