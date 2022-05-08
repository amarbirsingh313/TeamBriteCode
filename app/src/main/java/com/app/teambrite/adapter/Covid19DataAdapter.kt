package com.app.teambrite.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.teambrite.R
import com.app.teambrite.database.DBHelper
import com.app.teambrite.model.Covid19DataModel


class Covid19DataAdapter()  : RecyclerView.Adapter<Covid19DataAdapter.ViewHolder>() {
    var state: String=""
    lateinit var covid19DataModel: Covid19DataModel
    lateinit var dbHelper: DBHelper
    var context: Context? = null
    constructor(context: Context, title: String, covid19DataModel: Covid19DataModel) : this(){
        this.state = title
        this.covid19DataModel = covid19DataModel
        dbHelper = DBHelper(context, null)
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View

            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("Range", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when {
            state.equals("Cases") -> {
                holder.textView1.text = "Date: "+covid19DataModel.casesTimeSeryDataModels.get(position).date
                holder.textView2.text = "Total Confirmed: "+covid19DataModel.casesTimeSeryDataModels.get(position).totalconfirmed
                holder.textView3.text = "Total Deceased: "+covid19DataModel.casesTimeSeryDataModels.get(position).totaldeceased
                holder.textView4.text = "Total Recovered: "+covid19DataModel.casesTimeSeryDataModels.get(position).totalrecovered

                holder.imageView.setOnClickListener(View.OnClickListener {
                    dbHelper.addName(covid19DataModel.casesTimeSeryDataModels.get(position).date.toString(),
                        covid19DataModel.casesTimeSeryDataModels.get(position).totalconfirmed.toString(),
                        covid19DataModel.casesTimeSeryDataModels.get(position).totaldeceased.toString(),
                        covid19DataModel.casesTimeSeryDataModels.get(position).totalrecovered.toString())

//                    val cursor = dbHelper.getName()
//
//                    // moving the cursor to first position and
//                    // appending value in the text view
//                    cursor!!.moveToFirst()
//                    print(cursor.getString(cursor.getColumnIndex(DBHelper.TEXT1_COl)))
//                    // at last we close our cursor
//                    cursor.close()
                })
            }
            state.equals("States") -> {
                holder.textView1.text = "State: "+covid19DataModel.statewiseDataModel.get(position).state
                holder.textView2.text = "Active: "+covid19DataModel.statewiseDataModel.get(position).active
                holder.textView3.text = "Recovered: "+covid19DataModel.statewiseDataModel.get(position).recovered
                holder.textView4.text = "Deaths: "+covid19DataModel.statewiseDataModel.get(position).deaths
                holder.imageView.setOnClickListener(View.OnClickListener {
                    dbHelper.addName(covid19DataModel.statewiseDataModel.get(position).state.toString(),
                        covid19DataModel.statewiseDataModel.get(position).active.toString(),
                        covid19DataModel.statewiseDataModel.get(position).recovered.toString(),
                        covid19DataModel.statewiseDataModel.get(position).deaths.toString())
                })
            }
            else -> {
                holder.ll_source.visibility = View.VISIBLE
                holder.textView1.text = "Tested as of: "+covid19DataModel.testedDataModel.get(position).testedasof
                holder.textView2.text = "Daily RTPCR samples collected: "+covid19DataModel.testedDataModel.get(position).dailyrtpcrsamplescollectedicmrapplication
                holder.textView3.text = "Samples reported today: "+covid19DataModel.testedDataModel.get(position).samplereportedtoday
                holder.textView4.text = "Total doses administered: "+covid19DataModel.testedDataModel.get(position).totaldosesadministered
               // holder.textView5.text = covid19DataModel.testedDataModel.get(position).source3

                holder.imageView.setOnClickListener {
                    dbHelper.addName(
                        covid19DataModel.testedDataModel.get(position).testedasof.toString(),
                        covid19DataModel.testedDataModel.get(position).dailyrtpcrsamplescollectedicmrapplication.toString(),
                        covid19DataModel.testedDataModel.get(position).samplereportedtoday.toString(),
                        covid19DataModel.testedDataModel.get(position).totaldosesadministered.toString()
                    )
                }

                holder.textView5.setOnClickListener { view: View ->
                    holder.textView5.setTag(position)
                    val clickedPosition = view.tag as Int
                    val browserIntent =
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(covid19DataModel.testedDataModel.get(clickedPosition).source)
                        )
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context!!.startActivity(browserIntent)

                }
            }
        }
    }

    override fun getItemCount(): Int {
        when {
            state.equals("Cases") -> {
                return covid19DataModel.casesTimeSeryDataModels.size
            }
            state.equals("States") -> {
                return covid19DataModel.statewiseDataModel.size
            }
            else -> {
                return covid19DataModel.testedDataModel.size
            }
        }
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView1: TextView = itemView.findViewById(R.id.text1)
        val textView2: TextView = itemView.findViewById(R.id.text2)
        val textView3: TextView = itemView.findViewById(R.id.text3)
        val textView4: TextView = itemView.findViewById(R.id.text4)
        val ll_source: LinearLayout = itemView.findViewById(R.id.ll_source);
        val textView5: TextView = itemView.findViewById(R.id.text5);
    }

}