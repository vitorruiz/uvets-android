package br.com.uvets.uvetsandroid.ui.petlist


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.data.PetRepository
import kotlinx.android.synthetic.main.fragment_pet_list.*

/**
 * A simple [Fragment] subclass.
 *
 */
class PetListFragment : Fragment() {

    val petRepository = PetRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pet_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val petNames = listOf("Rufus", "Sofia", "Rex")

        rvPetList.layoutManager = LinearLayoutManager(context)
        rvPetList.adapter = PetAdapter(context!!, petNames)

        petRepository.getPets()
    }
}
