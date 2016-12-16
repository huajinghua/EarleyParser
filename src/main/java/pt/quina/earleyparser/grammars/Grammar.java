package pt.quina.earleyparser.grammars;

import pt.quina.earleyparser.components.RHS;

import java.util.HashMap;
import java.util.Vector;

/******************************************************************************
 * author: Breanna Ammons
 * project: EarleyParser with parse trees
 *
 * Grammar
 *   This is a base class that should be extended for any grammar that
 *   will be used within this program to parse sentences.
 *
 *****************************************************************************/
public abstract class Grammar {
    // A mapping between a LHS (the String) and an array of RHS's.
    HashMap<String, RHS[]> rules;

    // An array of LHS's that are Parts of Speech.
    Vector<String> partsOfSpeech;

    Grammar() {
        rules = new HashMap<>();
        partsOfSpeech = new Vector<>();
    }

    public RHS[] getRHS(String lhs) {
        RHS[] rhs = null;
        if (rules.containsKey(lhs))
            rhs = rules.get(lhs);

        return rhs;
    }

    public boolean isPartOfSpeech(String s) {
        return partsOfSpeech.contains(s);
    }

}