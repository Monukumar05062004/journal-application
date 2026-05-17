package net.engineeringdigest.journalApp.controller;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;
    @GetMapping
    public List<User> getAllUsers(){
       return  userService.getAll();
    }
//    @GetMapping("{Password}")
//    public Optional<User> getUserByPassword(@RequestBody String password){
//        return  userService.findUserByPassword(password);
//    }
@PutMapping
public ResponseEntity<?> updateUser(@RequestBody User user) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();

    User userInDb = userService.findByUserName(userName);

    if (userInDb == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User not found");
    }

    // Update fields
    userInDb.setUserName(user.getUserName());

    // Encode password before saving
    userInDb.setPassword((user.getPassword()));

    userService.updateUser(userInDb);

    return ResponseEntity.ok("User updated successfully");
}
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody User user){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        System.out.println("Username: " + user.getUserName());
        System.out.println("Password: " + user.getPassword()); // Only for debugging

        User existingUser = userService.findByUserName(user.getUserName());
        if (existingUser != null) {
            System.out.println("Stored password: " + existingUser.getPassword());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(user.getPassword(), existingUser.getPassword())) {
                return new ResponseEntity<>("Login Successful", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }




}
