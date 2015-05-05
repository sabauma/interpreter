package interpreter.node;

import com.oracle.truffle.api.frame.VirtualFrame;

public class FloatNode extends SchemeNode
{
	private final double value;
	
	public FloatNode(double value)
	{
		this.value = value;
	}
	
	@Override
	public double executeFloat(VirtualFrame env)
	{
	    return this.value;
	}

	@Override
	public Object execute(VirtualFrame env)
	{
		return this.value;
	}
}
