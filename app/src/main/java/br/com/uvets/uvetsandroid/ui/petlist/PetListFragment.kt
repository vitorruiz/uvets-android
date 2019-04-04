package br.com.uvets.uvetsandroid.ui.petlist


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.ui.ContainerActivity
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_pet_list.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 *
 */
class PetListFragment : BaseFragment() {

    private val mViewModel: PetListViewModel by viewModel()
    private lateinit var mPetAdapter: PetAdapter

    companion object {
        const val REQUEST_CREATE_PET = 100
        const val REQUEST_UPDATE_PET = 200
    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_pet_list, container, false)
//    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_pet_list
    }

    override fun initComponents(rootView: View) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //mViewModel = ViewModelProviders.of(this).get(PetListViewModel::class.java)
        mViewModel.attachNavigator(this)

        setUpView()
        setUpObservers()

        mViewModel.fetchPets()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {
                REQUEST_CREATE_PET -> {
                    val pet = data?.getParcelableExtra<Pet>("pet")
                    pet?.let {
                        mPetAdapter.addIem(it)
                    }
                }
                REQUEST_UPDATE_PET -> {
                    val pet = data?.getParcelableExtra<Pet>("pet")
                    pet?.let {
                        mPetAdapter.updateItem(it)
                    }
                }
            }

        }
    }

    private fun setUpView() {
        activity!!.title = "Seus pets"

        mPetAdapter = PetAdapter(context!!, arrayListOf()) { position, pet ->
            startActivityForResult(ContainerActivity.updatePetView(context!!, pet), REQUEST_UPDATE_PET)
        }
        rvPetList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rvPetList.adapter = mPetAdapter

        swipeRefresh.setOnRefreshListener {
            mViewModel.fetchPets(true)
        }

        fbCreatePet.setOnClickListener {
            startActivityForResult(ContainerActivity.createPetView(context!!), REQUEST_CREATE_PET)
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
