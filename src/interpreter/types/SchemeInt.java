package interpreter.types;

import interpreter.node.IntNode;
import interpreter.node.SchemeNode;

public class SchemeInt extends SchemeNumber
{
	private final int value;
	
	public SchemeInt(int val)
	{
		this.value = val;
	}
	
	public String toString()
	{
		return Integer.toString(this.value);
	}
	
	public SchemeNode asCode()
	{
		return new IntNode(this.value);
	}

	@Override
	public boolean equal(SchemeObject obj)
	{
		if (obj instanceof SchemeInt)
		{
			return this.value == ((SchemeInt) obj).value;
		}
		return false;
	}

	@Override
	public SchemeNumber add(SchemeNumber other)
	{
		return other.addInt(this.value);
	}

	@Override
	public SchemeNumber mul(SchemeNumber other)
	{
		return other.mulInt(this.value);
	}

	@Override
	public SchemeNumber addInt(int val)
	{
		return new SchemeInt(this.value + val);
	}

	@Override
	public SchemeNumber addFloat(double val) {
		return new SchemeFloat((double) this.value + val);
	}

	@Override
	public SchemeNumber mulInt(int val)
	{
		return new SchemeInt(this.value * val);
	}

	@Override
	public SchemeNumber mulFloat(double val)
	{
		return new SchemeFloat((double) this.value * val);
	}
}
