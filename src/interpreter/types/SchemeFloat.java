package interpreter.types;

public class SchemeFloat extends SchemeObject
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

	@Override
	public boolean equal(SchemeObject obj)
	{
		if (obj instanceof SchemeFloat)
		{
			return this.value == ((SchemeFloat) obj).value;
		}
		return false;
	}
}
