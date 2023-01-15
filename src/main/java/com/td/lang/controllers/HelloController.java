package com.td.lang.controllers;

import com.td.lang.models.Word;
import com.td.lang.security.PersonDetails;
import com.td.lang.services.WordService;
import com.td.lang.util.WordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/hello")
public class HelloController {
    private final WordService wordService;
    private final WordValidator wordValidator;

    @Autowired
    public HelloController(WordService wordService, WordValidator wordValidator) {
        this.wordService = wordService;
        this.wordValidator = wordValidator;
    }

    @GetMapping()
    public String showMainPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
        model.addAttribute("username", personDetails.getPerson().getUsername());
        model.addAttribute("word", wordService.findById(personDetails.getPerson().getId()));
        return "hello-page";
    }

    @PostMapping("/addWord")
    public String createWord(@ModelAttribute("word") @Valid Word word,
                             BindingResult bindingResult) {
        wordValidator.validate(word, bindingResult);

        bindingResult.hasErrors();

        wordService.saveWord(word);

        return "redirect:";
    }

    @DeleteMapping("/{id}")
    public String deleteWord(@PathVariable("id") int id) {
        wordService.deleteWord(id);
        return "redirect:";
    }
}
