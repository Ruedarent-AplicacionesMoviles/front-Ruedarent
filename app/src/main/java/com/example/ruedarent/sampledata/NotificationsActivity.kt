package com.example.ruedarent.sampledata

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ruedarent.R
import com.example.ruedarent.data.DatabaseBuilder
import com.example.ruedarent.data.sampledata.Notifications
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationsActivity : AppCompatActivity(){
private lateinit var backarrow3 : ImageView
lateinit var notifications: List<Notifications>
lateinit var notificationsAdapter: NotificationAdapter



    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notifications)

    val rvNotificacion = findViewById<RecyclerView>(R.id.rvNotifications)
    rvNotificacion.layoutManager = LinearLayoutManager(this)

    notificationsAdapter = NotificationAdapter(listOf())
    backarrow3 = findViewById(R.id.backButton3)

        backarrow3.setOnClickListener {
        finish()
     }

        intertNotificationManually()



    }

    private fun intertNotificationManually() {
        CoroutineScope(Dispatchers.IO).launch {
            if(DatabaseBuilder.getInstance(this@NotificationsActivity).notificationsDao().getAll().isEmpty()){
                val noti1 = Notifications(
                    id = 1,
                    title = "Bienvenido",
                    message = "Gracias por elegirnos"

                )
                val noti2 = Notifications(
                    id = 2,
                    title = "Fuiste Elegido",
                    message = "Para representarnos en la plataforma"
                )
                val noti3 = Notifications(
                    id = 3,
                    title = "Eres increible",
                    message = "Gracias por elegirnos"
                )

                DatabaseBuilder.getInstance(this@NotificationsActivity).notificationsDao().insert(noti1,noti2,noti3)
            }
            loadNotifications()
        }
    }

    private fun loadNotifications() {
        CoroutineScope(Dispatchers.IO).launch {
            notifications = DatabaseBuilder.getInstance(this@NotificationsActivity).notificationsDao().getAll()

            launch(Dispatchers.Main){
                notificationsAdapter = NotificationAdapter(notifications)
                findViewById<RecyclerView>(R.id.rvNotifications).adapter = notificationsAdapter
            }
        }
    }

}