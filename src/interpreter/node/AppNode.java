package interpreter.node;

import com.oracle.truffle.api.nodes.Node.Child;
import com.oracle.truffle.api.nodes.Node.Children;

import interpreter.env.Environment;
import interpreter.types.SchemeObject;

public class AppNode extends SchemeNode
{
	@Child    private final SchemeNode   rator;
	@Children private final SchemeNode[] rands;
	
	public AppNode(SchemeNode rator, SchemeNode[] rands)
	{
		this.rator = rator;
		this.rands = rands;
	}

	@Override
	public SchemeObject eval(Environment env)
	{
		SchemeObject[] args = new SchemeObject[this.rands.length];
		SchemeObject func   = this.rator.eval(env);
		
		for (int i = 0; i < this.rands.length; ++i)
		{
			args[i] = this.rands[i].eval(env);
		}
		
		return func.call(args);
	}
	
	public String toString()
	{
		String ret = "(" + this.rator.toString();
		for (int i = 0; i < this.rands.length; ++i)
		{
			ret += " " + this.rands[i].toString();
		}
		return ret + ")";
	}
}
