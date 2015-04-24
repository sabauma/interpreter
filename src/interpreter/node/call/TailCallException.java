package interpreter.node.call;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.nodes.ControlFlowException;

public class TailCallException extends ControlFlowException
{
    private static final long serialVersionUID = 1L;
    
    public final CallTarget func;
    public final Object[]   args;
    
    public TailCallException(CallTarget func, Object[] args)
    {
        this.func = func;
        this.args = args;
    }
}
