import java.awt.*;
import java.io.Serializable;

public class MyStroke extends BasicStroke implements Serializable {
    public MyStroke(float width, int cap, int join, float miterlimit) {
        this(width, cap, join, miterlimit, null, 0.0f);
    }
    public MyStroke(float width, int cap, int join, float miterlimit,
                    float[] dash, float dash_phase){
        super(width, cap, join, miterlimit, dash, dash_phase);
    }
    public MyStroke(float width, int cap, int join) {
        this(width, cap, join, 10.0f, null, 0.0f);
    }
    public MyStroke(float width) {
        this(width, CAP_SQUARE, JOIN_MITER, 10.0f, null, 0.0f);
    }
    public MyStroke() {
        this(1.0f, CAP_SQUARE, JOIN_MITER, 10.0f, null, 0.0f);
    }
}
