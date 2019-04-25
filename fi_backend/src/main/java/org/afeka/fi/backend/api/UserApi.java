package org.afeka.fi.backend.api;

import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.http.GeneralResponse;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RequestMapping(path = "api/user")
@RestController
public class UserApi extends CommonApi {
    @Autowired
    UserRepository userRepository;

    @PostMapping(path="/register", produces = "application/json",headers = HttpHeaders.AUTHORIZATION)
    public User registerUser(HttpServletRequest request,@RequestBody User user) throws Exception {
        logger.called("registerUser","user",user);
        securityCheck(request, Role.admin);
        if (user.role.equals(Role.admin))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add user with Role Admin to the system");
        if (userRepository.findById(user.userName).isPresent())
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User "+user.userName +" already exist in the system");
        return userRepository.save(user);

    }
    @PostMapping(path="/update", produces = "application/json",headers = HttpHeaders.AUTHORIZATION)
    public User updateUser(HttpServletRequest request,@RequestBody User user) throws Exception {
        logger.called("updateUser","user",user);
        securityCheck(request, Role.admin);
        if (user.role.equals(Role.admin))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update user with Role Admin");
        if (!userRepository.findById(user.userName).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User "+user.userName +" is not exist in the system. Please user register api");
        return userRepository.update(user);
    }

    @DeleteMapping(path="/delete/{userName}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public GeneralResponse deleteUser(HttpServletRequest request, @PathVariable String userName) throws Exception {
        logger.called("deleteUser","userName",userName);
        securityCheck(request, Role.admin);
        Optional<User> userToDelete=userRepository.findById(userName);
        if (!userToDelete.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User "+userName +" not exist in the system");
        if (userToDelete.get().role.equals(Role.admin))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete user with Role Admin from the system");
        userRepository.deleteById(userName);
        return new GeneralResponse("Success to delete user "+userName);
    }

    @GetMapping(path="/getAll",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public List<User> getAllUsers(HttpServletRequest request) throws Exception {
        logger.called("getAllUsers","","");
        securityCheck(request, Role.admin);
        return  userRepository.findAll();
    }
    @GetMapping(path="/get/{userName}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public User getUser(HttpServletRequest request,@PathVariable String userName) throws Exception {
        logger.called("getUser","user","");
        securityCheck(request, Role.admin);
        Optional<User> user= userRepository.findById(userName);
        if (!user.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User "+userName +" not exist in the system");
        return user.get();
    }


    //@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    @PostMapping(path="/login",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public ResponseEntity login(HttpServletRequest request) throws Exception {
        User user=securityCheck(request, Role.admin,Role.user,Role.viewer,Role.generator,Role.provider);
        logger.called("login", "user",user);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,request.getHeader(HttpHeaders.AUTHORIZATION)).body(user);
    }
}
