package interpreter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import interpreter.env.BuiltinFunctions;
import interpreter.env.Environment;
import interpreter.node.QuoteNode;
import interpreter.node.SchemeNode;
import interpreter.reader.Reader;
import interpreter.types.SchemeInt;
import interpreter.types.SchemeObject;

public class Main
{
	public static String input = "((lambda (x) (* (+ x x) x x)) 17)";
	public static void main(String[] args) throws IOException
	{
		InputStream istream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		SchemeObject n = Reader.readExpr(istream);
		System.out.println(n.toString());
		System.out.println(n.asCode());
		System.out.println(n.asCode().eval(BuiltinFunctions.BaseEnv));
	}
}
