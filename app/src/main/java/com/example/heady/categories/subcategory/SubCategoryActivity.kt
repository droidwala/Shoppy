package com.example.heady.categories.subcategory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.heady.R
import com.example.heady.categories.BannerAdapter
import com.example.heady.categories.BannerClickManager
import com.example.heady.categories.childcategory.ChildCategoryViewModel
import com.example.heady.categories.childcategory.ChildCategoryViewState
import com.example.heady.model.Category
import com.example.heady.products.productsListIntent
import com.example.heady.utils.inject
import com.example.heady.utils.plusAssign
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_sub_category.api_error_text
import kotlinx.android.synthetic.main.activity_sub_category.loader
import kotlinx.android.synthetic.main.activity_sub_category.rv
import kotlinx.android.synthetic.main.toolbar_with_title.page_title
import kotlinx.android.synthetic.main.toolbar_with_title.toolbar
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import timber.log.Timber

/**
 * Shows SubCategories of Child Categories like Formal Shoes and Casual shoes under Foot Wear
 * Created by punitdama on 14/12/17.
 */
const val CATEGORY_ID = "id"
const val CATEGORY_NAME = "name"

// Entry point
fun Context.subCategoryIntent(category_id: Int, category_name: String): Intent {
    return Intent(this, SubCategoryActivity::class.java).apply {
        putExtra(CATEGORY_ID, category_id)
        putExtra(CATEGORY_NAME, category_name)
    }
}

class SubCategoryActivity() : DaggerAppCompatActivity(), BannerClickManager {
    /**
     * Service locator pattern:
     * Instead of the using member injection, we are trying to fetch
     * the dependencies generated in DaggerAppComponent using provision method in AppComponent interface + Extension function
     * This allows us to instantiate dependencies lazily using Ext function defined in DaggerUtil.kt
     */
    private val viewModel: ChildCategoryViewModel by inject { childCategoryViewModel() }

    private val compositeSubscription by lazy(LazyThreadSafetyMode.NONE) { CompositeSubscription() }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { BannerAdapter(this) }

    private var category_id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_categories)

        category_id = intent.getIntExtra(com.example.heady.categories.childcategory.CATEGORY_ID, 0)
        if (category_id == 0) {
            finish()
        }

        toolbarSetUp()

        rv.layoutManager = GridLayoutManager(this, 2)
        rv.adapter = adapter

        compositeSubscription += viewModel.fetchData(category_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Timber.d("Sub categories fetched") },
                        { error -> Timber.d("Error fetching sub categories" + error.localizedMessage) })

        compositeSubscription += viewModel.viewState()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::render,
                        { error -> Timber.d("Error rendering viewState " + error.localizedMessage) })
    }

    private fun render(viewState: ChildCategoryViewState) {
        when (viewState.isLoading) {
            true -> loader.visibility = View.VISIBLE
            false -> loader.visibility = View.GONE
        }

        if (viewState.error != null) {
            api_error_text.visibility = View.VISIBLE
            api_error_text.text = viewState.error
        } else {
            api_error_text.visibility = View.GONE
        }

        viewState.categories?.let {
            adapter.addSubBannerItems(it)
        }
    }

    private fun toolbarSetUp() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { finish() }
        page_title.text = intent.getStringExtra(CATEGORY_NAME)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.unsubscribe()
    }

    // Exit point
    override fun openSubCategory(category: Category) {
        startActivity(productsListIntent(category.id, category.name))
    }
}