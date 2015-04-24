package interpreter;

import interpreter.node.BuiltinNode;
import interpreter.node.AddNode;
import interpreter.node.AddNodeFactory;
import interpreter.node.SchemeNode;
import interpreter.reader.Converter;
import interpreter.reader.Reader;
import interpreter.types.SchemeClosure;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;

public class Main
{
    
    private static VirtualFrame createTopFrame(FrameDescriptor fd)
    {
        VirtualFrame frame = Truffle.getRuntime().createVirtualFrame(
                new Object[] {}, fd);
        frame.setObject(fd.addFrameSlot("+"),
                BuiltinNode.createBuiltinFunction(AddNodeFactory.getInstance(), frame));
        return frame;
    }
    
    private static Object execute(SchemeNode node, VirtualFrame top)
    {
        SchemeNode[]    body            = new SchemeNode[] {node};
        FrameDescriptor frameDescriptor = top.getFrameDescriptor();
        SchemeClosure   function = SchemeClosure.create(
                new FrameSlot[] {}, body, frameDescriptor);
        
        MaterializedFrame globalScope = top.materialize();
        return function.callTarget.call(new Object[] {globalScope});
    }

    public static String input = "((lambda (x) (+ x x)) 1)";

    public static void main(String[] args) throws IOException
    {
        InputStream istream = new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8));
        Object n         = Reader.readExpr(istream);
        VirtualFrame top = createTopFrame(new FrameDescriptor());
        SchemeNode node  = Converter.convert(n, top.getFrameDescriptor());
        
        
        System.out.println(n.toString());
        System.out.println(node.toString());
        
        System.out.println(execute(node, top));
        // System.out.println(n.asCode());
        // System.out.println(n.asCode().eval(BuiltinFunctions.BaseEnv));
    }
}
