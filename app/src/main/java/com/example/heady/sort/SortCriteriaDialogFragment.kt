package com.example.heady.sort

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.heady.databinding.SortBottomDialogBinding
import com.example.heady.products.ProductsActivity
import com.example.heady.utils.CLEAR_SORT_FLAGS

/**
 * Created by punitdama on 14/12/17.
 */
const val SELECTED_CRITERIA = "selected_criteria"
class SortCriteriaDialogFragment() : BottomSheetDialogFragment(),SortCriteriaClickManager{

    private lateinit var viewBinding : SortBottomDialogBinding
    private var selected_criteria : String? = null

    companion object {
        fun newInstance(selected_criteria : String?) : SortCriteriaDialogFragment{
            val args = Bundle()
            args.putString(SELECTED_CRITERIA,selected_criteria)
            val frag = SortCriteriaDialogFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selected_criteria = arguments?.getString(SELECTED_CRITERIA)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = SortBottomDialogBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewBinding.rv.recyclerViewSetUp()

        viewBinding.clearAll.setOnClickListener {
            selected_criteria?.let {
                sortBy(CLEAR_SORT_FLAGS)
                dismiss()
            }?: dismiss()
        }
    }

    private val recyclerViewSetUp : RecyclerView.() -> Unit = {
        adapter = SortCriteriaAdapter(selected_criteria,this@SortCriteriaDialogFragment)
    }

    override fun sortBy(query: String) {
        (activity as ProductsActivity).sortData(query)
        dismiss()
    }

}