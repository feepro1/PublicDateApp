package com.main.chats.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.main.chats.R
import com.main.chats.databinding.FragmentChatsBinding
import com.main.chats.di.provider.ProvideChatsComponent
import com.main.chats.presentation.adapter.FragmentAdapter
import com.main.chats.presentation.viewmodel.ChatsViewModel
import com.main.chats.presentation.viewmodel.ChatsViewModelFactory
import com.main.core.viewmodel.CoreViewModel
import com.main.core.viewmodel.CoreViewModelFactory
import javax.inject.Inject
import kotlin.properties.Delegates.notNull

class ChatsFragment : Fragment() {
    private val binding by lazy { FragmentChatsBinding.inflate(layoutInflater) }
    @Inject
    lateinit var chatsViewModelFactory: ChatsViewModelFactory
    private val chatsViewModel: ChatsViewModel by activityViewModels { chatsViewModelFactory }
    @Inject
    lateinit var coreViewModelFactory: CoreViewModelFactory
    private val coreViewModel: CoreViewModel by activityViewModels { coreViewModelFactory }
    private var fragmentAdapter by notNull<FragmentAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as ProvideChatsComponent).provideChatsComponent().inject(this)

        val tabLayoutNames = resources.getStringArray(R.array.tabLayoutNames).toList()
        fragmentAdapter = FragmentAdapter(
            requireActivity(), tabLayoutNames, chatsViewModel, findNavController(), coreViewModel
        )

        chatsViewModel.observeChats(this) { chats ->
            fragmentAdapter.chatsAdapter.mapAll(chats)
        }

        binding.mainBottomNavigationView.menu.getItem(0).isChecked = true
        binding.viewpager.adapter = fragmentAdapter
        binding.mainBottomNavigationView.setOnItemSelectedListener { menuItem ->
            chatsViewModel.manageMenuItem(menuItem, findNavController())
        }
        binding.viewpager.isSaveEnabled = false

        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = tabLayoutNames[position]
        }.attach()
    }
}