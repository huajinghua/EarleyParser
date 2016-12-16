package pt.quina.earleyparser.grammars;

import org.apache.commons.lang3.ArrayUtils;
import pt.quina.earleyparser.components.RHS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Grammar created by reading the contents of a file.
 */
public class ParsedGrammar extends Grammar {
    private File grammarFile;

    public ParsedGrammar(File grammarFile) throws IOException {
        super();
        if (!grammarFile.exists()) {
            throw new FileNotFoundException("The file " + grammarFile + " could not be found.");
        }
        this.grammarFile = grammarFile;
        initRules();
    }

    private void initRules() throws IOException {
        try (Stream<String> lines = Files.lines(grammarFile.toPath(), StandardCharsets.UTF_8)) {
            for (String line : (Iterable<String>) lines::iterator) {
                if (line.lastIndexOf("->") > -1) {
                    String[] linePieces = line.trim().split("->", 2);
                    String ruleName = linePieces[0].trim();

                    String[] ruleParts = linePieces[1].trim().split("\\|");
                    if ("POS".equals(ruleName)) {
                        partsOfSpeech.addAll(Arrays.asList(ruleParts));
                    } else {
                        List<RHS> rhsList = new ArrayList<>(ruleParts.length);
                        for (String rulePart : ruleParts) {
                            RHS rhs = new RHS(rulePart.trim().split(" "));
                            rhsList.add(rhs);
                        }

                        if (rules.containsKey(ruleName)) {
                            RHS[] rhsNew = rhsList.toArray(new RHS[rhsList.size()]);
                            RHS[] rhsOld = rules.get(ruleName);
                            RHS[] rhs = ArrayUtils.addAll(rhsOld, rhsNew);
                            rules.put(ruleName, rhs);
                        } else {
                            rules.put(ruleName, rhsList.toArray(new RHS[rhsList.size()]));
                        }
                    }
                }
            }
        }
    }


}
