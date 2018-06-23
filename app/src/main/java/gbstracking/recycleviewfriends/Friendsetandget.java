package gbstracking.recycleviewfriends;

/**
 * Created by HP on 30/03/2018.
 */

public class Friendsetandget  {
    public Friendsetandget(){

    }

    private String email;
    private  String id;
    Boolean online;
     String photo;
    String username;
    double lat;
    double lon;


    public Friendsetandget(String email, String id,Boolean online, String photo,String username,double lat,double lon){
        this.email=email;
        this.id=id;
        this.online=online;
        this.photo=photo;
        this.username=username;
        this.lat = lat;
        this.lon = lon;
    }
    public Friendsetandget(String id, String photo,String username,double lat,double lon){

        this.id=id;

        this.photo=photo;
        this.username=username;
        this.lat = lat;
        this.lon = lon;
    }
    public String getEmail() {
        return email;
    }


    public String GetEmail(){
        return email;
    }
    public void setEmail(String Email){
        this.email=Email;
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
}
