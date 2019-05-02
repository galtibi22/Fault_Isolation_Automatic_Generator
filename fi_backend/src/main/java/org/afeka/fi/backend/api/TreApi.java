package org.afeka.fi.backend.api;

import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.exception.DataNotValidException;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.factory.TreFactory;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.pojo.commonstructure.NdParent;
import org.afeka.fi.backend.pojo.commonstructure.TRE;
import org.afeka.fi.backend.pojo.http.ViewCreateRequest;
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

@RequestMapping(path = "api/fronted/tre")
@RestController
public class TreApi extends CommonApi {

    @PostMapping(value = "/new",produces = "application/json")
    public TRE newTre(HttpServletRequest request, @RequestBody ViewCreateRequest viewCreateRequest) {
        logger.called("newTreApi","viewCreateRequest",viewCreateRequest);
        User user= securityCheck(request, Role.user);
        return repositoryService.add(treFactory.newTRE(user.userName,viewCreateRequest));
    }
    @GetMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public TRE getTre(HttpServletRequest request, @PathVariable String id) throws ResourceNotFoundException {
        logger.called("getTreApi","treId",id);
        securityCheck(request,Role.user);
        return repositoryService.getTre(id);
    }
    @GetMapping(value = "/",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public List<TRE> getTres(HttpServletRequest request) throws ResourceNotFoundException {
        logger.called("getTresApi","","");
        User user=securityCheck(request,Role.user);
        List<TRE> tres=repositoryService.getTres(user);
        logger.info("getTresApi return "+Helpers.initGson().toJson(tres));
        return repositoryService.getTres(user);
    }
    @DeleteMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public List<TRE> deleteTre(HttpServletRequest request, @PathVariable String id) throws ResourceNotFoundException, DeleteEntityExption {
        logger.called("deleteTreApi","id",id);
        User user=securityCheck(request,Role.user);
        repositoryService.deleteTre(id,user);
        return repositoryService.getTres(user);

    }

    @PutMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public TRE updateTre(HttpServletRequest request, @RequestBody TRE tre, @PathVariable String id) throws ResourceNotFoundException {
        securityCheck(request, Role.user);
        logger.called("updateTreApi", "tre",tre);
        repositoryService.findTre(id);
        return repositoryService.updateTre(tre);

    }

    @PostMapping(value="/export"/*,headers = HttpHeaders.AUTHORIZATION*/)
    public ResponseEntity<Resource> export(HttpServletRequest request, @RequestBody TRE tre) throws ResourceNotFoundException, IOException, JAXBException, DataNotValidException {
        logger.called("exportApi", "tre",tre);
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
        Path resultPath= Files.createTempDirectory("results").toAbsolutePath();
        treFactory.export(resultPath,treToExport);
        Path zipPath=Files.createTempFile("export",".zip");
        Helpers.zip(resultPath,zipPath);
        InputStreamResource inputStream= new InputStreamResource(new FileInputStream(zipPath.toFile()));
        return ResponseEntity.ok().header("Content-Disposition", "attachment;filename=download.zip")
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .contentLength(zipPath.toFile().length())
                .body(inputStream);

    }
}
