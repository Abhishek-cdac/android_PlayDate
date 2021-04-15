

package com.playdate.app.model;

public class SearchHistoryItem {

    private int id;

    private String query;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "SearchHistoryItem{" +
                "id=" + id +
                ", query='" + query + '\'' +
                '}';
    }
}
