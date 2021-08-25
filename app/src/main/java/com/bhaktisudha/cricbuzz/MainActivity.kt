package com.bhaktisudha.cricbuzz

import Menus
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinnewsapirecycler.RestaurantData.Restaurant
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset


class MainActivity : AppCompatActivity(), ItemClicked {
    lateinit var resturantAdapter: RestaurantAdapter
    lateinit var input: EditText
    lateinit var etSearch: EditText
    private var rstName = ArrayList<Restaurant>()
    private var menuList = ArrayList<Menus>()
    private var filterResturanat = ArrayList<Restaurant>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {

        input = findViewById(R.id.search_text)
        val recylr: RecyclerView = findViewById(R.id.resturant_recycler)
        recylr.layoutManager = LinearLayoutManager(this)
        resturantAdapter = RestaurantAdapter(this, this)
        recylr.adapter = resturantAdapter
        try {
            val obj = JSONObject(loadJSONFromAsset())
            //val menuObj = JSONObject(loadMenuJSONFromAsset())
            val userArray = obj.getJSONArray("restaurants")
           // val menuAaaray = menuObj.getJSONArray("menus")
            for (i in 0 until userArray.length()) {
                val resturantDetail = userArray.getJSONObject(i)
                resturantDetail.get("name")
                var restaurant = Restaurant()
                restaurant.address = resturantDetail.get("address").toString()
                restaurant.cuisine_type = resturantDetail.get("cuisine_type").toString()
                restaurant.name = resturantDetail.get("name").toString()
                restaurant.id = resturantDetail.get("id") as Int?
                rstName.add(restaurant)
                println("bbhh${resturantDetail.get("name")} ")
                println("BHAK" + rstName)
            }
           /* for (i in 0 until menuAaaray.length()){
                val menuDetails = menuAaaray.getJSONObject(i)

                var menu = Menus()
                menu.restaurantId = menuDetails.get("restaurantId") as Int?
                menuList.add(menu)


            }*/

            resturantAdapter.updateList(rstName)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        etSearch = findViewById(R.id.search_text)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filterItem(s.toString())
            }
        })

        etSearch.setOnClickListener {
            etSearch.text.clear()
        }

    }


    private fun filterItem(text: String) {
        filterResturanat.clear()
        rstName.forEach {
            val name: String = it.toString()
            if (name.toLowerCase().contains(text.toLowerCase())) {

                filterResturanat.add(it)
            }
        }


        resturantAdapter.updateList(filterResturanat)
    }

    private fun loadJSONFromAsset(): String {
        val json: String?
        try {
            val inputStream = assets.open("restaurants.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json

    }


    override fun itemClicked(clickItem: Restaurant) {
        rstName.forEach {

            if(clickItem.id == it.id){
                Toast.makeText(this,"Itemclicked ${it.name}",Toast.LENGTH_SHORT).show()
                var menuActivity = Intent(this, MenuActivity::class.java)
                menuActivity.putExtra("resturant",clickItem)
                startActivity(menuActivity)
            }
        }

    }
}



