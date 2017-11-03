package app.android.mmauri.laboratorio2.models;

/**
 * Created by marc on 10/10/17.
 */

public class Fruit {
    private String name;
    private String origin;
    private int iconId;

    public Fruit(String name, String origin, int iconId) {
        this.name = name;
        this.origin = origin;
        this.iconId = iconId;
    }

    public Fruit(String name, int iconId) {

        this.name = name;
        this.iconId = iconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return (!isUnknown() ? origin : "Unknown");
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public boolean isUnknown() {
        return !(this.origin != null && !this.origin.isEmpty());
    }
}
