package com.example.heady.categories.parentcategory

import android.os.Bundle
import android.view.View
import com.example.heady.R
import com.example.heady.categories.BannerAdapter
import com.example.heady.categories.BannerClickManager
import com.example.heady.categories.childcategory.childCategoryIntent
import com.example.heady.model.Category
import com.example.heady.utils.plusAssign
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_with_title.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import javax.inject.Inject

/**
 * Shows Parent Categories like
 * Mens Wear and Electronics
 * Created by punitdama on 12/12/17.
 */
class MainActivity : DaggerAppCompatActivity(), BannerClickManager {
    private val compositeSubscription by lazy(LazyThreadSafetyMode.NONE){ CompositeSubscription() }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { BannerAdapter(this) }
    @Inject lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        page_title.text = getString(R.string.app_name)
        rv.adapter = adapter

        compositeSubscription += viewModel.viewState()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::render,
                        {error -> Timber.d("Error rendering view state" + error.localizedMessage)})

        compositeSubscription += viewModel.fetchData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Timber.d("Response received")},
                        {_ -> Timber.d("Error receiving response")})

    }

    private fun render(viewState : MainViewState){
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

        viewState.response?.categories?.let {
            adapter.addItems(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.unsubscribe()
    }

    //Exit point
    override fun openSubCategory(category: Category) {
        startActivity(childCategoryIntent(category.id,category.name))
    }
}