package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HelloController {

    private final ApplicationRepository applicationRepository;

    public HelloController(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/apply")
    public String applyForm(Model model) {
        model.addAttribute("applyForm", new ApplyForm());
        return "apply";
    }

    @PostMapping("/apply")
    public String applySubmit(@Valid @ModelAttribute ApplyForm applyForm,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "apply";
        }

        applicationRepository.insert(applyForm);

        model.addAttribute("companyName", applyForm.getCompanyName());
        model.addAttribute("contactName", applyForm.getContactName());
        model.addAttribute("email", applyForm.getEmail());
        model.addAttribute("planName", applyForm.getPlanName());
        return "complete";
    }

    @GetMapping("/complete")
    public String complete() {
        return "complete";
    }

    @GetMapping("/applications")
    public String applications(Model model) {
        model.addAttribute("applicationList", applicationRepository.findAll());
        return "applications";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        applicationRepository.delete(id);
        return "redirect:/applications";
    }
}