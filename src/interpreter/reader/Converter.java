package interpreter.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import interpreter.node.BooleanNode;
import interpreter.node.DefineNode;
import interpreter.node.DefineNodeGen;
import interpreter.node.FloatNode;
import interpreter.node.IfNode;
import interpreter.node.IntNode;
import interpreter.node.LambdaNode;
import interpreter.node.LambdaNodeGen;
import interpreter.node.QuoteNode;
import interpreter.node.QuoteNode.QuoteKind;
import interpreter.node.call.AppNode;
import interpreter.node.QuoteNodeGen;
import interpreter.node.SchemeNode;
import interpreter.node.VarNode;
import interpreter.node.VarNodeGen;
import interpreter.types.SchemeClosure;
import interpreter.types.SchemeList;
import interpreter.types.SchemeNil;
import interpreter.types.SchemeSymbol;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;

public class Converter
{
	public static SchemeNode convert(Object obj, FrameDescriptor desc)
	{
		if (obj instanceof Long)
			return convert((long) obj, desc);
		if (obj instanceof Boolean)
			return convert((boolean) obj, desc);
		if (obj instanceof Double)
			return convert((double) obj, desc);
		if (obj instanceof SchemeSymbol)
			return convert((SchemeSymbol) obj, desc);
		if (obj instanceof SchemeList)
			return convert((SchemeList) obj, desc);
		
		throw new IllegalArgumentException("Unknown type: " + obj.getClass());
	}
	
	public static IntNode convert(long n, FrameDescriptor desc)
	{
		return new IntNode(n);
	}
	
	public static BooleanNode convert(boolean b, FrameDescriptor desc)
	{
		return b ? BooleanNode.TRUE : BooleanNode.FALSE;
	}
	
	public static FloatNode convert(double d, FrameDescriptor desc)
	{
		return new FloatNode(d);
	}
	
	public static VarNode convert(SchemeSymbol sym, FrameDescriptor d)
	{
		return VarNodeGen.create(d.findOrAddFrameSlot(sym.name));
	}
	
	public static SchemeNode convert(SchemeList list, FrameDescriptor d)
	{
		// Need to handle application, define, lambda, if, and quote.
		List<Object> lst = list.arrayView();
		Object car       = lst.get(0);
		List<Object> cdr = lst.subList(1, lst.size());
		
		if (car == SchemeSymbol.LAMBDA)
			return convertLambda(lst, d);
		if (car == SchemeSymbol.QUOTE)
			return convertQuote(lst, d);
		if (car == SchemeSymbol.DEFINE)
			return convertDefine(lst, d);
		if (car == SchemeSymbol.IF)
			return convertIf(lst, d);
		
		// This is pretty slick for Java
		return new AppNode(convert(car, d),
				StreamSupport.stream(cdr.spliterator(), false)
				.map(o -> convert(o, d))
				.toArray(size -> new SchemeNode[size]));
	}
	
	public static DefineNode convertDefine(List<Object> list, FrameDescriptor d)
	{
		SchemeSymbol sym = (SchemeSymbol) list.get(1);
		Object body      = list.get(2);
		return DefineNodeGen.create(convert(body, d), d.findOrAddFrameSlot(sym.name));
	}
	
	public static IfNode convertIf(List<Object> list, FrameDescriptor d)
	{
		return new IfNode(
				convert(list.get(1), d),
				convert(list.get(2), d),
				convert(list.get(3), d));
	}
	
	public static LambdaNode convertLambda(List<Object> list, FrameDescriptor d)
	{
		FrameDescriptor env     = d.copy(); //new FrameDescriptor();
		List<FrameSlot> formals = new ArrayList<>();
		for (Object arg : ((SchemeList) list.get(1)).arrayView())
		{
			formals.add(convert((SchemeSymbol) arg, env).getSlot());
		}
		
		List<SchemeNode> body = new ArrayList<>();
		for (Object b : list.subList(2, list.size()))
		{
			body.add(convert(b, env));
		}
		body.get(body.size() - 1).setIsTail();
		
		SchemeClosure function = SchemeClosure.create(
				formals.toArray(new FrameSlot[] {}),
				body.toArray(new SchemeNode[] {}),
				env);
		return LambdaNodeGen.create(function);
	}
	
	public static QuoteNode convertQuote(List<Object> list, FrameDescriptor d)
	{
		Object value = list.get(1);
		SchemeNode node = null;
		QuoteKind kind;
		if (value instanceof Long)
		{
			kind = QuoteKind.LONG;
			node = convert((long) value, d);
		}
		else if (value instanceof Boolean)
		{
			kind = QuoteKind.BOOLEAN;
			node = convert((boolean) value, d);
		}
		else if (value instanceof Double)
		{
			kind = QuoteKind.FLOAT;
			node = convert((double) value, d);
		}
		else if (value instanceof SchemeSymbol)
		{
			kind = QuoteKind.SYMBOL;
			node = convert((SchemeSymbol) value, d);
		}
		else if (value instanceof SchemeList)
		{
			kind = value instanceof SchemeNil ? QuoteKind.NIL : QuoteKind.CONS;
			node = convert((SchemeList) value, d);
		}
		else
		{
			throw new IllegalArgumentException("Unknown quoted value: " + value.getClass());
		}
		return QuoteNodeGen.create(node, kind);
	}
}
