package org.genomesmanager.bioprograms;
import  java.util.prefs.*;

/**
 * Created by michele on 7/26/16.
 */
public class Configuration {
    private static final String BLASTP_EXECUTABLE_PATH = "blastp_exec_path";
    private static final String BLASTP_DEFAULT_EXECUTABLE_PATH = "/usr/bin/blastp";

    private static final String BLASTN_EXECUTABLE_PATH = "blastn_exec_path";
    private static final String BLASTN_DEFAULT_EXECUTABLE_PATH = "/usr/bin/blastn";

    private static final String BLASTALL_EXECUTABLE_PATH = "blastall_exec_path";
    private static final String BLASTALL_DEFAULT_EXECUTABLE_PATH = "/usr/bin/blastall";

    private static final String CROSS_MATCH_EXECUTABLE_PATH = "cross_match_executable_path";
    private static final String CROSS_MATCH_DEFAULT_EXECUTABLE_PATH = "/usr/bin/cross_match";

    private static final String FORMATDB_EXECUTABLE_PATH = "formatdb_executable_path";
    private static final String FORMATDB_DEFAULT_EXECUTABLE_PATH = "/usr/bin/formatdb";

    private static final String LUCY_EXECUTABLE_PATH = "lucy_executable_path";
    private static final String LUCY_DEFAULT_EXECUTABLE_PATH = "/usr/local/bin/lucy";

    private static final String PHRED_EXECUTABLE_PATH = "phred_executable_path";
    private static final String PHRED_DEFAULT_EXECUTABLE_PATH = "/usr/bin/phred";

    private static final String SFFFILE_EXECUTABLE_PATH = "sfffile_executable_path";
    private static final String SFFFILE_DEFAULT_EXECUTABLE_PATH = "/usr/bin/sfffile";

    private static final String SFFINFO_EXECUTABLE_PATH = "sffinfo_executable_path";
    private static final String SFFINFO_DEFAULT_EXECUTABLE_PATH = "/usr/bin/sffinfo";

    private static final Preferences preferences = Preferences.userNodeForPackage(Configuration.class);

    public static void setBlastpExecutablePath(String configurationValue) {
        preferences.put(BLASTP_EXECUTABLE_PATH, configurationValue);
    }

    public static String getBlastpExecutablePath() {
        return preferences.get(BLASTP_EXECUTABLE_PATH, BLASTP_DEFAULT_EXECUTABLE_PATH);
    }

    public static void setBlastnExecutablePath(String configurationValue) {
        preferences.put(BLASTN_EXECUTABLE_PATH, configurationValue);
    }

    public static String getBlastnExecutablePath() {
        return preferences.get(BLASTN_EXECUTABLE_PATH, BLASTN_DEFAULT_EXECUTABLE_PATH);
    }

    public static void setBlastallExecutablePath(String configurationValue) {
        preferences.put(BLASTALL_EXECUTABLE_PATH, configurationValue);
    }

    public static String getBlastallExecutablePath() {
        return preferences.get(BLASTALL_EXECUTABLE_PATH, BLASTALL_DEFAULT_EXECUTABLE_PATH);
    }

    public static void setCrossMatchExecutablePath(String configurationValue) {
        preferences.put(CROSS_MATCH_EXECUTABLE_PATH, configurationValue);
    }

    public static String getCrossMatchExecutablePath() {
        return preferences.get(CROSS_MATCH_EXECUTABLE_PATH, CROSS_MATCH_DEFAULT_EXECUTABLE_PATH);
    }

    public static void setFormatdbExecutablePath(String configurationValue) {
        preferences.put(FORMATDB_EXECUTABLE_PATH, configurationValue);
    }

    public static String getFormatdbExecutablePath() {
        return preferences.get(FORMATDB_EXECUTABLE_PATH, FORMATDB_DEFAULT_EXECUTABLE_PATH);
    }

    public static void setLucyExecutablePath(String configurationValue) {
        preferences.put(LUCY_EXECUTABLE_PATH, configurationValue);
    }

    public static String getLucyExecutablePath() {
        return preferences.get(LUCY_EXECUTABLE_PATH, LUCY_DEFAULT_EXECUTABLE_PATH);
    }

    public static void setPhredExecutablePath(String configurationValue) {
        preferences.put(PHRED_EXECUTABLE_PATH, configurationValue);
    }

    public static String getPhredExecutablePath() {
        return preferences.get(PHRED_EXECUTABLE_PATH, PHRED_DEFAULT_EXECUTABLE_PATH);
    }

    public static void setSfffileExecutablePath(String configurationValue) {
        preferences.put(SFFFILE_EXECUTABLE_PATH, configurationValue);
    }

    public static String getSfffileExecutablePath() {
        return preferences.get(SFFFILE_EXECUTABLE_PATH, SFFFILE_DEFAULT_EXECUTABLE_PATH);
    }

    public static void setSffinfoExecutablePath(String configurationValue) {
        preferences.put(SFFINFO_EXECUTABLE_PATH, configurationValue);
    }

    public static String getSffinfoExecutablePath() {
        return preferences.get(SFFINFO_EXECUTABLE_PATH, SFFINFO_DEFAULT_EXECUTABLE_PATH);
    }

    public static void resetToDefaults() {
        preferences.put(BLASTP_EXECUTABLE_PATH, BLASTP_DEFAULT_EXECUTABLE_PATH);
        preferences.put(BLASTN_EXECUTABLE_PATH, BLASTN_DEFAULT_EXECUTABLE_PATH);
        preferences.put(BLASTALL_EXECUTABLE_PATH, BLASTALL_DEFAULT_EXECUTABLE_PATH);
        preferences.put(CROSS_MATCH_EXECUTABLE_PATH, CROSS_MATCH_DEFAULT_EXECUTABLE_PATH);
        preferences.put(FORMATDB_EXECUTABLE_PATH, FORMATDB_DEFAULT_EXECUTABLE_PATH);
        preferences.put(LUCY_EXECUTABLE_PATH, LUCY_DEFAULT_EXECUTABLE_PATH);
        preferences.put(PHRED_EXECUTABLE_PATH, PHRED_DEFAULT_EXECUTABLE_PATH);
        preferences.put(SFFFILE_EXECUTABLE_PATH, SFFFILE_DEFAULT_EXECUTABLE_PATH);
        preferences.put(SFFINFO_EXECUTABLE_PATH, SFFINFO_DEFAULT_EXECUTABLE_PATH);
    }
}
