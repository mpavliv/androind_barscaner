package com.example.vikamebli.db;

public class SN {
    public String number;
    public String product;
    public String fabric1;
    public String fabric2;
    public String fabric3;
    public String myorder;
    public String notes;

    public SN(String number, String product, String fabric1, String fabric2, String fabric3, String myorder, String notes) {
        this.number = number;
        this.product = product;
        this.fabric1 = fabric1;
        this.fabric2 = fabric2;
        this.fabric3 = fabric3;
        this.myorder = myorder;
        this.notes = notes;
    }

    public SN(String str){
        String[] strArray = str.split("/;", -1);
        number = strArray[0];
        product = strArray[1];
        fabric1 = strArray[2];
        fabric2 = strArray[3];
        fabric3 = strArray[4];
        myorder = strArray[5];
        notes = strArray[6];

    }

}
