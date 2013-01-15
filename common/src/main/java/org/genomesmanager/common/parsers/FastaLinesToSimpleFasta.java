package org.genomesmanager.common.parsers;

import java.util.ArrayList;
import java.util.List;

import org.genomesmanager.common.formats.SimpleFasta;

public class FastaLinesToSimpleFasta {

    public static List<SimpleFasta> GetSimpleFastas(List<String> fastaLines) {
        List<SimpleFasta> fastas = new ArrayList<SimpleFasta>();
        int i = 0;
        String line = fastaLines.get(i);
        String id = "";
        StringBuilder sequence = new StringBuilder();
        while (i < fastaLines.size()) {
            if (line.startsWith(">")) {
                id = line;
                id = id.replace(">", "");
                id = id.replaceFirst(" .+$", "");
                sequence = new StringBuilder();
            }
            if (i == fastaLines.size() - 1) {
                break;
            }
            i++;
            line = fastaLines.get(i);
            while (i < fastaLines.size() - 1 && !line.startsWith(">")) {
                sequence.append(line.replace("\n", ""));
                i++;
                line = fastaLines.get(i);
            }
            if (i == fastaLines.size() - 1 && sequence.length() == 0) {
                sequence.append(line.replace("\n", ""));
            }
            fastas.add(new SimpleFasta("", id, sequence.toString()));
        }
        return fastas;
    }
}
