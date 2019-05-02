package org.afeka.fi.backend.api;

import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.pojo.commonstructure.NdParent;
import org.afeka.fi.backend.pojo.commonstructure.TRE;
import org.afeka.fi.backend.pojo.http.ViewCreateRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(path = "api/fronted/ndparent")
@RestController
public class NdParentApi extends CommonApi {
    @PutMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public NdParent updateNdParent(HttpServletRequest request, @RequestBody NdParent ndParent, @PathVariable String id) throws ResourceNotFoundException {
        securityCheck(request, Role.user);
        logger.called("updateNdParentApi", "ndParent",ndParent);
        repositoryService.findNdParent(id);
        return repositoryService.updateNdParent(ndParent);
    }

    @DeleteMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public TRE deleteNdParent(HttpServletRequest request, @PathVariable String id) throws ResourceNotFoundException, DeleteEntityExption {
        logger.called("deleteNdParentApi","id",id);
        securityCheck(request,Role.user);
        String parentId=repositoryService.findNdParent(id).treId;
        repositoryService.deleteNdParent(id);
        return repositoryService.getTre(parentId);
    }

    @GetMapping(value = "/{id}",headers = HttpHeaders.AUTHORIZATION,produces = "application/json")
    public NdParent getNdParent(HttpServletRequest request, @PathVariable String id) throws ResourceNotFoundException {
        logger.called("getNdParentApi","ndParentId",id);
        securityCheck(request,Role.user);
        return repositoryService.getNdParent(id);

    }

    @PostMapping(value = "/new/{treId}",produces = "application/json")
    public NdParent ndParentNew(HttpServletRequest request,@RequestBody ViewCreateRequest viewCreateRequest, @PathVariable String treId) throws ResourceNotFoundException {
        logger.called("ndParentNewApi","viewCreateRequest",viewCreateRequest);
        securityCheck(request,Role.user);
        repositoryService.findTre(treId);
        return repositoryService.add(ndParentFactory.newNdParent(viewCreateRequest, treId));
    }
}
