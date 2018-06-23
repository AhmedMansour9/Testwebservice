package gbstracking;

/**
 * Created by HP on 01/05/2018.
 */

public class GetandSetFriendOnlineHome {
    String username;
    String photo;
    double lat;
    double lon;
    String id;
    public GetandSetFriendOnlineHome(){}
    public GetandSetFriendOnlineHome(String username, String photo, double lat, double lon,String id) {
        this.username = username;
        this.photo = photo;
        this.lat = lat;
        this.lon = lon;
        this.id=id;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
