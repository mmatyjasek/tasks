package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.exception.NotFoundEntityException;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbService {

    private static final Long ID = 1L;
    @Autowired
    private TaskRepository repository;

    public List<Task> getAllTasks(){
        return repository.findAll();
    }

    public Task getTask() {return repository.findById(ID).orElseThrow(() -> new NotFoundEntityException("Could not found: " + ID));}
}
