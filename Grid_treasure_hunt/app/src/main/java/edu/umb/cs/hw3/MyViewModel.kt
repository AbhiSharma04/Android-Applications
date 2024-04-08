package edu.umb.cs.hw3

import android.util.Log
import android.widget.ArrayAdapter
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.umb.cs.hw3.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList
val HIGHEST_SCORE = intPreferencesKey("highest_score")
class MyViewModel: ViewModel() {
    private var counterJob: Job?=null
    private var playerJob: Job?=null

    private val r = Random()
    val w = 5
    var tiles = ArrayList<String>(w * w)
    val timeout=20

    var curx=0
    var cury=0
    var X_pos=0
    var O_pos=0
    var flag=0
    var count=0
    var high_score= MutableLiveData<Int>(0)
    var treasures=MutableLiveData<Int>()
    var tilescovered=MutableLiveData<Int>()
    val string1=MutableLiveData<String>()
    val string2=MutableLiveData<String>()
    val string3=MutableLiveData<String>()


    /*********/
    init{
        myInit()
        high_score.value=0
        treasures.value=0
        tilescovered.value=0
        string1.value="  Treasures"
        string2.value=" Tiles"
        string3.value=" :High Score"
        //X_position()
    }
    var _counter = MutableLiveData (0)

    val counter: LiveData<Int>
        get() = _counter

    fun myInit(){
        tiles.clear()
        repeat (w*w) {
            tiles.add(" ")
        }
        curx = r.nextInt(w)
        cury = r.nextInt(w)
        O_pos = cury * w + curx;
        Log.i("MyLog", O_pos.toString()+"player pos")
        tiles.set(O_pos, "O")
    }


    fun pos():Int{
        curx = r.nextInt(w)
        cury = r.nextInt(w)
        X_pos = cury * w + curx
        return X_pos
    }
    fun X_position(binding: ActivityMainBinding) { CoroutineScope(Dispatchers.Main).launch {
        var j=0;
        Log.i("MyLog", flag.toString()+"tf")
        while (j<4) {
            X_pos = pos()
            Log.i("MyLog", X_pos.toString())
            if (tiles.get(X_pos).contains("X")) {
                continue
            }
            else if(tiles.get(X_pos).contains(("O"))){
                continue
            }
               else {
                delay(1000)
                tiles.set(X_pos, "X")
                (binding.gridView1.adapter as ArrayAdapter<String>).notifyDataSetChanged()
                j++

            }
            }

        }

    }

    fun movement(position:Int,binding: ActivityMainBinding, dataStore: DataStore<Preferences>){

        if(playerJob?.isActive == true) playerJob?.cancel()

        playerJob = CoroutineScope(Dispatchers.Main).launch {

            updatehighscore(dataStore)


            Log.i("MyLog", position.toString()+"p")
        if(_counter.value!! >0)  {

            var end_pos=position
        var end_pos_x=end_pos%5
        var end_pos_y=end_pos/5


            var O_pos_x=O_pos%5
            var O_pos_y=O_pos/5

        while((end_pos)!=O_pos){

            tiles.set(O_pos," ")
            Log.i("MY", O_pos_x.toString() + " " + O_pos_y)
                    Log.i("MY", end_pos_x.toString() + " " + end_pos_y)
            if(O_pos_x>end_pos_x){
                O_pos_x -= 1
                Log.i("MY", O_pos_x.toString() + " " + O_pos_y)
                Log.i("MY", end_pos_x.toString() + " " + end_pos_y)

                    Log.i("MyLog", "Score is = "+count)
            }
            else if(O_pos_x<end_pos_x){
                O_pos_x += 1
                Log.i("MyLog", O_pos_y.toString()+"r")

            }
            else if(O_pos_y>end_pos_y){
                O_pos_y-=1

            }
            else if(O_pos_y<end_pos_y) {
                O_pos_y += 1

            }
            if(tiles.get(O_pos_y*5+O_pos_x).contains("X")) {
                treasures.value= treasures.value?.plus(1)
                updatehighscore(dataStore)
                tiles.set(O_pos_y*5+O_pos_x,"O")
                (binding.gridView1.adapter as ArrayAdapter<String>).notifyDataSetChanged()
                X_pos = pos()
                Log.i("MyLog", "X generation from movement$X_pos")
                if (tiles.get(X_pos).contains("X")) {
                    continue
                }
                else if(tiles.get(X_pos).contains(("O"))){
                    continue
                }
                else {
                    delay(500)
                    tiles.set(X_pos, "X")
                    (binding.gridView1.adapter as ArrayAdapter<String>).notifyDataSetChanged()
                }
            }
            else{
            tiles.set(O_pos_y*5+O_pos_x,"O")
            (binding.gridView1.adapter as ArrayAdapter<String>).notifyDataSetChanged()}

            tilescovered.value=(tilescovered.value)?.plus(1)
            O_pos=O_pos_y*5+O_pos_x

            Log.i("MyLog", "Count is = "+high_score.value)
            Log.i("MyLog", "Temp pos = "+O_pos)
            delay(500)
        }

    }
        }}


    fun setCounter(i:Int){_counter.value=i}

    fun decCounter(){
        _counter.value = _counter.value?.minus(1)
    }


    fun counter1() {
        viewModelScope.launch{
            initTimer()
            while(counter.value!! >0){
                delay(1000)
                decCounter()
            }
        }
    }

    fun setHighScore(high_socre: Int) {
        high_score.postValue(high_socre)
    }

    fun counter2(dataStore: DataStore<Preferences>){
        if(counterJob?.isActive == true) counterJob?.cancel()
        counterJob=viewModelScope.launch{
            initTimer()
            try {
                repeat(timeout) {
                    delay(1000)
                    decCounter()
                }
            }catch (e:CancellationException){}
        }
    }

    fun counter3(binding: ActivityMainBinding) {
        viewModelScope.launch{//(Dispatchers.Default){
            var i=0
            println(Thread.currentThread().name)
            while(i <=timeout){
                delay(1000)
                //Thread.sleep(1000)
                //decCounter()
                //withContext(Dispatchers.Main) {
                binding.textView2.text = (timeout - i).toString()
                //}
                i++
            }
        }
    }
    fun updatehighscore(dataStore: DataStore<Preferences>){
        if(treasures.value!! >= high_score.value!!){
            high_score.value=treasures.value
        }
        GlobalScope.launch {
            dataStore.edit {
                it[HIGHEST_SCORE] = high_score.value ?: 0
            }
        }
    }
    fun initTimer(){
        setCounter(timeout);
    }


}