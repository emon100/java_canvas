import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * 状态机
 * @author AlbertTan
 */
public class States implements StatesModel {

    /**
     * 当前选择颜色
     */
    protected Color currentColor = Color.black;

    /**
     * 当前透明度
     */
    protected float alpha = 1.0f;

    /**
     * 当前选择类型
     */
    protected TYPE cursorType = TYPE.PATH;

    /**
     * 当前文件已经被保存
     */
    public boolean hasBeenSaved = true;

    /**
     * 画笔样式
     */
    protected MyStroke myStroke;

    /**
     * 操作栈，包含所有execute过的command
     */
    Stack<Command> commandStack = new Stack<>();

    /**
     * 撤销栈，包含所有unexecute过的command，
     * 若有新的command被执行，撤销栈被清空
     */
    Stack<Command> unDoneStack = new Stack<>();

    /**
     * 所有可画对象
     */
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

    /**
     * @param command  被执行的操作
     */
    @Override
    public void execute(Command command) {
        command.execute();
        commandStack.push(command);

        //执行command后清空撤销栈
        unDoneStack.clear();
        hasBeenSaved = false;
    }

    /**
     * 撤销上次操作
     */
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

    /**
     * 重做上次撤销
     */
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

    /**
     * @param d 设置可画对象
     */
    @Override
    public void setDrawable(ArrayList<Drawable> d) {
        drawables = d;
    }

    /**
     * @return 返回当前所有可画对象
     */
    @Override
    public ArrayList<Drawable> getAllDrawable() {
        return drawables;
    }

    /**
     * @param f 设置透明度
     */
    @Override
    public void setAlpha(float f) {
        f = alpha;
    }

    /**
     * @return myStroke 返回当前画笔细节
     */
    @Override
    public MyStroke getMyStroke() {
        return myStroke;
    }

    /**
     * @param my 设置当前画笔细节
     */
    @Override
    public void setMyStroke(MyStroke my) {
        myStroke = my;
    }

    /**
     * @return alpha 当前透明度
     */
    @Override
    public float getAlpha() {
        return alpha;
    }

    /**
     * @return cursorType 当前画笔类型
     */
    @Override
    public TYPE getType() {
        return cursorType;
    }

    /**
     * @param t 设置当前画笔类型
     */
    @Override
    public void setType(TYPE t) {
        cursorType=t;
        System.out.println(t);
    }

    /**
     * @return currentColor 返回当前颜色
     */
    @Override
    public Color getColor() {
        return currentColor;
    }

    /**
     * @param cur 设置为当前颜色
     */
    @Override
    public void setColor(Color cur) {
        currentColor = cur;
        System.out.println(cur);
    }

}
