package interpreter.types;

import interpreter.env.Environment;
import interpreter.node.SchemeNode;

public class SchemeClosure extends SchemeObject
{
	// A closure consists of its arguments, the code for the body
	// of the function, and the environment in which the lambda
	// was evaluated.
	private final String[]    params;
	private final SchemeNode  code;
	private final Environment env;
	
	public SchemeClosure(String[] params, SchemeNode code, Environment env)
	{
		this.params = params;
		this.code   = code;
		this.env    = env;
	}
	
	public SchemeObject call(SchemeObject[] args)
	{
		if (args.length != this.params.length)
		{
			throw new RuntimeException("arity mismatch");
		}
		
		Environment extended = new Environment(this.env);
		for (int i = 0; i < args.length; ++i)
		{
			extended.extend(this.params[i], args[i]);
		}
		
		return this.code.eval(extended);
	}
	
	
	public String toString()
	{
		return "#<procedure>";
	}
}
