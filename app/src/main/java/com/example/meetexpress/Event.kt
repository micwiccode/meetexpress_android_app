package com.example.meetexpress

import android.graphics.Bitmap
import com.google.firebase.firestore.QuerySnapshot
import java.time.LocalDate
import java.util.*

class Event() {

    var name = ""
    var date: Long = 0
    var actualPeople: Int = 0
    var maxPeople: Int = 0
    var place = ""
    var category = ""
    var photo: Int = 0
    constructor(_name: String, _date: Long, _actualPeople: Int, _maxPeople: Int, _place: String, _category : String, _photo: Int = 0) : this(){
        name = _name
        date = _date
        actualPeople = _actualPeople
        maxPeople = _maxPeople
        place = _place
        category = _category
        photo = _photo
    }

}