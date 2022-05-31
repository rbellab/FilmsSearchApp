package eu.berngardt.filmssearch.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.berngardt.filmssearch.databinding.FragmentWatchLaterBinding
import eu.berngardt.filmssearch.ui.AnimationHelper
import kotlinx.android.synthetic.main.fragment_watch_later.*

class WatchLaterFragment : Fragment() {

    private var _binding: FragmentWatchLaterBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWatchLaterBinding.inflate(layoutInflater)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(fragment_watch_later, requireActivity(), 3)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}