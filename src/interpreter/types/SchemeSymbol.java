package interpreter.types;

import interpreter.node.SchemeNode;
import interpreter.node.VarNode;

import java.util.HashMap;

public class SchemeSymbol extends SchemeObject
{
	private static final HashMap<String, SchemeSymbol> CACHE = new HashMap<String, SchemeSymbol>();

	public static SchemeSymbol makeSymbol(String name)
	{
		if (CACHE.containsKey(name))
		{
			return CACHE.get(name);
		}
		
		SchemeSymbol newSym = new SchemeSymbol(name);
		CACHE.put(name, newSym);
		return newSym;
	}
	
	// Special symbols
	public static final SchemeSymbol LET    = makeSymbol("let");
	public static final SchemeSymbol LAMBDA = makeSymbol("lambda");
	public static final SchemeSymbol QUOTE  = makeSymbol("quote");
	
	private final String name;
	
	private SchemeSymbol(String sym)
	{
		this.name = sym;
	}
	
	public SchemeNode asCode()
	{
		return new VarNode(this.name);
	}
	
	@Override
	public boolean equal(SchemeObject obj)
	{
		// Symbols are interned, so object identity is equality
		// if I am doing everything correctly.
		return this == obj;
	}

	public String toString()
	{
		return this.name;
	}
}
