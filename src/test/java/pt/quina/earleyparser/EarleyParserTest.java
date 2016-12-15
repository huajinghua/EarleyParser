package pt.quina.earleyparser;

import org.junit.Test;
import pt.quina.earleyparser.grammars.ParsedGrammar;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by NB22771 on 14/12/2016.
 */
public class EarleyParserTest {
    private EarleyParser earleyParser;

    public EarleyParserTest() throws IOException {
        String grammarFile = getClass().getResource("/trab3_grup17.cfg").getFile();
        ParsedGrammar parsedGrammar = new ParsedGrammar(new File(grammarFile));
        this.earleyParser = new EarleyParser(parsedGrammar);
    }

    @Test
    public void getGrammar() throws Exception {
        assertNotNull(earleyParser.getGrammar().getRHS("S"));
    }

    @Test
    public void getCharts() throws Exception {

    }

    @Test
    public void parseSentence() throws Exception {
        assertTrue(earleyParser.parseSentence("Adjudicações na Madeira desrespeitam as regras".split("\\s+")));
        assertTrue(earleyParser.parseSentence("Comboio descarrila em Nova_Iorque".split("\\s+")));
        assertFalse(earleyParser.parseSentence("Comboio descarrila".split("\\s+")));
    }

}