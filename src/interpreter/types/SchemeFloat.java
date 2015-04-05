package interpreter.types;

import interpreter.node.FloatNode;
import interpreter.node.SchemeNode;

public class SchemeFloat extends SchemeNumber
{
	private final double value;
	
	public SchemeFloat(double val)
	{
		this.value = val;
	}
	
	public String toString()
	{
		return Double.toString(this.value);
	}

	public SchemeNode asCode()
	{
		return new FloatNode(this.value);
	}
	
	@Override
	public boolean equal(SchemeObject obj)
	{
		if (obj instanceof SchemeFloat)
		{
			return this.value == ((SchemeFloat) obj).value;
		}
		return false;
	}

	@Override
	public SchemeNumber add(SchemeNumber other)
	{
		return other.addFloat(this.value);
	}

	@Override
	public SchemeNumber mul(SchemeNumber other)
	{
		return other.mulFloat(this.value);
	}

	@Override
	public SchemeNumber addInt(int val)
	{
		return new SchemeFloat(this.value + (double) val);
	}

	@Override
	public SchemeNumber addFloat(double val)
	{
		return new SchemeFloat(this.value + val);
	}

	@Override
	public SchemeNumber mulInt(int val)
	{
		return new SchemeFloat(this.value * (double) val);
	}

	@Override
	public SchemeNumber mulFloat(double val)
	{
		return new SchemeFloat(this.value * val);
	}
}
