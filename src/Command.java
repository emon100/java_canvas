/*
命令类，封装了一个用户操作 
*/
public interface Command {
    void execute();
    void unexecute();
}
