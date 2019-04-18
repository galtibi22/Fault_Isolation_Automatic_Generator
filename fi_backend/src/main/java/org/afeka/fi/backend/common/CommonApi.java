package org.afeka.fi.backend.common;

import org.afeka.fi.backend.clients.FiGeneratorClient;
import org.afeka.fi.backend.clients.OcrClient;
import org.afeka.fi.backend.factory.FiFactory;
import org.afeka.fi.backend.factory.NdFactory;
import org.afeka.fi.backend.factory.NdParentFactory;
import org.afeka.fi.backend.factory.TreFactory;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.repository.RepositoryService;
import org.afeka.fi.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@Service

public class CommonApi extends FiCommon{
    @Autowired
    protected ApplicationContext context;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    protected FiFactory fiFactory;
    @Autowired
    protected NdFactory ndFactory;
    @Autowired
    protected NdParentFactory ndParentFactory;
    @Autowired
    protected TreFactory treFactory;

    protected OcrClient ocrClient;
    protected FiGeneratorClient fiGenerator;


    @Autowired
    public void setOcrClient(@Value("${ocrprovider}") String ocrprovider) {
        ocrClient= (OcrClient) context.getBean(ocrprovider);
    }
    @Autowired
    public void setFiGeneratorClient(@Value("${figenerator}") String figenerator) {
        fiGenerator= (FiGeneratorClient) context.getBean(figenerator);
    }

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
/*
    public String getOcrclass() {
        return ocrclass;
    }

    public void setOcrclass(String ocrclass) {
        this.ocrclass = ocrclass;
    }*/
}
