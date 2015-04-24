package interpreter.node;

import com.oracle.truffle.api.frame.VirtualFrame;

public class BooleanNode extends SchemeNode
{
	private final boolean value;
	
	public static final BooleanNode TRUE  = new BooleanNode(true);
	public static final BooleanNode FALSE = new BooleanNode(false);
	
	private BooleanNode(boolean value)
	{
		this.value = value;
	}
	
	@Override
	public boolean executeBoolean(VirtualFrame env)
	{
		return this.value;
	}
	
	@Override
	public Object execute(VirtualFrame env)
	{
		return this.value;
	}
	
	@Override
	public String toString()
	{
		return "" + this.value;
	}
}
