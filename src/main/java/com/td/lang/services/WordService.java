package com.td.lang.services;

import com.td.lang.models.Person;
import com.td.lang.models.Word;
import com.td.lang.repositories.WordRepository;
import com.td.lang.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class WordService {
    private final WordRepository wordRepository;

    @Autowired
    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public List<Word> findAll() {
        return wordRepository.findAll();
    }

    @Transactional()
    public void saveWord(Word word) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
        word.setUserId(personDetails.getPerson().getId());
        word.setLevel(0);
        word.setCreationTime(LocalDateTime.now());
        wordRepository.save(word);
    }

    @Transactional()
    public void deleteWord(int id) {
        wordRepository.deleteById(id);
    }
}
