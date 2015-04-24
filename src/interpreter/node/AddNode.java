package interpreter.node;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "+")
@GenerateNodeFactory
public abstract class AddNode extends BuiltinNode
{
    @Specialization
    public long add(long value0, long value1)
    {
        return value0 + value1;
    }

    @Specialization
    public double add(double value0, double value1)
    {
        return value0 + value1;
    }
}
