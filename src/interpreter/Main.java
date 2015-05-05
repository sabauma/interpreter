package interpreter;

import interpreter.node.prims.AddNodeFactory;
import interpreter.node.prims.NowNodeFactory;
import interpreter.node.prims.SubNodeFactory;
import interpreter.node.prims.CarNodeFactory;
import interpreter.node.prims.CdrNodeFactory;
import interpreter.node.prims.ConsNodeFactory;
import interpreter.node.prims.LEQNodeFactory;
import interpreter.node.SchemeNode;
import interpreter.node.prims.BuiltinNode;
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
        frame.setObject(fd.addFrameSlot("-"),
                BuiltinNode.createBuiltinFunction(SubNodeFactory.getInstance(), frame));
        frame.setObject(fd.addFrameSlot("cons"),
                BuiltinNode.createBuiltinFunction(ConsNodeFactory.getInstance(), frame));
        frame.setObject(fd.addFrameSlot("car"),
                BuiltinNode.createBuiltinFunction(CarNodeFactory.getInstance(), frame));
        frame.setObject(fd.addFrameSlot("cdr"),
                BuiltinNode.createBuiltinFunction(CdrNodeFactory.getInstance(), frame));
        frame.setObject(fd.addFrameSlot("<="),
                BuiltinNode.createBuiltinFunction(LEQNodeFactory.getInstance(), frame));
        frame.setObject(fd.addFrameSlot("now"),
                BuiltinNode.createBuiltinFunction(NowNodeFactory.getInstance(), frame));
        return frame;
    }
    
    private static Object execute(SchemeNode[] nodes, VirtualFrame top)
    {
        FrameDescriptor frameDescriptor = top.getFrameDescriptor();
        SchemeClosure   function = SchemeClosure.create(
                new FrameSlot[] {}, nodes, frameDescriptor);
        
        MaterializedFrame globalScope = top.materialize();
        return function.callTarget.call(new Object[] {globalScope});
    }

    public static String input = "((lambda (x) (if x 1 2)) #f)";
    public static String Y = "(lambda (f) ((lambda (x) (f (lambda (v) ((x x) v)))) (lambda (x) (f (lambda (v) ((x x) v))))))";
    public static String P = "(lambda (fib) (lambda (n) (if (<= n 1) 1 (+ (fib (- n 1)) (fib (- n 2))))))";
    public static String FIB = "(define fib (lambda (n) (if (<= n 1) 1 (+ (fib (- n 1)) (fib (- n 2))))))";
    public static String FIBN = "(fib 30)";
    public static String TIME = "(define start (now)) (fib 40) (define end (now)) (- end start)";
    public static String PROGRAM = FIB + " " + FIBN + FIBN + FIBN + TIME;

    public static void main(String[] args) throws IOException
    {
        InputStream istream = new ByteArrayInputStream(
                PROGRAM.getBytes(StandardCharsets.UTF_8));
        
        Object[] n        = Reader.readProgram(istream);
        VirtualFrame top  = createTopFrame(new FrameDescriptor());
        SchemeNode[] node = Converter.convertArray(n, top.getFrameDescriptor());
        
        Object v = execute(node, top);
        System.out.println("Execution time: " + (Long) v / 1000000L + "ms");
    }
}
