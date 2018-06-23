package gbstracking.Userlogin;

/**
 * Created by HP on 01/04/2018.
 */

public class UserloginMain {
    public String username;
    public String id;
    public String photo;
    public String email;

    public UserloginMain() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    // Send User data to users firebase
    public UserloginMain(String username, String email, String id, String photo) {
        this.id=id;
        this.photo=photo;
        this.username = username;
        this.email = email;
    }

    public void setEmail(String Email) {
        this.email=Email;
    }
    public String emaill() {
        return email;
    }

}
