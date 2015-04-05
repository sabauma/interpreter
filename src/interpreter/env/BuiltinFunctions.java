package interpreter.env;

import interpreter.types.SchemeInt;
import interpreter.types.SchemeNumber;
import interpreter.types.SchemeObject;

public class BuiltinFunctions
{
	public static SchemeNumber[] coerceArray(SchemeObject[] args, String errname)
	{
		for (SchemeObject o : args)
		{
			if (!(o instanceof SchemeNumber))
			{
				throw new IllegalArgumentException("errname: " + o.toString() + " is not a number");
			}
		}
		return (SchemeNumber[]) args;
	}
	
	public static final class Plus extends SchemeObject
	{
		public SchemeObject call(SchemeObject[] args)
		{
			SchemeNumber acc = new SchemeInt(0);
			for (SchemeObject o : args)
			{
				if (!(o instanceof SchemeNumber))
				{
					throw new IllegalArgumentException("+: " + o.toString() + " is not a number");
				}
				acc = ((SchemeNumber) o).add(acc);
			}
			return acc;
		}
	}
	
	public static final class Times extends SchemeObject
	{
		public SchemeObject call(SchemeObject[] args)
		{
			SchemeNumber acc = new SchemeInt(1);
			for (SchemeObject o : args)
			{
				if (!(o instanceof SchemeNumber))
				{
					throw new IllegalArgumentException("*: " + o.toString() + " is not a number");
				}
				acc = ((SchemeNumber) o).mul(acc);
			}
			return acc;
		}
	}
	
	public static final Environment BaseEnv = new Environment();
	static
	{
		BaseEnv.extend("+", new Plus());
		BaseEnv.extend("*", new Times());
	}
}
