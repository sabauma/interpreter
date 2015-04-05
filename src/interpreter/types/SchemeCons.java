package interpreter.types;

import interpreter.node.AppNode;
import interpreter.node.LambdaNode;
import interpreter.node.QuoteNode;
import interpreter.node.SchemeNode;

import java.util.ArrayList;
import java.util.List;

public class SchemeCons extends SchemeList
{
	public final SchemeObject car;
	public final SchemeObject cdr;
	
	public static SchemeCons cons(SchemeObject car, SchemeObject cdr)
	{
		return new SchemeCons(car, cdr);
	}
	
	public SchemeCons(SchemeObject car, SchemeObject cdr)
	{
		this.car = car;
		this.cdr = cdr;
	}
	
	@Override
	public int length()
	{
		int i = 1;
		SchemeObject cur = this.cdr;
		while (cur instanceof SchemeCons)
		{
			cur = ((SchemeCons) cur).cdr;
			i += 1;
		}
		return i;
	}
	
	public SchemeNode asCode()
	{
		List<SchemeObject> view = this.arrayView();
		SchemeObject car = this.car;
		
		if (car.equal(SchemeSymbol.QUOTE))
		{
			assert view.size() == 2;
			// parse quote
			return new QuoteNode(view.get(1));
		}
		if (car.equal(SchemeSymbol.LET))
		{
			assert view.size() == 3;
			// parse let
		}
		if (car.equal(SchemeSymbol.LAMBDA))
		{
			assert view.size() == 3;
			// parse lambda
			SchemeObject bindings = view.get(1);
			if (!(bindings instanceof SchemeList))
			{
				throw new IllegalArgumentException("malformed let expression");
			}
			List<SchemeObject> bindingsArr = ((SchemeList) bindings).arrayView();
			return new LambdaNode(bindingsArr, view.get(2).asCode());
		}
		view = view.subList(1, view.size());
		
		SchemeNode[] args = new SchemeNode[view.size()];
		for (int i = 0; i < view.size(); ++i)
		{
			args[i] = view.get(i).asCode();
		}
		// parse application
		return new AppNode(car.asCode(), args);
	}

	@Override
	public List<SchemeObject> arrayView()
	{
		ArrayList<SchemeObject> retval = new ArrayList<SchemeObject>();
		SchemeObject cur = this.cdr;
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
	public boolean equal(SchemeObject obj)
	{
		if (obj instanceof SchemeCons)
		{
			SchemeCons pair = (SchemeCons) obj;
			return this.car.equal(pair.car) && this.cdr.equal(pair.cdr);
		}
		return false;
	}
	
}
