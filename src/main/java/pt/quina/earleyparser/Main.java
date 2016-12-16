package pt.quina.earleyparser;


import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import pt.quina.earleyparser.components.Chart;
import pt.quina.earleyparser.components.ParseTree;
import pt.quina.earleyparser.components.State;
import pt.quina.earleyparser.grammars.Grammar;
import pt.quina.earleyparser.grammars.MyGrammar;
import pt.quina.earleyparser.grammars.ParsedGrammar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Stream;



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
public class Main {


    public static void main(String[] args) {
        //test(new String[]{"O","presidente", "condena", "luta", "partidária"}, new EarleyParser(new NewGrammar()));

        Grammar grammar;
        File sentencesFile;

        CommandLineParser parser = new BasicParser();
        Options options = buildOptions();
        try {
            CommandLine cmd = parser.parse(options, args);

            grammar = new ParsedGrammar(new File(cmd.getOptionValue("cfg")));

            sentencesFile = new File(cmd.getOptionValue("s"));

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(getRunningFile(), options);
            return;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        try (Stream<String> lines = Files.lines(sentencesFile.toPath(), StandardCharsets.UTF_8)) {
            for (String line : (Iterable<String>) lines::iterator) {
                if (StringUtils.isNotBlank(line)) {
                    String[] strings = line.trim().split("\\s+");
                    MyGrammar my = new MyGrammar();
                    test(strings, new EarleyParser(grammar));
                    test(strings, new EarleyParser(my));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static String getRunningFile() {
        try {
            return (new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath())).getName();
        } catch (URISyntaxException e) {
            return "RUNNING-JAR";
        }
    }

    private static Options buildOptions() {
        Options options = new Options();

        Option cfgOption = new Option("cfg", "grammar-file", true, "The Context Free Grammar file");
        cfgOption.setRequired(true);
        options.addOption(cfgOption);

        Option sentenceOption = new Option("s", "sentences-file", true, "The file containing the sentences to be parsed using the grammar file");
        sentenceOption.setRequired(false);
        options.addOption(sentenceOption);

        Option printTreeOption = new Option("t", "print-tree", true, "Prints the syntax tree for each sentence.");
        sentenceOption.setRequired(false);
        options.addOption(printTreeOption);

        return options;
    }


    // Test uses the parser on the sentence passed in. It will then
    //  print out the charts that were produced during the parsing, as
    //  well as all parse trees.
    private static void test(String[] sent, EarleyParser parser) {


        String sentence = StringUtils.join(sent, " ").concat(".");

        System.out.println("\nSentence: \"" + sentence + "\"");
        boolean successful = parser.parseSentence(sent);
        System.out.println("Parse Successful:" + successful);

        if (!successful) return;

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
            System.out.println(pt.get(i));
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
                    backPointers += ", " + map.get(stateSource);
            }
        }
        return backPointers;
    }

    private static Map<State, String> getStatesMap(Chart[] charts) {
        Map<State, String> stateMap = new HashMap<>();
        int counter = 0;
        for (Chart chart : charts) {
            for (int j = 0; j < chart.size(); j++) {
                State state = chart.getState(j);
                stateMap.put(state, "S" + counter++);
            }
        }
        return stateMap;
    }

}
