package org.afeka.fi.backend.api;

import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.common.FiCommon;
import org.afeka.fi.backend.common.Helpers;
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



    @PostMapping(path="/new/{ndId}/{fiDocId}", produces = "application/json",headers = HttpHeaders.AUTHORIZATION)

    public GeneralResponse newFis(HttpServletRequest request, @PathVariable String ndId, @RequestBody String str, @PathVariable Long fiDocId) throws Exception {
        logger.info("call newFis api with body"+str);
        FI[]fis=new FI[1];
        fis=Helpers.initGson().fromJson(str,fis.getClass());
        securityCheck(request, Role.generator);
        ND nd=repositoryService.getNd(ndId);
        for (FI fi:fis){
            fi=fiFactory.newFI(fi,ndId,fiDocId);
            repositoryService.add(fi);
        }
        return new GeneralResponse("success to add "+fis.length+ " fis to ndId="+ndId);
    }

}