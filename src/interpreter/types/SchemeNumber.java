package interpreter.types;

public abstract class SchemeNumber extends SchemeObject
{
	public abstract SchemeNumber add(SchemeNumber other);
	public abstract SchemeNumber mul(SchemeNumber other);
	
	public abstract SchemeNumber addInt(long value);
	public abstract SchemeNumber addFloat(double val);
	
	public abstract SchemeNumber mulInt(long value);
	public abstract SchemeNumber mulFloat(double val);
}
