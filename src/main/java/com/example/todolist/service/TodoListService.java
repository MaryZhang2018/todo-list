package com.example.todolist.service;

import com.example.todolist.entity.TodoItem;
import com.example.todolist.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class TodoListService {
    @Autowired
    TodoItemRepository todoItemRepository;

    /**
     * It creates a todo item per the provided title and description and persists in configured database
     *
     * @param todoItem the todo item with title and description to be created
     * @return TodoItem saved in database with generated id
     */
    public TodoItem createTodoItem(TodoItem todoItem) {
        if(null == todoItem || !StringUtils.hasText(todoItem.getTitle())) { //assume description optional
            throw new IllegalArgumentException("Invalid to do item or title is missing");
        }

        TodoItem itemCreated = todoItemRepository.save(todoItem);
        // switch to logger later
        if(null != itemCreated && null != itemCreated.getId()) {
            System.out.println("The created TodoItem has auto-generated id " + itemCreated.getId());
        } else {
            System.out.println("Error: the created TodoItem is null OR w/o auto-generated id");
        }

        return itemCreated;
    }

    public List<TodoItem> getTodoList() {
        List<TodoItem> listReturned = todoItemRepository.findAll();

        if(CollectionUtils.isEmpty(listReturned)) {
            System.out.println("Todo list returned is empty");
        } else {
            System.out.println(listReturned.size() + " todo items found in the list");
        }

        return listReturned;
    }

    public Optional<TodoItem> getTodoItem(Long id) {
        if(null == id || id < 1) {
            throw new IllegalArgumentException("The id is invalid");
        }

        Optional<TodoItem> itemReturned = todoItemRepository.findById(id);
        if(itemReturned.isPresent()) {
            System.out.println("Found TodoItem with id " + id);
        } else {
            System.out.println("Not Found TodoItem with id " + id);
        }

        return itemReturned;
    }

    public Optional<TodoItem> updateTodoItem(TodoItem item) {
        if(null == item) {
            throw new IllegalArgumentException("Error: the todo item is null");
        }

        Long id = item.getId();
        if(null == id || id < 1) {
            throw new IllegalArgumentException("The id of todo item is invalid");
        }

        Optional<TodoItem> itemUpdated = Optional.empty();
        if(todoItemRepository.existsById(id)) {
            System.out.println("Found TodoItem with id " + id);
            itemUpdated = Optional.ofNullable(todoItemRepository.save(item));
        } else {
            System.out.println("Provided todo item not found");
        }

        return itemUpdated;
    }

    public void deleteTodoItem(Long id) {
        if(null == id || id < 1) {
            throw new IllegalArgumentException("The id is invalid");
        }

        try {
            todoItemRepository.deleteById(id);
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getClass().getSimpleName() + " happened");
            throw ex;
        }
    }
}
