package gbstracking.friends;

/**
 * Created by HP on 11/04/2018.
 */

public class bolleaanuser {
    Boolean online;
    String email;
    Boolean privacy;
    public bolleaanuser(){}

    public bolleaanuser(Boolean online,String email,Boolean privacy) {
        this.online = online;
        this.email=email;
        this.privacy=privacy;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Boolean privacy) {
        this.privacy = privacy;
    }
}
