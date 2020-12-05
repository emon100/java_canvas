import java.util.*;
import java.awt.*;

/**
 * 状态集合接口
 * @author Emon100
 */
public interface StatesModel {

    /**
     * 当前选中画笔类型
     */
    enum TYPE{
        SELECT,LINE,PATH,TRIANGLE,RECTANGLE,ELLIPSE,TEXTBOX,ERASER,FILL
    }

    /**
     * 执行command
     * @param command  被执行的操作
     */
    void execute(Command command);

    /**
     * 撤销
     */
    void undo();

    /**
     * 重做
     */
    void redo();

    /**
     * @return 所有可以在画板中被画/写出来的对象
     */
    ArrayList<Drawable> getAllDrawable();

    /**
     * @param d 设置可画对象
     */
    void setDrawable(ArrayList<Drawable> d);

    /**
     * @return 返回当前类型
     */
    StatesModel.TYPE getType();

    /**
     * @param t 设置当前类型
     */
    void setType(StatesModel.TYPE t);

    /**
     * @return 返回当前颜色
     */
    Color getColor();

    /**
     * @param cur 设置为当前颜色
     */
    void setColor(Color cur);

    /**
     * @return 返回透明度
     */
    float getAlpha();

    /**
     * @param f 设置透明度
     */
    void setAlpha(float f);

    /**
     * @return 返回当前画笔细节
     */
    MyStroke getMyStroke();

    /**
     * @param my 设置当前画笔细节
     */
    void setMyStroke(MyStroke my);
}
