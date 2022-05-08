package com.app.teambrite.activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.app.teambrite.R
import com.app.teambrite.adapter.ViewPagerAdapter
import com.app.teambrite.api.TaskServiceAPI
import com.app.teambrite.application.TeamBriteApplication
import com.app.teambrite.fragment.CovidDataFragment
import com.app.teambrite.model.Covid19DataModel
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.Calendar.getInstance

class MainActivity : AppCompatActivity() {
    private lateinit var pager: ViewPager
    private lateinit var tab: TabLayout
    private lateinit var bar: Toolbar
    private var mProgressDialog: ProgressDialog? = null
    val adapter = ViewPagerAdapter(supportFragmentManager)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager = findViewById(R.id.viewPager)
        tab = findViewById(R.id.tabs)
        bar = findViewById(R.id.toolbar)

        setSupportActionBar(bar)

        if (checkForInternet(this)) {
            fetchCovid19Data()
        } else{
            showSessionAlert("No Internet!")
        }

    }

    private fun fetchCovid19Data() {
        showProgressDialog("Please Wait...")
        val taskServiceAPI: TaskServiceAPI? = (application as TeamBriteApplication).getApis()
        val b: Call<Covid19DataModel> = taskServiceAPI!!.getCovid19Data()
        b.enqueue(object : Callback<Covid19DataModel?> {
            override fun onResponse(
                call: Call<Covid19DataModel?>,
                response: Response<Covid19DataModel?>
            ) {
                hideProgressDialog()
                if (response.body() == null) {
                    showSessionAlert("Data Null - Something Went Wrong on Server Side!")
                    return
                }
                if (response.isSuccessful()) {
                    adapter.addFragment(CovidDataFragment(applicationContext, "Cases",
                        response.body()!!
                    ),"Cases")
                    adapter.addFragment(CovidDataFragment(applicationContext, "States",
                        response.body()!!
                    ), "States")
                    adapter.addFragment(CovidDataFragment(applicationContext, "Tests",
                        response.body()!!
                    ), "Tests")

                    pager.adapter = adapter
                    tab.setupWithViewPager(pager)
                } else {
                    showSessionAlert("Error : Something Went wrong on Server Side!")
                }
            }

            override fun onFailure(call: Call<Covid19DataModel?>, t: Throwable) {
                hideProgressDialog()
                if (t is IOException) {
                    showSessionAlert("No network available, please check your WiFi or Data connection!")
                    // logging probably not necessary
                } else {
                    showSessionAlert("Failure - Something Went Wrong on Server Side!")
                    // todo log to some central bug tracking service
                }
            }
        })
    }

    fun showProgressDialog(message: String?) {
        hideProgressDialog()
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this, R.style.AppTheme)
            mProgressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mProgressDialog!!.setCancelable(false)
            if (message != null) {
                mProgressDialog!!.setTitle(message)
            } else {
                mProgressDialog!!.setTitle("Loading...")
            }
            mProgressDialog!!.show()
        } else {
            Log.e("FreeAds", "mProgressDialog is not Null. So Not able to show Progress Dialog.")
        }
    }

    fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing()) {
            mProgressDialog!!.dismiss()
            mProgressDialog = null
        }
    }

    fun showSessionAlert(message: String?) {
        try {
            val alertbuilderupdate: AlertDialog.Builder
            alertbuilderupdate =
                AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert)
            alertbuilderupdate.setCancelable(false)
            alertbuilderupdate.setTitle("Alert")
                .setMessage(message)
                .setPositiveButton(
                    "OK"
                ) { dialog: DialogInterface, which: Int ->
                    // continue with delete
                    dialog.dismiss()
                }
            val alert11 = alertbuilderupdate.create()
            alert11.show()
        } catch (e: Exception) {
            Log.e("error", e.toString())
        }
    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

}