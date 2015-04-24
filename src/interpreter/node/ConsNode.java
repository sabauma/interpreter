package interpreter.node;

import interpreter.types.SchemeCons;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "cons")
@GenerateNodeFactory
public abstract class ConsNode extends BuiltinNode
{
    @Specialization
    public SchemeCons cons(Object car, Object cdr)
    {
        return SchemeCons.cons(car, cdr);
    }
}
