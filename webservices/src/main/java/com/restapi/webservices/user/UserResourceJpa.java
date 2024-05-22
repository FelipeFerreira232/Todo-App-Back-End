package com.restapi.webservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.restapi.webservices.post.Post;
import com.restapi.webservices.post.PostRepository;

import jakarta.validation.Valid;

@RestController
public class UserResourceJpa {
	private UserRepository userRepository;
	private PostRepository postRepository;

	public UserResourceJpa(UserRepository userRepository, PostRepository postRepository) {
		super();
		this.userRepository = userRepository;
		this.postRepository = postRepository;
		;
	}

	@GetMapping("/jpa-users")
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/jpa-users/{id}")
	public EntityModel<User> findById(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);

		if (user.isEmpty())
			throw new UserNotFoundException("id: " + id);

		WebMvcLinkBuilder webMvcLinkBuilder = linkTo(methodOn(this.getClass()).getUsers());

		EntityModel<User> entityModel = EntityModel.of(user.get());
		entityModel.add(webMvcLinkBuilder.withRel("get-users"));
		return entityModel;
	}

	@GetMapping("/jpa-users/{id}/posts")
	public List<Post> getUserPosts(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);

		if (user.isEmpty())
			throw new UserNotFoundException("id: " + id);

		return user.get().getPosts();
	}

	@PostMapping("/jpa-users/{id}/posts")
	public ResponseEntity<Object> createUserPost(@PathVariable int id, @Valid @RequestBody Post post) {
		Optional<User> user = userRepository.findById(id);

		if (user.isEmpty())
			throw new UserNotFoundException("id: " + id);
		
		post.setUser(user.get());
		
		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PostMapping("/jpa-users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/jpa-users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}
}
