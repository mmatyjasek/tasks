package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);
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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TrelloConfig trelloConfig;

    public List<TrelloBoardDto> getTrelloBoards() {
        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(createGetBoardsUrl(), TrelloBoardDto[].class);
            return Optional.ofNullable(boardsResponse).map(Arrays::asList).orElseGet(ArrayList::new);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public CreatedTrelloCardDto createNewCard(TrelloCardDto trelloCardDto) {
        return restTemplate.postForObject(createPostCardUrl(trelloCardDto),null, CreatedTrelloCardDto.class);
    }

    private URI createGetBoardsUrl() {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint()
                + "/members/" + trelloConfig.getTrelloUsername() + "/boards")
                .queryParam(KEY, trelloConfig.getTrelloAppKey())
                .queryParam(TOKEN, trelloConfig.getTrelloToken())
                .queryParam(FIELDS, FIELDS_VALUE)
                .queryParam(LISTS, ALL).build().encode().toUri();
    }

    private URI createPostCardUrl(TrelloCardDto trelloCardDto) {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                .queryParam(KEY, trelloConfig.getTrelloAppKey())
                .queryParam(TOKEN, trelloConfig.getTrelloToken())
                .queryParam(NAME, trelloCardDto.getName())
                .queryParam(DESC, trelloCardDto.getDescription())
                .queryParam(POS, trelloCardDto.getPos())
                .queryParam(ID_LIST, trelloCardDto.getListId())
                .build().encode().toUri();
    }
}
