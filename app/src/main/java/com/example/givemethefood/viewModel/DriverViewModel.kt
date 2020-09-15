package com.example.givemethefood.viewModel

import com.example.givemethefood.Data.Driver
import kotlin.random.Random

class DriverViewModel {

    private var driverList = listOf<Driver>(
        Driver(1, "Ronnie Anderson","https://images.cdn3.stockunlimited.net/clipart/indian-costume_1387517.jpg", "098-787-2365", "SUV", "ADR194"),
        Driver(2, "John Brown","https://icons-for-free.com/iconfiles/png/512/hipster+jumper+man+icon-1320166693390277744.png", "012-987-3654", "MINIVAN", "FTE158"),
        Driver(3, "Ann Brook", "https://static.thenounproject.com/png/410773-200.png","073-652-1987", "STATION WAGON", "DSR254"),
        Driver(4, "Frank Hill","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRragExPJ0skW_KOPneVtgQLxLAtWt2BMF8vA&usqp=CAU", "095-316-9872", "4VF58G", "QW87TY"),
        Driver(5, "Robert Furlan","https://www.emoji.co.uk/files/mozilla-emojis/smileys-people-mozilla/11425-man.png", "049-873-2058", "VAN", "T369YW")
    )

    fun getDriver(): Driver {
        val randomDriver = Random.nextInt(1, 5)
        return driverList[randomDriver]
    }

    fun getDurationTime(): Int {
        return Random.nextInt(25,59)
    }
}