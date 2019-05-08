package org.afeka.fi.backend.api;

import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.exception.*;
import org.afeka.fi.backend.factory.TreFactory;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.pojo.commonstructure.NdParent;
import org.afeka.fi.backend.pojo.commonstructure.TRE;
import org.afeka.fi.backend.pojo.http.GeneralResponse;
import org.afeka.fi.backend.pojo.http.ViewCreateRequest;
import org.afeka.fi.backend.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RequestMapping(path = "api/fronted/nd")
@RestController
public class NdApi extends CommonApi {

    private EntityService<ND> ndService;
    @Autowired
    public void init(EntityService<ND> ndService){
        this.ndService=ndService;
    }

    @PostMapping(value = "/new/{ndParentId}",produces = "application/json",headers = HttpHeaders.AUTHORIZATION)
    public ND ndNew(HttpServletRequest request,@RequestBody ViewCreateRequest viewCreateRequest, @PathVariable String ndParentId) throws ResourceNotFoundException, AddEntityExption {
        logger.called("ndNewApi","viewCreateRequest",viewCreateRequest);
        securityCheck(request,Role.user);
        return ndService.add(viewCreateRequest,ndParentId);
    }

    @GetMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public ND getNd(HttpServletRequest request, @PathVariable String id) throws ResourceNotFoundException {
        logger.called("getNdApi","ndId",id);
        securityCheck(request,Role.user,Role.viewer);
        return ndService.get(id);

    }
    @DeleteMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public GeneralResponse deleteNd(HttpServletRequest request, @PathVariable String id) throws ResourceNotFoundException, DeleteEntityExption, PermissionExption {
        logger.called("deleteNdApi","id",id);
        securityCheck(request,Role.user);
        ndService.delete(id);
        return new GeneralResponse("Success to delete nd with id "+id );
    }
    @PutMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public ND updateND(HttpServletRequest request, @RequestBody ND nd, @PathVariable String id) throws ResourceNotFoundException, PermissionExption {
        securityCheck(request, Role.user);
        logger.called("updateNDApi", "ND",nd);
        return ndService.update(nd);
    }

}
