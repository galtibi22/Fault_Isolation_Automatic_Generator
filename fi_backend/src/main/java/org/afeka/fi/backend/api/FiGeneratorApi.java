package org.afeka.fi.backend.api;

import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.http.GeneralResponse;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(path = "/api/figenerator")
@RestController
public class FiGeneratorApi extends CommonApi {


    @PostMapping(path="/new/{id}", produces = "application/json",headers = HttpHeaders.AUTHORIZATION)

    public GeneralResponse newFis(HttpServletRequest request,@PathVariable String id, @RequestBody FI[] fis) throws Exception {
        logger.called("newFis post request","ndId",id);
        securityCheck(request, Role.generator);
        ND nd=repositoryService.getNd(id);
        for (FI fi:fis){
            fi=fiFactory.newFI(fi.PG,id);
            repositoryService.add(fi);
        }
        return new GeneralResponse("success to add "+fis.length+ " fis to ndId="+id);
    }

   /* @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee)
    {
        Integer id = employeeDao.getAllEmployees().getEmployeeList().size() + 1;
        employee.setId(id);

        employeeDao.addEmployee(employee);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(employee.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }*/
}