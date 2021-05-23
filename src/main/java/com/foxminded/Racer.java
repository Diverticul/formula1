package com.foxminded;

import java.util.Date;

public class Racer implements Comparable<Racer> {
    private Date lapTime;
    private String name;
    private String carModel;

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public Date getLapTime() {
        return lapTime;
    }

    public void setLapTime(Date time) {
        this.lapTime = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Racer o) {
        return getLapTime().compareTo(o.getLapTime());
    }
}
