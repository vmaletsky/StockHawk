
package com.sam_chordas.android.stockhawk.historicaldata;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Diagnostics {

    @SerializedName("url")
    @Expose
    public List<Url> url = new ArrayList<Url>();
    @SerializedName("publiclyCallable")
    @Expose
    public String publiclyCallable;
    @SerializedName("cache")
    @Expose
    public List<Cache> cache = new ArrayList<Cache>();
    @SerializedName("query")
    @Expose
    public List<Query_> query = new ArrayList<Query_>();
    @SerializedName("javascript")
    @Expose
    public Javascript javascript;
    @SerializedName("user-time")
    @Expose
    public String userTime;
    @SerializedName("service-time")
    @Expose
    public String serviceTime;
    @SerializedName("build-version")
    @Expose
    public String buildVersion;

}
