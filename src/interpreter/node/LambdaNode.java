package interpreter.node;

import java.util.Arrays;
import java.util.List;

import interpreter.env.Environment;
import interpreter.types.SchemeClosure;
import interpreter.types.SchemeObject;
import interpreter.types.SchemeSymbol;

public class LambdaNode extends SchemeNode
{
	private final String[] formals;
	private final SchemeNode body;
	
	public LambdaNode(String[] formals, SchemeNode body)
	{
		this.formals = formals;
		this.body    = body;
	}
	
	public LambdaNode(List<SchemeObject> formals, SchemeNode body)
	{
		this.body = body;
		String[] fmls = new String[formals.size()];
		for (int i = 0; i < formals.size(); ++i)
		{
			fmls[i] = ((SchemeSymbol) formals.get(i)).toString();
		}
		this.formals = fmls;
	}
	
	@Override
	public SchemeObject eval(Environment env)
	{
		return new SchemeClosure(this.formals, this.body, env);
	}
	
	public String toString()
	{
		String args = Arrays.toString(this.formals).replace(", ", " ");
		
		return "(lambda (" + args.substring(1, args.length() - 1) + ") " + body.toString() + ")";
	}
}
