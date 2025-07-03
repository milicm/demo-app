/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mm.app.service;

import com.mm.app.exception.EntityExistsException;
import com.mm.app.exception.InvalidEntityException;
import com.mm.app.model.User;
import java.util.List;

/**
 *
 * @author Milos
 */
public interface UserService {

    User save(User user) throws EntityExistsException;

    List<User> findAll();

    void deleteById(Long id) throws InvalidEntityException;
}
