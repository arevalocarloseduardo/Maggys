package com.appsmaggys.caear.appfuentedesodamaggys


import android.os.Bundle
import android.support.v4.app.Fragment

import com.appsmaggys.caear.appfuentedesodamaggys.Fragments.IntroFragments.firstFragment
import com.appsmaggys.caear.appfuentedesodamaggys.Fragments.IntroFragments.secondFragment
import com.appsmaggys.caear.appfuentedesodamaggys.Fragments.IntroFragments.thirdFragment
import com.github.paolorotolo.appintro.AppIntro




class IntroActivity : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(firstFragment())
        addSlide(secondFragment())
        addSlide(thirdFragment())

        showSkipButton(false)
       // isProgressButtonEnabled = false
        setFadeAnimation()
       //setVibrate(true)
       //setVibrateIntensity(30)
        setDoneText("Listo!")
    }

   override fun onDonePressed(currentFragment: Fragment) {
        super.onDonePressed(currentFragment)
            finish()
    }
}
