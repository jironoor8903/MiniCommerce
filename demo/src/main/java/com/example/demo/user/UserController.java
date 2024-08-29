package com.example.demo.user;

import com.example.demo.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.product.Product;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-username")
    public Optional<User> getUsersByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/by-email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody SignUpRequest signupRequest) {
        try {
            User user = new User();
            user.setUsername(signupRequest.getUsername());
            user.setEmail(signupRequest.getEmail());
            user.setPasswordHash(signupRequest.getPassword());
            User newUser = userService.signUpUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Object> signIn(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        Optional<User> optionalUser = userService.getUserByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (userService.checkPassword(password, user.getPasswordHash())) {
                return new ResponseEntity<>(Map.of("message", "Sign-in successful"), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("error", "Invalid username or password"), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/add-to-balance")
    public ResponseEntity<Object> addToBalance(@RequestBody AddBalanceRequest request) {
        try {
            User updatedUser = userService.addToBalance(request);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/buy-product")
    public ResponseEntity<Object> buyProduct(@RequestBody BuyProductRequest request) {
        try {
            Product purchasedProduct = userService.buyProduct(request);
            return new ResponseEntity<>(purchasedProduct, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/sell-product")
    public ResponseEntity<Object> sellProduct(@RequestBody SellProductRequest request) {
        try {
            Product soldProduct = userService.sellProduct(request);
            return new ResponseEntity<>(soldProduct, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
