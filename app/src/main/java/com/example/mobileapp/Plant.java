package com.example.mobileapp;

public abstract class Plant {
    private long id;

    public abstract String PlantName();
    public abstract String Interval();
    public abstract String LastWatered();

    public Plant(String name, String interval, String lastWatered) {
        setPlantName(name);
        setInterval(interval);
        setLastWatered(lastWatered);
    }


    public abstract void setPlantName(String name);
    public abstract void setInterval(String interval);
    public abstract void setLastWatered(String lastWatered);

    public void setId(long id) {
        this.id = id;
    }



}
