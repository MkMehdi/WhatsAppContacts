package ma.mkmehdi.whatsappcontacts.model;

/**
 * Created by Elmehdi Mellouk on 06/06/18.
 * mellouk.elmehdi@gmail.com
 */
public class Contact {
    private String id;
    private String name;
    private String numberPhone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public Contact(String id, String name, String numberPhone) {
        this.id = id;
        this.name = name;
        this.numberPhone = numberPhone;
    }
}
