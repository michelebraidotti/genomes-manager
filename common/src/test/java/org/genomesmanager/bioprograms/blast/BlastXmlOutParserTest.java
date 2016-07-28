package org.genomesmanager.bioprograms.blast;

import org.junit.Test;

import javax.xml.bind.JAXBContext;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URL;

/**
 * Created by michele on 7/15/16.
 */
public class BlastXmlOutParserTest {

    // TODO!!! Find a way of setting javax.xml.accessExternalDTD=all
    // somewhere else than the test java vm options.
    @Test
    public void test() throws JAXBException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("blastParserTest/blast_out_test.xml");
        File file = new File(url.getPath());
        JAXBContext jaxbContext = JAXBContext.newInstance("org.genomesmanager.bioprograms.blast");
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BlastOutput blastOutput = (BlastOutput) unmarshaller.unmarshal(file);
        System.out.println(blastOutput);
    }
}
