package br.com.uvets.uvetsandroid.ui.vetdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.data.model.VetService
import kotlinx.android.synthetic.main.item_vet_service.view.*

class VetServiceAdapter(private var list: MutableList<VetService>) :
    RecyclerView.Adapter<VetServiceAdapter.VetServiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VetServiceViewHolder {
        return VetServiceViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_vet_service,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VetServiceViewHolder, position: Int) {
        holder.bindView(list[position])
    }

    inner class VetServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(service: VetService) = with(itemView) {
            tvServiceName.text = service.name
            tvServicePrice.text = service.price.toString()
        }
    }
}