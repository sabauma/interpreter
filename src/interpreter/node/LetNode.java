package interpreter.node;

import interpreter.env.Environment;
import interpreter.types.SchemeObject;

public class LetNode extends SchemeNode
{
	private final String[]     lhss;
	private final SchemeNode[] rhss;
	private final SchemeNode   body;

	public LetNode(String[] lhss, SchemeNode[] rhss, SchemeNode body)
	{
		assert lhss.length == rhss.length;
		this.lhss = lhss;
		this.rhss = rhss;
		this.body = body;
	}
	
	@Override
	public SchemeObject eval(Environment env)
	{
		Environment extended = new Environment(env);
		for (int i = 0; i < this.lhss.length; ++i)
		{
			extended.extend(this.lhss[i], this.rhss[i].eval(env));
		}
		return this.body.eval(extended);
	}
	
	public String toString()
	{
		String ret = "(let (";
		for (int i = 0; i < this.lhss.length; ++i)
		{
			ret += " " + "[" + this.lhss[i] + " " + this.rhss[i].toString() + "]";
		}
		ret += ") " + this.body.toString() + ")";
		return ret;
	}
}
