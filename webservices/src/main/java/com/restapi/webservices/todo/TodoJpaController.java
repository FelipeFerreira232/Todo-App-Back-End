package com.restapi.webservices.todo;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TodoJpaController {
	
	//private TodoService todoService;
	
	private TodoRepository todoRepository;
	
	public TodoJpaController(/*TodoService todoService, TodoRepositoryStatic todoRepository */ 
			TodoRepository todoRepository) {
		//this.todoService = todoService;
		this.todoRepository = todoRepository;
	}
	
	@GetMapping("/users/{username}/todos")
	public List<Todo> retrieveTodos(@PathVariable String username) {
		//return todoService.findByUsername(username);
		return todoRepository.findByUsername(username);
	}

	@GetMapping("/users/{username}/todos/{id}")
	public Optional<Todo> retrieveTodo(@PathVariable String username,
			@PathVariable int id) {
		//return todoService.findById(id);
		return todoRepository.findById(id);
	}

	@DeleteMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username,
			@PathVariable int id) {
		//todoService.deleteById(id);
		todoRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/users/{username}/todos/{id}")
	public Todo updateTodo(@PathVariable String username,
			@PathVariable int id, @RequestBody Todo todo) {
		//todoService.updateTodo(todo);
		todoRepository.deleteById(id);
		return todoRepository.save(todo);
	}

	@PostMapping("/users/{username}/todos")
	public Todo createTodo(@PathVariable String username,
			 @RequestBody Todo todo) {
		todo.setUsername(username);
		todo.setId(null);
		return todoRepository.save(todo);
	}

}
