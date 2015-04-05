package interpreter.types;

import interpreter.Evaluatable;
import interpreter.env.Environment;
import interpreter.node.QuoteNode;
import interpreter.node.SchemeNode;

// This serves as the base class for function types.
// Unfortunately, Scheme types do not fit nicely into a single
// inheritance hierarchy, so SchemeObjects have a few methods
// attached to them that simply raise errors.
public abstract class SchemeObject implements Evaluatable
{	
	public SchemeObject eval(Environment env)
	{
		return this;
	}
	
	public SchemeObject call(SchemeObject[] args)
	{
		throw new RuntimeException(this.toString() + " is not callable");
	}
	
	// Most scheme values simply map to their quoted forms.
	// This should only be used to reconstruct the AST after
	// parsing.
	public SchemeNode asCode()
	{
		return new QuoteNode(this);
	}
	
	public boolean equal(SchemeObject obj)
	{
		return this == obj;
	}
}
