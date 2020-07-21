package com.company.vahicles

data class SportCar(var speed: Double) : Car() {
    override fun toString(): String {
        return ", maxSpeed: ".plus(speed)
    }
}