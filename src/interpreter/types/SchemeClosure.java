package interpreter.types;

import interpreter.node.SchemeNode;
import interpreter.node.SchemeRootNode;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.MaterializedFrame;

public class SchemeClosure extends Object {
    public final RootCallTarget callTarget;
    private MaterializedFrame env;

    public SchemeClosure(RootCallTarget callTarget) {
        this.callTarget = callTarget;
    }

    public void setLexicalScope(MaterializedFrame env) {
        this.env = env;
    }

    public MaterializedFrame getLexicalScope() {
        return this.env;
    }

    public static SchemeClosure create(FrameSlot[] args,
            SchemeNode[] bodyNodes, FrameDescriptor frameDescriptor) {
        return new SchemeClosure(Truffle.getRuntime().createCallTarget(
                SchemeRootNode.create(args, bodyNodes, frameDescriptor)));
    }
}
