package edu.oregonstate.cs492.finalproject.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import edu.oregonstate.cs492.finalproject.R

class PopUpFragment : DialogFragment(R.layout.popup_overview) {
    private val tag = "Pop Up"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(tag, "Entered $tag")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.AppDialogTheme)
    }
}