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
        return "ViewCreateRequest{" + "lbl='" + lbl + '\'' + ", des='" + des + '\'' + '}';
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @NotNull
    String lbl;
    @NotNull
    String des;

}
