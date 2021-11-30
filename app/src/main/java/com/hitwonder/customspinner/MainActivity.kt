package com.hitwonder.customspinner

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources


class MainActivity : AppCompatActivity() {

    lateinit var tvSelectContent: TextView
    val customSpinnerList = ArrayList<String>()
    var popupWindowDogs: PopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tvSelectContent = findViewById(R.id.tvSelectContent)

        customSpinnerList.add("Kawasaki Ninja H2")
        customSpinnerList.add("Yamaha YZF-R1")
        customSpinnerList.add("BMW S 1000 RR")
        customSpinnerList.add("Suzuki Hayabusa")
        customSpinnerList.add("Honda CBR1000RR")

        // initialize pop up window once items are added in the array
        popupWindowDogs = popupWindowDogs()

        tvSelectContent.setOnClickListener { view ->
            popupWindowDogs!!.showAsDropDown(view, -5, 0)
        }

    }


    private fun popupWindowDogs(): PopupWindow {
        // initialize a pop up window type
        val popupWindow = PopupWindow(this)

        // the drop down list is a list view
        val listView = ListView(this)
        listView.adapter = customSpinnerAdapter()

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { a, v, position, id ->

                // add some animation when a list item was clicked
                val fadeInAnimation: Animation =
                    AnimationUtils.loadAnimation(v.context, android.R.anim.fade_in)
                fadeInAnimation.duration = 10
                v.startAnimation(fadeInAnimation)

                // dismiss the pop up (close)
                popupWindowDogs!!.dismiss()

                // get selected text
                tvSelectContent.text = a.getItemAtPosition(position).toString()

            }

        // some other visual settings
        popupWindow.isFocusable = true

        // set pop width & height
        popupWindow.width = 900
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT

        // set background for pop window
        popupWindow.setBackgroundDrawable(
            AppCompatResources.getDrawable(
                applicationContext,
                R.drawable.round_pop_background
            )
        )


        // set the list view as pop up window content
        popupWindow.contentView = listView
        return popupWindow
    }

    private fun customSpinnerAdapter(): ArrayAdapter<String> {
        return object :
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, customSpinnerList) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                val item = getItem(position)
                // visual settings for the list item
                val listItem = TextView(this@MainActivity)
                // set text to list
                listItem.text = item.toString()
                // set text size
                listItem.textSize = 18f
                // set spacing
                listItem.setPadding(30, 30, 30, 30)
                // text colour of list
                listItem.setTextColor(Color.BLACK)
                return listItem
            }
        }
    }
}