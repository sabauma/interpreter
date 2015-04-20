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
	
	@Specialization(guards = "isScopeSet()")
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
}
