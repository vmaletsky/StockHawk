
package com.sam_chordas.android.stockhawk.historicaldata;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Query_ {

    @SerializedName("execution-start-time")
    @Expose
    public String executionStartTime;
    @SerializedName("execution-stop-time")
    @Expose
    public String executionStopTime;
    @SerializedName("execution-time")
    @Expose
    public String executionTime;
    @SerializedName("params")
    @Expose
    public String params;
    @SerializedName("content")
    @Expose
    public String content;

}
