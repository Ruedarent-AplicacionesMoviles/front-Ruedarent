package com.example.ruedarent.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ruedarent.R
import com.example.ruedarent.notifications.data.Notifications

class NotificationAdapter(val notifications: List<Notifications>): RecyclerView.Adapter<NotificationPrototype>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationPrototype {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prototype_notifications, parent, false)
        return NotificationPrototype(view)
    }

    override fun onBindViewHolder(holder: NotificationPrototype, position: Int) {
        holder.bind(notifications.get(position))
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

}

class NotificationPrototype(itemView: View): RecyclerView.ViewHolder(itemView) {
    val tvTitle = itemView.findViewById<TextView>(R.id.tvNotiName)
    val tvBody = itemView.findViewById<TextView>(R.id.tvdescription2)

    fun bind(notification: Notifications){
        tvTitle.text = notification.title
        tvBody.text = notification.message
    }

}
