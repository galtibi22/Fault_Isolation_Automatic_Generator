package org.afeka.fi.backend.api;

import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.exception.FileNotSupportExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.pojo.commonstructure.TRE;
import org.afeka.fi.backend.pojo.fiGenerator.FiGeneratorType;
import org.afeka.fi.backend.pojo.http.GeneralResponse;
import org.afeka.fi.backend.pojo.commonstructure.NdParent;
import org.afeka.fi.backend.pojo.http.ViewCreateRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RequestMapping(path = "api/fronted")
@RestController
public class FrontedApi extends CommonApi {
    @PostMapping(value = "/tre/new",produces = "application/json")
    public TRE newTre(HttpServletRequest request,@RequestBody  ViewCreateRequest viewCreateRequest) {
        logger.called("newTre","viewCreateRequest",viewCreateRequest);
        User user= securityCheck(request,Role.user);
        return repositoryService.add(treFactory.newTRE(user.userName,viewCreateRequest));
    }

    @PostMapping(value = "/fi/new/{ndId}/{type}",headers = HttpHeaders.AUTHORIZATION)
    public GeneralResponse fiNew(HttpServletRequest request, @RequestBody MultipartFile fiDoc, @PathVariable String ndId,@PathVariable String type) {
        try {
            securityCheck(request, Role.user);
            logger.called("fiNew", "ndId " + ndId + " and fiDoc", fiDoc.getName());
            repositoryService.findNd(ndId);
            fiGeneratorClient.fiDocumentValidator(fiDoc);
            fiGeneratorClient.executeFiGenerator(fiDoc, ndId,FiGeneratorType.valueOf(type) );
            return new GeneralResponse("Success to execute FiGeneratorClient for with new fiDoc " + fiDoc.getName() + " for ndId" + ndId);
        }catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fiDoc not exist in the request or the key name is not fiDoc", e);
        }
        catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ND with id +" + ndId + " not found", e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Cannot find fiGeneratorClient type " +type, e);
        } catch (FileNotSupportExption fx) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fiDoc type " + fiDoc.getName() + " not supported", fx);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot Start fi generator client" +e);

        }
    }

    @PostMapping(value = "/nd/new/{ndParentId}",produces = "application/json",headers = HttpHeaders.AUTHORIZATION)
    public NdParent ndNew(HttpServletRequest request,@RequestBody ViewCreateRequest viewCreateRequest, @PathVariable String ndParentId) {
        try {
            logger.called("ndNew","viewCreateRequest",viewCreateRequest);
            securityCheck(request,Role.user);
            NdParent ndParent = repositoryService.findNdParent(ndParentId);
            repositoryService.add(ndFactory.newND(viewCreateRequest, ndParentId));
            return repositoryService.getNdParent(ndParentId);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NdParent with id +" + ndParentId + " not found", e);
        }
    }

    @PostMapping(value = "/ndparent/new/{treId}",produces = "application/json")
    public TRE ndParentNew(HttpServletRequest request,@RequestBody ViewCreateRequest viewCreateRequest, @PathVariable String treId) {
        try {
            logger.called("ndParentNew","viewCreateRequest",viewCreateRequest);
            securityCheck(request,Role.user);
            repositoryService.findTre(treId);
            repositoryService.add(ndParentFactory.newNdParent(viewCreateRequest, treId));
            return repositoryService.getTre(treId);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TRE with id +" + treId + " not found", e);
        }
    }

    @GetMapping(value = "/tre/{treId}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public TRE getTre(HttpServletRequest request, @PathVariable String treId) {
        try {
            logger.called("getTre","treId",treId);
            securityCheck(request,Role.user);
            return repositoryService.getTre(treId);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TRE with id +" + treId + " not found", e);
        }
    }
    @GetMapping(value = "/tre/",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public List<TRE> getTres(HttpServletRequest request) {
        try {
            logger.called("getTres","","");
            securityCheck(request,Role.user);
            return repositoryService.getTres();
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TRES", e);
        }
    }

    @GetMapping(value = "/ndparent/{ndParentId}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public NdParent getNdParent(HttpServletRequest request, @PathVariable String ndParentId) {
        try {
            logger.called("getNdParent","ndParentId",ndParentId);
            securityCheck(request,Role.user);
            return repositoryService.getNdParent(ndParentId);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NdParent with id +" + ndParentId + " not found", e);
        }
    }

    @GetMapping(value = "/nd/{ndId}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public ND getNd(HttpServletRequest request, @PathVariable String ndId) {
        try {
            logger.called("getNd","ndId",ndId);
            securityCheck(request,Role.user,Role.viewer);
            return repositoryService.getNd(ndId);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ND with id +" + ndId + " not found", e);
        }
    }

    @GetMapping(value = "/fi/{fiId}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public FI getFi(HttpServletRequest request, @PathVariable String fiId) {
        try {
            logger.called("getFi","fiId",fiId);

            securityCheck(request,Role.user,Role.viewer);
            return repositoryService.getFi(fiId);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FI with id +" + fiId + " not found", e);
        }
    }




}