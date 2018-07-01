package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private SimpleEmailService emailService;

    @Test
    public void shouldReturnTrelloBoardsDto() {
        //Given
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(new TrelloBoardDto("Board 1", "1", new ArrayList<>()));
        trelloBoardDtoList.add(new TrelloBoardDto("Board 2", "2", new ArrayList<>()));
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtoList);
        //When
        List<TrelloBoardDto> retrievedTrelloBoardFtoList = trelloService.fetchTrelloBoards();
        //Then
        assertEquals(trelloBoardDtoList, retrievedTrelloBoardFtoList);

    }

    @Test
    public void shouldReturnTrelloCard() {
        //Given
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "Card 1"
                , "url", new Badges(0,new AttachmentsByType(new Trello(0,0))));
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card 1", "test_card", "pos1", "1" );
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(adminConfig.getAdminMail()).thenReturn("test@test.com");
        //When
        CreatedTrelloCardDto returnedNewCardDto = trelloService.createTrelloCard(trelloCardDto);
        //Then
        assertEquals(createdTrelloCardDto, returnedNewCardDto);

    }
}