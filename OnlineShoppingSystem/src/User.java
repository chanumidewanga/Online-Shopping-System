import java.io.Serializable;

//Implementing Serializable interface
public class User implements Serializable {
    private static final long serialVersionUID = -4997024102041845121L;
    private String username;
    private String IDnumber;
    private int loginCount;


    //Create User constructor
    public User(String username, String password, int loginCount) {
        this.username = username;
        this.IDnumber = password;
        this.loginCount = loginCount;
    }
    public void addLoginCount(){
        loginCount++;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return IDnumber;
    }

}
