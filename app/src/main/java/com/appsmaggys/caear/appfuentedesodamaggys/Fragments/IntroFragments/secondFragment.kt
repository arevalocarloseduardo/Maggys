package com.appsmaggys.caear.appfuentedesodamaggys.Fragments.IntroFragments
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appmaggys.caear.appfuentedesodamaggys.R


class secondFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_second, container, false)
        return v
    }
    companion object {
        fun newInstance(): secondFragment {
            val fragment= secondFragment()
            return fragment
        }
    }
}

