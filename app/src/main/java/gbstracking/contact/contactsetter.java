package gbstracking.contact;

/**
 * Created by HP on 23/05/2018.
 */

public class contactsetter {

    private String Name;
    private String Phone;

    public contactsetter(){}

    public contactsetter(String name, String phone) {
        Name = name;
        Phone = phone;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }


}
