package com.example.demo.user;

import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;

    @Autowired
    public UserService(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User signUpUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent() ||
                userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Username or email already exists");
        }

        String encodedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(encodedPassword);

        user.setBalance(0.0);
        user.setStoreCredit(0.0);

        return userRepository.save(user);
    }

    public User updateUser(Long id, User newUserDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUserDetails.getUsername());
                    user.setPasswordHash(newUserDetails.getPasswordHash());
                    user.setEmail(newUserDetails.getEmail());
                    user.setStoreCredit(newUserDetails.getStoreCredit());
                    user.setBalance(newUserDetails.getBalance());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void deleteUser(Long id) {
        boolean exists = userRepository.existsById(id);
        if (!exists) {
            throw new RuntimeException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    public User addToBalance(AddBalanceRequest request) {
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (checkPassword(request.getPassword(), user.getPasswordHash())) {
                user.setBalance(user.getBalance() + request.getAmount());
                return userRepository.save(user);
            } else {
                throw new RuntimeException("Invalid password");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Product buyProduct(BuyProductRequest request) {
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (checkPassword(request.getPassword(), user.getPasswordHash())) {
                Optional<Product> productOpt = productRepository.findById(request.getProductId());
                if (productOpt.isPresent()) {
                    Product product = productOpt.get();

                    if (product.getBoughtByUser() != null) {
                        throw new RuntimeException("Product has already been purchased by another user");
                    }

                    double resellPrice = product.getResellPrice();

                    double totalAvailable = user.getBalance() + user.getStoreCredit();
                    if (totalAvailable >= resellPrice) {
                        if (user.getStoreCredit() >= resellPrice) {
                            user.setStoreCredit(user.getStoreCredit() - resellPrice);
                        } else {
                            double remainingAmount = resellPrice - user.getStoreCredit();
                            user.setStoreCredit(0);
                            user.setBalance(user.getBalance() - remainingAmount);
                        }

                        product.setBoughtByUser(user);
                        return productRepository.save(product);
                    } else {
                        throw new RuntimeException("Insufficient funds");
                    }
                } else {
                    throw new RuntimeException("Product not found");
                }
            } else {
                throw new RuntimeException("Invalid password");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Product sellProduct(SellProductRequest request) {
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (checkPassword(request.getPassword(), user.getPasswordHash())) {
                Product product = new Product();
                product.setName(request.getProductName());
                product.setCategory(request.getProductCategory());
                product.setDescription(request.getProductDescription());
                product.setOriginalPrice(request.getOriginalPrice());
                product.setResellPrice(request.getResellPrice());
                product.setSoldByUser(user);

                productRepository.save(product);

                user.setStoreCredit(user.getStoreCredit() + request.getResellPrice());
                userRepository.save(user);

                return product;
            } else {
                throw new RuntimeException("Invalid password");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }
}