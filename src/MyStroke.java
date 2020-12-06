import java.awt.*;
import java.io.Serializable;

/**
 * 自己实现的可序列化笔画类
 */
public class MyStroke implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -5593482416116997360L;
    /*
     *这下面的属性与BasicStroke一一对应
     */
    float width; 
    int cap;
    int join;
    float miterlimit;
    float[] dash;
    float dash_phase;
    
    public MyStroke(float width, int cap, int join, float miterlimit) {
        this(width, cap, join, miterlimit, null, 0.0f);
    }
    public MyStroke(float w, int c, int j, float mi,
                    float[] da, float da_ph){
        width=w;
        cap=c;
        join=j;
        miterlimit=mi;
        dash=da;
        dash_phase=da_ph;
    }
    public MyStroke(float width, int cap, int join) {
        this(width, cap, join, 10.0f, null, 0.0f);
    }
    public MyStroke(float width) {
        this(width, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);
    }
    public MyStroke() {
        this(1.0f,BasicStroke. CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);
    }
    /**
     * 返回MyStroke对应的BasicStroke对象
     * @return
     */
    BasicStroke getBasicStroke(){
        return new BasicStroke(width, cap, join,miterlimit, dash, 0.0f);
    }
}
