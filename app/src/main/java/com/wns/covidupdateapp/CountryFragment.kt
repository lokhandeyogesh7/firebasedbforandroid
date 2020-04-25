package com.wns.covidupdateapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_country.*

class CountryFragment : Fragment() {

    var arrayList = ArrayList<CountryModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrayList = ArrayList()

        var model1 = CountryModel("China", R.drawable.ic_china,1)
        arrayList.add(model1)
        var model2 = CountryModel("Costa Rica", R.drawable.ic_costa_rica,2)
        arrayList.add(model2)
        var model3 = CountryModel("India", R.drawable.ic_india,3)
        arrayList.add(model3)
        var model4 = CountryModel("Philippines", R.drawable.ic_philippines,1)
        arrayList.add(model4)
        var model5 = CountryModel("Poland", R.drawable.ic_poland,2)
        arrayList.add(model5)
        var model6 = CountryModel("Romania", R.drawable.ic_romania,3)
        arrayList.add(model6)
        var model7 = CountryModel("South Africa", R.drawable.ic_south_africa,1)
        arrayList.add(model7)
        var model8 = CountryModel("Spain", R.drawable.ic_spain,2)
        arrayList.add(model8)
        var model9 = CountryModel("Sri Lanka", R.drawable.ic_sri_lanka,3)
        arrayList.add(model9)
        var model10 = CountryModel("Turkey", R.drawable.ic_turkey,1)
        arrayList.add(model10)
        var model11 = CountryModel("United Kingdom", R.drawable.ic_uk,2)
        arrayList.add(model11)
        var model12 = CountryModel("United States of America", R.drawable.ic_usa,3)
        arrayList.add(model12)

        val mAdapter =
            CountryAdapter(arrayList, object : CountryAdapter.clickedItem{
                override fun itemClicked(country: String) {
                    startActivity(Intent(activity,CountryWiseListActivity::class.java).putExtra("country",country))
                }
            })
        val mLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(activity)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = mAdapter
    }
}
