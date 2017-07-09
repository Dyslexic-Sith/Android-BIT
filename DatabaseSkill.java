package com.project.sam.bitservices;

/**
 * Created by Sam on 23/05/2017.
 */
public class DatabaseSkill {

    private int id;
    private String title;

    //region Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    //endregion

    //region Constructors
    public DatabaseSkill() {

    }

    public DatabaseSkill(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public DatabaseSkill(String title) {
        this.title = title;
    }
    //endregion
}
