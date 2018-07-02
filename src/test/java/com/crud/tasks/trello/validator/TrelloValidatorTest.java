package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloValidatorTest {

    @Autowired
    private TrelloValidator trelloValidator;

    @Test
    public void shouldFilterTestTrelloBoards() {
        //Given
        TrelloBoard trelloBoard1 = new TrelloBoard("Board1", "1", new ArrayList<>());
        TrelloBoard trelloBoard2 = new TrelloBoard("Board2 test", "2", new ArrayList<>());
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);
        //When
        List<TrelloBoard> filteredTrelloBoards = trelloValidator.validateTrelloBoards(trelloBoards);
        //Then
        assertEquals(1, filteredTrelloBoards.size());
        assertEquals(trelloBoard1, filteredTrelloBoards.get(0));

    }

    @Test
    public void shouldReturnTrueWhenNoTestInCardName() {
        TrelloCard trelloCard = new TrelloCard("Card 1", "test_card", "pos1", "1");
        boolean result = trelloValidator.validateCard(trelloCard);
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseWhenTestInCardName() {
        TrelloCard trelloCard = new TrelloCard("Card 1 test", "test_card", "pos1", "1");
        boolean result = trelloValidator.validateCard(trelloCard);
        assertFalse(result);
    }

}