package com.example.mobileapp.ui.home

class Plant(plantName: String, plantInterval: String) {
    var plantName: String = plantName
    var plantInterval: String = plantInterval

    override fun toString(): String {
        return "Plant: $plantName, Interval: $plantInterval"
    }

}
