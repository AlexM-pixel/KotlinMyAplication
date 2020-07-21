package com.company.vahicles

class Lorry : Car() {
    var carrying: Int = 20
    override fun toString(): String {
        return carrying.toString().plus(" ,").plus(engine)
    }
}