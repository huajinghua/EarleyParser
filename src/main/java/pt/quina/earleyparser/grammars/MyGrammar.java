package pt.quina.earleyparser.grammars;

import pt.quina.earleyparser.components.RHS;

public class MyGrammar extends Grammar {
    public MyGrammar() {
        super();
        initialize();
    }

    private void initialize() {
        initRules();
        initPOS();
    }

    // Create the rules for this New Grammar
    private void initRules() {
        // S -> NP VP
        String s = "S";
        String[] s1 = {"NP", "VP"};
        RHS[] sRHS = {new RHS(s1)};
        rules.put(s, sRHS);

        // NP -> Noun
        // NP -> Noun PP
        // NP -> Art Noun
        String np = "NP";
        String[] np1 = {"Noun"};
        String[] np2 = {"Noun", "PP"};
        String[] np3 = {"Art", "Noun"};
        RHS[] npRHS = {new RHS(np1), new RHS(np2), new RHS(np3)};
        rules.put(np, npRHS);

        // VP -> Verb NP
        // VP -> Verb PP
        String vp = "VP";
        String[] vp1 = {"Verb", "NP"};
        String[] vp2 = {"Verb", "PP"};

        RHS[] vpRHS = {new RHS(vp1), new RHS(vp2)};
        rules.put(vp, vpRHS);

        // PP -> Prep NP
        String pp = "PP";
        String[] pp1 = {"Prep", "NP"};
        RHS[] ppRHS = {new RHS(pp1)};
        rules.put(pp, ppRHS);

        // Noun -> Comboio | Madeira | Nova_Iorque | regras | Adjudicações
        String noun = "Noun";
        String[] noun1 = {"Comboio"};
        String[] noun2 = {"Madeira"};
        String[] noun3 = {"Nova_Iorque"};
        String[] noun4 = {"regras"};
        String[] noun5 = {"Adjudicações"};
        RHS[] nounRHS = {new RHS(noun1), new RHS(noun2), new RHS(noun3),
                new RHS(noun4), new RHS(noun5),
        };
        rules.put(noun, nounRHS);

        // Verb -> descarrila | desrespeitam
        String verb = "Verb";
        String[] verb1 = {"descarrila"};
        String[] verb2 = {"desrespeitam"};
        RHS[] verbRHS = {new RHS(verb1), new RHS(verb2)};
        rules.put(verb, verbRHS);

        // Prep -> em | na
        String prep = "Prep";
        String[] prep1 = {"em"};
        String[] prep2 = {"na"};
        RHS[] prepRHS = {new RHS(prep1),new RHS(prep2)};
        rules.put(prep, prepRHS);

        // Article-> as
        String article = "Art";
        String[] article1 = {"as"};
        RHS[] articleRHS = {new RHS(article1)};
        rules.put(article, articleRHS);
    }

    private void initPOS() {
        partsOfSpeech.add("Noun");
        partsOfSpeech.add("Verb");
        partsOfSpeech.add("Prep");
        partsOfSpeech.add("Art");
    }
}
