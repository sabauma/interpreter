package interpreter.node;

import com.oracle.truffle.api.frame.VirtualFrame;

public class IntNode extends SchemeNode
{
	private final long value;
	
	public IntNode(long value)
	{
		this.value = value;
	}
	
	@Override
	public long executeLong(VirtualFrame env)
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
