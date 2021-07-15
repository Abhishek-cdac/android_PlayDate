package com.playdate.app.model;

public class PopularGames {

    private String names;
    private int game_image;

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public int getGame_image() {
        return game_image;
    }

    public void setGame_image(int game_image) {
        this.game_image = game_image;
    }

    public PopularGames(String names, int game_image) {

        this.names = names;
        this.game_image = game_image;
    }
}

