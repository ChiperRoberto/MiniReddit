package pl.coderslab.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.User;
import pl.coderslab.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    // POST /users - creează user nou
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public User createUser() {
////        User user = new User("roberto123", "email", "password", "details");
////        return userService.createUser(user);
//    }

    // GET /users/{id} - ia user după ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if(user == null) {
            throw new RuntimeException("User not found!");
        }
        return user;
    }

    // GET /users - returnează lista completă de useri
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // PUT /users/{id} - actualizează user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        updatedUser.setId(id);
        return userService.updateUser(updatedUser);
    }

    // DELETE /users/{id} - șterge user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}