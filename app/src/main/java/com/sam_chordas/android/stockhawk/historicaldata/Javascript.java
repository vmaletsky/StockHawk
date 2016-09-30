
package com.sam_chordas.android.stockhawk.historicaldata;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Javascript {

    @SerializedName("execution-start-time")
    @Expose
    public String executionStartTime;
    @SerializedName("execution-stop-time")
    @Expose
    public String executionStopTime;
    @SerializedName("execution-time")
    @Expose
    public String executionTime;
    @SerializedName("instructions-used")
    @Expose
    public String instructionsUsed;
    @SerializedName("table-name")
    @Expose
    public String tableName;

}
