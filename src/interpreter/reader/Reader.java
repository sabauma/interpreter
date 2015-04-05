package interpreter.reader;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.List;

import interpreter.types.SchemeBoolean;
import interpreter.types.SchemeFloat;
import interpreter.types.SchemeInt;
import interpreter.types.SchemeList;
import interpreter.types.SchemeObject;
import interpreter.types.SchemeSymbol;

public class Reader
{
	public static SchemeObject read(InputStream istream) throws IOException
	{
		return read(new PushbackReader(new InputStreamReader(istream)));
	}
	
	public static SchemeObject readExpr(InputStream istream) throws IOException
	{
		return readNode(new PushbackReader(new InputStreamReader(istream)));
	}
	
	private static SchemeObject read(PushbackReader pstream) throws IOException
	{
		List<SchemeObject> nodes = new ArrayList<SchemeObject>();
		
		eatWhitespace(pstream);
		char c = (char) pstream.read();
		while ((byte) c != -1)
		{
			pstream.unread(c);
			nodes.add(readNode(pstream));
			eatWhitespace(pstream);
			c = (char) pstream.read();
		}
		return SchemeList.fromList(nodes);
	}
	
	private static void eatWhitespace(PushbackReader pstream) throws IOException
	{
		char c = (char) pstream.read();
		while (Character.isWhitespace(c))
		{
			c = (char) pstream.read();
		}
		pstream.unread(c);
	}
	
	private static SchemeObject readNode(PushbackReader pstream) throws IOException
	{
		char c = (char) pstream.read();
		pstream.unread(c);
		if (c == '(')
		{
			return readList(pstream);
		}
		if (Character.isDigit(c))
		{
			return readNumber(pstream);
		}
		if (c == '#')
		{
			return readBoolean(pstream);
		}
		if (c == ')')
		{
			throw new IllegalArgumentException("Unmatched close paren");
		}
		return readSymbol(pstream);
	}
	
	private static SchemeSymbol readSymbol(PushbackReader pstream) throws IOException
	{
		StringBuilder b = new StringBuilder();
		char c = (char) pstream.read();
		while (!Character.isWhitespace(c) && (byte) c != -1 && c != '(' && c != ')')
		{
			b.append(c);
			c = (char) pstream.read();
		}
		pstream.unread(c);
		return SchemeSymbol.makeSymbol(b.toString());
	}
	
	private static SchemeList readList(PushbackReader pstream) throws IOException
	{
		char paren = (char) pstream.read();
		assert paren == '(';
		List<SchemeObject> list = new ArrayList<SchemeObject>();
		do
		{
			eatWhitespace(pstream);
			char c = (char) pstream.read();
			
			if (c == ')')
			{
				break;
			}
			if ((byte) c == -1)
			{
				throw new EOFException("EOF reached before closing paren");
			}
			pstream.unread(c);
			list.add(readNode(pstream));
		} while (true);
		return SchemeList.fromList(list);
	}
	
	private static SchemeObject readNumber(PushbackReader pstream) throws IOException
	{
		StringBuilder b = new StringBuilder();
		char c = (char) pstream.read();
		while (Character.isDigit(c) || c == '.')
		{
			b.append(c);
			c = (char) pstream.read();
		}
		pstream.unread(c);
		try
		{
			return new SchemeInt(Integer.valueOf(b.toString(), 10));
		}
		catch (NumberFormatException nfe)
		{
			return new SchemeFloat(Double.valueOf(b.toString()));
		}
	}
	
	private static final SchemeSymbol TRUE_SYM = SchemeSymbol.makeSymbol("t");
	private static final SchemeSymbol FALSE_SYM = SchemeSymbol.makeSymbol("f");
	
	private static SchemeBoolean readBoolean(PushbackReader pstream) throws IOException
	{
		char hash = (char) pstream.read();
		assert hash == '#';
		
		SchemeSymbol sym = readSymbol(pstream);
		if (TRUE_SYM.equals(sym))
		{
			return SchemeBoolean.TRUE;
		}
		if (FALSE_SYM.equals(sym))
		{
			return SchemeBoolean.FALSE;
		}
		throw new IllegalArgumentException("Unknown value: #" + sym.toString());
	}
}
