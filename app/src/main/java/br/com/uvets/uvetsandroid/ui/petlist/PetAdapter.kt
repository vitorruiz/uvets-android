package br.com.uvets.uvetsandroid.ui.petlist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.data.model.Pet
import kotlinx.android.synthetic.main.item_pet_list.view.*

class PetAdapter(val context: Context, private var petList: List<Pet>) : RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): PetViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pet_list, parent, false)
        return PetViewHolder(view)
    }

    override fun getItemCount(): Int {
        return petList.size
    }

    override fun onBindViewHolder(viewHolder: PetViewHolder, position: Int) {
        viewHolder.bindView(petList[position])
    }

    fun refreshList(petList: List<Pet>) {
        this.petList = petList
        notifyDataSetChanged()
    }

    class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(pet: Pet) = with(itemView) {
            pet_name.text = pet.name
        }
    }
}