package com.tomtom.ecommerce.service;

import com.tomtom.ecommerce.bean.person.User;
import com.tomtom.ecommerce.dao.UserRepository;
import com.tomtom.ecommerce.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUsers() {
        List<UserEntity> entities = userRepository.findAll();
        if (entities != null || entities.isEmpty()) {
            return entities.stream().map(entity -> {
                User user = new User();
                user.setId(entity.getId());
                user.setName(entity.getName());
                return user;
            }).collect(Collectors.toList());
        }
        return new ArrayList<>(0);
    }

    public void saveOrUpdateUser(User user) {
        UserEntity entity = new UserEntity();
        entity.setName(user.getName());
        entity.setStatus(Boolean.TRUE);
        userRepository.save(entity);
    }

    public User getUser(Long userId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        if (optionalUserEntity.isPresent()) {
            User user = new User();
            user.setName(optionalUserEntity.get().getName());
            user.setId(optionalUserEntity.get().getId());
            return user;
        }
        return null;
    }
}
