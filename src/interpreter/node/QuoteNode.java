package interpreter.node;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import interpreter.node.QuoteNode.QuoteKind;

@NodeChild("literalNode")
@NodeField(name = "kind", type = QuoteKind.class)
public abstract class QuoteNode extends SchemeNode
{
	public static enum QuoteKind
	{
		LONG,
		FLOAT,
		BOOLEAN,
		SYMBOL,
		CONS,
		NIL
	}
	
	protected abstract QuoteKind getKind();
	
	@Specialization(guards = "isLongKind")
	protected long quoteLong(VirtualFrame env, long value)
	{
		return value;
	}
	
	@Specialization(guards = "isFloatKind")
	protected double quoteFloat(VirtualFrame env, double value)
	{
		return value;
	}
	
	@Specialization(guards = "isBooleanKind")
	protected boolean quoteBoolean(VirtualFrame env, boolean value)
	{
		return value;
	}
	
	@Specialization(contains = {"quoteLong", "quoteFloat", "quoteBoolean"})
	protected Object quote(VirtualFrame env, Object value)
	{
		return value;
	}
	
	protected boolean isLongKind()
	{
		return this.getKind() == QuoteKind.LONG;
	}
	
	protected boolean isBooleanKind()
	{
		return this.getKind() == QuoteKind.BOOLEAN;
	}
	
	protected boolean isFloatKind()
	{
		return this.getKind() == QuoteKind.FLOAT;
	}
}
