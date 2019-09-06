package com.shadybond.nhleague.models;

public class TeamsModel {
    Integer id;
    String name;
    String urlTeamImage;

    public TeamsModel(Integer id, String name, String urlTeamImage){
        this.id = id;
        this.name = name;
        this.urlTeamImage = urlTeamImage;
    }

    public Integer returnTeamId(){
        return this.id;
    }

    public String returnTeamName(){
        return this.name;
    }

    public String returnUrlTeamImage() { return this.urlTeamImage; }
}

