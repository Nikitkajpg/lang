package com.td.lang.controllers;

import com.td.lang.models.Word;
import com.td.lang.services.WordService;
import com.td.lang.util.WordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("word", wordService.findAll());
        return "hello-page";
    }

    @PostMapping("/addToDic")
    public String createWord(@ModelAttribute("word") @Valid Word word,
                             BindingResult bindingResult) {
        wordValidator.validate(word, bindingResult);

        bindingResult.hasErrors();

        wordService.saveWord(word);

        return "redirect:";
    }
}
