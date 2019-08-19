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
import com.transferwise.sequencelayout.SequenceLayout
import kotlinx.android.synthetic.main.activity_track_order.*

class TrackOrderActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_track_order)

    val status:Int = intent.getIntExtra("status",0)
    val statusDate:ArrayList<String> = intent.getStringArrayListExtra("statusDate")

    Log.d("TrackOrderActivity",statusDate.toString());
    Log.d("TrackOrderActivity",status.toString());

    for (i in status until  7){
      statusDate.add(i,"0");
    }
    Log.d("Track",statusDate.toString());

    val statusList:ArrayList<TrackingItems> = ArrayList()
    statusList.add(TrackingItems(false,statusDate[0],"Opened by user",""))
    statusList.add(TrackingItems(false,statusDate[1],"Validated by Secy",""))
    statusList.add(TrackingItems(false,statusDate[2],"Forwarded by warden",""))
    statusList.add(TrackingItems(false,statusDate[3],"Approved by IPS office",""))
    statusList.add(TrackingItems(false,statusDate[4],"Work ongoing",""))
    statusList.add(TrackingItems(false,statusDate[5],"Worked resolved",""))
    statusList.add(TrackingItems(false,statusDate[6],"Complaint closed",""))

    val tempStatusList:ArrayList<TrackingItems> = ArrayList()

    for (i in 0 until (status-1)) {
     tempStatusList.add(statusList[i])
      Log.d("Track",statusList[i].anchor);
    }
    tempStatusList.add(TrackingItems(true,statusList[status].anchor,statusList[status].title,statusList[status].subtitle))

    sequence_layout.setAdapter(TrackOrderAdapter(tempStatusList))
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {

    val actionBar = supportActionBar!!
    actionBar.setHomeButtonEnabled(true)
    actionBar.setDisplayHomeAsUpEnabled(true)
    actionBar.setBackgroundDrawable(ColorDrawable(Color.parseColor("#5cae80")))
    actionBar.title = Html.fromHtml("<font color='#ffffff'>Complaint</font>") as CharSequence?
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

