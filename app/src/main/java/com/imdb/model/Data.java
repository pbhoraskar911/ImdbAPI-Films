
package com.imdb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("inTheaters")
    @Expose
    private List<InTheater> inTheaters = new ArrayList<InTheater>();

    /**
     * @return The inTheaters
     */
    public List<InTheater> getInTheaters() {
        return inTheaters;
    }

    /**
     * @param inTheaters The inTheaters
     */
    public void setInTheaters(List<InTheater> inTheaters) {
        this.inTheaters = inTheaters;
    }

}
