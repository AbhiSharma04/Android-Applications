package edu.umb.cs.hw3

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import edu.umb.cs.hw3.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {


    private val viewModel:MyViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    val HIGHEST_SCORE = intPreferencesKey("highest_score")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.viewmodel = viewModel
        binding.lifecycleOwner=this


        /** Datastore operations**/
        //use map to access the data, returns a Flow<Int>
        var curHS = dataStore.data.map {
            it[HIGHEST_SCORE]
        }

        //read the int value by calling "first()"
        //the operation has to be handled by a separate coroutine
        GlobalScope.launch {
            var high_socre = curHS.first()?.toInt() ?: 0
            viewModel.setHighScore(high_socre)
            println("Highest score:"+curHS.first().toString())
        }
        //modify the value
        GlobalScope.launch {
            dataStore.edit {
                it[HIGHEST_SCORE] = 5
            }
        }
        //read the value again
        curHS = dataStore.data.map {
            it[HIGHEST_SCORE]
        }
        GlobalScope.launch {
            println("Highest score:"+curHS.first().toString())
        }

        val gridView=binding.gridView1
        val adapter = ArrayAdapter(
            this,
            R.layout.list_item, R.id.textView, viewModel.tiles
        )
        gridView.adapter = adapter

        gridView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?, v: View,
                position: Int, id: Long
            ) {
                viewModel.movement(position,binding,dataStore)
                Toast.makeText(
                    applicationContext,
                    position.toString() as CharSequence,
                    Toast.LENGTH_SHORT
                ).show()
                Log.i("MyLog",position.toString())
                //Log.i("MyLog",id.toString())
            }


        }



        val myHandler = object: Handler(Looper.getMainLooper()){
            override fun handleMessage(message: Message) {
                viewModel.decCounter()
                println("handle msg:"+viewModel.counter.value.toString())

            }
        }

        binding.button.setOnClickListener{
            viewModel.treasures.value=0
            viewModel.tilescovered.value=0
            //viewModel.score.value=0
            viewModel.myInit()
            (binding.gridView1.adapter as ArrayAdapter<String>).notifyDataSetChanged()
            viewModel.X_position(binding)
            (binding.gridView1.adapter as ArrayAdapter<String>).notifyDataSetChanged()
            //viewModel.counter1()
            viewModel.counter2(dataStore)
            //viewModel.counter3(binding)
            //viewModel.viewModelScope.launch{
            //    counter4(myHandler)
            //}

        }
    }

    suspend fun counter4(myHandler: Handler) {
        viewModel.initTimer()
        repeat(viewModel.counter.value?:0){
            delay(1000)
            myHandler.sendEmptyMessage(0)
        }
    }
}