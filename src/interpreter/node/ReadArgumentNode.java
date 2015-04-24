package interpreter.node;

import com.oracle.truffle.api.frame.VirtualFrame;

public class ReadArgumentNode extends SchemeNode
{
	public final int argumentIndex;
	
	public ReadArgumentNode(int argumentIndex)
	{
		this.argumentIndex = argumentIndex;
	}
	
	@Override
	public Object execute(VirtualFrame env)
	{
		if (!this.isArgumentIndexInRange(env, this.argumentIndex))
		{
			throw new IllegalArgumentException("insufficient arguments");
		}
		return this.getArgument(env, this.argumentIndex);
	}
}
