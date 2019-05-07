package org.afeka.fi.backend.api;

import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.exception.*;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.commonstructure.*;
import org.afeka.fi.backend.pojo.http.GeneralResponse;
import org.afeka.fi.backend.services.EntityService;
import org.afeka.fi.backend.services.FiServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@RequestMapping(path = "api/fronted/fi")
@RestController
public class FiApi extends CommonApi {

    private FiServiceInterface fiService;
    private EntityService<FiDoc> fiDocService;

    @Autowired
    public void init(FiServiceInterface fiService, EntityService<FiDoc> fiDocService){
        this.fiService=fiService;
        this.fiDocService=fiDocService;
    }
    @PostMapping(value = "/new/{ndId}/{type}",headers = HttpHeaders.AUTHORIZATION)
    public GeneralResponse newFi(HttpServletRequest request, @RequestBody MultipartFile fiDoc, @PathVariable String ndId,@PathVariable String type) throws Exception{
            securityCheck(request, Role.user);
            logger.called("newFiApi", "ndId " + ndId + " and fiDoc", fiDoc.getOriginalFilename());
            fiService.newFi(fiDoc,ndId,type);
            return new GeneralResponse("Success to execute FiGeneratorClient for with new fiDoc " + fiDoc.getOriginalFilename() + " for ndId " + ndId);
        }

    @GetMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public FI getFi(HttpServletRequest request, @PathVariable String id) throws ResourceNotFoundException {
            logger.called("getFiApi","fiId",id);
            securityCheck(request,Role.user,Role.viewer);
            return fiService.get(id);
    }

    @DeleteMapping(value = "{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public GeneralResponse deleteFi(HttpServletRequest request, @PathVariable String id) throws ResourceNotFoundException, DeleteEntityExption {
            logger.called("deleteFiApi","id",id);
            securityCheck(request,Role.user);
            fiService.delete(id);
            return new GeneralResponse("success to delete fi with id"+ id);

    }

    @PutMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public FI updateFi(HttpServletRequest request, @RequestBody FI fi, @PathVariable String id) throws ResourceNotFoundException {
            securityCheck(request, Role.user);
            logger.called("updateFiApi", "FI",fi);
            return fiService.update(fi);

    }

    @GetMapping(value = "/{fiId}/fidoc",headers = HttpHeaders.AUTHORIZATION)
    public ResponseEntity<Resource> getFiDoc(HttpServletRequest request, @PathVariable String fiId) throws ResourceNotFoundException, IOException {
            logger.called("getFiDocApi","fiId",fiId);
            securityCheck(request,Role.user,Role.viewer);
            FiDoc fiDoc=fiDocService.get(fiId);
            return initFileResponse(new MockMultipartFile(fiDoc.name,fiDoc.doc));

    }


}