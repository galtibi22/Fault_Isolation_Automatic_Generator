package org.afeka.fi.backend.api;

import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.exception.FileNotSupportExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.pojo.commonstructure.TRE;
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

@RequestMapping(path = "api/fronted")
@RestController
public class FrontedApi extends CommonApi {
    @PostMapping(value = "/tre/new",produces = "application/json")
    public TRE newTre(HttpServletRequest request) {
        User user= securityCheck(request,Role.user);
        return repositoryService.add(treFactory.newTRE(user.userName));
    }

    @PostMapping(value = "/fi/new/{ndId}",produces = "application/json",headers = HttpHeaders.AUTHORIZATION)
    public GeneralResponse fiNew(HttpServletRequest request, @RequestBody MultipartFile fiDoc, @PathVariable String ndId) {
        try {
            securityCheck(request, Role.user);
            repositoryService.findNd(ndId);
            fiGeneratorClient.fiDocumentValidator(fiDoc);
            fiGeneratorClient.executeFiGenerator(fiDoc, ndId);
            return new GeneralResponse("Success to execute FiGeneratorClient for with new fiDoc " + fiDoc.getName() + " for ndId" + ndId);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ND with id +" + ndId + " not found", e);
        } catch (IOException io) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot find fiGeneratorClient type " + fiDoc.getName(), io);
        } catch (FileNotSupportExption fx) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fiDoc type " + fiDoc.getName() + " not supported", fx);
        }
    }

    @PostMapping(value = "/nd/new/{ndParentId}",produces = "application/json",headers = HttpHeaders.AUTHORIZATION)
    public NdParent ndNew(HttpServletRequest request,@RequestBody ViewCreateRequest viewCreateRequest, @PathVariable String ndParentId) {
        try {
            securityCheck(request,Role.user);
            NdParent ndParent = repositoryService.findNdParent(ndParentId);
            repositoryService.add(ndFactory.newND(viewCreateRequest.getLbl(), ndParentId));
            return repositoryService.getNdParent(ndParentId);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NdParent with id +" + ndParentId + " not found", e);
        }
    }

    @PostMapping(value = "/ndparent/new/{treId}",produces = "application/json")
    public TRE ndParentNew(HttpServletRequest request,@RequestBody ViewCreateRequest viewCreateRequest, @PathVariable String treId) {
        try {
            securityCheck(request,Role.user);
            repositoryService.findTre(treId);
            repositoryService.add(ndParentFactory.newNdParent(viewCreateRequest.getLbl(), treId));
            return repositoryService.getTre(treId);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TRE with id +" + treId + " not found", e);
        }
    }

    @GetMapping(value = "/tre/{treId}",headers = HttpHeaders.AUTHORIZATION)
    public TRE getTre(HttpServletRequest request, @PathVariable String treId) {
        try {
            securityCheck(request,Role.user);
            return repositoryService.findTre(treId);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TRE with id +" + treId + " not found", e);
        }
    }

    @GetMapping(value = "/ndparent/{ndParentId}",headers = HttpHeaders.AUTHORIZATION)
    public NdParent getNdParent(HttpServletRequest request, @PathVariable String ndParentId) {
        try {
            securityCheck(request,Role.user);
            return repositoryService.getNdParent(ndParentId);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NdParent with id +" + ndParentId + " not found", e);
        }
    }

    @GetMapping(value = "/nd/{ndId}",headers = HttpHeaders.AUTHORIZATION)
    public ND getNd(HttpServletRequest request, @PathVariable String ndId) {
        try {
            securityCheck(request,Role.user,Role.viewer);
            return repositoryService.getNd(ndId);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ND with id +" + ndId + " not found", e);
        }
    }

    @GetMapping(value = "/fi/{fiId}",headers = HttpHeaders.AUTHORIZATION)
    public FI getFi(HttpServletRequest request, @PathVariable String fiId) {
        try {
            securityCheck(request,Role.user,Role.viewer);
            return repositoryService.getFi(fiId);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FI with id +" + fiId + " not found", e);
        }
    }




}