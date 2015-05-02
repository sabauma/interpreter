package interpreter.node.call;

import interpreter.node.SchemeNode;
import interpreter.types.SchemeClosure;

import java.util.Arrays;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

public class AppNode extends SchemeNode
{
    private boolean isTail;

    @Child    protected SchemeNode rator;
    @Children protected final SchemeNode[] rands;
    @Child    protected IndirectCallNode callNode;

    public AppNode(SchemeNode rator, SchemeNode[] rands)
    {
        this.isTail = false;
        this.rator = rator;
        this.rands = rands;
        this.callNode = Truffle.getRuntime().createIndirectCallNode();
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame virtualFrame)
    {
        SchemeClosure func = this.evaluateRator(virtualFrame);
        CompilerAsserts.compilationConstant(this.rands.length);

        Object[] args = new Object[this.rands.length + 1];
        args[0] = func.getLexicalScope();

        for (int i = 0; i < this.rands.length; ++i)
        {
            args[i + 1] = this.rands[i].execute(virtualFrame);
        }
        
        if (this.isTail)
        {
            throw new TailCallException(func.callTarget, args);
        }
        
        return this.call(virtualFrame, func.callTarget, args);
    }
    
    // This code cannot go into the execute method, as the Explode loop
    // annotation will try to unroll it.
    private Object call(VirtualFrame virtualFrame, CallTarget target, Object[] args)
    {
        while (true)
        {
            try
            {
                return this.callNode.call(virtualFrame, target, args);
            }
            catch (TailCallException tce)
            {
                target = tce.func;
                args   = tce.args;
            }
        }
    }

    private SchemeClosure evaluateRator(VirtualFrame virtualFrame)
    {
        try
        {
            return this.rator.executeSchemeClosure(virtualFrame);
        }
        catch (UnexpectedResultException e)
        {
            throw new UnsupportedSpecializationException(this,
                    new Node[] { this.rator }, e);
        }
    }

    @Override
    public void setIsTail()
    {
        this.isTail = true;
    }

    @Override
    public String toString()
    {
        return "(" + this.rator + " " + Arrays.toString(this.rands) + ")";
    }
}
