package gbstracking.searchplaces;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by HP on 27/04/2018.
 */

public class PlaceInfo {
    private String name;
    private String address;
    private String phoneNumber;
    private String id;
    private Uri website;
    private LatLng latlong;
    private float rating;
    private String attruibtion;
    public PlaceInfo(){}

    public PlaceInfo(String name, String address, String phoneNumber, String id, Uri website, LatLng latlong, float rating, String attruibtion) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.website = website;
        this.latlong = latlong;
        this.rating = rating;
        this.attruibtion = attruibtion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Uri getWebsite() {
        return website;
    }

    public void setWebsite(Uri website) {
        this.website = website;
    }

    public LatLng getLatlong() {
        return latlong;
    }

    public void setLatlong(LatLng latlong) {
        this.latlong = latlong;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAttruibtion() {
        return attruibtion;
    }

    public void setAttruibtion(String attruibtion) {
        this.attruibtion = attruibtion;
    }

    @Override
    public String toString() {
        return "PlaceInfo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", id='" + id + '\'' +
                ", website=" + website +
                ", latlong=" + latlong +
                ", rating=" + rating +
                ", attruibtion='" + attruibtion + '\'' +
                '}';
    }
}
