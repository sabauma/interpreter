package interpreter;

import interpreter.types.SchemeClosure;
import interpreter.types.SchemeCons;
import interpreter.types.SchemeNil;
import interpreter.types.SchemeSymbol;

import com.oracle.truffle.api.dsl.TypeSystem;


@TypeSystem({long.class,
			 boolean.class,
			 double.class,
			 SchemeClosure.class,
			 SchemeSymbol.class,
		     SchemeCons.class,
		     SchemeNil.class})
public abstract class SchemeTypes { }
