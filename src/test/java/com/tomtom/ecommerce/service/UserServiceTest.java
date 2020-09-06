package com.tomtom.ecommerce.service;

import com.tomtom.ecommerce.bean.person.User;
import com.tomtom.ecommerce.dao.UserRepository;
import com.tomtom.ecommerce.entity.UserEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private static final Long USER_ID = 123L;


    @Test
    public void testGetAllUser() {
        List<UserEntity> entities = new LinkedList<>();
        entities.add(getDummyUserEntity());
        Mockito.when(userRepository.findAll()).thenReturn(entities);
        List<User> response = userService.getAllUsers();
        Assert.assertFalse(response.isEmpty());
        Assert.assertEquals(response.get(0).getId(), entities.get(0).getId());
    }


    @Test
    public void testNoUserPresent(){
        Mockito.when(userRepository.findAll()).thenReturn(new LinkedList<>());
        List<User> response = userService.getAllUsers();
        Assert.assertTrue(response.isEmpty());
    }

    @Test
    public void testCreateNewUser(){
        userService.saveOrUpdateUser(getDummyUserBean());
        Mockito.verify(userRepository,Mockito.times(1)).save(Mockito.any(UserEntity.class));
    }

    @Test
    public void testGetUserIfPresent(){
        UserEntity userEntity = getDummyUserEntity();
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(userEntity));
        User user = userService.getUser(USER_ID);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getId(),userEntity.getId());
    }

    @Test
    public void testGetUserIfNotPresent(){
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
        User user = userService.getUser(USER_ID);
        Assert.assertNull(user);

    }

    private UserEntity getDummyUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setName("Test");
        entity.setStatus(true);
        return entity;
    }


    private User getDummyUserBean() {
        User entity = new User();
        entity.setId(1L);
        entity.setName("Test");
        return entity;
    }

}
