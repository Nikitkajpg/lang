package com.td.lang.util;

import com.td.lang.models.Word;
import com.td.lang.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class WordValidator implements Validator {
    private final WordService wordService;

    @Autowired
    public WordValidator(WordService wordService) {
        this.wordService = wordService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Word.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Word word = (Word) target;
    }
}
