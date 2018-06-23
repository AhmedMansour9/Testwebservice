package gbstracking.friends;

/**
 * Created by HP on 21/04/2018.
 */

public class emailsetandget  {
    private String email;
    private String username;
    public emailsetandget(){}
    public emailsetandget(String email,String username){
        this.email=email;
        this.username=username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
