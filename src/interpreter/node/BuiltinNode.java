package interpreter.node;

import interpreter.types.SchemeClosure;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeChild(value = "arguments", type = SchemeNode[].class)
public abstract class BuiltinNode extends SchemeNode
{

    public static SchemeClosure createBuiltinFunction(
            NodeFactory<? extends BuiltinNode> factory,
            VirtualFrame outerFrame)
    {
        int argumentCount          = factory.getExecutionSignature().size();
        SchemeNode[] argumentNodes = new SchemeNode[argumentCount];
        for (int i = 0; i < argumentCount; i++)
        {
            argumentNodes[i] = new ReadArgumentNode(i);
        }
        BuiltinNode node = factory.createNode((Object) argumentNodes);
        return new SchemeClosure(Truffle.getRuntime().createCallTarget(
                new SchemeRootNode(new SchemeNode[] { node },
                        new FrameDescriptor())));
    }
}
