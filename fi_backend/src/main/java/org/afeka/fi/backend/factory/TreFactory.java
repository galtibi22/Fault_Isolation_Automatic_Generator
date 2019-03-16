package org.afeka.fi.backend.factory;

import org.afeka.fi.backend.common.Generator;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.exception.DataNotValidException;
import org.afeka.fi.backend.pojo.commonstructure.NdParent;
import org.afeka.fi.backend.pojo.commonstructure.TRE;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;

public class TreFactory extends ViewFactory <TRE> {
    //private TRE tre;

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
    public TRE newTRE(){
        view=new TRE();
        return v("0806").mxPgs("10").srch("111101").nLnkCols("3").lnkCol0("manNm").lnkCol0tl("Manual Name").lnkCol0w("30").lnkCol1("chpNm")
                .lnkCol1tl("Chapter Name").lnkCol1w("30").lnkCol2("nm").lnkCol2tl("Target Name").lnkCol2w("40").fiRigid("0").prnt("000000").ful("1").
                        ID(Generator.id()).get();
    }

    private TreFactory ID(String id) {
        view.ID=id;
        return this;
    }

    private TreFactory v(String v){
        view.v=v;
        return this;
    }
    private TreFactory mxPgs(String mxPgs){
        view.mxPgs=mxPgs;
        return this;
    }

    private TreFactory srch(String srch){
        view.srch=srch;
        return this;
    }

    private TreFactory nLnkCols(String nLnkCols){
        view.nLnkCols=nLnkCols;
        return this;
    }

    private TreFactory lnkCol0(String lnkCol0){
        view.lnkCol0=lnkCol0;
        return this;
    }

    private TreFactory lnkCol0tl(String lnkCol0tl){
        view.lnkCol0tl=lnkCol0tl;
        return this;
    }

    private TreFactory lnkCol0w(String lnkCol0w){
        view.lnkCol0w=lnkCol0w;
        return this;
    }

    private TreFactory lnkCol1(String lnkCol1){
        view.lnkCol1=lnkCol1;
        return this;
    }

    private TreFactory lnkCol1tl(String lnkCol1tl){
        view.lnkCol1tl=lnkCol1tl;
        return this;
    }

    private TreFactory lnkCol1w(String lnkCol1w){
        view.lnkCol1w=lnkCol1w;
        return this;
    }

    private TreFactory lnkCol2(String lnkCol2){
        view.lnkCol2=lnkCol2;
        return this;
    }

    private TreFactory lnkCol2tl(String lnkCol2tl){
        view.lnkCol2tl=lnkCol2tl;
        return this;
    }

    private TreFactory lnkCol2w(String lnkCol2w){
        view.lnkCol2w=lnkCol2w;
        return this;
    }

    private TreFactory fiRigid(String fiRigid){
        view.fiRigid=fiRigid;
        return this;
    }

    private TreFactory prnt(String prnt){
        view.prnt=prnt;
        return this;
    }

    private TreFactory ful(String ful){
        view.ful=ful;
        return this;
    }



    @Override
    public void export(String path,TRE tre) throws JAXBException, IOException, DataNotValidException {
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext= JAXBContext.newInstance(TRE.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(tre,
                new FileOutputStream(new File(path+"fiTre.xml")));

        for (NdParent ndParent:tre.ndParents){
            NdParentFactory ndParentFactory=new NdParentFactory();
            ndParentFactory.export(path,ndParent);
        }
    }

/*    @Override
    public void add(NdParent ndParent) throws Exception {
        tre.ndParents.add(ndParent);
    }*/


}
