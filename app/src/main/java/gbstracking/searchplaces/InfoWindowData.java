package gbstracking.searchplaces;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by HP on 30/04/2018.
 */

public class InfoWindowData {
    String image;
    public InfoWindowData(){

    }

    public InfoWindowData(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
