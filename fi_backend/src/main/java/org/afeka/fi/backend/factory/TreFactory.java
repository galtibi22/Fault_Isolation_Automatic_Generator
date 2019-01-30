package org.afeka.fi.backend.factory;

import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.pojo.commonstructure.TRE;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;

public class TreFactory extends ViewFactory {
    private TRE tre;

    /**
     * <TRE v="0806"
     * mxPgs="12"
     * srch="111101"
     * nLnkCols="3"
     * lnkCol0="manNm"
     * lnkCol0tl="Manual Name"
     * lnkCol0w="30"
     * lnkCol1="chpNm"
     * lnkCol1tl="Chapter Name"
     * lnkCol1w="30"
     * lnkCol2="nm"
     * lnkCol2tl="Target Name"
     * lnkCol2w="40"
     * fiRigid="0"
     * prnt="000000"
     * ful="1">
     */
    public void TreFactory(){
        tre=new TRE();
        v("0806").mxPgs("10").srch("111101").nLnkCols("3").lnkCol0("manNm").lnkCol0tl("Manual Name").lnkCol0w("30").lnkCol1("chpNm")
                .lnkCol1tl("Chapter Name").lnkCol1w("30").lnkCol2("nm").lnkCol2tl("Target Name").lnkCol2w("40").fiRigid("0").prnt("000000").ful("1");
    }

    private TreFactory v(String v){
        tre.v=v;
        return this;
    }
    private TreFactory mxPgs(String mxPgs){
        tre.mxPgs=mxPgs;
        return this;
    }

    private TreFactory srch(String srch){
        tre.srch=srch;
        return this;
    }

    private TreFactory nLnkCols(String nLnkCols){
        tre.nLnkCols=nLnkCols;
        return this;
    }

    private TreFactory lnkCol0(String lnkCol0){
        tre.lnkCol0=lnkCol0;
        return this;
    }

    private TreFactory lnkCol0tl(String lnkCol0tl){
        tre.lnkCol0tl=lnkCol0tl;
        return this;
    }

    private TreFactory lnkCol0w(String lnkCol0w){
        tre.lnkCol0w=lnkCol0w;
        return this;
    }

    private TreFactory lnkCol1(String lnkCol1){
        tre.lnkCol1=lnkCol1;
        return this;
    }

    private TreFactory lnkCol1tl(String lnkCol1tl){
        tre.lnkCol1tl=lnkCol1tl;
        return this;
    }

    private TreFactory lnkCol1w(String lnkCol1w){
        tre.lnkCol1w=lnkCol1w;
        return this;
    }

    private TreFactory lnkCol2(String lnkCol2){
        tre.lnkCol2=lnkCol2;
        return this;
    }

    private TreFactory lnkCol2tl(String lnkCol2tl){
        tre.lnkCol2tl=lnkCol2tl;
        return this;
    }

    private TreFactory lnkCol2w(String lnkCol2w){
        tre.lnkCol2w=lnkCol2w;
        return this;
    }

    private TreFactory fiRigid(String fiRigid){
        tre.fiRigid=fiRigid;
        return this;
    }

    private TreFactory prnt(String prnt){
        tre.prnt=prnt;
        return this;
    }

    private TreFactory ful(String ful){
        tre.ful=ful;
        return this;
    }



    @Override
    public void export(String path) throws JAXBException, IOException {
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext= JAXBContext.newInstance(ND.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(tre,
                new FileOutputStream(new File(path+"fiTre.xml")));

        for (ND nd:tre.ND){
            new NdFactory(nd).export(path);
        }
    }

    @Override
    public void add(Object obj) throws Exception {
        ND nd=(ND)obj;
        tre.ND.add(nd);
    }
}
