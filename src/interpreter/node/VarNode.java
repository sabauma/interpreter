package interpreter.node;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeField(name = "slot", type = FrameSlot.class)
public abstract class VarNode extends SchemeNode
{
	public abstract FrameSlot getSlot();
	
	public static interface FrameGet<T>
	{
		public T get(Frame frame, FrameSlot slot)
			throws FrameSlotTypeException;
	}
	
	public <T> T readUpStack(FrameGet<T> getter, VirtualFrame virtualFrame)
		throws FrameSlotTypeException
	{
	    FrameSlot slot = this.getSlot();
	    Object identifier = slot.getIdentifier();
		T value = getter.get(virtualFrame, slot);
		if (value != null)
		{
		    return value;
		}
		
		CompilerDirectives.transferToInterpreterAndInvalidate();
		MaterializedFrame frame = getLexicalScope(virtualFrame);
		while (value == null)
		{
			FrameDescriptor desc = frame.getFrameDescriptor();
			slot = desc.findFrameSlot(identifier);
			if (slot != null)
			{
			    value = getter.get(frame, slot);
			}
			
			if (value != null)
			{
			    break;
			}
			
			frame = getLexicalScope(frame);
			if (frame == null)
			{
			    CompilerDirectives.transferToInterpreterAndInvalidate();
			    throw new RuntimeException("Unknown identifier" + identifier);
			}
		}
		
		this.replace(LexicalReadNodeGen.create(frame, slot));
		return value;
	}
	
	@Specialization(rewriteOn = FrameSlotTypeException.class)
	protected long readLong(VirtualFrame virtualFrame)
		throws FrameSlotTypeException
	{
		return this.readUpStack(Frame::getLong, virtualFrame);
	}
	
	@Specialization(rewriteOn = FrameSlotTypeException.class)
	protected boolean readBoolean(VirtualFrame virtualFrame)
		throws FrameSlotTypeException
	{
		return this.readUpStack(Frame::getBoolean, virtualFrame);
	}
	
	@Specialization(rewriteOn = FrameSlotTypeException.class)
	protected Object readObject(VirtualFrame virtualFrame)
	    throws FrameSlotTypeException
	{
		return this.readUpStack(Frame::getObject, virtualFrame);
	}
	
	@Specialization(contains = {"readLong", "readBoolean", "readObject"})
	protected Object read(VirtualFrame virtualFrame)
	{
		try
		{
			return this.readUpStack(Frame::getValue, virtualFrame);
		}
		catch (FrameSlotTypeException e)
		{
			// This should never happen
		}
		return null;
	}
	
	public String toString()
	{
		return this.getSlot().getIdentifier().toString();
	}
}
