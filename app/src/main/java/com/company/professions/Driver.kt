package com.company.professions

class Driver : Person() {
    var experience: Int = 2
    override fun toString(): String {
        return "experience=".plus( experience).plus(", ").plus(fullName).plus(", ").plus(age)
    }
}