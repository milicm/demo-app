/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mm.app.repository;

import com.mm.app.model.User;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Milos
 */
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveTest() {
        User user = userRepository.save(new User("UR_1", "UR_1", "ur1@mail.com", "ur1"));
        Assertions.assertNotNull(user);
    }

    @Test
    public void deleteTest() {
        User user = userRepository.save(new User("UR_2", "UR_2", "ur2@mail.com", "ur2"));
        Assertions.assertNotNull(user);
        userRepository.delete(user);
        Optional<User> u = userRepository.findById(user.getId());
        Assertions.assertFalse(u.isPresent());
    }

    @Test
    public void findByEmailTest() {
        User user = userRepository.save(new User("UR_3", "UR_3", "ur3@mail.com", "ur3"));
        Assertions.assertNotNull(user);
        Optional<User> u = userRepository.findByEmail(user.getEmail());
        Assertions.assertTrue(u.isPresent());
        Assertions.assertEquals(user, u.get());
    }

}
