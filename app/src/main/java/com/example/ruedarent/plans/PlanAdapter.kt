package com.example.ruedarent.plans

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.ruedarent.R
import com.example.ruedarent.plans.data.Plan

class PlanAdapter(val plans : List<Plan>) : Adapter<PlanPrototype>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanPrototype {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prototype_plan, parent, false)
        return PlanPrototype(view)
    }

    override fun onBindViewHolder(holder: PlanPrototype, position: Int) {
        holder.bind(plans.get(position))
    }

    override fun getItemCount(): Int {
        return plans.size
    }

}

class PlanPrototype(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //vincula los componentes con logica
    val tvPlanChoose = itemView.findViewById<TextView>(R.id.tvPlanChoose)
    val tvDescriptionPlan = itemView.findViewById<TextView>(R.id.tvDescriptionPlan)
    val tvBenefit1 = itemView.findViewById<TextView>(R.id.tvBenefit1)
    val tvBenefit2 = itemView.findViewById<TextView>(R.id.tvBenefit2)

    fun bind(plans: Plan){
        tvPlanChoose.text = plans.plan_name
        tvDescriptionPlan.text = plans.plan_description
        tvBenefit1.text = plans.plan_benefit1
        tvBenefit2.text = plans.plan_benefit2
    }
}
