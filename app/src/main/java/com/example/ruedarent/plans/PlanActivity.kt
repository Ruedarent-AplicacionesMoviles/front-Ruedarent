package com.example.ruedarent.plans

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ruedarent.R
import com.example.ruedarent.data.DatabaseBuilder
import com.example.ruedarent.plans.data.Plan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlanActivity : AppCompatActivity() {
    private lateinit var backarrow : ImageView

    lateinit var plans: List<Plan>
    lateinit var planAdapter: PlanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan) // Asegúrate de que el layout sea el correcto

        // Inicializar RecyclerView
        val rvplan = findViewById<RecyclerView>(R.id.rv_plan)
        rvplan.layoutManager = LinearLayoutManager(this)

        // Inicializar el Adapter vacío por el momento
        planAdapter = PlanAdapter(listOf())
        rvplan.adapter = planAdapter
        backarrow = findViewById(R.id.backButton2)


        // Cargar los datos
        backarrow.setOnClickListener {
            finish()
        }

        insertPlansManually()
    }

    private fun insertPlansManually() {
        CoroutineScope(Dispatchers.IO).launch {
            if (DatabaseBuilder.getInstance(this@PlanActivity).planDao().getAll().isEmpty()) {
                val plan1 = Plan(
                    id = 1,
                    plan_name = "Bronze",
                    plan_description = "Free per month and then $1.99/month",
                    plan_benefit1 = "Allows 2 publications per month",
                    plan_benefit2 = "Basic assistance"
                )
                val plan2 = Plan(
                    id = 2,
                    plan_name = "Silver",
                    plan_description = "$5.99/month",
                    plan_benefit1 = "Allows 10 publications per month",
                    plan_benefit2 = "Basic assistance"
                )
                val plan3 = Plan(
                    id = 3,
                    plan_name = "Gold",
                    plan_description = "$19.99/month",
                    plan_benefit1 = "Unlimited publications",
                    plan_benefit2 = "Assistance 24/7"
                )

                DatabaseBuilder.getInstance(this@PlanActivity).planDao().insertPlan(plan1, plan2, plan3)
            }

            loadPlan()
        }
    }



    private fun loadPlan(){
        CoroutineScope(Dispatchers.IO).launch {
            // Cargar los planes desde la base de datos
            plans = DatabaseBuilder.getInstance(this@PlanActivity).planDao().getAll()

            // Actualizar el RecyclerView en el hilo principal
            launch(Dispatchers.Main) {
                planAdapter = PlanAdapter(plans) // Crear un nuevo adapter con los datos
                findViewById<RecyclerView>(R.id.rv_plan).adapter = planAdapter // Establecer el nuevo adapter
            }
        }
    }



}