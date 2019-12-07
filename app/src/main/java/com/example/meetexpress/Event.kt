package com.example.meetexpress

import java.io.Serializable

class Event() :Serializable {

    var name = ""
    var date: Long = 0
    var time: Long = 0
    var actualPeople: Int = 0
    var maxPeople: Int = 0
    var place = ""
    var category = ""
    var photo: Int = 0

    constructor(_name: String, _date: Long, _time:Long, _actualPeople: Int, _maxPeople: Int, _place: String, _category : String, _photo: Int = 0) : this(){
        name = _name
        date = _date
        time = _time
        actualPeople = _actualPeople
        maxPeople = _maxPeople
        place = _place
        category = _category
        photo = _photo
    }

}