package org.afeka.fi.backend.api;

import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.exception.DataNotValidException;
import org.afeka.fi.backend.exception.FileNotSupportExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
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
            String fiDocId=repositoryService.save(fiDoc);
            fiGeneratorClient.executeFiGenerator(fiDoc,fiDocId,ndId,FiGeneratorType.valueOf(type) );
            return new GeneralResponse("Success to execute FiGeneratorClient for with new fiDoc " + fiDoc.getName() + " for ndId " + ndId);
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
    public ND ndNew(HttpServletRequest request,@RequestBody ViewCreateRequest viewCreateRequest, @PathVariable String ndParentId) {
        try {
            logger.called("ndNew","viewCreateRequest",viewCreateRequest);
            securityCheck(request,Role.user);
            NdParent ndParent = repositoryService.findNdParent(ndParentId);
            return repositoryService.add(ndFactory.newND(viewCreateRequest, ndParentId));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NdParent with id +" + ndParentId + " not found", e);
        }
    }

    @PostMapping(value = "/ndparent/new/{treId}",produces = "application/json")
    public NdParent ndParentNew(HttpServletRequest request,@RequestBody ViewCreateRequest viewCreateRequest, @PathVariable String treId) {
        try {
            logger.called("ndParentNew","viewCreateRequest",viewCreateRequest);
            securityCheck(request,Role.user);
            repositoryService.findTre(treId);
            return repositoryService.add(ndParentFactory.newNdParent(viewCreateRequest, treId));
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
            User user=securityCheck(request,Role.user);
            return repositoryService.getTres(user);
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
    @DeleteMapping(value = "/fi/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public ND deleteFi(HttpServletRequest request, @PathVariable String id) {
        try {
            logger.called("deleteFi","id",id);
            securityCheck(request,Role.user);
            String parentId=repositoryService.findFI(id).ndId;
            repositoryService.deleteFi(id);
            return repositoryService.getNd(parentId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_FAILURE, e.getMessage());
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
    @DeleteMapping(value = "/nd/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public NdParent deleteNd(HttpServletRequest request, @PathVariable String id) {
        try {
            logger.called("deleteNd","id",id);
            securityCheck(request,Role.user);
            String parentId=repositoryService.findNd(id).ndParentId;
            repositoryService.deleteNd(id);
            return repositoryService.getNdParent(parentId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_FAILURE, e.getMessage());
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @DeleteMapping(value = "/ndparent/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public TRE deleteNdParent(HttpServletRequest request, @PathVariable String id) {
        try {
            logger.called("deleteNdParent","id",id);
            securityCheck(request,Role.user);
            String parentId=repositoryService.findNdParent(id).treId;
            repositoryService.deleteNdParent(id);
            return repositoryService.getTre(parentId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_FAILURE, e.getMessage());
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
    @DeleteMapping(value = "/tre/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public List<TRE> deleteTre(HttpServletRequest request, @PathVariable String id) {
        try {
            logger.called("deleteTre","id",id);
            User user=securityCheck(request,Role.user);
            repositoryService.deleteTre(id,user);
            return repositoryService.getTres(user);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_FAILURE, e.getMessage());
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }


    @PutMapping(value = "/fi/{fiId}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public FI updateFi(HttpServletRequest request, @RequestBody FI fi, @PathVariable String fiId) {
        try {
            securityCheck(request, Role.user);
            logger.called("updateFi", "FI",fi);
            repositoryService.findFI(fiId);
            FI fiUpdated=repositoryService.updateFi(fi);
            return Helpers.initGson().fromJson(fiUpdated.fiJson,FI.class);

        }catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_FAILURE, e.getMessage());
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Cannot find FI with id "+fiId,e);
        }
    }

    @PutMapping(value = "/nd/{ndId}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public ND updateND(HttpServletRequest request, @RequestBody ND nd, @PathVariable String ndId) {
        try {
            securityCheck(request, Role.user);
            logger.called("updateND", "ND",nd);
            repositoryService.findNd(ndId);
            return repositoryService.updateND(nd);
        }catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_FAILURE, e.getMessage());
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Cannot find FI with id "+ndId,e);
        }
    }

    @PutMapping(value = "/ndparent/{ndparentId}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public NdParent updateNdParent(HttpServletRequest request, @RequestBody NdParent ndParent, @PathVariable String ndparentId) {
        try {
            securityCheck(request, Role.user);
            logger.called("updateNdParent", "ndParent",ndParent);
            repositoryService.findNdParent(ndparentId);
            return repositoryService.updateNdParent(ndParent);
        }catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_FAILURE, e.getMessage());
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Cannot find ndparent with id "+ndparentId,e);
        }
    }

    @PutMapping(value = "/tre/{treId}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public TRE updateTre(HttpServletRequest request, @RequestBody TRE tre, @PathVariable String treId) {
        try {
            securityCheck(request, Role.user);
            logger.called("updateTre", "tre",tre);
            repositoryService.findTre(treId);
            return repositoryService.updateTre(tre);
        }catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_FAILURE, e.getMessage());
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Cannot find tre with id "+treId,e);
        }
    }

    @PostMapping(value="/export",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public ResponseEntity<Resource> export(HttpServletRequest request, @RequestBody TRE tre) throws ResourceNotFoundException, IOException, JAXBException, DataNotValidException {
        TRE treToExport=repositoryService.findTre(tre.ID);
        for(NdParent ndParent:tre.ndParents){
            NdParent ndParentToExport=repositoryService.findNdParent(ndParent.ID);
            treToExport.ndParents.add(ndParentToExport);
            for(ND nd:ndParent.ND){
               ND ndToExport=repositoryService.findNd(nd.ID);
               ndParentToExport.ND.add(ndToExport);
               for (FI fi:nd.FI){
                   ndToExport.FI.add(repositoryService.getFi(fi.ID));
               }
            }
        }
        TreFactory treFactory=new TreFactory();
        Path resultPath=Files.createTempDirectory("results").toAbsolutePath();
        treFactory.export(resultPath,treToExport);
        Path zipPath=Files.createTempFile("export",".zip");
        Helpers.zip(resultPath,zipPath);
        InputStreamResource inputStream= new InputStreamResource(new FileInputStream(zipPath.toFile()));
        return ResponseEntity.ok().header("Content-Disposition", "attachment;filename=download.zip")
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .contentLength(zipPath.toFile().length())
                .body(inputStream);

    }

    @GetMapping(value = "/fi/{fiId}/fidoc",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public ResponseEntity<Resource> getFiDoc(HttpServletRequest request, @PathVariable String fiId) {
        try {
            logger.called("getFiDoc","fiId",fiId);
            securityCheck(request,Role.user,Role.viewer);
            Long fiDocId=repositoryService.getFi(fiId).fiDocId;
            logger.info("Find fiDocId "+fiDocId+" for FI with id "+fiId);
            FiDoc fiDoc=repositoryService.getFiDoc(fiDocId);
            byte[] fiDocByte=fiDoc.doc;
            InputStreamResource inputStream= new InputStreamResource(new ByteArrayInputStream(fiDoc.doc));
            return ResponseEntity.ok().header("Content-Disposition", "attachment;filename="+fiDoc.name)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .contentLength(fiDocByte.length)
                    .body(inputStream);

        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(), e);
        }
    }
}