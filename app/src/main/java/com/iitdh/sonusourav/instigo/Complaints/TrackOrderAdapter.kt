package com.iitdh.sonusourav.instigo.Complaints

import com.transferwise.sequencelayout.SequenceAdapter
import com.transferwise.sequencelayout.SequenceStep

class TrackOrderAdapter(private val items: List<TrackingItems>) : SequenceAdapter<TrackOrderAdapter.TrackingItems>() {

  override fun getCount(): Int {
    return items.size
  }

  override fun getItem(position: Int): TrackingItems {
    return items[position]
  }

  override fun bindView(sequenceStep: SequenceStep, item: TrackingItems) {
    with(sequenceStep) {
      setActive(item.isActive)
      setAnchor(item.anchor)
      setTitle(item.title)
      setTitleTextAppearance(android.R.style.TextAppearance_Material_Body2)
      setSubtitle(item.subtitle)
      setAnchorMinWidth(200)
    }
  }

  data class TrackingItems(val isActive: Boolean,
    val anchor: String,
    val title: String,
    val subtitle: String

  )
}