
package com.sam_chordas.android.stockhawk.historicaldata;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Results {

    @SerializedName("quote")
    @Expose
    public ArrayList<Quote> quote = new ArrayList<>();

}
