package interpreter.node;

import interpreter.env.Environment;
import interpreter.types.SchemeFloat;
import interpreter.types.SchemeObject;

public class FloatNode extends SchemeNode
{
	private final double value;
	
	public FloatNode(double value)
	{
		this.value = value;
	}

	@Override
	public SchemeObject eval(Environment env)
	{
		return new SchemeFloat(this.value);
	}
}
