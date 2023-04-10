package com.bayu07750.mademovie.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.bayu07750.mademovie.presentation.util.extension.updatePaddingWithInsets

open class BaseFragment <VB: ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updatePaddingWithInsets()
    }

    open fun updatePaddingWithInsets() {
        binding.root.updatePaddingWithInsets(applyToBottom = false)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    open fun initView() {}

    open fun initData() {}

    open fun actions() {}

    open fun observe() {}
}