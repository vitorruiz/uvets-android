package br.com.uvets.uvetsandroid.ui.vetlist


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.uvets.uvetsandroid.R
import kotlinx.android.synthetic.main.fragment_vet_list.*

class VetListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vet_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val vetNames = listOf("Veterinário Legal", "Veterinário Top")

        rv_vet_list.layoutManager = LinearLayoutManager(context)
        rv_vet_list.adapter = VetAdapter(context!!, vetNames)
    }

}
