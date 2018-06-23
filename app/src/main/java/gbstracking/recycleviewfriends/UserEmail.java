package gbstracking.recycleviewfriends;

import android.content.Context;

/**
 * Created by HP on 30/03/2018.
 */

public class UserEmail {
   private String email;
    private String id;
    private Boolean online;
    private String photo;
    private String username;
    private Boolean privacy;

    public UserEmail(){}
    public UserEmail(String email,String id,Boolean online,String photo,String username){
        this.email=email;
        this.id=id;
        this.online=online;
        this.photo=photo;
        this.username=username;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Boolean privacy) {
        this.privacy = privacy;
    }
}
