package pt.quina.earleyparser;

import pt.quina.earleyparser.components.RHS;
import pt.quina.earleyparser.components.Chart;
import pt.quina.earleyparser.components.State;
import pt.quina.earleyparser.grammars.Grammar;

/******************************************************************************
 * author: Breanna Ammons
 * project: EarleyParser with parse trees
 *
 * EarleyParser
 *   This does the hard labor for parsing the sentence into charts that we can
 *   use to determine all of the parse trees that describe the sentence.
 *
 *****************************************************************************/

public class EarleyParser {
    private Grammar grammar;
    private String[] sentence;
    private Chart[] charts;

    public EarleyParser(Grammar g) {
        grammar = g;
    }

    Grammar getGrammar() {
        return grammar;
    }

    Chart[] getCharts() {
        return charts;
    }

    /**************************************************************************
     * parseSentence()
     *   This is the main loop for parsing the sentence into the chart. It will
     *   return true if there is at least one successful parse of the sentence.
     *************************************************************************/
    boolean parseSentence(String[] s) {
        sentence = s;
        charts = new Chart[sentence.length + 1];
        for (int i = 0; i < charts.length; i++)
            charts[i] = new Chart();

        // Add the initial state " $ -> @ S "
        String[] start1 = {"@", "S"};
        RHS startRHS = new RHS(start1);
        State start = new State("$", startRHS, 0, 0, null);
        charts[0].addState(start);

        for (Chart chart : charts) {
            for (int j = 0; j < chart.size(); j++) {
                State st = chart.getState(j);
                String next_term = st.getAfterDot();

                if (st.isDotLast())
                    completer(st);    // State's RHS = ... @
                else if (grammar.isPartOfSpeech(next_term))
                    scanner(st);    // State's RHS = ... @ A ..., where A is a part of speech
                else
                    predictor(st);    // State's RHS = ... @ A ..., where A is NOT a part of speech
            }
        }

        // Determine if there was a successful parse.
        String[] fin = {"S", "@"};
        RHS finRHS = new RHS(fin);
        State finish = new State("$", finRHS, 0, sentence.length, null);

        State last = charts[sentence.length].getState(charts[sentence.length].size() - 1);

        return finish.equals(last);
    }

    /**************************************************************************
     * predictor()
     *   After this function completes all possible states that could
     *   potentially continue from the state s is added to the charts.
     *************************************************************************/
    private void predictor(State s) {
        String lhs = s.getAfterDot();
        RHS[] rhs = grammar.getRHS(lhs);
        int j = s.getJ();

        for (RHS rh : rhs) {
            State ns = new State(lhs, rh.addDot(), j, j, s);
            charts[j].addState(ns);
        }
    }

    /**************************************************************************
     * scanner()
     *   After this function completes any rules for the LHS that are 1 term
     *   only and match the word in the sentence will be added to the chart.
     *************************************************************************/
    private void scanner(State s) {
        String lhs = s.getAfterDot();
        RHS[] rhs = grammar.getRHS(lhs);

        int j = s.getJ();

        for (RHS rh : rhs) {
            String[] terms = rh.getTerms();
            if (terms.length == 1 && j < sentence.length && terms[0].compareToIgnoreCase(sentence[j]) == 0) {
                State ns = new State(lhs, rh.addDotLast(), j, j + 1, s);
                charts[j + 1].addState(ns);
            }
        }
    }

    /**************************************************************************
     * completer()
     *   After this function completes, any state in the i-th chart for which
     *   the string after the dot matches the current state's LHS will be added
     *   to the j-th chart with the dot moved to the right.
     *************************************************************************/
    private void completer(State s) {
        String lhs = s.getLHS();

        for (int a = 0; a < charts[s.getI()].size(); a++) {
            State st = charts[s.getI()].getState(a);
            String after = st.getAfterDot();
            if (after != null && lhs.compareTo(after) == 0) {
                State ns = new State(st.getLHS(), st.getRHS().moveDot(),
                        st.getI(), s.getJ(), s);
                charts[s.getJ()].addState(ns);
            }
        }
    }


}