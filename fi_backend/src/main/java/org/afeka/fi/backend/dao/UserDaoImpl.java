package org.afeka.fi.backend.dao;

import org.afeka.fi.backend.exception.AddEntityExption;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.pojo.http.ViewCreateRequest;
import org.afeka.fi.backend.repository.UserRepository;
import org.afeka.fi.backend.services.EntityService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements EntityDao<User> {
    private UserRepository userRepository;

    @Autowired
    public void init(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public User update(User user) throws ResourceNotFoundException {
        find(user.userName);
        return userRepository.update(user);
    }

    @Override
    public void delete(String id) throws ResourceNotFoundException, DeleteEntityExption {
        userRepository.deleteById(id);
    }

    @Override
    public User get(String id) throws ResourceNotFoundException {
        return find(id);
    }

    @Override
    public User find(String id) throws ResourceNotFoundException {
        return userRepository.find(id);
    }

    @Override
    public List<User> getAll(String parentId) throws ResourceNotFoundException {
        return userRepository.findAll();
    }

    @Override
    public User add(User user) throws AddEntityExption {
        if(userRepository.findById(user.userName).isPresent()){
            throw new AddEntityExption("user "+user.userName +" already exist");
        }
        return userRepository.save(user);
    }
}
