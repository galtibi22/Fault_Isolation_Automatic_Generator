package org.afeka.fi.backend.common;

import org.afeka.fi.backend.clients.FiGeneratorClient;
import org.afeka.fi.backend.clients.OcrAbbyyCloud;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
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

    protected OcrClient ocrClient=new OcrAbbyyCloud();
    protected FiGeneratorClient fiGenerator;


 /*   @Autowired
    public void setOcrClient(@Value("${ocrprovider}") String ocrprovider) {
        ocrClient= (OcrClient) context.getBean(ocrprovider);
    }*/
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

    protected ResponseEntity<Resource> initFileResponse(MultipartFile file) throws IOException {
        String extension=Helpers.getFileExtension(file);
        MediaType mediaType = null;
        if (extension.equals("doc") ||extension.equals("docx"))
            mediaType=MediaType.parseMediaType("application/msword");
        else if( extension.equals("pdf"))
            mediaType=MediaType.APPLICATION_PDF;
        else if (extension.equals("zip"))
            mediaType=MediaType.APPLICATION_OCTET_STREAM;
        return ResponseEntity.ok().header("Content-Disposition", "attachment;filename="+file.getName())
                .contentType(mediaType)
                .contentLength(file.getSize())
                .body(file.getResource());
    }
    protected ResponseEntity<Resource> initFileResponse(Path path) throws IOException {
        InputStream inputStream= new FileInputStream(path.toFile());
        return initFileResponse(new MockMultipartFile(path.getFileName().toString(),inputStream));
    }
/*
    public String getOcrclass() {
        return ocrclass;
    }

    public void setOcrclass(String ocrclass) {
        this.ocrclass = ocrclass;
    }*/
}
