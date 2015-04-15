package interpreter.node;

import com.oracle.truffle.api.frame.VirtualFrame;

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
	public Object execute(VirtualFrame env)
	{
		VirtualFrame extended = new VirtualFrame();
		for (int i = 0; i < this.lhss.length; ++i)
		{
			extended.extend(this.lhss[i], this.rhss[i].execute(env));
		}
		return this.body.execute(extended);
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
