package com.tek4tv.login.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.tek4tv.login.R
import com.tek4tv.login.network.UserBody
import com.tek4tv.login.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        btn_login.setOnClickListener {
            login()
        }

        registerObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun registerObservers()
    {
        viewModel.user.observe(this)
        {
            txt_result.text = it.toString()

            val startVideoActivity = Intent(applicationContext, VideoListActivity::class.java)
            startActivity(startVideoActivity)
            finish()
        }
    }

    private fun login()
    {
        if(!isNetworkAvailable())
        {
            Toast.makeText(this, "Network not available", Toast.LENGTH_SHORT).show()
            return
        }

        if(viewModel.token == "")
            viewModel.getToken()

        val username = et_username.text.toString()
        val pass = et_password.text.toString()

        viewModel.login(UserBody(username, pass))
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork ?: return false

        val nc = cm.getNetworkCapabilities(activeNetwork) ?: return false

        return (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                && nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}