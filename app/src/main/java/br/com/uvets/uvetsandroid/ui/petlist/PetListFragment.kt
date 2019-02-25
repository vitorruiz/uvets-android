package br.com.uvets.uvetsandroid.ui.petlist


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.loading
import br.com.uvets.uvetsandroid.showError
import kotlinx.android.synthetic.main.fragment_pet_list.*

/**
 * A simple [Fragment] subclass.
 *
 */
class PetListFragment : Fragment() {

    private lateinit var mViewModel: PetListViewModel
    private lateinit var mPetAdapter: PetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pet_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(PetListViewModel::class.java)

        setUpView()
        setUpObservers()

        mViewModel.fetchPets()
    }

    private fun setUpView() {
        mPetAdapter = PetAdapter(context!!, arrayListOf())
        rvPetList.layoutManager = LinearLayoutManager(context)
        rvPetList.adapter = mPetAdapter
    }

    private fun setUpObservers() {
        mViewModel.mPetListLiveData.observe(this, Observer { petList ->
            petList?.let { mPetAdapter.refreshList(it) }
        })

        mViewModel.isLoadingLiveData.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading(it)
            }
        })

        mViewModel.errorMessageLiveData.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                showError(it)
            }
        })
    }
}
