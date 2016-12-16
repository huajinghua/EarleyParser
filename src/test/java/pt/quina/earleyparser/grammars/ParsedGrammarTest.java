package pt.quina.earleyparser.grammars;

import org.junit.Assert;
import pt.quina.earleyparser.components.RHS;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParsedGrammarTest {

    private Grammar grammar;


    public ParsedGrammarTest() throws Exception {
        URL resource = ParsedGrammarTest.class.getResource("/trab3_grup17.cfg");
        grammar = new ParsedGrammar(new File(resource.getFile()));
    }

    @org.junit.Test
    public void getRHS() throws Exception {
        RHS[] rulesS = grammar.getRHS("S");
        Assert.assertNotNull("S", rulesS);
        assertEquals("Total Regras S:", 1, rulesS.length);

        RHS[] rulesNP = grammar.getRHS("NP");
        Assert.assertNotNull("NP", rulesNP);
        assertEquals("Total Regras NP:", 3, rulesNP.length);

        RHS[] rulesVP = grammar.getRHS("VP");
        Assert.assertNotNull("VP", rulesVP);
        assertEquals("Total Regras VP:", 2, rulesVP.length);

        RHS[] rulesPP = grammar.getRHS("PP");
        Assert.assertNotNull("PP", rulesPP);
        assertEquals("Total Regras PP:", 1, rulesPP.length);

        RHS[] rulesArt = grammar.getRHS("Art");
        Assert.assertNotNull("Art", rulesArt);
        assertEquals("Total Regras Art:", 1, rulesArt.length);

        RHS[] rulesNoun = grammar.getRHS("Noun");
        Assert.assertNotNull("Noun", rulesNoun);
        assertEquals("Total Regras Noun:", 5, rulesNoun.length);

        RHS[] rulesVerb = grammar.getRHS("Verb");
        Assert.assertNotNull("Verb", rulesVerb);
        assertEquals("Total Regras Verb:", 2, rulesVerb.length);

        RHS[] rulesPrep = grammar.getRHS("Prep");
        Assert.assertNotNull("Prep", rulesPrep);
        assertEquals("Total Regras Prep:", 2, rulesPrep.length);
    }

    @org.junit.Test
    public void isPartOfSpeech() throws Exception {
        assertTrue(grammar.isPartOfSpeech("Noun"));
        assertTrue(grammar.isPartOfSpeech("Verb"));
        assertTrue(grammar.isPartOfSpeech("Prep"));
        assertTrue(grammar.isPartOfSpeech("Art"));
    }
}