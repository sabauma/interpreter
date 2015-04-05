package interpreter.test;

import static org.junit.Assert.*;
import interpreter.env.Environment;
import interpreter.node.AppNode;
import interpreter.node.LambdaNode;
import interpreter.node.SchemeNode;
import interpreter.node.QuoteNode;
import interpreter.types.SchemeInt;
import interpreter.types.SchemeBoolean;

import org.junit.Test;

public class TestObjects
{
	public final SchemeInt five       = new SchemeInt(5);
	public final SchemeNode fiveNode  = new QuoteNode(five);
	public final SchemeNode falseNode = new QuoteNode(SchemeBoolean.FALSE);
	public final SchemeNode[] vals    = { fiveNode };
	
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
}
