package com.example.scm.data.model

class MenuItems(
    var itemName: String = "",
    var selected: String = "",
    var itemImage: Int = 0,
    var menuSubItems : ArrayList<MenuItems> = ArrayList(),
    var open_sub: String = "",
    var title_name: String = ""
)