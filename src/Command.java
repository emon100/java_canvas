/**
 * 命令类，封装了一个单位的用户操作
 * @author 王一蒙
*/
public interface Command {
    /**
     * 执行此操作
     */
    void execute();

    /**
     * 撤销此操作
     */
    void unexecute();
}
