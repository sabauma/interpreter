package interpreter.node;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

import interpreter.types.SchemeClosure;

@NodeField(name = "function", type = SchemeClosure.class)
public abstract class LambdaNode extends SchemeNode
{
	public abstract SchemeClosure getFunction();
	
	private boolean scopeSet = false;
	
	@Specialization(guards = "isScopeSet")
	public SchemeClosure getScopedFunction(VirtualFrame env)
	{
		return this.getFunction();
	}
	
	@Specialization(contains = {"getScopedFunction"})
	public Object getSchemeFunction(VirtualFrame env)
	{
		SchemeClosure func = this.getFunction();
		func.setLexicalScope(env.materialize());
		return func;
	}
	
	protected boolean isScopeSet()
	{
		return this.scopeSet;
	}
	
	public static SchemeClosure createSchemeFunction(
			RootNode rootNode, VirtualFrame currentFrame)
	{
		return new SchemeClosure(
				Truffle.getRuntime().createCallTarget(rootNode));
	}
	
//	private final String[] formals;
//	private final SchemeNode body;
//	
//	public LambdaNode(String[] formals, SchemeNode body)
//	{
//		this.formals = formals;
//		this.body    = body;
//	}
//	
//	public LambdaNode(List<SchemeObject> formals, SchemeNode body)
//	{
//		this.body = body;
//		String[] fmls = new String[formals.size()];
//		for (int i = 0; i < formals.size(); ++i)
//		{
//			fmls[i] = ((SchemeSymbol) formals.get(i)).toString();
//		}
//		this.formals = fmls;
//	}
//	
//	@Override
//	public SchemeObject eval(Environment env)
//	{
//		return new SchemeClosure(this.formals, this.body, env);
//	}
//	
//	public String toString()
//	{
//		String args = Arrays.toString(this.formals).replace(", ", " ");
//		
//		return "(lambda (" + args.substring(1, args.length() - 1) + ") " + body.toString() + ")";
//	}
}
