package org.afeka.fi.backend.api;

import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.exception.*;
import org.afeka.fi.backend.factory.TreFactory;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.pojo.commonstructure.*;
import org.afeka.fi.backend.pojo.fiGenerator.FiGeneratorType;
import org.afeka.fi.backend.pojo.http.GeneralResponse;
import org.afeka.fi.backend.pojo.http.ViewCreateRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RequestMapping(path = "api/fronted/fi")
@RestController
public class FiApi extends CommonApi {

    @PostMapping(value = "/new/{ndId}/{type}",headers = HttpHeaders.AUTHORIZATION)
    public GeneralResponse fiNew(HttpServletRequest request, @RequestBody MultipartFile fiDoc, @PathVariable String ndId,@PathVariable String type) throws Exception{
            securityCheck(request, Role.user);
            logger.called("fiNewApi", "ndId " + ndId + " and fiDoc", fiDoc.getOriginalFilename());
            repositoryService.findNd(ndId);
            fiGenerator.fiDocumentValidator(fiDoc);
            String fiDocId=repositoryService.add(fiDoc);
            fiGenerator.runFiGenerator(fiDoc,fiDocId,FiGeneratorType.valueOf(type),ndId);
            return new GeneralResponse("Success to execute FiGeneratorClient for with new fiDoc " + fiDoc.getOriginalFilename() + " for ndId " + ndId);
        }

    @GetMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public FI getFi(HttpServletRequest request, @PathVariable String id) throws ResourceNotFoundException {
            logger.called("getFiApi","fiId",id);
            securityCheck(request,Role.user,Role.viewer);
            return repositoryService.getFi(id);
    }

    @DeleteMapping(value = "{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public ND deleteFi(HttpServletRequest request, @PathVariable String id) throws ResourceNotFoundException, DeleteEntityExption {
            logger.called("deleteFiApi","id",id);
            securityCheck(request,Role.user);
            String parentId=repositoryService.findFI(id).ndId;
            repositoryService.deleteFi(id);
            return repositoryService.getNd(parentId);
    }

    @PutMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public FI updateFi(HttpServletRequest request, @RequestBody FI fi, @PathVariable String id) throws ResourceNotFoundException {
            securityCheck(request, Role.user);
            logger.called("updateFiApi", "FI",fi);
            repositoryService.findFI(id);
            FI fiUpdated=repositoryService.updateFi(fi);
            return Helpers.initGson().fromJson(fiUpdated.fiJson,FI.class);


    }

    @GetMapping(value = "/{fiId}/fidoc",headers = HttpHeaders.AUTHORIZATION)
    public ResponseEntity<Resource> getFiDoc(HttpServletRequest request, @PathVariable String fiId) throws ResourceNotFoundException {
            logger.called("getFiDocApi","fiId",fiId);
            securityCheck(request,Role.user,Role.viewer);
            Long fiDocId=repositoryService.getFi(fiId).fiDocId;
            logger.info("Find fiDocId "+fiDocId+" for FI with id "+fiId);
            FiDoc fiDoc=repositoryService.getFiDoc(fiDocId);
            byte[] fiDocByte=fiDoc.doc;
            InputStreamResource inputStream= new InputStreamResource(new ByteArrayInputStream(fiDoc.doc));
            return ResponseEntity.ok().header("Content-Disposition", "attachment;filename="+fiDoc.name)
                    .contentType(MediaType.parseMediaType("application/msword"))
                    .contentLength(fiDocByte.length)
                    .body(inputStream);

    }


}