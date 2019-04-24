package br.com.uvets.uvetsandroid.ui.petlist


import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.ui.ContainerActivity
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_pet_list.*
import org.koin.android.viewmodel.ext.android.viewModel


class PetListFragment : BaseFragment() {

    private val mViewModel: PetListViewModel by viewModel()
    private lateinit var mPetAdapter: PetAdapter

    companion object {
        const val REQUEST_CREATE_PET = 100
        const val REQUEST_UPDATE_PET = 200
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_pet_list
    }

    override fun getTile(): String? {
        return getString(R.string.title_pets)
    }

    override fun getEmptyMessage(): String? {
        return getString(R.string.pet_list_empty)
    }

    override fun getEmptyImage(): Drawable? {
        return ContextCompat.getDrawable(context!!, R.drawable.ic_pets_24dp)
    }

    override fun getRelatedEmptyView(): View? {
        return swipeRefresh
    }

    override fun initComponents(rootView: View, savedInstanceState: Bundle?) {
        mViewModel.attachNavigator(this)

        setUpView()
        setUpObservers()

        mViewModel.fetchPets()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {
                REQUEST_CREATE_PET -> {
//                    val pet = data?.getParcelableExtra<Pet>("pet")
//                    pet?.let {
//                        mPetAdapter.addIem(it)
//                    }
                    mViewModel.fetchPets()
                }
                REQUEST_UPDATE_PET -> {
//                    val pet = data?.getParcelableExtra<Pet>("pet")
//                    pet?.let {
//                        mPetAdapter.updateItem(it)
//                    }
                    mViewModel.fetchPets()
                }
            }
        }
    }

    private fun setUpView() {
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
            showEmptyView(petList.isEmpty())
        })
    }

    override fun showLoader(isLoading: Boolean) {
        if (swipeRefresh != null) swipeRefresh.isRefreshing = isLoading
    }

//    private fun setupSwipeGesture() {
//        val swipe = object : ItemTouchHelper.SimpleCallback(
//            0, // Posições permitidas para mover a view. zero = nenhuma
//            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT // Posições de swipe
//        ) {
//
//            private val icon: Drawable = ContextCompat.getDrawable(context!!, R.drawable.ic_close)!!
//            private val background: ColorDrawable = ColorDrawable(Color.RED)
//
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean = false // Não permite mover itens
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
//                MaterialDialog(context!!).show {
//                    message(text = "Deseja excluir o pet?")
//                    positiveButton(text = "Confirmar") {
//                        mViewModel.deletePet(mPetAdapter.removeItemAt(viewHolder.adapterPosition).id!!)
//                    }
//                }
//            }
//
//            override fun onChildDraw(
//                c: Canvas,
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                dX: Float,
//                dY: Float,
//                actionState: Int,
//                isCurrentlyActive: Boolean
//            ) {
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//
//                val itemView = viewHolder.itemView
//                val backgroundCornerOffset = 20 //so background is behind the rounded corners of itemView
//
//                val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
//                val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
//                val iconBottom = iconTop + icon.intrinsicHeight
//
//                if (dX > 0) { // Swiping to the right
//                    val iconLeft = itemView.left + iconMargin + icon.intrinsicWidth
//                    val iconRight = itemView.left + iconMargin
//                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
//
//                    background.setBounds(
//                        itemView.left, itemView.top,
//                        itemView.left + dX.toInt() + backgroundCornerOffset, itemView.bottom
//                    )
//                } else if (dX < 0) { // Swiping to the left
//                    val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
//                    val iconRight = itemView.right - iconMargin
//                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
//
//                    background.setBounds(
//                        itemView.right + dX.toInt() - backgroundCornerOffset,
//                        itemView.top, itemView.right, itemView.bottom
//                    )
//                } else { // view is unSwiped
//                    background.setBounds(0, 0, 0, 0)
//                }
//
//                background.draw(c)
//                icon.draw(c)
//            }
//        }
//        val itemTouchHelper = ItemTouchHelper(swipe)
//        itemTouchHelper.attachToRecyclerView(rvPetList)
//    }
}
