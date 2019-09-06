package com.shadybond.nhleague.models;

public class PlayerModel {
    Integer playerId;
    String countryName;

    public PlayerModel(Integer playerId, String countryName){
        this.playerId = playerId;
        this.countryName = countryName;
    }
}
