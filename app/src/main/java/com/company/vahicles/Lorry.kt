package com.company.vahicles

import com.company.details.Engine
import com.company.professions.Driver

class Lorry(carClass: String, marka: String, veight: Int, driver: Driver, engine: Engine,carrying:Int) : Car(carClass, marka, veight,
    driver,
    engine
) {
    var _carrying: Int
    init {
        _carrying=carrying
    }
    override fun toString(): String {
        return _carrying.toString().plus(" ,").plus(engine)
    }
}