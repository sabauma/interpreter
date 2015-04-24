package interpreter.node;

import interpreter.node.DefineNodeGen;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.RootNode;

public class SchemeRootNode extends RootNode
{
	@Children private final SchemeNode[] body;
	
	public SchemeRootNode(SchemeNode[] body, FrameDescriptor fd)
	{
		super(null, fd);
		this.body = body;
	}

	@Override
	@ExplodeLoop
	public Object execute(VirtualFrame frame)
	{
		int count = this.body.length - 1;
		CompilerAsserts.compilationConstant(count);
		for (int i = 0; i < count; ++i)
		{
			this.body[i].execute(frame);
		}
		return this.body[count].execute(frame);
	}

	public static SchemeRootNode create(
			FrameSlot[] argumentNames,
			SchemeNode[] bodyNodes,
			FrameDescriptor frameDescriptor)
	{
		SchemeNode[] allNodes = new SchemeNode[argumentNames.length + bodyNodes.length];
		for (int i = 0; i < argumentNames.length; ++i)
		{
			allNodes[i] = DefineNodeGen.create(
					new ReadArgumentNode(i), argumentNames[i]);
		}
		System.arraycopy(bodyNodes, 0, allNodes, argumentNames.length, bodyNodes.length);
		return new SchemeRootNode(allNodes, frameDescriptor);
	}
}
