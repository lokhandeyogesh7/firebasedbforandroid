package com.wns.covidupdateapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_firebase_data.*


class FirebaseDataActivity : AppCompatActivity() {
    private lateinit var dbReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_data)
        supportActionBar!!.hide()
        button.setOnClickListener {
            filterQueryWise()
        }

        val tabsPagerAdapter = TabsAdapter(this, supportFragmentManager)

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = tabsPagerAdapter

        val tabs = findViewById<TabLayout>(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


//get reference to the "users" node
        dbReference = FirebaseDatabase.getInstance().getReference("country_wise")
        // Read from the database
        // Read from the database
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) { // This method is called once with the initial value and again
// whenever data at this location is updated.
                val value =
                    dataSnapshot.value!! as HashMap<*,*>
//                Log.d("FragmentActivity.TAG", "Value is: $value")
                println("values are "+value.size)
                for (postSnapshot1 in dataSnapshot.children) {
                    println("inside for loop "+postSnapshot1.value)
                    println("inside for loop "+postSnapshot1.child("country_name").value)
                    val temp  = postSnapshot1.child("country_name")
                    println("temp "+temp.value)
                }
            }

            override fun onCancelled(error: DatabaseError) { // Failed to read value
                Log.w("FragmentActivity.TAG", "Failed to read value.", error.toException())
            }
        })

    }

    fun filterQueryWise(){
        val reference = FirebaseDatabase.getInstance().reference
        val query: Query = reference.child("country_wise").orderByChild("country_name").equalTo("Poland")
        println("query is ajshd asd "+query.path)
        query.addValueEventListener(object : ChildEventListener, ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                println("data changed "+dataSnapshot.value)
                if (dataSnapshot.exists()) { // dataSnapshot is the "issue" node with all children with id 0
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
