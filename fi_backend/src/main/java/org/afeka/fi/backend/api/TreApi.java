package org.afeka.fi.backend.api;

import com.itextpdf.text.DocumentException;
import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.exception.AddEntityExption;
import org.afeka.fi.backend.exception.DataNotValidException;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.pojo.commonstructure.TRE;
import org.afeka.fi.backend.pojo.http.GeneralResponse;
import org.afeka.fi.backend.pojo.http.ViewCreateRequest;
import org.afeka.fi.backend.services.TreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@RequestMapping(path = "api/fronted/tre")
@RestController
public class TreApi extends CommonApi {
    private TreServiceImpl treService;
    @Autowired
    public void initServices(TreServiceImpl treService){
        this.treService=treService;
    }
    @PostMapping(value = "/new",produces = "application/json")
    public TRE newTre(HttpServletRequest request, @RequestBody ViewCreateRequest viewCreateRequest) throws AddEntityExption {
        logger.called("newTreApi","viewCreateRequest",viewCreateRequest);
        User user= securityCheck(request, Role.user);
        return  treService.add(viewCreateRequest,user.userName);
        //return
    }
    @GetMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public TRE getTre(HttpServletRequest request, @PathVariable String id) throws ResourceNotFoundException {
        logger.called("getTreApi","treId",id);
        securityCheck(request,Role.user);
        return treService.get(id);

    }
    @GetMapping(value = "/",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public List<TRE> getTres(HttpServletRequest request) throws ResourceNotFoundException {
        logger.called("getTresApi","","");
        User user=securityCheck(request,Role.user);
        return treService.getAll(user.userName);
    }
    @DeleteMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public GeneralResponse deleteTre(HttpServletRequest request, @PathVariable String id) throws ResourceNotFoundException, DeleteEntityExption {
        logger.called("deleteTreApi","id",id);
        User user=securityCheck(request,Role.user);
        treService.delete(id);
        return new GeneralResponse("Success to delete TRE "+id);

    }

    @PutMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public TRE updateTre(HttpServletRequest request, @RequestBody TRE tre, @PathVariable String id) throws ResourceNotFoundException {
        securityCheck(request, Role.user);
        logger.called("updateTreApi", "tre",tre);
        treService.find(id);
        return treService.update(tre);

    }


    @PostMapping(value="/export",headers = HttpHeaders.AUTHORIZATION)
    public ResponseEntity<Resource> export(HttpServletRequest request, @RequestBody TRE tre) throws ResourceNotFoundException, IOException, JAXBException, DataNotValidException {
        logger.called("exportApi", "tre",tre);
        securityCheck(request, Role.user);
        return initFileResponse(treService.export(tre));

    }
    @PostMapping(value="/export/report",headers =HttpHeaders.AUTHORIZATION)
    public ResponseEntity<Resource> exportReport(HttpServletRequest request, @RequestBody TRE tre) throws ResourceNotFoundException, IOException, JAXBException, DataNotValidException, DocumentException {
        logger.called("exportReportApi", "tre",tre);
        securityCheck(request, Role.user);

        return initFileResponse(treService.exportReport(tre));

    }


}
