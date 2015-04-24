package interpreter.node;

import interpreter.types.SchemeCons;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "car")
@GenerateNodeFactory
public abstract class CarNode extends BuiltinNode
{
    @Specialization
    public Object car(SchemeCons cons)
    {
        return cons.car;
    }
}
