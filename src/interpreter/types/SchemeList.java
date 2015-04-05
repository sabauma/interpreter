package interpreter.types;

import java.util.List;

public abstract class SchemeList extends SchemeObject
{	
	public abstract int length();
	public abstract List<SchemeObject> arrayView();
	
	public static SchemeList fromList(List<SchemeObject> vals)
	{
		SchemeList acc = SchemeNil.NIL;
		for (int i = vals.size() - 1; i >= 0; --i)
		{
			acc = SchemeCons.cons(vals.get(i), acc);
		}
		return acc;
	}
	
	public String toString()
	{
		// TODO: Handle improper lists
		List<SchemeObject> lst = this.arrayView();
		StringBuilder b = new StringBuilder("(");
		for (SchemeObject o : lst)
		{
			b.append(o.toString()).append(" ");
		}
		return b.toString().trim() + ")";
	}
}
