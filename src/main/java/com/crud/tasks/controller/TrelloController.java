package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trello")
public class TrelloController {

    private static final String NAME = "Kodilla";

    @Autowired
    private TrelloClient trelloClient;

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public void getTrelloBoards() {

        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards.stream().filter(b-> b.getId() != null)
                .filter(b -> b.getName() != null)
                .filter(b -> b.getName().contains(NAME))
                .collect(Collectors.toList())
                .forEach(trelloBoardDto -> {
                    System.out.println("Board: id = "  + trelloBoardDto.getId() + ", name = " + trelloBoardDto.getName());
                    System.out.println("This board contains following lists:");
                    trelloBoardDto.getLists().forEach(trelloList ->
                            System.out.println("List: name = " + trelloList.getName() + ", id = " + trelloList.getId() + ", closed = " + trelloList.isClosed()));
                    System.out.println("\n");
                });
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTrelloCard")
    public CreatedTrelloCard createdTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloClient.createNewCard(trelloCardDto);
    }
}
