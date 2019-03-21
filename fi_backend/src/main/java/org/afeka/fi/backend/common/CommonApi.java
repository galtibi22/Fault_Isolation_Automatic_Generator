package org.afeka.fi.backend.common;

import org.afeka.fi.backend.clients.FiGeneratorClientLocal;
import org.afeka.fi.backend.factory.FiFactory;
import org.afeka.fi.backend.factory.NdFactory;
import org.afeka.fi.backend.factory.NdParentFactory;
import org.afeka.fi.backend.factory.TreFactory;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.pojo.commonstructure.NdParent;
import org.afeka.fi.backend.repository.RepositoryService;
import org.afeka.fi.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

public class CommonApi extends FiCommon{

    @Autowired
    public UserRepository userRepository;
    @Autowired
    public RepositoryService repositoryService;
    @Autowired
    public FiFactory fiFactory;
    @Autowired
    public NdFactory ndFactory;
    @Autowired
    public NdParentFactory ndParentFactory;
    @Autowired
    public TreFactory treFactory;

    public FiGeneratorClientLocal fiGeneratorClient=new FiGeneratorClientLocal();


    public User securityCheck(HttpServletRequest request, Role... roles){
        logger.start("securityCheck");
        User authUser=authCheck(request);
        permissionCheck(authUser,request,roles);
        logger.finish("securityCheck");
        return authUser;
    }

    private void permissionCheck(User authUser, HttpServletRequest request, Role[] roles) {
        logger.called("permissionCheck","userRole",authUser.role+" and required roles "+ Arrays.toString(roles));
        for(Role role:roles){
            if (authUser.role.equals(role))return;
        }
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "User "+authUser.userName+ " doesn't has the " +
                "correct permissions. RoleRequired "+Arrays.toString(roles));
    }


    private User authCheck(HttpServletRequest request){
        logger.start("authCheck");
        String auth=request.getHeader(HttpHeaders.AUTHORIZATION);
        if (auth==null || !auth.toLowerCase().startsWith("basic"))
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Missing "+HttpHeaders.AUTHORIZATION);
        User actualUser=Helpers.decodeBasicAuth(auth);
        Optional<User> user=userRepository.findById(actualUser.userName);
        if (!user.isPresent())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User "+actualUser.userName+" is not authorized");
        if (!user.get().password.equals(actualUser.password))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password for user "+actualUser.userName);
        logger.finish("authCheck success for user "+user.get().userName);
        return user.get();
    }

}
