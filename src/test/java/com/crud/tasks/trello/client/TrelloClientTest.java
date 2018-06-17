package com.crud.tasks.trello.client;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

    private static final String URL = "http://test.com";
    private static final String TEST = "test";
    private static final String USER = "user";
    private static final String TEST_BOARD = "test_board";
    private static final String TEST_ID = "test_id";
    private static final String CARD_NAME = "Test task";
    private static final String CARD_DESC = "Test Description";
    private static final String CARD_POS = "top";
    private static final String CARD_ID = "1";

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Before
    public void init() {
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn(URL);
        when(trelloConfig.getTrelloAppKey()).thenReturn(TEST);
        when(trelloConfig.getTrelloToken()).thenReturn(TEST);
        when(trelloConfig.getTrelloUsername()).thenReturn(USER);
    }

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        //Given
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto(TEST_BOARD, TEST_ID, new ArrayList<>());

        URI uri = new URI("http://test.com/members/user/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);

        //When

        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        //Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals(TEST_ID, fetchedTrelloBoards.get(0).getId());
        assertEquals(TEST_BOARD, fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException {
        //Given
        URI uri = new URI("http://test.com/members/user/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(null);

        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        //Then
        assertEquals(0, fetchedTrelloBoards.size());
    }

    @Test
    public void shouldCreateCard() throws URISyntaxException {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                CARD_NAME,
                CARD_DESC,
                CARD_POS,
                TEST_ID
        );

        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");

        CreatedTrelloCard createdTrelloCard = new CreatedTrelloCard(
                CARD_ID,
                CARD_NAME,
                URL,
                new Badges(0, new AttachmentsByType(new Trello(0,0)))
        );

        when(restTemplate.postForObject(uri, null, CreatedTrelloCard.class)).thenReturn(createdTrelloCard);

        //When
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);

        //Then
        assertEquals(CARD_ID, newCard.getId());
        assertEquals(CARD_NAME, newCard.getName());
        assertEquals(URL, newCard.getShortUrl());
        assertEquals(0, newCard.getBadges().getVotes());
        assertEquals(0, newCard.getBadges().getAttachments().getTrello().getBoard());
        assertEquals(0, newCard.getBadges().getAttachments().getTrello().getCard());
    }

}