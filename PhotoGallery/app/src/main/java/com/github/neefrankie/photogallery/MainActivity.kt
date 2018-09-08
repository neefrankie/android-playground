package com.github.neefrankie.photogallery

import android.support.v4.app.Fragment

class MainActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return PhotoGalleryFragment.newInstance()
    }

}
