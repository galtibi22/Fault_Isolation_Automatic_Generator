package org.afeka.fi.tests.units;
import static j2html.TagCreator.*;

import org.afeka.fi.tests.FiCommonTest;
import org.junit.Test;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class J2HtmlTest extends FiCommonTest {

    public J2HtmlTest() throws FileNotFoundException {
    }

    @Test
    public void generateSimpaleHtmlPage() throws FileNotFoundException {
     String html=html(
                head(
                        title("Title"),
                        link().withRel("stylesheet").withHref("/css/main.css")
                ),
                body(
                        main(attrs("#main.content"),
                                h1("Heading!")
                        )
                )
        ).render();
        PrintWriter out = new PrintWriter("test.html");
        out.println(html);
        out.close();
        //logger.info(html);

    }
   // Desktop.getDesktop.

}
