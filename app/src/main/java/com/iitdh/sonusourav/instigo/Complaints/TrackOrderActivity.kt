package com.iitdh.sonusourav.instigo.Complaints

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.iitdh.sonusourav.instigo.Complaints.TrackOrderAdapter.TrackingItems
import com.iitdh.sonusourav.instigo.R
import kotlinx.android.synthetic.main.activity_track_order.sequence_layout

class TrackOrderActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_track_order)

    val statusDate:ArrayList<String> = intent.getStringArrayListExtra("statusDate")
    val trackStatus: ArrayList<String> = intent.getStringArrayListExtra("trackStatus")

    Log.d("TrackOrderActivity", statusDate.toString())
    Log.d("TrackOrderActivity", trackStatus.toString())

    val statusList:ArrayList<TrackingItems> = ArrayList()
    for (i in 0 until (trackStatus.size - 1)) {
      statusList.add(TrackingItems(false, statusDate[i], trackStatus[i], ""))
    }
    statusList.add(
        TrackingItems(true, statusDate[trackStatus.size - 1], trackStatus[trackStatus.size - 1], "")
    )
    Log.d("Track", statusList.toString())

    sequence_layout.setAdapter(TrackOrderAdapter(statusList))
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {

    val actionBar = supportActionBar!!
    actionBar.setHomeButtonEnabled(true)
    actionBar.setDisplayHomeAsUpEnabled(true)
    actionBar.setBackgroundDrawable(ColorDrawable(Color.parseColor("#5cae80")))
    actionBar.title = Html.fromHtml("<font color='#ffffff'>Complaint</font>")
    return super.onCreateOptionsMenu(menu)

  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {

    super.onOptionsItemSelected(item)

    when (item.itemId) {
      android.R.id.home -> {
        onBackPressed()
        return true
      }
    }
    return true

  }
}

