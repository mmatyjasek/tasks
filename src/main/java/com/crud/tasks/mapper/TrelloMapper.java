package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class TrelloMapper {

    public List<TrelloBoard> mapToBoards(final List<TrelloBoardDto> trelloBoardDto) {
        return trelloBoardDto.stream()
                .map(t -> new TrelloBoard(t.getName(), t.getId(), mapToLists(t.getLists())))
                .collect(toList());
    }

    public List<TrelloBoardDto> mapToBoardsDto(final List<TrelloBoard> trelloBoards) {
        return trelloBoards.stream().
                map(t -> new TrelloBoardDto(t.getName(), t.getId(), mapToListsDto(t.getLists())))
                .collect(toList());
    }

    public List<TrelloList> mapToLists(final List<TrelloListDto> trelloListDto) {
        return trelloListDto.stream().
                map(t -> new TrelloList(t.getId(), t.getName(), t.isClosed())).
                collect(toList());
    }

    public List<TrelloListDto> mapToListsDto(final List<TrelloList> trelloLists) {
        return trelloLists.stream().
                map(t -> new TrelloListDto(t.getId(), t.getName(), t.isClosed())).
                collect(toList());
    }

    public TrelloCardDto mapToCardDto(final TrelloCard trelloCard) {
        return new TrelloCardDto(trelloCard.getName(), trelloCard.getDescription(),
                trelloCard.getPos(), trelloCard.getListId());
    }

    public TrelloCard mapToCard(final TrelloCardDto trelloCardDto) {
        return new TrelloCard(trelloCardDto.getName(), trelloCardDto.getDescription(),
                trelloCardDto.getPos(), trelloCardDto.getListId());
    }

}
