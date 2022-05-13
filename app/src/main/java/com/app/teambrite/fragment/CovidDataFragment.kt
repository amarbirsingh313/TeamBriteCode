package com.app.teambrite.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.teambrite.R
import com.app.teambrite.adapter.Covid19DataAdapter
import com.app.teambrite.model.Covid19DataModel

class CovidDataFragment(val mcontext: Context, val title: String, val covid19DataModel: Covid19DataModel) : Fragment() {
    lateinit var recycler_view: RecyclerView
    lateinit var covid19DataAdapter: Covid19DataAdapter
    var state: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tests, container, false)
        // Inflate the layout for this fragment
        recycler_view = view.findViewById(R.id.recyclerview)
        recycler_view.setVisibility(View.VISIBLE)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recycler_view.setLayoutManager(layoutManager)
        state = title
        covid19DataAdapter = Covid19DataAdapter(mcontext,title,covid19DataModel)

        recycler_view.setAdapter(covid19DataAdapter)
        return view
    }

}