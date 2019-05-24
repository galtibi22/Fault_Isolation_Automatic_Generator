package org.afeka.fi.backend.services;

import org.afeka.fi.backend.dao.EntityDao;
import org.afeka.fi.backend.dao.UserDaoImpl;
import org.afeka.fi.backend.exception.AddEntityExption;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.PermissionExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.pojo.http.ViewCreateRequest;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements EntityService<User> {


    private EntityDao<User> userDao;
    @Autowired
    public void init(EntityDao<User> userDao){
        this.userDao=userDao;
    }
    @Override
    public User add(ViewCreateRequest viewCreateRequest, String parentId) throws ResourceNotFoundException {
       throw new NotImplementedException("use add with object");
    }

    @Override
    public User update(User user) throws ResourceNotFoundException, PermissionExption {
        if (user.role.equals(Role.admin))
            throw new PermissionExption("Cannot update user with Role Admin");
        return userDao.update(user);
    }

    @Override
    public void delete(String id) throws ResourceNotFoundException, DeleteEntityExption, PermissionExption {
        User userToDelete=userDao.find(id);
        if (userToDelete.role.equals(Role.admin))
            throw new PermissionExption("Cannot delete user with Role Admin from the system");
        userDao.delete(id);
    }

    @Override
    public User get(String id) throws ResourceNotFoundException {
        return userDao.get(id);
    }

    @Override
    public User find(String id) throws ResourceNotFoundException {
        return userDao.find(id);
    }

    @Override
    public List<User> getAll(String parentId) throws ResourceNotFoundException {
        return userDao.getAll("");

    }

    @Override
    public User add(User user) throws PermissionExption, AddEntityExption {
        if (user.role.equals(Role.admin))
            throw new PermissionExption("Cannot add user with Role Admin to the system");
        return userDao.add(user);
    }
}
