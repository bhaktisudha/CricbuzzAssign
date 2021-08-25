package com.bhaktisudha.cricbuzz

import Categories
import Menu_items
import Menus
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinnewsapirecycler.RestaurantData.Restaurant
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class MenuActivity : AppCompatActivity() {
    private var menuList = ArrayList<Menus>()
    private var catagoryList = ArrayList<Categories>()
    private var menuItemsList = ArrayList<Menu_items>()
    lateinit var menuText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        init()
    }

    fun init() {
        try {
            menuText = findViewById(R.id.menu_text)
            val menuObj = JSONObject(loadMenuJSONFromAsset())
            val menuAaaray = menuObj.getJSONArray("menus")

            for (i in 0 until menuAaaray.length()) {
                val menuDetails = menuAaaray.getJSONObject(i)

                var menu = Menus()
                menu.restaurantId = menuDetails.get("restaurantId") as Int?
                val menuCatagory = menuDetails.getJSONArray("categories")
                // val menuAaaray = menuObj.getJSONArray("menus")
                for (i in 0 until menuCatagory.length()) {
                    val menuDetail = menuCatagory.getJSONObject(i)
                    val categories = Categories()
                    // categories.id = menuDetail.get("id") as Int?
                    categories.name = menuDetail.get("name").toString()
                    val menuAaaray = menuDetail.getJSONArray("menu_items")
                    for (i in 0 until menuAaaray.length()) {
                        val menuItemDetail = menuAaaray.getJSONObject(i)
                        menuItemDetail.get("description")
                        var menuItems = Menu_items()
                        menuItems.description = menuItemDetail.get("description").toString()
                        menuItemsList.add(menuItems)
                    }
                    //  categories.menu_items = menuDetail.get("menu_items") as List<Menu_items>?
                    catagoryList.add(categories)
                    /*  var menuItems = Menu_items()
                      menuItems.id = catagoryList.get(i).menu_items?.get(i)?.id
                      menuItems.name = catagoryList.get(i).menu_items?.get(i)?.description
                      menuItemsList.add(menuItems)*/
                    menuItemsList.size

                }
                menu.categories = catagoryList
                menuList.add(menu)
                catagoryList.forEach {

                    menuText.text = it.name

                }


            }


        } catch (e: JSONException) {
            e.printStackTrace()
        }

        var name: TextView = findViewById(R.id.rest_name)
        val people = intent.getSerializableExtra("resturant") as? Restaurant

        name.text = people?.name
        val id = people?.id ?: null

        menuList.forEach {
            if (it.restaurantId == id) {
                Toast.makeText(this, "Itemclicked ${it.restaurantId}", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun loadMenuJSONFromAsset(): String {
        val json: String?
        try {
            val inputStream = assets.open("menus.json")
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

}