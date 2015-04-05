package interpreter.node;

import interpreter.env.Environment;
import interpreter.types.SchemeInt;
import interpreter.types.SchemeObject;

public class IntNode extends SchemeNode
{
	private final long value;
	
	public IntNode(long value)
	{
		this.value = value;
	}
	
	@Override
	public SchemeObject eval(Environment env)
	{
		return new SchemeInt(this.value);
	}

}
