package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@Slf4j
public class TrelloValidator {

    public void validateCard(final TrelloCard trelloCard){
        if(trelloCard.getName().toLowerCase().contains("test")) {
            log.info("Someone is testing my application!");
        } else {
            log.info("Seems that my application is used in a proper way.");
        }
    }

    public List<TrelloBoard> validateTrelloBoards(final List<TrelloBoard> trelloBoards) {
        log.info("Start filtering boards...");
        List<TrelloBoard> filtredTrelloBoards = trelloBoards.stream()
                .filter(t -> !t.getName().equalsIgnoreCase("test"))
                .collect(toList());
        log.info("Boards have been filtered. Current list size: " + filtredTrelloBoards.size());
        return filtredTrelloBoards;
    }
}
