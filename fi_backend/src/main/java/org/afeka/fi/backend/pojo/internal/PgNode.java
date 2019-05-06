package org.afeka.fi.backend.pojo.internal;

import org.afeka.fi.backend.pojo.commonstructure.PG;

public class PgNode {
    private int n;
    private PgNode yes;
    private PgNode no;

    public PgNode(int n){
        this.n=n;

    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public PgNode getYes() {
        return yes;
    }

    public void setYes(PgNode yes) {
        this.yes = yes;
    }

    public PgNode getNo() {
        return no;
    }

    public void setNo(PgNode no) {
        this.no = no;
    }
}
