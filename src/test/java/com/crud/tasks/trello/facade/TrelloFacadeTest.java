package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloMapper trelloMapper;

    @Test
    public void shouldFetchEmptyList() {
        //Given
        List<TrelloListDto> trelloListsDto = new ArrayList<>();
        trelloListsDto.add(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
        trelloBoardsDto.add(new TrelloBoardDto("1", "tests", trelloListsDto));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1", "tests", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoardsDto);
        when(trelloMapper.mapToBoards(trelloBoardsDto)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(new ArrayList<>());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(new ArrayList<>());

        //When
        List<TrelloBoardDto> fetchedTrelloBordsDto = trelloFacade.fetchTrelloBoards();
        //Then
        assertNotNull(fetchedTrelloBordsDto);
        assertEquals(0, fetchedTrelloBordsDto.size());
    }

    @Test
    public void shouldFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloListsDto = new ArrayList<>();
        trelloListsDto.add(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
        trelloBoardsDto.add(new TrelloBoardDto("tests", "1", trelloListsDto));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("tests", "1", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoardsDto);
        when(trelloMapper.mapToBoards(trelloBoardsDto)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(trelloBoardsDto);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        //When
        List<TrelloBoardDto> fetchedTrelloBordsDto = trelloFacade.fetchTrelloBoards();
        //Then
        assertNotNull(fetchedTrelloBordsDto);
        assertEquals(1, fetchedTrelloBordsDto.size());
        fetchedTrelloBordsDto.forEach(t -> {
            assertEquals("1", t.getId());
            assertEquals("tests", t.getName());
            t.getLists().forEach(l -> {
                assertEquals("1", l.getId());
                assertEquals("test_list", l.getName());
                assertFalse(l.isClosed());
            });
        });

    }

    @Test
    public void createCard() {

        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card 1", "test_card", "pos1", "1" );
        TrelloCard trelloCard = new TrelloCard("Card 1", "test_card", "pos1", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "Card 1"
                , "url", new Badges(0,new AttachmentsByType(new Trello(0,0))));
        when(trelloService.createTrelloCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(trelloMapper.mapToCard(trelloCardDto)).thenReturn(trelloCard);
        when(trelloMapper.mapToCardDto(trelloCard)).thenReturn(trelloCardDto);

        //When
        CreatedTrelloCardDto facadeCreatedCard = trelloFacade.createCard(trelloCardDto);
        //Then
        assertEquals(createdTrelloCardDto, facadeCreatedCard);

    }
}