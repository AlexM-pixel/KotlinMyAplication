package com.company.details

data class Engine(val company: String, val power: Int) {
    override fun toString(): String {
        return "company: ".plus(company).plus(" ,power= ").plus(power)
    }
}