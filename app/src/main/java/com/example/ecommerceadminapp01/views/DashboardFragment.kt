package com.example.ecommerceadminapp01.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerceadminapp01.R
import com.example.ecommerceadminapp01.adapters.DashboardItemAdapter
import com.example.ecommerceadminapp01.databinding.FragmentDashboardBinding
import com.example.ecommerceadminapp01.models.DashboardItemType
import com.example.ecommerceadminapp01.viewmodels.LoginViewModel


class DashboardFragment : Fragment() {
    private lateinit var binding:FragmentDashboardBinding
    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDashboardBinding.inflate(inflater,container,false)
        //observe by using the same live data
        //observe the livedata
        //The opposite will be checked here
        //If we notice after arriving the dashboard user didn't log in
        //so in that case we will send the user for log in
        loginViewModel.authLD.observe(viewLifecycleOwner){
            if(it == LoginViewModel.Authentication.UNAUTHENTICATED){
                findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
            }
        }
        val adapter=DashboardItemAdapter{
            navigateToDashboardItemPage(it)
        }
        val glm = GridLayoutManager(requireActivity(),2)
        binding.dashboardRecyclerView.layoutManager=glm
        binding.dashboardRecyclerView.adapter=adapter
        return binding.root
    }

    private fun navigateToDashboardItemPage(it: DashboardItemType) {
        when(it){
            DashboardItemType.ADD_PRODUCT -> findNavController().navigate(R.id.action_dashboardFragment_to_addProductFragment)
            DashboardItemType.VIEW_PRODUCT -> findNavController().navigate(R.id.action_dashboardFragment_to_viewProductFragment)
            DashboardItemType.ORDER -> findNavController().navigate(R.id.action_dashboardFragment_to_orderFragment)
            DashboardItemType.CATEGORY -> findNavController().navigate(R.id.action_dashboardFragment_to_categoryFragment)
            DashboardItemType.REPORT -> findNavController().navigate(R.id.action_dashboardFragment_to_reportFragment)
            DashboardItemType.USER -> findNavController().navigate(R.id.action_dashboardFragment_to_manageUserFragment)
            DashboardItemType.SETTINGS -> findNavController().navigate(R.id.action_dashboardFragment_to_settingsFragment)
        }
    }


}