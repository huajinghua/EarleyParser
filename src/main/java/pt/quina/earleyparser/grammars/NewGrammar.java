package pt.quina.earleyparser.grammars;

import pt.quina.earleyparser.components.RHS;

/******************************************************************************
 * author: Breanna Ammons
 * project: EarleyParser with parse trees
 * 
 * NewGrammar
 *   The NewGrammar is an example of an extension of the Grammar class. It
 *   is more complex then the SimpleGrammar. 
 * 
 *   S  -> NP VP
 * 
 *   NP -> NP PP
 *   NP -> Noun
 *   NP -> NP Conj NP
 *   NP -> Article NP
 *   NP -> Adj NP
 * 
 *   VP -> Verb NP
 *   VP -> VP PP
 *   VP -> Adv VP
 * 
 *   PP -> Prep NP
 * 		
 *   Noun -> John | Mary | Denver | men | women | Police | dogs
 *   Verb -> called | like
 *   Prep -> from
 *   Conj -> and
 *   Adj -> old
 *   Adv -> quickly
 *   Article-> the
 * 
 *****************************************************************************/

public class NewGrammar extends Grammar
{
	public NewGrammar()
	{
		super();
		initialize();
	}

	@Override
	public String buildRulesTree() {
		return null;
	}

	private void initialize()
	{
		initRules();
		initPOS();
	}

	// Create the rules for this New Grammar
	private void initRules()
	{
		// S -> NP VP
		String s = "S";
		String[] s1 = { "NP", "VP" };
		RHS[] sRHS = { new RHS(s1) };
		rules.put(s, sRHS);

		// NP -> NP PP
		// NP -> Noun
		// NP -> NP Conj NP
		// NP -> Article NP
		// NP -> Adj NP
		String np = "NP";
		String[] np1 = { "Art", "N" };
		String[] np2 = { "N", "Adj" };
		String[] np3 = { "N" };
		String[] np4 = { "Art", "NP" };
		String[] np5 = { "N", "PP" };
		RHS[] npRHS = { new RHS(np1), new RHS(np2), new RHS(np3),
						new RHS(np4), new RHS(np5)
		};
		rules.put(np, npRHS);

		// VP -> Verb NP
		// VP -> VP PP
		// VP -> Adv VP
		String vp = "VP";
		String[] vp1 = { "V", "NP" };
		RHS[] vpRHS = { new RHS(vp1) };
		rules.put(vp, vpRHS);

		// PP -> Prep NP
		String pp = "PP";
		String[] pp1 = { "Prep", "NP" };
		RHS[] ppRHS = { new RHS(pp1) };
		rules.put(pp, ppRHS);

		// Noun -> John | Mary | Denver | men | women | Police | dogs
		String noun = "N";
		String[] noun1 = { "presidente" };
		String[] noun2 = { "luta" };
		String[] noun3 = { "Trump" };
		String[] noun4 = { "personalidade" };
		String[] noun5 = { "ano" };

		RHS[] nounRHS = { new RHS(noun1), new RHS(noun2), new RHS(noun3),
						  new RHS(noun4), new RHS(noun5)
		};
		rules.put(noun, nounRHS);

		// Verb -> called | like
		String verb = "V";
		String[] verb1 = { "condena" };
		String[] verb2 = { "é" };
		RHS[] verbRHS = { new RHS(verb1), new RHS(verb2) };
		rules.put(verb, verbRHS);

		// Prep -> from
		String prep = "Prep";
		String[] prep1 = { "do" };
		RHS[] prepRHS = { new RHS(prep1) };
		rules.put(prep, prepRHS);

		// Adj -> old
		String adj = "Adj";
		String[] adj1 = { "partidária" };
		RHS[] adjRHS = { new RHS(adj1) };
		rules.put(adj, adjRHS);

		// Article-> the
		String article = "Art";
		String[] article1 = { "O" };
		String[] article2 = { "a" };
		RHS[] articleRHS = { new RHS(article1) };
		rules.put(article, articleRHS);
	}

	private void initPOS()
	{
		partsOfSpeech.add("N");
		partsOfSpeech.add("V");
		partsOfSpeech.add("Adj");
		partsOfSpeech.add("Art");
		partsOfSpeech.add("Prep");
	}
}
