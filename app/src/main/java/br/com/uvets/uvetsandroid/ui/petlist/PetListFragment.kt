package br.com.uvets.uvetsandroid.ui.petlist


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_pet_list.*

/**
 * A simple [Fragment] subclass.
 *
 */
class PetListFragment : BaseFragment() {

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
        mViewModel.attachNavigator(this)

        setUpView()
        setUpObservers()

        mViewModel.fetchPets()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            val pet = data?.getParcelableExtra<Pet>("pet")
            pet?.let {
                mPetAdapter.addIem(it)
            }
        }
    }

    private fun setUpView() {
        activity!!.title = "Seus pets"

        mPetAdapter = PetAdapter(context!!, arrayListOf())
        rvPetList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rvPetList.adapter = mPetAdapter

        swipeRefresh.setOnRefreshListener {
            mViewModel.fetchPets(true)
        }
    }

    private fun setUpObservers() {
        mViewModel.mPetListLiveData.observe(this, Observer { petList ->
            petList?.let { mPetAdapter.refreshList(it) }
        })
    }

    override fun showLoader(isLoading: Boolean) {
        super.showLoader(isLoading)
        if (swipeRefresh != null && !isLoading) swipeRefresh.isRefreshing = false
    }
}
