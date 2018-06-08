package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    private static final String KEY = "key";
    private static final String TOKEN = "token";
    private static final String FIELDS = "fields";
    private static final String FIELDS_VALUE = "name,id";
    private static final String LISTS = "lists";
    private static final String ALL = "all";
    private static final String NAME = "name";
    private static final String DESC = "desc";
    private static final String POS = "pos";
    private static final String ID_LIST = "idList";

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.username}")
    private String trelloUsername;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {

        TrelloBoardDto [] boardsResponse = restTemplate.getForObject(createGetBoardsUrl(), TrelloBoardDto[].class);

        return Optional.ofNullable(boardsResponse).map(Arrays::asList).orElseGet(ArrayList::new);
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto) {
        return restTemplate.postForObject(createPostCardUrl(trelloCardDto),null, CreatedTrelloCard.class);
    }

    private URI createGetBoardsUrl() {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloUsername + "/boards")
                .queryParam(KEY, trelloAppKey)
                .queryParam(TOKEN, trelloToken)
                .queryParam(FIELDS, FIELDS_VALUE)
                .queryParam(LISTS, ALL).build().encode().toUri();
    }

    private URI createPostCardUrl(TrelloCardDto trelloCardDto) {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/cards")
                .queryParam(KEY, trelloAppKey)
                .queryParam(TOKEN, trelloToken)
                .queryParam(NAME, trelloCardDto.getName())
                .queryParam(DESC, trelloCardDto.getDescription())
                .queryParam(POS, trelloCardDto.getPos())
                .queryParam(ID_LIST, trelloCardDto.getListId())
                .build().encode().toUri();
    }
}
