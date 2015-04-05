package interpreter.node;

import interpreter.env.Environment;
import interpreter.types.SchemeBoolean;
import interpreter.types.SchemeObject;

public class IfNode extends SchemeNode
{
	private final SchemeNode tst;
	private final SchemeNode thn;
	private final SchemeNode alt;
	
	public IfNode(SchemeNode tst, SchemeNode thn, SchemeNode alt)
	{
		this.tst = tst;
		this.thn = thn;
		this.alt = alt;
	}
	
	@Override
	public SchemeObject eval(Environment env)
	{
		Object pred = this.tst.eval(env);
		return pred == SchemeBoolean.FALSE ? this.alt.eval(env) : this.thn.eval(env);
	}
	
	public String toString()
	{
		return "(if (" + this.tst.toString() + ") " 
			 + this.thn.toString() 
			 + " " + this.alt.toString();
	}
}
