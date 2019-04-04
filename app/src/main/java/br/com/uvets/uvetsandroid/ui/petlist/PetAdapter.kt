package br.com.uvets.uvetsandroid.ui.petlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.data.model.Pet
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_pet_list.view.*

class PetAdapter(
    val context: Context,
    private var petList: MutableList<Pet>,
    private val onPetClicked: (Int, Pet) -> Unit
) :
    RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): PetViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pet_list, parent, false)
        return PetViewHolder(view)
    }

    override fun getItemCount(): Int {
        return petList.size
    }

    override fun onBindViewHolder(viewHolder: PetViewHolder, position: Int) {
        viewHolder.bindView(petList[position], onPetClicked)
    }

    fun refreshList(petList: List<Pet>) {
        this.petList = petList.toMutableList()
        notifyDataSetChanged()
    }

    fun addIem(pet: Pet) {
        petList.add(pet)
        notifyItemInserted(petList.size - 1)
    }

    fun updateItem(pet: Pet) {
        val index = petList.indexOfFirst { it.id == pet.id }
        petList[index] = pet
        notifyItemChanged(index)
    }

    class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(pet: Pet, onPetClicked: (Int, Pet) -> Unit) = with(itemView) {
            tvPetName.text = pet.name
            pet.photoUrl?.let {
                Picasso.get()
                    .load(it)
                    .into(ivPetPhoto)
            }

            setOnClickListener {
                onPetClicked(adapterPosition, pet)
            }
        }
    }
}