/******************************************************************************
 * author: Breanna Ammons
 * project: EarleyParser with parse trees
 *
 * Main
 *   This the main program. It creates and runs the parser on 4 example 
 *   sentences. The test function displays all of the charts produced during 
 *   the parsing and will print any parse tree that was produced.
 *
 *****************************************************************************/

import earleyparser.components.Chart;
import earleyparser.components.ParseTree;
import earleyparser.components.State;
import earleyparser.grammars.MyGrammar;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        /*// John called Mary.
        String[] sentence1 = { "John", "called", "Mary" };

		// John called Mary from Dever.
		String[] sentence2 = { "John", "called", "Mary", "from", "Denver" };

		// John called the Police from Denver.
		String[] sentence3 = { "John", "called", "the", "Police", "from", "Denver" };

		// Old men and women like dogs.
		String[] sentence4 = { "Old", "men", "and", "women", "like", "dogs" };

		Grammar grammar = new NewGrammar();
		EarleyParser parser = new EarleyParser(grammar);

		test(sentence1, parser);
		test(sentence2, parser);
		test(sentence3, parser);
		test(sentence4, parser);
		*/
        //test(new String[]{"Comboio", "descarrila", "em", "Nova_Iorque"}, new EarleyParser(new MyGrammar()));
        //test(new String[]{"Adjudicações", "na", "Madeira", "desrespeitam", "as", "regras"}, new EarleyParser(new MyGrammar()));

        test(new String[]{"Comboio", "descarrila"}, new EarleyParser(new MyGrammar()));

    }


    // Test uses the parser on the sentence passed in. It will then
    //  print out the charts that were produced during the parsing, as
    //  well as all parse trees.
    static void test(String[] sent, EarleyParser parser) {
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < sent.length - 1; i++)
            out.append(sent[i] + " ");
        out.append(sent[sent.length - 1] + ".");

        String sentence = out.toString();

        System.out.println("\nSentence: \"" + sentence + "\"");
        boolean successful = parser.parseSentence(sent);
        System.out.println("Parse Successful:" + successful);

        Chart[] charts = parser.getCharts();
        System.out.println("");
        System.out.println("Charts produced by the sentence \"" + sentence + "\"");


        for (int i = 0; i < charts.length; i++) {
            System.out.println("Chart " + i + ":");
            System.out.println(charts[i]);
        }

        Vector<ParseTree> pt = ParseTree.getTree(parser.getGrammar(), charts);
        for (int i = 0; i < pt.size(); i++) {
            System.out.println("Parse Tree " + i + ":");
            System.out.println((ParseTree) pt.get(i));
        }

        if (pt.size() == 0)
            System.out.println("There were no parse trees.");

        printCharts(charts);
    }

    private static void printCharts(Chart[] charts) {
        int counter = 0;
        Map<State, String> statesMap = getStatesMap(charts);
        for (int i = 0; i < charts.length; i++) {
            System.out.printf("Chart %d:\n", i);
            for (int j = 0; j < charts[i].size(); j++) {
                State state = charts[i].getState(j);
                System.out.printf("S%d;%s[%s]\n", counter++, state.getCSV(), getBackPointers(statesMap, state.getSources()));
            }
        }
    }

    private static String getBackPointers(Map<State, String> map, Vector<State> sources) {
        String backPointers = "";
        for (State stateSource : sources) {
            if (map.containsKey(stateSource)) {
                if ("".equals(backPointers))
                    backPointers += map.get(stateSource);
                else
                    backPointers += ", "+map.get(stateSource) ;
            }
        }
        return backPointers;
    }

    private static Map<State, String> getStatesMap(Chart[] charts) {
        Map<State, String> stateMap = new HashMap<>();
        int counter = 0;
        for (int i = 0; i < charts.length; i++) {
            for (int j = 0; j < charts[i].size(); j++) {
                State state = charts[i].getState(j);
                stateMap.put(state, "S" + counter++);
            }
        }
        return stateMap;
    }
}
