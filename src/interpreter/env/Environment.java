package interpreter.env;

import interpreter.types.SchemeObject;
import java.util.HashMap;

public class Environment
{
	public static final Environment EMPTY_ENV = new Environment();
	
	// A mapping from names to scheme object.
	// A null value associated with a key indicates
	// an undefined value in the environment.
	private final HashMap<String, SchemeObject> env;
	private final Environment parent;
	
	public Environment()
	{
		this.env    = new HashMap<String, SchemeObject>();
		this.parent = null;
	}
	
	public Environment(Environment other)
	{
		this.env    = new HashMap<String, SchemeObject>();
		this.parent = other;
	}
	
	public SchemeObject lookup(String name)
	{
		if (this.env.containsKey(name))
		{
			SchemeObject result = this.env.get(name);
			if (result == null)
			{
				throw new RuntimeException(name + ": not yet defined");
			}
			return this.env.get(name);
		}
		if (this.parent != null)
		{
			return this.parent.lookup(name);
		}
		throw new RuntimeException("Unbound identifier " + name);
	}
	
	public void extend(String name, SchemeObject val)
	{
		this.env.put(name, val);
	}
	
	public void extendUndefined(String name)
	{
		this.env.put(name, null);
	}
}
