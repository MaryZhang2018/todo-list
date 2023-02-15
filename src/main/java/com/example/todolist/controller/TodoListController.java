package com.example.todolist.controller;

import com.example.todolist.entity.TodoItem;
import com.example.todolist.service.TodoListService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(value = "/api/todolist")
public class TodoListController {

    @Autowired
    private TodoListService todoListService;

    /**
     * It creates a todo item per provided content and persists in configured database
     * @param todoItem the request body of todo item with title (required) and description
     * @return ResponseEntity with the created todo item or null and related HTTP status
     */
    @PostMapping
    @ResponseStatus(value=HttpStatus.CREATED)
    public TodoItem createTodoItem(@RequestBody @Valid @NotNull TodoItem todoItem) {
        TodoItem itemCreated = todoListService.createTodoItem(todoItem);
        if(null != itemCreated) {
            return itemCreated;
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create the todo item");
        }
    }

    /**
     * NOTE: pagination may be used if long list of items
     * It gets the list of all todo items
     * @return the todo list or error if empty
     */
    @GetMapping
    @ResponseStatus(value=HttpStatus.OK)
    public List<TodoItem> getTodoList() {
        List<TodoItem> todoList = todoListService.getTodoList();
        if(!CollectionUtils.isEmpty(todoList)) {
            return todoList;
        } else {
            // we can simply return empty list instead of not found exception
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The todo list is empty");
        }
    }

    /**
     * It gets an existing todo item per the id in path
     * @param id the id for the todo item
     * @return the todo item or error if not found
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(value=HttpStatus.OK)
    public TodoItem getTodoItem(@PathVariable("id") @NotNull Long id) {
        Optional<TodoItem> itemOptional = todoListService.getTodoItem(id);
        if(itemOptional.isPresent()) {
            return itemOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No todo item with provided id is found");
        }
    }

    /**
     * It updates an existing todo item of title and/or description in database
     * @param todoItem the todo item with content to be updated to
     * @return the updated todo item or error if not found
     */
    @PutMapping
    @ResponseStatus(value=HttpStatus.OK)
    public TodoItem updateTodoItem(@RequestBody @Valid @NotNull TodoItem todoItem) {
        Optional<TodoItem> itemOptional = todoListService.updateTodoItem(todoItem);
        if(itemOptional.isPresent()) {
            return itemOptional.get();
        } else {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "The provided todo item is not found");
        }
    }

    /**
     * It deletes an existing todo item per provided id
     * @param id the todo item id
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    public void deleteTodoItem(@PathVariable("id") @NotNull Long id) {
        todoListService.deleteTodoItem(id);
    }

}
