package br.com.uvets.uvetsandroid.ui.vetlist


import android.view.View
import androidx.lifecycle.Observer
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_vet_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class VetListFragment : BaseFragment() {

    private val mViewModel: VetListViewModel by viewModel()
    private lateinit var mVetAdapter: VetAdapter

    override fun getLayoutResource(): Int {
        return R.layout.fragment_vet_list
    }

    override fun initComponents(rootView: View) {
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
