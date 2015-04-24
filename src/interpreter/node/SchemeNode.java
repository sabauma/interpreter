package interpreter.node;


import interpreter.SchemeTypes;
import interpreter.SchemeTypesGen;
import interpreter.types.SchemeClosure;
import interpreter.types.SchemeCons;
import interpreter.types.SchemeNil;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

@TypeSystemReference(SchemeTypes.class)
@NodeInfo(language = "Scheme Language", description = "Abstract AST nodes")
public abstract class SchemeNode extends Node
{
	public abstract Object execute(VirtualFrame virtualFrame);
	
	public void setIsTail() { }
	
	public long executeLong(VirtualFrame virtualFrame)
		throws UnexpectedResultException
	{
		return SchemeTypesGen.expectLong(this.execute(virtualFrame));
	}
	
	public double executeFloat(VirtualFrame virtualFrame)
	    throws UnexpectedResultException
	{
	    return SchemeTypesGen.expectDouble(this.execute(virtualFrame));
	}
	
	public boolean executeBoolean(VirtualFrame virtualFrame)
			throws UnexpectedResultException
	{
		return SchemeTypesGen.expectBoolean(this.execute(virtualFrame));
	}
	
	public SchemeClosure executeSchemeClosure(VirtualFrame virtualFrame)
			throws UnexpectedResultException
	{
		return SchemeTypesGen.expectSchemeClosure(this.execute(virtualFrame));
	}
	
	public SchemeCons executeSchemeCons(VirtualFrame virtualFrame)
			throws UnexpectedResultException
	{
		return SchemeTypesGen.expectSchemeCons(this.execute(virtualFrame));
	}
	
	public SchemeNil executeSchemeNil(VirtualFrame virtualFrame)
			throws UnexpectedResultException
	{
		return SchemeTypesGen.expectSchemeNil(this.execute(virtualFrame));
	}
	
	protected boolean isArgumentIndexInRange(VirtualFrame env, int index)
	{
		return (index + 1) < env.getArguments().length;
	}
	
	protected Object getArgument(VirtualFrame env, int index)
	{
		return env.getArguments()[index + 1];
	}
	
	protected static MaterializedFrame getLexicalScope(Frame frame)
	{
		Object[] args = frame.getArguments();
		if (args.length > 0)
		{
			return (MaterializedFrame) args[0];
		}
		return null;
	}
}
