package edu.umb.cs.hw2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.security.Key

class MainactivityViewModel : ViewModel() {
    private var countorange=MutableLiveData<Int>()
    private var countblue=MutableLiveData<Int>()
    private var countgreen=MutableLiveData<Int>()
    private var countred= MutableLiveData<Int>()
    var count_per_orange=MutableLiveData<String>()
    var count_per_blue=MutableLiveData<String>()
    var count_per_green=MutableLiveData<String>()
    var count_per_red=MutableLiveData<String>()
    var firstrank=MutableLiveData<String>()
    var secondrank=MutableLiveData<String>()
    var thirdrank=MutableLiveData<String>()
    var fourrank=MutableLiveData<String>()
    var totalcount=MutableLiveData<Double>()


    init{
        count_per_red.value=" 0(00%)";count_per_blue.value=" 0(00%)";count_per_orange.value=" 0(00%)";count_per_green.value=" 0(00%)"; countred.value=0;countblue.value=0
        countblue.value=0;countorange.value=0;countgreen.value=0;firstrank.value="1st";secondrank.value="2nd";thirdrank.value="3rd";fourrank.value="4th"

    }
    fun total() {
        totalcount.value =
            countred.value?.toDouble()!! + countorange.value?.toDouble()!! + countgreen.value?.toDouble()!! + countblue.value?.toDouble()!!
    }
    fun updatecountred(){
        countred.value=(countred.value)?.plus(1)
        updaterank()
        updatecountpercent()

    }
    fun updatecountorange(){
        countorange.value=(countorange.value)?.plus(1)
        updaterank()
        updatecountpercent()

    }
    fun updatecountblue(){
        countblue.value=(countblue.value)?.plus(1)
        updaterank()
        updatecountpercent()

    }
    fun updatecountgreen(){
        countgreen.value=(countgreen.value)?.plus(1)
        updaterank()
        updatecountpercent()

    }

    fun updatecountpercent(){
        count_per_blue.value=percent(countblue.value!!)
        count_per_red.value=percent(countred.value!!)
        count_per_orange.value=percent(countorange.value!!)
        count_per_green.value=percent(countgreen.value!!)
    }

    fun  percent(count:Int): String{
        total()
        var total=totalcount.value!!
        var counttodouble= count.toDouble()!!
        var percentage=(counttodouble/total)*100
        var per_2 = String.format("%.2f", percentage)
        var percentinstring="$count($per_2)%"

        return percentinstring

    }

    fun updaterank(){
        val map = mapOf("RED" to countred.value, "BLUE" to countblue.value, "ORANGE" to countorange.value,"GREEN" to countgreen.value)
        var new = map.toList().sortedBy { (key, value) -> value }
        firstrank.value = "1st " + new.elementAt(3).first.toString()
        secondrank.value = "2nd " + new.elementAt(2).first.toString()
        thirdrank.value = "3rd " + new.elementAt(1).first.toString()
        fourrank.value = "4th " + new.elementAt(0).first.toString()

    }
}


