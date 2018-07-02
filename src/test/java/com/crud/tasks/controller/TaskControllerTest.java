package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    private DbService dbService;

    @Test
    public void shouldGetTasks() throws Exception{
        //Given
        List<TaskDto> tasksDto = new ArrayList<>();
        tasksDto.add(new TaskDto(1L, "Test task 1", "Testing"));
        when(taskMapper.mapToTaskDtoList(dbService.getAllTasks())).thenReturn(tasksDto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test task 1")))
                .andExpect(jsonPath("$[0].content", is("Testing")));
    }

    @Test
    public void shouldGetTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test task 1", "Testing");
        Task task = new Task(1L, "Test task 1", "Testing");
        when(dbService.getTask(1L)).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test task 1")))
                .andExpect(jsonPath("$.content", is("Testing")));
    }

    @Test
    public void shouldDeleteTask() throws Exception{
        mockMvc.perform(delete("/v1/task/deleteTask")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test task 1", "Testing");
        when(taskMapper.mapToTaskDto(dbService.saveTask(taskMapper.mapToTask(taskDto)))).thenReturn(taskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test task 1")))
                .andExpect(jsonPath("$.content", is("Testing")));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        TaskDto taskDto = new TaskDto(1L, "Test task 1", "Testing");
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}