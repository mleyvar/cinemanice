package com.marcopololeyva.cinemanice.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.marcopololeyva.cinemanice.model.ResultMovie;

import java.util.ArrayList;
import java.util.List;

public class TopRatedResponse {
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;
    @SerializedName("results")
    @Expose
    private ArrayList<ResultMovie> results = null;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<ResultMovie> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResultMovie> results) {
        this.results = results;
    }
    }
