package pt.quina.earleyparser.grammars;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Stream;

/**
 * Created by NB22771 on 13/12/2016.
 */
public class GrammarParser extends Grammar {
    private File grammarFile;

    public GrammarParser(File grammarFile) throws FileNotFoundException {
        super();
        if (!grammarFile.exists())
            throw new FileNotFoundException("The file " + grammarFile + " could not be found.");
        this.grammarFile = grammarFile;


    }

    private void initRules() throws IOException {
        try (Stream<String> lines = Files.lines(grammarFile.toPath(), StandardCharsets.UTF_8)) {
            for (String line : (Iterable<String>) lines::iterator) {
                if (line.lastIndexOf("->") > -1) {
                    String[] linePieces = line.split("->", 2);
                    String rule = linePieces[0];
                    //todo: carregar os constituintes da regra
                }
            }
        }
    }
}
