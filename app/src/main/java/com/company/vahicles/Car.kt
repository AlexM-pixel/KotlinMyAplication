package com.company.vahicles

import com.company.details.Engine
import com.company.professions.Driver

open class Car {
    val carClass: String = "Ford"
    val marka: String = "scorpio"
    val veight: Int = 1500
    val driver: Driver = Driver()
    val engine: Engine = Engine("ford",100)
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
            .plus(veight).plus(" , ").plus(driver).plus(" ,").plus(engine).plus(SportCar(300.0))
    }

    fun printInfo() {
        println(Car().toString())
    }
}