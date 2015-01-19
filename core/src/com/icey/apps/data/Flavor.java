package com.icey.apps.data;

/** Flavor class - stores flavor info
 *
 * Created by Allen on 1/12/15.
 */
public class Flavor {

    private String name; //name of flavor
    private int type = -1; //1=PG, 2=VG, 3=Other

    //the values
    private double percent = -1; //percent desired
    private double amount = 0; //amount needed

    public Flavor(String name){
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
