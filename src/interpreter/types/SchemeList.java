package interpreter.types;

import java.util.List;

public abstract class SchemeList extends Object {
    public abstract int length();

    public abstract List<Object> arrayView();

    public static SchemeList fromList(List<Object> vals)
    {
        SchemeList acc = SchemeNil.NIL;
        for (int i = vals.size() - 1; i >= 0; --i)
        {
            acc = SchemeCons.cons(vals.get(i), acc);
        }
        return acc;
    }

    @Override
    public String toString()
    {
        List<Object> lst = this.arrayView();
        StringBuilder b = new StringBuilder("(");
        for (Object o : lst)
        {
            b.append(o.toString()).append(" ");
        }
        return b.toString().trim() + ")";
    }
}
