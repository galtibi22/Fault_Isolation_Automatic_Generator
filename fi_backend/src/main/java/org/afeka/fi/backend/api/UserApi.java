package org.afeka.fi.backend.api;

import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.exception.*;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.http.GeneralResponse;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.repository.UserRepository;
import org.afeka.fi.backend.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
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

    private EntityService<User> userService;

    public void init(EntityService<User> userService){
        this.userService=userService;
    }

    @PostMapping(path="/register", produces = "application/json",headers = HttpHeaders.AUTHORIZATION)
    public User registerUser(HttpServletRequest request,@RequestBody User user) throws Exception {
        logger.called("registerUser","user",user);
        securityCheck(request, Role.admin);
        return userService.add(user);

    }
    @PostMapping(path="/update", produces = "application/json",headers = HttpHeaders.AUTHORIZATION)
    public User updateUser(HttpServletRequest request,@RequestBody User user) throws Exception {
        logger.called("updateUser","user",user);
        securityCheck(request, Role.admin);
        return userService.update(user);
    }

    @DeleteMapping(path="/delete/{userName}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public GeneralResponse deleteUser(HttpServletRequest request, @PathVariable String userName) throws Exception {
        logger.called("deleteUser","userName",userName);
        securityCheck(request, Role.admin);
        userService.delete(userName);
        return new GeneralResponse("Success to delete user "+userName);
    }

    @GetMapping(path="/getAll",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public List<User> getAllUsers(HttpServletRequest request) throws Exception {
        logger.called("getAllUsers","","");
        securityCheck(request, Role.admin);
        return  userService.getAll("");
    }
    @GetMapping(path="/get/{userName}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public User getUser(HttpServletRequest request,@PathVariable String userName) throws Exception {
        logger.called("getUser","user","");
        securityCheck(request, Role.admin);
        return userService.get(userName);
    }

    @PostMapping(path="/login",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public ResponseEntity login(HttpServletRequest request) throws Exception {
        User user=securityCheck(request, Role.admin,Role.user,Role.viewer,Role.generator,Role.provider);
        logger.called("login", "user",user);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,request.getHeader(HttpHeaders.AUTHORIZATION)).body(user);
    }
}
