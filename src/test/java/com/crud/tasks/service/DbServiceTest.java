package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void shouldReturnListOfTasks() {
        //Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "Task 1", "Test task 1"));
        tasks.add(new Task(2L, "Task 2", "Test task 2"));
        when(taskRepository.findAll()).thenReturn(tasks);
        //When
        List<Task> retrievedTasks = dbService.getAllTasks();
        //Then
        assertEquals(tasks, retrievedTasks);
    }

    @Test
    public void shouldReturnOptionalTask() {
        //Given
        Task task = new Task(1L, "Task 1", "Test task 1");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        //When
        Task retrievedTask = dbService.getTask(1L).get();
        //Then
        assertEquals(task, retrievedTask);
    }

    @Test
    public void shouldReturnTask() {
        //Given
        Task task = new Task(1L, "Task 1", "Test task 1");
        when(taskRepository.save(task)).thenReturn(task);
        //When
        Task returnedTask = dbService.saveTask(task);
        //Then
        assertEquals(task, returnedTask);

    }
}