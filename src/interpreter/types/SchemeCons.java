package interpreter.types;

import java.util.ArrayList;
import java.util.List;

public class SchemeCons extends SchemeList
{
	public final Object car;
	public final Object cdr;
	
	public static SchemeCons cons(Object car, Object cdr)
	{
		return new SchemeCons(car, cdr);
	}
	
	public SchemeCons(Object car, Object cdr)
	{
		this.car = car;
		this.cdr = cdr;
	}
	
	@Override
	public int length()
	{
		int i = 1;
		Object cur = this.cdr;
		while (cur instanceof SchemeCons)
		{
			cur = ((SchemeCons) cur).cdr;
			i += 1;
		}
		return i;
	}

	@Override
	public List<Object> arrayView()
	{
		ArrayList<Object> retval = new ArrayList<Object>();
		Object cur = this.cdr;
		retval.add(this.car);
		while (cur instanceof SchemeCons)
		{
			SchemeCons pair = (SchemeCons) cur;
			retval.add(pair.car);
			cur = pair.cdr;
		}
		return retval;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof SchemeCons
			&& this.car.equals(((SchemeCons) obj).car)
			&& this.cdr.equals(((SchemeCons) obj).cdr);
	}
}
