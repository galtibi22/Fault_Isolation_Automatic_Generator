package org.afeka.fi.backend.pojo.http;

import javax.validation.constraints.NotNull;

public class ViewCreateRequest {
    public String getLbl() {
        return lbl;
    }

    public void setLbl(String lbl) {
        this.lbl = lbl;
    }

    @Override
    public String toString() {
        return "NdRequest{" + "lbl='" + lbl + '\'' + '}';
    }

    @NotNull
    String lbl;

}
