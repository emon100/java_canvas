import java.awt.*;
import java.io.Serializable;

public class MyStroke implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -5593482416116997360L;
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

    BasicStroke getBasicStroke(){
        return new BasicStroke(width, cap, join,miterlimit, dash, 0.0f);
    }
}
