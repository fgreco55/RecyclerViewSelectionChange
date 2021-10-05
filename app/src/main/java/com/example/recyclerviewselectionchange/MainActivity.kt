package com.example.recyclerviewselectionchange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FrankRVAdapter
    private lateinit var tv: EditText
    val frankData = ArrayList<String>()      // create empty ArrayList

    override fun onCreate(savedInstanceState: Bundle?) {

            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val rv = findViewById<RecyclerView>(R.id.recyclerView)
            rv.layoutManager = LinearLayoutManager(this)

            // Create data source -------------------------------
            Log.i("Frank", "Creating ArrayList...")
            createData(frankData, 50)           // populate it

            tv = this.findViewById<EditText>(R.id.itemText)
            Log.i("Frank", "******* tv: [$tv]")

            // Attach your RecyclerView Adapter to the RecyclerView instance
            adapter = FrankRVAdapter(frankData, tv)
            rv.adapter = adapter

            createCallbacks()           // Set up callback for editText
    }

    /*
    createCallbacks() - Keep callback init in separate function
    */
    private fun createCallbacks() {

        // Set the KeyListener on the Edittext and look for <CR> =======================
        tv.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(myview: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {

                    val currentPos = adapter.getPositionSelected()      // Call our method that saved the position of the selected item
                    val userInput = tv.getText().toString()             // just a convenience variable...
                    Log.i("Frank", "ENTER key pressed. pos: $currentPos   selection: [$userInput]")

                    // change the actual data and then tell the adapter things were changed...
                    frankData[currentPos] = userInput
                    adapter.notifyDataSetChanged()          // should really use notifyItemChanged(pos)

                    return true

                } else
                    return false
            }
        })
    }

    /*
     createData() - create a data source (of size num) with random names taken from a static list
     */
    private fun createData(mydata: ArrayList<String>, num: Int) {
        var s: String
        val names = arrayListOf<String>()
        names.addAll(
            listOf(
                "Frank Greco",
                "Miles Davis",
                "Louie Armstrong",
                "Mick Jagger",
                "BB King",
                "Duane Allman",
                "Aretha Franklin"
            )
        )

        for (i in 0..num) {
            s = names.get(Random.nextInt(0, names.size)) + " - $i"
            mydata.add(s)
            Log.i("Frank", "name [" + s + "]")
        }
    }
}
