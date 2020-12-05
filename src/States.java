import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class States implements StatesModel {

    //当前选择颜色
    protected Color currentColor = Color.black;

    protected float alpha = 1.0f;

    //当前选择类型
    protected TYPE cursorType = TYPE.PATH;

    //当前文件已经被保存
    public boolean hasBeenSaved = true;

    //画笔样式
    protected MyStroke myStroke;

    //操作栈
    Stack<Command> commandStack = new Stack<>();
    //撤销栈
    Stack<Command> unDoneStack = new Stack<>();

    ArrayList<Drawable> drawables = new ArrayList<>();

    /**
     * 初始化States, 用于新建文件
     */
    public void iniStates() {
        cursorType = TYPE.PATH;
        currentColor = Color.black;
        getAllDrawable().clear();
        commandStack.clear();
        unDoneStack.clear();
    }
    @Override
    public void execute(Command command) {
        command.execute();
        commandStack.push(command);

        //执行command后清空撤销栈
        unDoneStack.clear();
        hasBeenSaved = false;
    }
    
    @Override
    public void undo() {
        //操作栈空
        if ( commandStack.empty() ) {
            System.out.println("Nothing to be undone");
            return;
        }
        //unexcute操作栈栈顶
        commandStack.peek().unexecute();

        //操作栈弹出至撤销栈
        unDoneStack.push( commandStack.peek() );

        //操作栈弹出
        commandStack.pop();
        hasBeenSaved = false;
    }

    @Override
    public void redo() {
        //撤销栈空
        if ( unDoneStack.empty() ) {
            System.out.println("Nothing to be redone");
            return;
        }
        //execute当前操作
        unDoneStack.peek().execute();

        //将撤销栈弹回操作栈
        commandStack.push( unDoneStack.peek() );

        //撤销栈弹出
        unDoneStack.pop();
        hasBeenSaved = false;
    }
    
    @Override
    public ArrayList<Drawable> getAllDrawable() {
        return drawables;
    }
    
    @Override
    public void setAlpha(float f) {
        f = alpha;
    }

    @Override
    public MyStroke getMyStroke() {
        return myStroke;
    }

    @Override
    public void setMyStroke(MyStroke my) {
        myStroke = my;
    }

    @Override
    public float getAlpha() {
        return alpha;
    }
    
    @Override
    public TYPE getType() {
        return cursorType;
    }

    @Override
    public void setType(TYPE t) {
        cursorType=t;
        System.out.println(t);
    }
    
    @Override
    public Color getColor() {
        return currentColor;
    }
    
    @Override
    public void setDrawable(ArrayList<Drawable> d) {
        drawables = d;
    }

    @Override
    public void setColor(Color cur) {
        currentColor = cur;
        System.out.println(cur);
    }

}
