package interpreter.types;

public class SchemeBoolean extends SchemeObject
{
	public static final SchemeBoolean TRUE = new SchemeBoolean();
	public static final SchemeBoolean FALSE = new SchemeBoolean();
	private SchemeBoolean() {}
	
	public static SchemeBoolean makeBool(Boolean b)
	{
		if (b == Boolean.TRUE)
		{
			return SchemeBoolean.TRUE;
		}
		else
		{
			return SchemeBoolean.FALSE;
		}
	}
	
	public boolean equal(SchemeObject other)
	{
		return this == other;
	}
	
	public String toString()
	{
		return this == SchemeBoolean.TRUE ? "#t" : "#f";
	}
}
