package org.genomesmanager.common.blast;

import org.genomesmanager.common.blastparser.*;
import org.junit.Test;

import javax.xml.bind.JAXBContext;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by michele on 7/15/16.
 */
public class BlastXmlOutParserTest {

    // TODO!!! Find a way of setting javax.xml.accessExternalDTD=all
    // somewhere else than the test java vm options.
    @Test
    public void test() throws JAXBException {
        File file = new File("common/src/test/resources/blastParserTest/blast_out_test.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance("org.genomesmanager.common.blast");
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BlastOutput blastOutput = (BlastOutput) unmarshaller.unmarshal(file);
        System.out.println(blastOutput);
    }
}
