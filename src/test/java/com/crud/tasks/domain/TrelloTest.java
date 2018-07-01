package com.crud.tasks.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrelloTest {

    @Test
    public void testGetBoard() {
        Trello trello = new Trello(0,0);
        int boardNo = trello.getBoard();
        assertEquals(0, boardNo);
    }

    @Test
    public void getCard() {
        Trello trello = new Trello(0,0);
        int cardNo = trello.getCard();
        assertEquals(0, cardNo);
    }
}