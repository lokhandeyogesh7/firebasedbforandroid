package com.wns.covidupdateapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wns.covidupdateapp.sheetlist.SheetList
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {

    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFirebase.visibility = View.GONE
        btnFirebase.setOnClickListener {
            startActivity(Intent(this@MainActivity,FirebaseDataActivity::class.java))
        }

        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)

        showProgressBar()


        object : Thread() {
            override fun run() {
                super.run()
                try {
                    val transport = AndroidHttp.newCompatibleTransport()
                    val factory: JsonFactory = JacksonFactory.getDefaultInstance()
                    val sheetsService = Sheets.Builder(transport, factory, null)
                        .setApplicationName("GoogleSheetDemo")
                        .build()
                    val spreadsheetId: String = Config.spreadsheet_id

                    val test = sheetsService.spreadsheets().get(spreadsheetId)
                        .setKey(Config.google_api_key).execute()
                    val sheetsts = test["sheets"]

                    println("shhets atr " + sheetsts)
                    var jsonStr = sheetsts.toString()
                    ///jsonStr.startsWith()
                    if (jsonStr.startsWith("[")) {
                        jsonStr = jsonStr.removeRange(0, 1)
                        println("in if " + jsonStr)
                    }
                    if (jsonStr.endsWith("]")) {
                        println("json str 2" + jsonStr[jsonStr.length - 2])
                        println("json str 1" + jsonStr[jsonStr.length - 1])
                        jsonStr = jsonStr.removeRange(jsonStr.length - 1, jsonStr.length)
                        println("in if else " + jsonStr)
                    }
                    println("json string is " + jsonStr)

                    val gson1 = Gson()
                    val jsonOutput = sheetsts.toString()
                    val listType: Type = object : TypeToken<List<SheetList?>?>() {}.type
                    val posts: List<SheetList> = gson1.fromJson(jsonOutput, listType)

                    println("list is " + posts.size)

                    //val sheetResponse  = Gson().fromJson(sheetsts.toString(),SheetList::class.java)

                    val gson = GsonBuilder().create()
//                    val list: List<SheetList> =Arrays.asList(Gson().fromJson(sheetsts.toString(),SheetList::class.java))

//  ?\                  println("sheet response is "+list.size)

                    val dataAdapter = CustomAdapter(
                        this@MainActivity,
                        posts
                    )

                    runOnUiThread {
                        spinner.adapter = dataAdapter
                        spinner.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) { // Get the value selected by the user
                                    println("get selected item " + parent.selectedItemPosition)
                                    print("selected " + posts[parent.selectedItemPosition].properties!!.title)
                                    Thread().run {
                                        showProgressBar()
                                        var ranges =
                                            posts[parent.selectedItemPosition].properties!!.title
                                        ranges = ranges + "!A:D"
                                        val result = sheetsService.spreadsheets().values()
                                            .get(spreadsheetId, ranges)
                                            .setKey(Config.google_api_key)
                                            .execute()
                                        for (i in 0 until result.getValues().size) {
                                            println("lift namde diadsdhkj " + result.getValues()[i].toString().toLowerCase())
                                            if (result.getValues()[i].toString().toLowerCase().contains(
                                                    "country"
                                                )
                                            ) {
                                                result.getValues().removeAt(i)
                                                break
                                            }
                                        }
                                        val mAdapter =
                                            SheetListsAdapter(result.getValues() as MutableList<MutableList<String>>)
                                        val mLayoutManager: RecyclerView.LayoutManager =
                                            LinearLayoutManager(applicationContext)
                                        recyclerView.layoutManager = mLayoutManager
                                        recyclerView.itemAnimator = DefaultItemAnimator()
                                        recyclerView.adapter = mAdapter
                                    }
                                    hideProgressBar()
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {}
                            }
                    }

                    /* val ranges: List<String> = Arrays.asList(
                         "22/4/2020!A:D"
                     )
                     val result = sheetsService.spreadsheets().values()
                         .batchGet(spreadsheetId)
                         .setKey(Config.google_api_key)
                         .setRanges(ranges).execute()

                     for (i in 0 until result.valueRanges.size) {
                         println("get us d d " + result.valueRanges[i].getValues().size)
                         for (j in 0 until result.valueRanges[i].getValues().size) {
                             println("lift namde diadsdhkj " + result.valueRanges[i].getValues()[j])
                         }
                     }

                     Log.d(
                         "success.",
                         "ranges retrieved: " + result.valueRanges.size
                     )*/

                } catch (e: Exception) {
                    e.printStackTrace()
                    println("exception " + e.localizedMessage)
                }
            }
        }.start()
    }


    class CustomAdapter(
        var context: Context,
        var countryNames: List<SheetList>
    ) :
        BaseAdapter() {
        var inflter: LayoutInflater
        override fun getCount(): Int {
            return countryNames.size
        }

        @SuppressLint("ViewHolder")
        override fun getView(
            i: Int,
            view: View?,
            viewGroup: ViewGroup?
        ): View {
            var view1 = view
            view1 = inflter.inflate(R.layout.spinner, null)
            val names = view1.findViewById<View>(R.id.title) as TextView
            names.text = countryNames[i].properties!!.title
            return view1
        }

        override fun getItem(p0: Int): Any {
            return p0
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        init {
            inflter = LayoutInflater.from(context)
        }
    }

    fun showProgressBar() {
        /*progressDialog = ProgressDialog(this)
        progressDialog!!.show()*/
        progressbar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    fun hideProgressBar() {
      //  println("hide progress" + progressDialog!!.isShowing)
        progressbar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
       /* if (progressDialog!!.isShowing) {
            progressDialog!!.hide()
            progressDialog = ProgressDialog(this)
            progressDialog!!.hide()
//            println("hide progress" + progressDialog!!.isShowing)
        }*/
    }
}
