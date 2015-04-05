package interpreter.node;

import interpreter.env.Environment;
import interpreter.types.SchemeObject;

public class VarNode extends SchemeNode
{
	private final String name;
	
	public VarNode(String name)
	{
		this.name = name;
	}

	@Override
	public SchemeObject eval(Environment env)
	{
		return env.lookup(this.name);
	}
	
	public String toString()
	{
		return this.name;
	}
}
