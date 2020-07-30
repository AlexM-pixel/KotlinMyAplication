package com.company.professions

 open class Person {
    val age: Int = 18
    var fullName: String= "Sergey Valerich"
     override fun toString(): String {
         return age.toString().plus(" ,").plus(fullName)
     }
}