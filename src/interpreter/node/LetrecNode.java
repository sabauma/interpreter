package interpreter.node;

import interpreter.env.Environment;
import interpreter.types.SchemeObject;

public class LetrecNode extends SchemeNode
{
	private final String[] lhss;
	private final SchemeNode[] rhss;
	private final SchemeNode body;

	public LetrecNode(String[] lhss, SchemeNode[] rhss, SchemeNode body)
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
		for (String s : this.lhss)
		{
			extended.extendUndefined(s);
		}
		
		for (int i = 0; i < this.lhss.length; ++i)
		{
			SchemeObject result = this.rhss[i].eval(extended);
			extended.extend(this.lhss[i], result);
		}
		
		return this.body.eval(extended);
	}
}
