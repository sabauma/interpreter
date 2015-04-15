package interpreter.node;

import com.oracle.truffle.api.frame.VirtualFrame;

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
	public Object execute(VirtualFrame env)
	{
		VirtualFrame extended = env;
		for (String s : this.lhss)
		{
			extended.extendUndefined(s);
		}
		
		for (int i = 0; i < this.lhss.length; ++i)
		{
			SchemeObject result = this.rhss[i].execute(extended);
			extended.extend(this.lhss[i], result);
		}
		
		return this.body.execute(extended);
	}
}
