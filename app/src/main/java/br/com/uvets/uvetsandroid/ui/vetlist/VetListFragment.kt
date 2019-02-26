package br.com.uvets.uvetsandroid.ui.vetlist


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_vet_list.*

class VetListFragment : BaseFragment() {

    private lateinit var mViewModel: VetListViewModel
    private lateinit var mVetAdapter: VetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vet_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(VetListViewModel::class.java)
        mViewModel.attachNavigator(this)

        setUpView()
        setUpObservers()

        mViewModel.fetchVets()
    }

    private fun setUpView() {
        mVetAdapter = VetAdapter(context!!, arrayListOf())
        rv_vet_list.layoutManager = LinearLayoutManager(context)
        rv_vet_list.adapter = mVetAdapter
    }

    private fun setUpObservers() {
        mViewModel.vetListLiveData.observe(this, Observer { vetList ->
            vetList?.let { mVetAdapter.refreshList(it) }
        })
    }
}
