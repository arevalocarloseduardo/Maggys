package com.appsmaggys.caear.appfuentedesodamaggys.Fragments.IntroFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appmaggys.caear.appfuentedesodamaggys.R


class firstFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_first, container, false)
        return v
    }
    companion object {
        fun newInstance(): firstFragment {
            val fragment= firstFragment()
            return fragment
        }
    }
}
