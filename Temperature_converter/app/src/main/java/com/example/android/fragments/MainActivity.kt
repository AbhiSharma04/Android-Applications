package com.example.android.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragTran = supportFragmentManager.beginTransaction()
        fragTran.add(R.id.article_fragment, BottomFragment())
        fragTran.commit()
    }

    fun onArticleSelected(position: Int) {

        val articleFrag =
            supportFragmentManager.findFragmentById(R.id.article_fragment) as BottomFragment

        //articleFrag.updateArticleView(position)
        when (position) {
            0 -> {
                supportFragmentManager.beginTransaction().apply {
                    help()
                    intent.putExtra("USER", "0")
                    replace(R.id.temperature_converter_fragment, TemperatureConverter()).commit()

                }
            }
            1 -> {
                supportFragmentManager.beginTransaction().apply {
                    help()
                    intent.putExtra("USER", "1")
                    replace(R.id.temperature_converter_fragment, TemperatureConverter()).commit()


                }
            }
            2 -> {
                supportFragmentManager.beginTransaction().apply {
                    intent.putExtra("USER", "2")
                    replace(R.id.temperature_converter_fragment, HelpFragment()).commit()
                }
            }
            //val article = findViewById<TextView>(R.id.article)
            //article.text = Ipsum.Articles[position]
        }

    }
    fun help() {
        supportFragmentManager.beginTransaction()
            .apply { replace(R.id.temperature_converter_fragment, HelpFragment()).commit() }
    }
}