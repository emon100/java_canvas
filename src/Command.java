/*
 * 命令类，封装了一个单位的用户操作
*/
public interface Command {
    void execute();   //执行这个操作
    void unexecute(); //撤销这个操作
}
