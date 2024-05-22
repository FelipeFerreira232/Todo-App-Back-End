package com.restapi.webservices.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDAOService {
    private static List<User> users = new ArrayList<>();
    
    private static int usersCount = 0;
    
    //Find all users
    static {
        users.add(new User(++usersCount, "Felipe", LocalDate.of(1996, 4, 3)));
        users.add(new User(++usersCount, "Java", LocalDate.of(1995, 5, 1)));
        users.add(new User(++usersCount, "Spring", LocalDate.of(2002, 10, 1)));
    }

    public List<User> findAll(){
        return users;
    }

    public User findOne(int id) {
        Predicate<? super  User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }
    
    public User createUser(User user) {
    	user.setId(++usersCount);
    	users.add(user);
    	return user;
    }

	public void deleteUser(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		users.removeIf(predicate);
	}
}
