package interpreter.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import interpreter.env.BuiltinFunctions;
import interpreter.env.Environment;
import interpreter.node.LambdaNode;
import interpreter.node.SchemeNode;
import interpreter.node.QuoteNode;
import interpreter.node.call.AppNode;
import interpreter.reader.Reader;
import interpreter.types.SchemeInt;
import interpreter.types.SchemeBoolean;
import interpreter.types.SchemeObject;

import org.junit.Test;

public class TestObjects
{
	public static final SchemeInt five       = new SchemeInt(5);
	public static final SchemeNode fiveNode  = new QuoteNode(five);
	public static final SchemeNode falseNode = new QuoteNode(SchemeBoolean.FALSE);
	public static final SchemeNode[] vals    = { fiveNode };
	public static final String input         = "((lambda (x) (* (+ x x) x x)) 17)";
	
	@Test
	public void testEvalNum()
	{
		assertSame(five, fiveNode.eval(Environment.EMPTY_ENV));
	}
	
	@Test
	public void testEvalBool()
	{
		assertSame(SchemeBoolean.FALSE, falseNode.eval(Environment.EMPTY_ENV));
	}
	
	@Test
	public void testEvalLambda()
	{
		String[] args = {"a"};
		SchemeNode n = new LambdaNode(args, fiveNode);
		SchemeNode m = new AppNode(n, vals);
		assertEquals("#<procedure>", n.eval(Environment.EMPTY_ENV).toString());
		assertEquals(five, m.eval(Environment.EMPTY_ENV));
	}
	
	@Test
	public void testParsing() throws IOException
	{
		InputStream istream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		SchemeObject n = Reader.readExpr(istream);
		SchemeObject result = n.asCode().eval(BuiltinFunctions.BaseEnv);
		assertEquals(result, new SchemeInt(9826));
	}
}
