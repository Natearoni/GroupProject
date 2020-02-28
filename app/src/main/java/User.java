import java.util.ArrayList;

public class User {
    private String email;
    private String password;
    private ArrayList taskList;

    //TODO Constructor for email/password combination authentication?
    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    //TODO return arraylist of tasks for use with recyclerview
    public ArrayList getTaskList() {
        return taskList;
    }

    //TODO get tasks from database?????
    public void setTaskList(ArrayList taskList) {
        this.taskList = taskList;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
