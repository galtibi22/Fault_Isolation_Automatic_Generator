package org.afeka.fi.tests.units;

import org.afeka.fi.backend.generator.HtmlGenerator;
import org.afeka.fi.tests.common.FiCommonTest;
import org.junit.Test;


public class HtmlGeneratorTests extends FiCommonTest {

    String fiTitleExp="<html>" +
            "    <head>" +
            "        <link rel=\"stylesheet\" href=\"../../dbPrj/setup/_lkCss_ietm.css\">" +
            "        <link rel=\"StyleSheet\" href=\"../../dbPrj/setup/_lkCss_lk.css\">" +
            "        <link rel=\"StyleSheet\" href=\"../../dbPrj/setup/_lkCss_reserved.css\">" +
            "        <script lang=\"javascript\" src=\"../../../gen/agen/funcs_doc.js\">" +
            "        </script>" +
            "        <script lang=\"javascript\" src=\"../../../man/srch/highlight.js\">" +
            "        </script>" +
            "    </head>" +
            "    <body>" +
            "        <br>" +
            "        <br>" +
            "        <div dir=\"ltr\">" +
            "            <h2>" +
            "                this is a title" +
            "            </h2>" +
            "            <p class=\"fiStpDsc\">" +
            "                this is a stp dsc" +
            "            </p>" +
            "        </div>" +
            "    </body>" +
            "</html>";
    String fiStepExp=" <html>" +
            "    <head>" +
            "        <link rel=\"stylesheet\" href=\"../../dbPrj/setup/_lkCss_ietm.css\">" +
            "        <link rel=\"StyleSheet\" href=\"../../dbPrj/setup/_lkCss_lk.css\">" +
            "        <link rel=\"StyleSheet\" href=\"../../dbPrj/setup/_lkCss_reserved.css\">" +
            "        <script lang=\"javascript\" src=\"../../../gen/agen/funcs_doc.js\">" +
            "        </script>" +
            "        <script lang=\"javascript\" src=\"../../../man/srch/highlight.js\">" +
            "        </script>" +
            "    </head>" +
            "    <body>" +
            "        <br>" +
            "        <br>" +
            "        <div dir=\"ltr\">" +
            "            <p class=\"fiStpPrc\">" +
            "                <br>" +
            "                Connect cable CBX to J5 receptacle." +
            "                <br>" +
            "            </p>" +
            "            <br>" +
            "            <p class=\"fiStpQst\">" +
            "                <br>" +
            "                Does the symptom appear again?" +
            "                <br>" +
            "            </p>" +
            "        </div>" +
            "    </body>" +
            "</html>";

    String fiNegExp=" <html>" +
            "    <head>" +
            "        <link rel=\"stylesheet\" href=\"../../dbPrj/setup/_lkCss_ietm.css\">" +
            "        <link rel=\"StyleSheet\" href=\"../../dbPrj/setup/_lkCss_lk.css\">" +
            "        <link rel=\"StyleSheet\" href=\"../../dbPrj/setup/_lkCss_reserved.css\">" +
            "        <script lang=\"javascript\" src=\"../../../gen/agen/funcs_doc.js\">" +
            "        </script>" +
            "        <script lang=\"javascript\" src=\"../../../man/srch/highlight.js\">" +
            "        </script>" +
            "    </head>" +
            "    <body onload=\"highlight();\">" +
            "        <div dir=\"ltr\">" +
            "            <h1 class=\"heading1\">" +
            "                Problem not Resolved" +
            "            </h1>" +
            "            <h1 class=\"heading1\">" +
            "                Please Contact Next Maintenance Level" +
            "            </h1>" +
            "        </div>" +
            "    </body>" +
            "</html>";
    String fiPosExp="<html>" +
            "    <head>" +
            "        <link rel=\"stylesheet\" href=\"../../dbPrj/setup/_lkCss_ietm.css\">" +
            "        <link rel=\"StyleSheet\" href=\"../../dbPrj/setup/_lkCss_lk.css\">" +
            "        <link rel=\"StyleSheet\" href=\"../../dbPrj/setup/_lkCss_reserved.css\">" +
            "        <script lang=\"javascript\" src=\"../../../gen/agen/funcs_doc.js\">" +
            "        </script>" +
            "        <script lang=\"javascript\" src=\"../../../man/srch/highlight.js\">" +
            "        </script>" +
            "    </head>" +
            "    <body onload=\"highlight();\">" +
            "        <div dir=\"ltr\">" +
            "            <h1 class=\"heading1\">" +
            "                Problem Resolved" +
            "            </h1>" +
            "            <p>" +
            "            </p>" +
            "            <p>" +
            "            </p>" +
            "            <h1 class=\"heading1\">" +
            "                Troubleshooting Process Completed Successfully" +
            "            </h1>" +
            "            <br>" +
            "            <p align=\"center\">" +
            "                <img height=\"75\" width=\"75\" src=\"cnfV_Green.png\">" +
            "            </p>" +
            "        </div>" +
            "    </body>" +
            "</html>";
    @Test
    public void generateFiTitle(){
        logger.info("Generate html page for fi title");
        HtmlGenerator generator=new HtmlGenerator();
        generator.fiTitle("this is a title");
        generator.fiStpDsc("this is a stp dsc");
        logger.info("Validate the expected html is :"+generator.toString());
        assert (generator.toString().trim().equals(fiTitleExp.trim()));
    }

    @Test
    public void generateFiStep(){
        logger.info("Generate html page for fi step");
        HtmlGenerator generator=new HtmlGenerator();
        generator.fiStpPrc("Connect cable CBX to J5 receptacle.");
        generator.fiStpQst("Does the symptom appear again?");

        logger.info("Validate the expected html is :"+generator.toString());
        assert (generator.toString().equals(fiStepExp));
    }
    @Test
    public void generateFiPosEnt(){
        logger.info("Generate html page for fi positive end");
        HtmlGenerator generator=new HtmlGenerator();
        generator.fiPosEnd();
        logger.info("Validate the expected html is :"+generator.toString());
        assert (generator.toString().equals(fiPosExp));

    }

    @Test
    public void generateFiNegEnt(){
        logger.info("Generate html page for fi positive end");
        HtmlGenerator generator=new HtmlGenerator();
        generator.fiNegEnd();
        logger.info("Validate the expected html is :"+generator.toString());

        assert (generator.toString().equals(fiNegExp));

    }
}
