package interpreter.types;

import interpreter.node.IntNode;
import interpreter.node.SchemeNode;

public class SchemeInt extends SchemeNumber
{
	private final long value;
	
	public SchemeInt(long value)
	{
		this.value = value;
	}
	
	public String toString()
	{
		return Long.toString(this.value);
	}
	
	public SchemeNode asCode()
	{
		return new IntNode(this.value);
	}
	
	@Override
	public boolean equals(Object other)
	{
		return other instanceof SchemeInt
			&& this.value == ((SchemeInt) other).value;
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
	public SchemeNumber addInt(long val)
	{
		return new SchemeInt(this.value + val);
	}

	@Override
	public SchemeNumber addFloat(double val) {
		return new SchemeFloat((double) this.value + val);
	}

	@Override
	public SchemeNumber mulInt(long val)
	{
		return new SchemeInt(this.value * val);
	}

	@Override
	public SchemeNumber mulFloat(double val)
	{
		return new SchemeFloat((double) this.value * val);
	}
}
