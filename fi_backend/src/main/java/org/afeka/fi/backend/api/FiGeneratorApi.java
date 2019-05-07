package org.afeka.fi.backend.api;

import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.common.FiCommon;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.http.GeneralResponse;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.services.FiGeneratorService;
import org.afeka.fi.backend.services.NdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping(path = "/api/figenerator")
@RestController
public class FiGeneratorApi extends CommonApi {


    private FiGeneratorService fiGeneratorService;

    @Autowired
    public void init(FiGeneratorService fiGeneratorService){
        this.fiGeneratorService=fiGeneratorService;
    }

    @PostMapping(path="/new/{ndId}/{fiDocId}", produces = "application/json",headers = HttpHeaders.AUTHORIZATION)
    public GeneralResponse newFis(HttpServletRequest request, @PathVariable String ndId, @RequestBody String json, @PathVariable Long fiDocId) throws Exception {
        logger.info("call newFis api with body"+json);
        securityCheck(request, Role.generator);
        List<FI> fis=fiGeneratorService.newFis(ndId,fiDocId,json);
        return new GeneralResponse("success to add "+fis.size()+ " fis to ndId="+ndId);
    }

}