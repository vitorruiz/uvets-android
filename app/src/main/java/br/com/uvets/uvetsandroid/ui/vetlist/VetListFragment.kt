package br.com.uvets.uvetsandroid.ui.vetlist


import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_vet_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class VetListFragment : BaseFragment() {

    private val mViewModel: VetListViewModel by viewModel()
    private lateinit var mVetAdapter: VetAdapter

    private var googleMap: GoogleMap? = null
    private lateinit var mMenuMapView: MenuItem
    private lateinit var mMenuListView: MenuItem

    override fun getLayoutResource(): Int {
        return R.layout.fragment_vet_list
    }

    override fun getTile(): String? {
        return "Veterinários"
    }

    override fun settings() {
        setHasOptionsMenu(true)
    }

    override fun initComponents(rootView: View) {
        mViewModel.attachNavigator(this)

        setUpView()
        setUpObservers()

        mViewModel.fetchVets()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_vet_list, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        mMenuListView = menu?.findItem(R.id.menu_list_view)!!
        mMenuMapView = menu.findItem(R.id.menu_map_view)!!

        mMenuListView.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_map_view -> {
                llMapLayout.visibility = View.VISIBLE
                swipeRefresh.visibility = View.GONE
                mMenuMapView.isVisible = false
                mMenuListView.isVisible = true
            }
            R.id.menu_list_view -> {
                llMapLayout.visibility = View.GONE
                swipeRefresh.visibility = View.VISIBLE
                mMenuListView.isVisible = false
                mMenuMapView.isVisible = true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpView() {
        mVetAdapter = VetAdapter(context!!, arrayListOf())
        rvVetList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rvVetList.adapter = mVetAdapter

        swipeRefresh.setOnRefreshListener {
            mViewModel.fetchVets(true)
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync {
            initMap(it)
        }
    }

    private fun initMap(map: GoogleMap?) {
        googleMap = map?.apply {
            mapType = GoogleMap.MAP_TYPE_NORMAL
        }
        mViewModel.vetListLiveData.observe(this, Observer { vetList ->
            vetList?.let {
                val markers = it.filter { it.address.lat != null && it.address.lon != null }.map { vet ->
                    MarkerOptions()
                        .title(vet.name)
                        .snippet(vet.classification)
                        .position(LatLng(vet.address.lat!!, vet.address.lon!!))
                }

                markers[0]?.let { firstMarker ->
                    googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(firstMarker.position, 15.0F))
                }

                markers.forEach { marker ->
                    googleMap?.addMarker(marker)
                }
            }
        })
    }

    private fun setUpObservers() {
        mViewModel.vetListLiveData.observe(this, Observer { vetList ->
            vetList?.let { mVetAdapter.refreshList(it) }
        })
    }

    override fun showLoader(isLoading: Boolean) {
        if (swipeRefresh != null) swipeRefresh.isRefreshing = isLoading
    }
}
