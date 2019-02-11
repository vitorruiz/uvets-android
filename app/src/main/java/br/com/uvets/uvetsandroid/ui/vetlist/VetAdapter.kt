package br.com.uvets.uvetsandroid.ui.vetlist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.uvets.uvetsandroid.R
import kotlinx.android.synthetic.main.item_vet_list.view.*

class VetAdapter(val context: Context, val vetList: List<String>) : RecyclerView.Adapter<VetAdapter.VetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): VetAdapter.VetViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_vet_list, parent, false)
        return VetAdapter.VetViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vetList.size
    }

    override fun onBindViewHolder(viewHolder: VetAdapter.VetViewHolder, position: Int) {
        viewHolder.bindView(vetList[position])
    }


    class VetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(vetName: String) = with(itemView) {
            vet_name.text = vetName
        }
    }
}