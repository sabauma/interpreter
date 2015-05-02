package interpreter.reader;

import interpreter.types.SchemeList;
import interpreter.types.SchemeSymbol;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.List;

public class Reader
{
    public static Object read(InputStream istream) throws IOException
    {
        return read(new PushbackReader(new InputStreamReader(istream)));
    }

    public static Object readExpr(InputStream istream) throws IOException
    {
        return readNode(new PushbackReader(new InputStreamReader(istream)));
    }
    
    public static Object[] readProgram(InputStream istream) throws IOException
    {
        PushbackReader reader = new PushbackReader(new InputStreamReader(istream));
        List<Object> vals = new ArrayList<>();
        
        while (true)
        {
            Reader.eatWhitespace(reader);
            int val = reader.read();
            if (val == -1 || val == 65535)
            {
                break;
            }
            reader.unread(val);
            vals.add(readNode(reader));
        }
        
        return vals.toArray();
    }

    private static Object read(PushbackReader pstream) throws IOException {
        List<Object> nodes = new ArrayList<Object>();

        eatWhitespace(pstream);
        char c = (char) pstream.read();
        while ((byte) c != -1) {
            pstream.unread(c);
            nodes.add(readNode(pstream));
            eatWhitespace(pstream);
            c = (char) pstream.read();
        }
        return SchemeList.fromList(nodes);
    }

    private static void eatWhitespace(PushbackReader pstream)
            throws IOException {
        char c = (char) pstream.read();
        while (Character.isWhitespace(c)) {
            c = (char) pstream.read();
        }
        pstream.unread(c);
    }

    private static Object readNode(PushbackReader pstream) throws IOException {
        char c = (char) pstream.read();
        pstream.unread(c);
        if (c == '(') {
            return readList(pstream);
        }
        if (Character.isDigit(c)) {
            return readNumber(pstream);
        }
        if (c == '#') {
            return readBoolean(pstream);
        }
        if (c == ')') {
            throw new IllegalArgumentException("Unmatched close paren");
        }
        return readSymbol(pstream);
    }

    private static SchemeSymbol readSymbol(PushbackReader pstream)
            throws IOException {
        StringBuilder b = new StringBuilder();
        char c = (char) pstream.read();
        while (!Character.isWhitespace(c) && (byte) c != -1 && c != '('
                && c != ')') {
            b.append(c);
            c = (char) pstream.read();
        }
        pstream.unread(c);
        return SchemeSymbol.makeSymbol(b.toString());
    }

    private static SchemeList readList(PushbackReader pstream)
            throws IOException {
        char paren = (char) pstream.read();
        assert paren == '(';
        List<Object> list = new ArrayList<>();
        do {
            eatWhitespace(pstream);
            char c = (char) pstream.read();

            if (c == ')') {
                break;
            }
            if ((byte) c == -1) {
                throw new EOFException("EOF reached before closing paren");
            }
            pstream.unread(c);
            list.add(readNode(pstream));
        } while (true);
        return SchemeList.fromList(list);
    }

    private static Object readNumber(PushbackReader pstream) throws IOException {
        StringBuilder b = new StringBuilder();
        char c = (char) pstream.read();
        while (Character.isDigit(c) || c == '.') {
            b.append(c);
            c = (char) pstream.read();
        }
        pstream.unread(c);
        try {
            return Long.valueOf(b.toString(), 10);
        } catch (NumberFormatException nfe) {
            return Double.valueOf(b.toString());
        }
    }

    private static final SchemeSymbol TRUE_SYM = SchemeSymbol.makeSymbol("t");
    private static final SchemeSymbol FALSE_SYM = SchemeSymbol.makeSymbol("f");

    private static Boolean readBoolean(PushbackReader pstream)
            throws IOException {
        char hash = (char) pstream.read();
        assert hash == '#';

        SchemeSymbol sym = readSymbol(pstream);
        if (TRUE_SYM.equals(sym)) {
            return Boolean.TRUE;
        }
        if (FALSE_SYM.equals(sym)) {
            return Boolean.FALSE;
        }
        throw new IllegalArgumentException("Unknown value: #" + sym.toString());
    }
}
