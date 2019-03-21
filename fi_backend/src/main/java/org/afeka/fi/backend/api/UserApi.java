package org.afeka.fi.backend.api;

import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.http.GeneralResponse;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@RequestMapping(path = "api/user")
@RestController
public class UserApi extends CommonApi {
    @Autowired
    UserRepository userRepository;

    @PostMapping(path="/register", produces = "application/json",headers = HttpHeaders.AUTHORIZATION)
    public GeneralResponse registerUser(HttpServletRequest request,@RequestBody User user) throws Exception {
        securityCheck(request, Role.admin);
        logger.start("register rest api for user "+user);
        if (user.role.equals(Role.admin))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add user with Role Admin to the system");
        if (userRepository.findById(user.userName).isPresent())
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User "+user.userName +" already exist in the system");
        userRepository.save(user);
        logger.finish("register rest api for user "+user);

        return new GeneralResponse("Success to register user "+user.userName);
    }

    @DeleteMapping(path="/delete/{userName}",headers = HttpHeaders.AUTHORIZATION)
    public GeneralResponse delete(HttpServletRequest request, @PathVariable String userName) throws Exception {
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
    /**
     *
     */
 /*   @PostMapping(path="/login",produces = "application/x-www-form-urlencoded")
    public Object login(HttpServletRequest request, @ModelAttribute("user") User user) throws Exception {
        logger.called("login", "user", "email " + user.email, "password " + user.password);
        Optional<User> userFound = userRepository.findById(user.email);
        if (!userFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User with "+user.email +" not registered to the system");
        }
        if (!userFound.get().password.equals(user.password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED," Incorrect password for user "+user.email +" ");
        }
        return new RedirectView("/app/dashboard.html");
    }*/
    @PostMapping(path="/login",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public GeneralResponse login(HttpServletRequest request) throws Exception {
        User user=securityCheck(request, Role.admin);
        logger.called("login", "user",user);
        if (user.role.equals(Role.admin))
            return new GeneralResponse(Role.admin.name());
        if (user.role.equals(Role.user))
            return new GeneralResponse(Role.user.name());
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"App for role "+user.role+ "  not implement yet");
    }
}
