package interpreter.types;

import java.util.HashMap;

public class SchemeSymbol extends Object {
    private static final HashMap<String, SchemeSymbol> CACHE = new HashMap<String, SchemeSymbol>();

    public static SchemeSymbol makeSymbol(String name) {
        if (CACHE.containsKey(name)) {
            return CACHE.get(name);
        }

        SchemeSymbol newSym = new SchemeSymbol(name);
        CACHE.put(name, newSym);
        return newSym;
    }

    // Special symbols
    public static final SchemeSymbol LET = makeSymbol("let");
    public static final SchemeSymbol LAMBDA = makeSymbol("lambda");
    public static final SchemeSymbol QUOTE = makeSymbol("quote");
    public static final SchemeSymbol DEFINE = makeSymbol("define");
    public static final SchemeSymbol IF = makeSymbol("if");

    public final String name;

    private SchemeSymbol(String sym) {
        this.name = sym;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
