package com.restapi.webservices.user;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class UserResource {
	private final UserDAOService userDAOService;

	public UserResource(UserDAOService userDAOService) {
		this.userDAOService = userDAOService;
	}

	@GetMapping("/users")
	public List<User> getUsers() {
		return userDAOService.findAll();
	}

	@GetMapping("/users/{id}")
	public EntityModel<User> findById(@PathVariable int id) {
		User user = userDAOService.findOne(id);
		
		if(user == null) {
			throw new UserNotFoundException("id: " + id);
		}
		
		WebMvcLinkBuilder webMvcLinkBuilder = linkTo(methodOn(	this.getClass()).getUsers() );
		
		EntityModel<User> entityModel = EntityModel.of(user);
		entityModel.add(webMvcLinkBuilder.withRel("get-users"));
		return entityModel;
	}

	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userDAOService.createUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userDAOService.deleteUser(id);
	}

}
