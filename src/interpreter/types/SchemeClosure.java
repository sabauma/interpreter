package interpreter.types;

import interpreter.node.SchemeNode;
import interpreter.node.SchemeRootNode;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.MaterializedFrame;

public class SchemeClosure extends SchemeObject
{
	public final RootCallTarget callTarget;
	private MaterializedFrame   env;
	
	public SchemeClosure(RootCallTarget callTarget)
	{
		this.callTarget = callTarget;
	}
	
	public void setLexicalScope(MaterializedFrame env)
	{
		this.env = env;
	}
	
	public static SchemeClosure create(FrameSlot[] args,
			SchemeNode[] bodyNodes, FrameDescriptor frameDescriptor)
	{
		return new SchemeClosure(
				Truffle.getRuntime().createCallTarget(
						SchemeRootNode.create(args, bodyNodes, frameDescriptor));
	}
	
	// A closure consists of its arguments, the code for the body
	// of the function, and the environment in which the lambda
	// was evaluated.
//	private final String[]    params;
//	public  final SchemeNode  code;
//	private final Environment env;
//	
//	public SchemeClosure(String[] params, SchemeNode code, Environment env)
//	{
//		this.params = params;
//		this.code   = code;
//		this.env    = env;
//	}
//	
//	public SchemeObject call(SchemeObject[] args)
//	{
//		if (args.length != this.params.length)
//		{
//			throw new RuntimeException("arity mismatch");
//		}
//		
//		Environment extended = new Environment(this.env);
//		for (int i = 0; i < args.length; ++i)
//		{
//			extended.extend(this.params[i], args[i]);
//		}
//		
//		return this.code.eval(extended);
//	}
//	
//	
//	public String toString()
//	{
//		return "#<procedure>";
//	}
}
