package com.company.vahicles

import com.company.details.Engine
import com.company.professions.Driver

class SportCar(speed: Double, carClass: String, marka: String, veight: Int, driver: Driver,
               engine: Engine
) : Car(carClass, marka, veight,
    driver, engine
) {
   var _speed: Double
    init {
        _speed=speed
    }
    override fun toString(): String {
        return ", maxSpeed: ".plus(_speed)
    }
}