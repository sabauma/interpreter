package interpreter.node;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.utilities.ConditionProfile;

public class IfNode extends SchemeNode
{
	@Child private final SchemeNode tst;
	@Child private final SchemeNode thn;
	@Child private final SchemeNode alt;
	
	private final ConditionProfile conditionProfile =
			ConditionProfile.createBinaryProfile();
	
	public IfNode(SchemeNode tst, SchemeNode thn, SchemeNode alt)
	{
		this.tst = tst;
		this.thn = thn;
		this.alt = alt;
	}
	
	@Override
	public Object execute(VirtualFrame env)
	{
		if (this.conditionProfile.profile(this.testResult(env)))
		{
			return this.thn.execute(env);
		}
		else
		{
			return this.alt.execute(env);
		}
	}
	
	private boolean testResult(VirtualFrame env)
	{
		try
		{
			return this.tst.executeBoolean(env);
		}
		catch (UnexpectedResultException e)
		{
			this.tst.execute(env);
			return false;
		}
	}
	
	public String toString()
	{
		return "(if (" + this.tst.toString() + ") " 
			 + this.thn.toString() 
			 + " " + this.alt.toString();
	}
}
