package com.example.mobileapp;

public class ConcretePlant extends Plant {

    private String plantName;
    private String interval;
    private String lastWatered;

    public ConcretePlant(String name, String interval, String lastWatered) {
        super(name, interval, lastWatered);
    }

    @Override
    public String PlantName() {
        return plantName;
    }

    @Override
    public String Interval() {
        return interval;
    }

    @Override
    public String LastWatered() {
        return lastWatered;
    }

    @Override
    public void setPlantName(String name) {
        this.plantName = name;
    }

    @Override
    public void setInterval(String interval) {
        this.interval = interval;
    }

    @Override
    public void setLastWatered(String lastWatered) {
        this.lastWatered = lastWatered;
    }
}