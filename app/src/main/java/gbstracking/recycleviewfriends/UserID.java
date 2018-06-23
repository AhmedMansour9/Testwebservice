package gbstracking.recycleviewfriends;

/**
 * Created by HP on 20/04/2018.
 */

public class UserID {

    public String email;
    public String id;
    Boolean online;
    public String photo;
    private String username;

    public UserID(String email,String id,Boolean online,String photo,String username)
    {
        this.email=email;
        this.id=id;
        this.online=online;
        this.photo=photo;
        this.username=username;
    }
    public UserID(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

