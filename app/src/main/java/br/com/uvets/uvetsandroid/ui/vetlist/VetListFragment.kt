package br.com.uvets.uvetsandroid.ui.vetlist


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.business.interfaces.FeatureFlagging
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import br.com.uvets.uvetsandroid.ui.vetdetail.VetDetailDialogFragment
import br.com.uvets.uvetsandroid.utils.ViewAnimation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_vet_list.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class VetListFragment : BaseFragment() {

    private val mFeatureFlagging: FeatureFlagging by inject()
    private val mViewModel: VetListViewModel by viewModel()
    private lateinit var mVetAdapter: VetAdapter

    private var googleMap: GoogleMap? = null
    private lateinit var mMenuMapView: MenuItem
    private lateinit var mMenuListView: MenuItem

    override fun getLayoutResource(): Int {
        return R.layout.fragment_vet_list
    }

    override fun getTile(): String? {
        return getString(R.string.title_vets)
    }

    override fun settings() {
        setHasOptionsMenu(true)
    }

    override fun initComponents(rootView: View, savedInstanceState: Bundle?) {
        mViewModel.attachNavigator(this)

        setUpView()
        setUpObservers()

        if (mFeatureFlagging.mapFeatureEnabled) {
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync {
                initMap(it)
            }
        }

        mViewModel.fetchVets()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_vet_list, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        mMenuListView = menu.findItem(R.id.menu_list_view)!!
        mMenuMapView = menu.findItem(R.id.menu_map_view)!!

        mMenuListView.isVisible = false

        if (!mFeatureFlagging.mapFeatureEnabled) {
            mMenuListView.isVisible = false
            mMenuMapView.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_map_view -> {
                ViewAnimation.fadeOut(swipeRefresh) {
                    ViewAnimation.expand(mapView)
                }
                mapView.visibility = View.VISIBLE
                swipeRefresh.visibility = View.GONE
                mMenuMapView.isVisible = false
                mMenuListView.isVisible = true
            }
            R.id.menu_list_view -> {
                mapView.visibility = View.GONE
                swipeRefresh.visibility = View.VISIBLE
                mMenuListView.isVisible = false
                mMenuMapView.isVisible = true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpView() {
        mVetAdapter = VetAdapter(context!!, arrayListOf()) {
            VetDetailDialogFragment.newInstance(it).show(activity!!.supportFragmentManager, null)
        }

        rvVetList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rvVetList.adapter = mVetAdapter

        swipeRefresh.setOnRefreshListener {
            mViewModel.fetchVets(true)
        }
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
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

                if (markers.isNotEmpty()) {
                    markers[0]?.let { firstMarker ->
                        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(firstMarker.position, 15.0F))
                    }

                    markers.forEach { marker ->
                        googleMap?.addMarker(marker)
                    }
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
