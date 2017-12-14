package com.example.heady.categories.subcategory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.heady.R
import com.example.heady.categories.BannerAdapter
import com.example.heady.categories.BannerClickManager
import com.example.heady.categories.childcategory.ChildCategoryViewModel
import com.example.heady.categories.childcategory.ChildCategoryViewState
import com.example.heady.model.Category
import com.example.heady.products.productsListIntent
import com.example.heady.utils.plusAssign
import com.example.heady.utils.toast
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.toolbar_with_title.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import javax.inject.Inject

/**
 * Shows SubCategories of Child Categories like Formal Shoes and Casual shoes under Foot Wear
 * Created by punitdama on 14/12/17.
 */
const val CATEGORY_ID = "id"
const val CATEGORY_NAME = "name"

//Entry point
fun Context.subCategoryIntent(category_id : Int, category_name : String) : Intent {
    return Intent(this, SubCategoryActivity::class.java).apply {
        putExtra(CATEGORY_ID,category_id)
        putExtra(CATEGORY_NAME,category_name)
    }
}

class SubCategoryActivity() : DaggerAppCompatActivity(),BannerClickManager{
    @Inject lateinit var viewModel : ChildCategoryViewModel

    private val compositeSubscription by lazy(LazyThreadSafetyMode.NONE) { CompositeSubscription() }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { BannerAdapter(this) }

    private var category_id : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_categories)

        category_id = intent.getIntExtra(com.example.heady.categories.childcategory.CATEGORY_ID,0)
        if(category_id == 0){
            finish()
        }

        toolbarSetUp()

        rv.layoutManager = GridLayoutManager(this,2)
        rv.adapter = adapter

        compositeSubscription += viewModel.fetchData(category_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ Timber.d("Sub categories fetched")},
                        {error -> Timber.d("Error fetching sub categories" + error.localizedMessage)})

        compositeSubscription += viewModel.viewState()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::render,
                        {error -> Timber.d("Error rendering viewState " + error.localizedMessage)})
    }

    private fun render(viewState: ChildCategoryViewState){
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

        viewState.categories?.let {
            adapter.addSubBannerItems(it)
        }
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

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.unsubscribe()
    }

    //Exit point
    override fun openSubCategory(category: Category) {
        startActivity(productsListIntent(category.id,category.name))
    }
}