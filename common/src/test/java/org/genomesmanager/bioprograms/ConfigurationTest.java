package org.genomesmanager.bioprograms;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by michele on 7/26/16.
 */
public class ConfigurationTest {

    @Test
    public void test() {
        Configuration.setBlastnExecutablePath("test");
        assertEquals("test", Configuration.getBlastnExecutablePath());

        Configuration.setBlastnExecutablePath("testBlastn");
        assertEquals("testBlastn", Configuration.getBlastnExecutablePath());

        Configuration.setBlastpExecutablePath("testBlastp");
        assertEquals("testBlastp", Configuration.getBlastpExecutablePath());

        Configuration.setBlastallExecutablePath("testBlastall");
        assertEquals("testBlastall", Configuration.getBlastallExecutablePath());

        Configuration.setCrossMatchExecutablePath("testCrossMatch");
        assertEquals("testCrossMatch", Configuration.getCrossMatchExecutablePath());

        Configuration.setFormatdbExecutablePath("testFormatdb");
        assertEquals("testFormatdb", Configuration.getFormatdbExecutablePath());

        Configuration.setLucyExecutablePath("testLucy");
        assertEquals("testLucy", Configuration.getLucyExecutablePath());

        Configuration.setPhredExecutablePath("testPhred");
        assertEquals("testPhred", Configuration.getPhredExecutablePath());

        Configuration.setSfffileExecutablePath("testSfffile");
        assertEquals("testSfffile", Configuration.getSfffileExecutablePath());

        Configuration.setSffinfoExecutablePath("testSffinfo");
        assertEquals("testSffinfo", Configuration.getSffinfoExecutablePath());

        // Configuration is persisted! Need to revert everything to defaults!
        Configuration.resetToDefaults();
    }
}
