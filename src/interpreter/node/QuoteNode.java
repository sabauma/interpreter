package interpreter.node;

import interpreter.env.Environment;
import interpreter.types.SchemeObject;

public class QuoteNode extends SchemeNode
{
	private final SchemeObject value;
	
	public QuoteNode(SchemeObject val)
	{
		this.value = val;
	}
	
	@Override
	public SchemeObject eval(Environment env)
	{
		return this.value;
	}
	
	public String toString()
	{
		return "'" + this.value.toString();
	}
}
