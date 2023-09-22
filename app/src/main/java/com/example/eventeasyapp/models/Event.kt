package com.example.eventeasyapp.models

class Event{
   var title:String = ""
    var time:String = ""
    var location:String = ""
    var price: String = ""
    var desc:String = ""
    var imageUrl:String = ""
    var id:String = ""

    constructor(
        title: String,
        time: String,
        location: String,
        price: String,
        desc: String,
        imageUrl: String,
        id: String
    ) {
        this.title = title
        this.time = time
        this.location = location
        this.price = price
        this.desc = desc
        this.imageUrl = imageUrl
        this.id = id
    }
    constructor()
}