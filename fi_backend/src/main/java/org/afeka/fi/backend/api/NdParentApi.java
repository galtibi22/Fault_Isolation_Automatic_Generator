package org.afeka.fi.backend.api;

import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.pojo.commonstructure.NdParent;
import org.afeka.fi.backend.pojo.commonstructure.TRE;
import org.afeka.fi.backend.pojo.http.GeneralResponse;
import org.afeka.fi.backend.pojo.http.ViewCreateRequest;
import org.afeka.fi.backend.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(path = "api/fronted/ndparent")
@RestController
public class NdParentApi extends CommonApi {

    private EntityService<NdParent> ndParentService;
    @Autowired
    public void init (EntityService<NdParent> ndParentService){
        this.ndParentService=ndParentService;
    }


    @PutMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public NdParent updateNdParent(HttpServletRequest request, @RequestBody NdParent ndParent, @PathVariable String id) throws ResourceNotFoundException {
        securityCheck(request, Role.user);
        logger.called("updateNdParentApi", "ndParent",ndParent);
        return ndParentService.update(ndParent);
    }

    @DeleteMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public GeneralResponse deleteNdParent(HttpServletRequest request, @PathVariable String id) throws ResourceNotFoundException, DeleteEntityExption {
        logger.called("deleteNdParentApi","id",id);
        securityCheck(request,Role.user);
        ndParentService.delete(id);
        return new GeneralResponse("success to delete ndParent with id "+id);
    }

    @GetMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public NdParent getNdParent(HttpServletRequest request, @PathVariable String id) throws ResourceNotFoundException {
        logger.called("getNdParentApi","ndParentId",id);
        securityCheck(request,Role.user);
        return ndParentService.get(id);

    }

    @PostMapping(value = "/new/{treId}",produces = "application/json")
    public NdParent ndParentNew(HttpServletRequest request,@RequestBody ViewCreateRequest viewCreateRequest, @PathVariable String treId) throws ResourceNotFoundException {
        logger.called("ndParentNewApi","viewCreateRequest",viewCreateRequest);
        securityCheck(request,Role.user);
        return  ndParentService.add(viewCreateRequest,treId);
    }
}
