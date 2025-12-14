package com.example.scm.utils.helper

import com.example.scm.R
import com.example.scm.data.model.MenuItems


object ArrayHelper {

    fun getHomeArray(): ArrayList<MenuItems> {
        val menuItems = ArrayList<MenuItems>()
        menuItems.add(MenuItems(Keys.taskassign))
        menuItems.add(MenuItems(Keys.writemessage ))
        return menuItems
    }
    fun getHomeTaskArray(): ArrayList<MenuItems> {
        val menuItems = ArrayList<MenuItems>()
        menuItems.add(MenuItems("Task Assign",Keys.stage1,  R.drawable.ic_home))
        menuItems.add(MenuItems("Task Status",Keys.stage2,  R.drawable.ic_home ))
     //   menuItems.add(MenuItems("Write Message",Keys.stage3, R.drawable.ic_home))

        return menuItems
    }

    fun getTaskArray(): ArrayList<MenuItems> {
        val menuItems = ArrayList<MenuItems>()
        menuItems.add(MenuItems(Keys.stage1, "", R.drawable.ic_home))
     //   menuItems.add(MenuItems(Keys.stage2, "", R.drawable.ic_home ))

        return menuItems
    }

    fun getTaskStatusArray(): ArrayList<MenuItems> {
        val menuItems = ArrayList<MenuItems>()
        menuItems.add(MenuItems(Keys.stage1, "", R.drawable.ic_home))
        menuItems.add(MenuItems(Keys.stage2, "", R.drawable.ic_home ))
        menuItems.add(MenuItems(Keys.stage3, "", R.drawable.ic_home))

        return menuItems
    }


    fun getpopularsearches(): ArrayList<MenuItems> {
        val popularItems = ArrayList<MenuItems>()
        popularItems.add(MenuItems("beauty products", "", 0))
        popularItems.add(MenuItems("watches", "", 0))
        popularItems.add(MenuItems("shoes", "", 0, arrayListOf(), "", "menu_mid"))
        popularItems.add(MenuItems("shirts", "", 0))
        popularItems.add(MenuItems("clothes", "", 0))
        popularItems.add(MenuItems("tops", "", 0))
        popularItems.add(MenuItems("tvs", "", 0))
        popularItems.add(MenuItems("laptops", "", 0))


        return popularItems
    }

    fun getMenuArray2(): ArrayList<MenuItems> {
        val menuItems = ArrayList<MenuItems>()
        menuItems.add(MenuItems(Keys.aboutus, "", 0, arrayListOf(), "", "menu_mid"))
        menuItems.add(MenuItems(Keys.faqs, "", 0))
        menuItems.add(MenuItems(Keys.refund_policys, "", 0))
        menuItems.add(MenuItems(Keys.privacy_policy, "", 0))
        menuItems.add(MenuItems(Keys.terms_of_Service, "", 0))

        return menuItems
    }

    fun getSortArray(): ArrayList<MenuItems> {
        val menuItems = ArrayList<MenuItems>()
        menuItems.add(MenuItems(Keys.any, "", 0))
        menuItems.add(MenuItems(Keys.toprated, "", 0))
        menuItems.add(MenuItems(Keys.popularity, "", 0))
        menuItems.add(MenuItems(Keys.price_low, "", 0))
        menuItems.add(MenuItems(Keys.price_high, "", 0))

        return menuItems
    }


}