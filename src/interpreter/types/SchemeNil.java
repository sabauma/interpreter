package interpreter.types;

import java.util.ArrayList;
import java.util.List;

public class SchemeNil extends SchemeList
{
	public static final SchemeNil NIL = new SchemeNil();
	
	private SchemeNil() {}
	
	@Override
	public int length()
	{
		return 0;
	}

	@Override
	public List<SchemeObject> arrayView()
	{
		return new ArrayList<SchemeObject>();
	}
}
