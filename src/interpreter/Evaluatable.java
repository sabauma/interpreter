package interpreter;

import interpreter.env.Environment;
import interpreter.types.SchemeObject;

public interface Evaluatable
{
	public SchemeObject eval(Environment env);
}
