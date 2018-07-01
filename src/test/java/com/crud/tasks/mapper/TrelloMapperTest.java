package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void shouldMapToBoards() {
        //Given
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("name1", "id1", new ArrayList<>());
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("name2", "id2", new ArrayList<>());
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto1);
        trelloBoardDtoList.add(trelloBoardDto2);

        //When
        List<TrelloBoard> trelloBoardList = trelloMapper.mapToBoards(trelloBoardDtoList);

        //Then
        assertEquals(trelloBoardDtoList.size(), trelloBoardList.size());
        assertEquals(trelloBoardDtoList.get(0).getId(), trelloBoardList.get(0).getId());
        assertEquals(trelloBoardDtoList.get(1).getId(), trelloBoardList.get(1).getId());
        assertEquals(trelloBoardDtoList.get(0).getName(), trelloBoardList.get(0).getName());
        assertEquals(trelloBoardDtoList.get(1).getName(), trelloBoardList.get(1).getName());
        assertEquals(trelloBoardDtoList.get(0).getLists().size(), trelloBoardList.get(0).getLists().size());
        assertEquals(trelloBoardDtoList.get(1).getLists().size(), trelloBoardList.get(1).getLists().size());
    }

    @Test
    public void mapToBoardsDto() {
        //Given
        TrelloBoard trelloBoard1 = new TrelloBoard("name1", "id1", new ArrayList<>());
        TrelloBoard trelloBoard2 = new TrelloBoard("name2", "id2", new ArrayList<>());
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(trelloBoard1);
        trelloBoardList.add(trelloBoard2);

        //When
        List<TrelloBoardDto> trelloBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoardList);

        //Then
        assertEquals(trelloBoardList.size(), trelloBoardDtoList.size());
        assertEquals(trelloBoardList.get(0).getId(), trelloBoardDtoList.get(0).getId());
        assertEquals(trelloBoardList.get(1).getId(), trelloBoardDtoList.get(1).getId());
        assertEquals(trelloBoardList.get(0).getName(), trelloBoardDtoList.get(0).getName());
        assertEquals(trelloBoardList.get(1).getName(), trelloBoardDtoList.get(1).getName());
        assertEquals(trelloBoardList.get(0).getLists().size(), trelloBoardDtoList.get(0).getLists().size());
        assertEquals(trelloBoardList.get(1).getLists().size(), trelloBoardDtoList.get(1).getLists().size());
    }

    @Test
    public void shouldMapToLists() {
        //Given
        TrelloListDto trelloListDto1 = new TrelloListDto("id1", "name1", true);
        TrelloListDto trelloListDto2 = new TrelloListDto("id2", "name2", false);
        List<TrelloListDto> listsDto = new ArrayList<>();
        listsDto.add(trelloListDto1);
        listsDto.add(trelloListDto2);
        //When
        List<TrelloList> trelloLists = trelloMapper.mapToLists(listsDto);
        //Then
        assertEquals(listsDto.size(), trelloLists.size());
        assertEquals(listsDto.get(0).getId(), trelloLists.get(0).getId());
        assertEquals(listsDto.get(1).getId(), trelloLists.get(1).getId());
        assertEquals(listsDto.get(0).getName(), trelloLists.get(0).getName());
        assertEquals(listsDto.get(1).getName(), trelloLists.get(1).getName());
        assertTrue(trelloLists.get(0).isClosed());
        assertFalse(trelloLists.get(1).isClosed());
    }

    @Test
    public void shouldMapToListsDto() {
        //Given
        TrelloList trelloList1 = new TrelloList("id1", "name1", true);
        TrelloList trelloList2 = new TrelloList("id2", "name2", false);
        List<TrelloList> lists = new ArrayList<>();
        lists.add(trelloList1);
        lists.add(trelloList2);
        //When
        List<TrelloListDto> listsDto = trelloMapper.mapToListsDto(lists);
        //Then
        assertEquals(lists.size(), listsDto.size());
        assertEquals(lists.get(0).getId(), listsDto.get(0).getId());
        assertEquals(lists.get(1).getId(), listsDto.get(1).getId());
        assertEquals(lists.get(0).getName(), listsDto.get(0).getName());
        assertEquals(lists.get(1).getName(), listsDto.get(1).getName());
        assertTrue(listsDto.get(0).isClosed());
        assertFalse(listsDto.get(1).isClosed());
    }

    @Test
    public void mapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("name", "desc", "pos", "listId");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals(trelloCard.getName(), trelloCardDto.getName());
        assertEquals(trelloCard.getDescription(), trelloCardDto.getDescription());
        assertEquals(trelloCard.getPos(), trelloCardDto.getPos());
        assertEquals(trelloCard.getListId(), trelloCardDto.getListId());
    }

    @Test
    public void mapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name", "desc", "pos", "listId");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals(trelloCardDto.getName(), trelloCard.getName());
        assertEquals(trelloCardDto.getDescription(), trelloCard.getDescription());
        assertEquals(trelloCardDto.getPos(), trelloCard.getPos());
        assertEquals(trelloCardDto.getListId(), trelloCard.getListId());
    }
}