package br.com.uvets.uvetsandroid.ui.vetlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.data.model.Vet
import kotlinx.android.synthetic.main.item_vet_list.view.*

class VetAdapter(val context: Context, private var vetList: List<Vet>, private val onSelected: (Vet) -> Unit) :
    RecyclerView.Adapter<VetAdapter.VetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): VetViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_vet_list, parent, false)
        return VetViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vetList.size
    }

    fun refreshList(list: List<Vet>) {
        this.vetList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: VetViewHolder, position: Int) {
        viewHolder.bindView(vetList[position])
    }


    inner class VetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(vet: Vet) = with(itemView) {
            tvVetName.text = vet.name
            tvRating.text = vet.rating.toString()
            tvClassification.text = vet.classification

            setOnClickListener {
                onSelected(vet)
            }
        }
    }
}