package org.afeka.fi.backend.html;
import j2html.tags.ContainerTag;
import org.afeka.fi.backend.common.FiCommon;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.junit.jupiter.api.Test;

import java.util.List;

import static j2html.TagCreator.*;
public class HtmlGenerator extends FiCommon {
    private ContainerTag div=div().withDir("ltr");
    private ContainerTag body=body(br(),br(),div);
    private ContainerTag head=html(
            head(
                    link().withRel("stylesheet").withHref("../../dbPrj/setup/_lkCss_ietm.css"),
                    link().withRel("StyleSheet").withHref("../../dbPrj/setup/_lkCss_lk.css"),
                    link().withRel("StyleSheet").withHref("../../dbPrj/setup/_lkCss_reserved.css"),
                    script().withLang("javascript").withSrc("../../../gen/agen/funcs_doc.js"),
                    script().withLang("javascript").withSrc("../../../man/srch/highlight.js")
            )
    );


    public void fiTitle(String txt){
        div.with(h2(txt));
    }

    public void fiStpDsc(String txt){
        div.with(p(txt).withClass("fiStpDsc"));
    }

    public void fiStpPrc(String txt){

        div.with(p(br()).withClass("fiStpPrc").withText(txt).with(br()));
    }

    public void fiStpQst(String txt){
        div.with(br(),p(br()).withClass("fiStpQst").withText(txt).with(br()));
    }

    /**
     *  <body onload="highlight();">
     * 	<div dir=rtl>
     *     <h1 class="heading1">Problem Resolved</h1>
     *     <p></p>
     *     <p></p>
     *     <h1 class="heading1">Troubleshooting Process Completed Successfully</h1>
     * 	</BR><BR>
     * 	<p align=center >
     * 	<img height=75 width=75 src="cnfV_Green.png">
     * 	</p>
     * 	</div>
     *   </body>
     */
    public void fiPosEnd (){
        body=body(
                div(h1("Problem Resolved").withClass("heading1"),
                    p(),
                    p(),
                    h1("Troubleshooting Process Completed Successfully").withClass("heading1"),
                    br(),
                    p(img().attr("height",75).
                        attr("width",75).
                        withSrc("cnfV_Green.png")).
                        attr("align","center")
                ).withDir("ltr")).attr("onload","highlight();");
    }


    /**
     *
     <body onload="highlight();">
     <div dir=rtl>
     <h1 class="heading1">Problem not Resolved</h1>
     <h1 class="heading1">Please Contact Next Maintenance Level</h1>
     </div>
     </body>
     */
    public void fiNegEnd (){
        body=body(
                div(h1("Problem not Resolved").withClass("heading1"),
                        h1("Please Contact Next Maintenance Level").withClass("heading1")).
                        withDir("ltr")).
                attr("onload","highlight();");
    }

    /**
     * <body ondragstart="return(false);"
     * ondrop="return(false);">
     * <img class="kmiImgToAnimate"
     * style="position:absolute;z-index:99;width:0;height:0;left:0;top:0">
     * <TABLE width=80% align=left cellpadding=2 cellspacing=3 border=0>
     *     <TR><TD valign=middle
     *     align=left
     *     dir=ltr
     *     height=15
     *     style="cursor:hand; color:white; font-family:Arial; font-size:12pt; background-color:#808080;"
     *     onclick="toTreeNode('0:(IN)SH-06-7IAF_6.5_1:0');">Pedestal - MOTOR PWR Switch OFF</TD>
     *     </TR>
     */
    public void ndDoc(String name,List<FI> fis){
        head=ndDocHead();
        ContainerTag table=ndDocTable(fis);
        body=body(
                img().
                        withClass("kmiImgToAnimate").
                        withStyle("position:absolute;z-index:99;width:0;height:0;left:0;top:0"),
                h2(name),
                table)
                .attr("ondragstart","return(false);")
                .attr("ondrop","return(false);");


    }

    private ContainerTag ndDocHead() {
                return head.with(
                        script().withLang("javascript").withSrc("../../../aMain/to/toTree.js"),
                        style(".highlight { background: #FFFF40; }"),
                        title("Created by FI Generator")
                );
    }

    private ContainerTag ndDocTable(List<FI> fis) {
        ContainerTag table=table().
                attr("width","80%").
                attr("align","left").
                attr("cellpadding","2").
                attr("cellspacing","3").
                attr("border","0");
        for (FI fi:fis){
            String color;

            if (Integer.parseInt(fi.ID.substring(fi.ID.length()-1))%2==0)
                color="#A9A9A9";
            else
                color="#808080";
                table.with(tr(
                    td(fi.lbl).
                            attr("valign","middle").
                            attr("align","left").
                            attr("dir","ltr").
                            attr("height","15").
                            attr("style","cursor:hand; " +
                                    "color:white; " +
                                    "font-family:Arial; " +
                                    "font-size:12pt; " +
                                    "background-color:"+color+";").
                            attr("onclick","toTreeNode('0:"+fi.ID+":0');")
            ));
        }
        return table;
    }

    public ContainerTag toHtml(){
        return head.with(body);
    }

    public String toString(){
        return head.with(body).renderFormatted();
    }


    public void ndParentDoc(String lbl) {
        head=ndDocHead();
        body=body(
            h2(lbl));

    }
}
