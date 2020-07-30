package com.company.vahicles

import com.company.details.Engine
import com.company.professions.Driver

open class Car(
    val carClass: String,
    val marka: String,
    val veight: Int,
    val driver: Driver,
    val engine: Engine
) {
    val _carClass: String
    val _marka: String
    val _veight: Int
    val _driver: Driver
    val _engine: Engine

    init {
        _carClass = carClass
        _marka=marka
        _veight=veight
        _driver=driver
        _engine=engine
    }

    fun start() {
        println("Поехали")
    }

    fun stop() {
        println("Останавливаемся")
    }

    fun turnRight() {
        println("Поворот направо")
    }

    fun turnLeft() {
        println("Поворот налево")
    }

    override fun toString(): String {
        return "carClass= ".plus(carClass).plus(" ,marka= ").plus(marka).plus(" ,veight= ")
            .plus(veight).plus(" , ").plus(driver).plus(" ,").plus(engine)
    }

    fun printInfo(car:Car): String {
        //println(car.toString())
        return car.toString()
    }
}