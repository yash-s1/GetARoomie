package com.example.getaroomie

class User {
    var name: String? = null
    var email: String? = null
    var uid: String? = null
    var movies: Boolean = false
    var music: Boolean = false
    var cycling: Boolean = false
    var swimming: Boolean = false
    var dancing: Boolean = false


    constructor(){}

    constructor(name: String?, email:String?, uid: String?, movies: Boolean, music: Boolean, cycling: Boolean, swimming: Boolean, dancing: Boolean){
        this.name = name
        this.email = email
        this.uid = uid
        this.movies = movies
        this.music = music
        this.cycling = cycling
        this.swimming = swimming
        this.dancing = dancing


    }

}