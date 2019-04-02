package br.com.uvets.uvetsandroid.ui.vetlist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_vet_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class VetListFragment : BaseFragment() {

    private val mViewModel: VetListViewModel by viewModel()
    private lateinit var mVetAdapter: VetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vet_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //mViewModel = ViewModelProviders.of(this).get(VetListViewModel::class.java)
        mViewModel.attachNavigator(this)

        setUpView()
        setUpObservers()

        mViewModel.fetchVets()
    }

    private fun setUpView() {
        activity!!.title = "Veterinários"

        mVetAdapter = VetAdapter(context!!, arrayListOf())
        rvVetList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rvVetList.adapter = mVetAdapter

        swipeRefresh.setOnRefreshListener {
            mViewModel.fetchVets(true)
        }
    }

    private fun setUpObservers() {
        mViewModel.vetListLiveData.observe(this, Observer { vetList ->
            vetList?.let { mVetAdapter.refreshList(it) }
        })
    }

    override fun showLoader(isLoading: Boolean) {
        super.showLoader(isLoading)
        if (!isLoading) swipeRefresh.isRefreshing = false
    }
}
