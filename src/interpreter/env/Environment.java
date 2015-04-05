package interpreter.env;

import java.util.HashMap;

public class Environment
{
	public static final Environment EMPTY_ENV = new Environment();
	
	private final HashMap<String, Object> env;
	private final Environment parent;
	
	public Environment()
	{
		this.env    = new HashMap<String, Object>();
		this.parent = null;
	}
	
	public Environment(Environment other)
	{
		this.env    = new HashMap<String, Object>();
		this.parent = other;
	}
	
	public Object lookup(String name)
	{
		if (this.env.containsKey(name))
		{
			return this.env.get(name);
		}
		if (this.parent != null)
		{
			return this.parent.lookup(name);
		}
		throw new RuntimeException("Unbound identifier " + name);
	}
	
	public void extend(String name, Object val)
	{
		this.env.put(name, val);
	}
}
