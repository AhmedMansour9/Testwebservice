package gbstracking;

/**
 * Created by HP on 29/04/2018.
 */

public class GetAndSethomeFriends {
  private   Boolean online;
  private   Boolean privacy;
  private   String photo;
  private   String username;
  private   String id;

    public GetAndSethomeFriends(){}
    public GetAndSethomeFriends(Boolean online, Boolean privacy, String photo,String username,String id) {
        this.online = online;
        this.privacy = privacy;
        this.photo = photo;
        this.username=username;
        this.id=id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Boolean getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Boolean privacy) {
        this.privacy = privacy;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
