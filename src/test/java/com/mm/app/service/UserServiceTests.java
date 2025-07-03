/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mm.app.service;

import com.mm.app.exception.EntityExistsException;
import com.mm.app.exception.InvalidEntityException;
import com.mm.app.model.User;
import com.mm.app.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 *
 * @author Milos
 */
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void saveSuccessTest() throws EntityExistsException {
        User user = new User(1L, "US_1", "US_1", "us1@mail.com", "urs");
        // when .. then
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User u = userService.save(user);
        Assertions.assertNotNull(u);
        Assertions.assertEquals(user, u);
    }

    @Test
    public void saveFailureTest() {
        User user = new User(2L, "US_2", "US_2", "us2@mail.com", "urs2");
        // when .. then
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Assertions.assertThrows(EntityExistsException.class, () -> {
            userService.save(user);
        });
    }

    @Test
    public void deleteSuccessTest() throws InvalidEntityException {
        User user = new User(3L, "US_3", "US_3", "us3@mail.com", "us3");
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.deleteById(user.getId());
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(user.getId());
    }

    @Test
    public void deleteFailureTest() throws InvalidEntityException {
        User user = new User(3L, "US_4", "US_4", "us4@mail.com", "us4");
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(InvalidEntityException.class, () -> {
            userService.deleteById(user.getId());
        });
    }
}
