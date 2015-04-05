package interpreter.types;

public class SchemeInt extends SchemeObject
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

	@Override
	public boolean equal(SchemeObject obj)
	{
		if (obj instanceof SchemeInt)
		{
			return this.value == ((SchemeInt) obj).value;
		}
		return false;
	}
}
