package pt.quina.earleyparser.components;

import java.util.Vector;

/******************************************************************************
 * author: Breanna Ammons
 * project: EarleyParser with parse trees
 *
 * Chart
 *   The Chart class maintains the states contained at each level of the
 *   parsing. It contains a vector that does not contain duplicates states.
 *   If additional behavior was needed in the Chart for parsing, this would
 *   be a potential place to add it. Right now, it combines sources of states
 *   that would have been duplicates. By combining the sources of the states
 *   we can trace all possible ways back throught the parse trees.
 *
 *****************************************************************************/
public class Chart {
    private Vector<State> chart;

    public Chart() {
        chart = new Vector<>();
    }

    public int size() {
        return chart.size();
    }

    /**************************************************************************
     * addState()
     *   Attempt to add the state. After this call, the state is guaranteed to
     * 	 be in this chart.
     *************************************************************************/
    public void addState(State s) {
        // If the state is already in the chart, we take the sources from the
        //  state passed in and add them to the state in the chart. This will
        //  enable us to find all of the parse trees that were produced.
        if (chart.contains(s)) {
            int i = chart.indexOf(s);
            State orig = chart.get(i);
            orig.addSources(s);
        } else {
            chart.add(s);
        }
    }

    /**************************************************************************
     * getState()
     *   Return the state at indice i. If i is invalid, null will be returned.
     *************************************************************************/
    public State getState(int i) {
        if (i < 0 || i >= chart.size())
            return null;

        return chart.get(i);
    }

    /**************************************************************************
     * toString()
     *   This is an over-ride of the toString function. It prints the chart and
     *   the states it contains in a way to make it more readable.
     *************************************************************************/
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();

        for (State aChart : chart) {
            out.append(aChart).append("\n");
        }

        return out.toString();
    }
}
