package com.example.heady.products

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.heady.R
import com.example.heady.sort.SortCriteriaDialogFragment
import com.example.heady.utils.CLEAR_SORT_FLAGS
import com.example.heady.utils.addClickListener
import com.example.heady.utils.plusAssign
import com.example.heady.viewutils.GridDividerDecoration
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.android.synthetic.main.sort_products_lyt.*
import kotlinx.android.synthetic.main.toolbar_with_title.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import javax.inject.Inject

/**
 * Shows List of Products under particular categories like
 *  Casual Shoes
 * |-> Sneakers,Loafers,etc.
 * Created by punitdama on 14/12/17.
 */
const val CATEGORY_ID = "id"
const val CATEGORY_NAME = "name"

//Entry point
//Extension function written to open screen
fun Context.productsListIntent(category_id : Int, category_name : String) : Intent {
    return Intent(this, ProductsActivity::class.java).apply {
        putExtra(CATEGORY_ID,category_id)
        putExtra(CATEGORY_NAME,category_name)
    }
}

class ProductsActivity() : DaggerAppCompatActivity(){

    @Inject lateinit var viewModel : ProductsViewModel
    private val compositeSubscription by lazy(LazyThreadSafetyMode.NONE) { CompositeSubscription() }

    //Using 2 adapters so that we can easily swap between two adapters when changing layoutManagers
    //from GridLayoutManager <-> LinearLayoutManager
    private val gridAdapter by lazy(LazyThreadSafetyMode.NONE) { ProductsAdapter(true) }
    private val listAdapter by lazy(LazyThreadSafetyMode.NONE) { ProductsAdapter(false) }

    private var category_id : Int = 0
    private lateinit var sortCriteriaDialogFragment : SortCriteriaDialogFragment
    private var selected_sort_criteria: String? = null //Defines current selected sort_criteria

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        category_id = intent.getIntExtra(CATEGORY_ID,0)
        if(category_id == 0){
            finish()
        }


        toolbarSetUp()
        rv.recyclerViewSetup()
        addClickListeners()

        compositeSubscription += viewModel.fetchData(category_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({Timber.d("Products fetched ")},
                        {error -> Timber.d("Error fetching products " + error.localizedMessage)})

        compositeSubscription += viewModel.viewState()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::render,
                        {error -> Timber.d("Error rendering viewState" + error.localizedMessage)})

    }

    //Triggered when user selects particular Sort criteria
    fun sortData(query : String){
        selected_sort_criteria = query
        compositeSubscription += viewModel.sortData(category_id,query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({Timber.d("Sorted by " + query)},
                        {error -> Timber.d("Error sorting by " + query + " " + error.localizedMessage)})
        if(selected_sort_criteria == CLEAR_SORT_FLAGS){
            selected_sort_criteria = null
        }

    }

    // Single Render method through which all UI changes are done
    // No multiple entry points where UI is changed causing state issues
    // Could have better managed using sealed classes
    private fun render(viewState: ProductsViewState){
        when(viewState.isLoading){
            true -> loader.visibility = View.VISIBLE
            false -> loader.visibility = View.GONE
        }

        if(viewState.error !=null){
            api_error_text.visibility = View.VISIBLE
            api_error_text.text = viewState.error
        }
        else{
            api_error_text.visibility = View.GONE
        }

        viewState.products?.let {
            gridAdapter.addProducts(it)
            listAdapter.addProducts(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.clear()
    }


    private fun toolbarSetUp(){
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener{finish()}
        page_title.text = intent.getStringExtra(CATEGORY_NAME)
    }


    private val recyclerViewSetup : RecyclerView.() -> Unit = {
        layoutManager = GridLayoutManager(this@ProductsActivity,2)
        addItemDecoration(GridDividerDecoration(this@ProductsActivity))
        adapter = gridAdapter
    }

    private fun addClickListeners(){
        layout_manager_selector_icon.setOnClickListener {
            when(rv.layoutManager){
                is GridLayoutManager -> {
                    setLayoutManager(LinearLayoutManager(this@ProductsActivity),listAdapter)
                    layout_manager_selector_icon.setImageResource(R.drawable.ic_grid_on_black_24dp)
                }
                is LinearLayoutManager ->{
                    setLayoutManager(GridLayoutManager(this@ProductsActivity,2),gridAdapter)
                    layout_manager_selector_icon.setImageResource(R.drawable.ic_list_black_24dp)
                }

            }
        }

        sort_container.addClickListener(View.OnClickListener {
            sortCriteriaDialogFragment = SortCriteriaDialogFragment.newInstance(selected_sort_criteria)
            sortCriteriaDialogFragment.show(supportFragmentManager,"Sort")
        })

    }

    //Changing LayoutManager and swapping Adapters for different item layouts in Grid/Linear layouts
    private fun setLayoutManager(layoutManager: RecyclerView.LayoutManager, adapter: ProductsAdapter){
        rv.layoutManager = layoutManager
        rv.swapAdapter(adapter,false)
    }

}
