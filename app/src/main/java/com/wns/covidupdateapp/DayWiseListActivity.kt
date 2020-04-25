package com.wns.covidupdateapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_day_wise_list.*

class DayWiseListActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_wise_list)
//        supportActionBar!!.hide()

        var dateStr = intent.getStringExtra("date")
        filterQueryWise(dateStr)
    }

    fun filterQueryWise(dateStr:String){
        val reference = FirebaseDatabase.getInstance().reference
        val query: Query = reference.child("country_wise").orderByChild("date").equalTo(dateStr)
        println("query is ajshd asd "+query.path)
        query.addValueEventListener(object : ChildEventListener, ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                println("data changed "+dataSnapshot.value)
                if (dataSnapshot.exists()) { // dataSnapshot is the "issue" node with all children with id 0
                    val mAdapter =
                        DayWiseListAdapter(dataSnapshot.children.toMutableList())
                    val mLayoutManager: RecyclerView.LayoutManager =
                        LinearLayoutManager(applicationContext)
                    recyclerView.layoutManager = mLayoutManager
                    recyclerView.itemAnimator = DefaultItemAnimator()
                    recyclerView.adapter = mAdapter
                    for (issue in dataSnapshot.children) { // do something with the individual "issues"
                        println("data us "+issue.value)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("cancelled "+databaseError.message)
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                println("data moved "+p0.value)
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                println("data changed "+p0.value)
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                println("data changed "+p0.value)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}
