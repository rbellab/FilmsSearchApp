package eu.berngardt.filmssearch.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import eu.berngardt.filmssearch.databinding.FragmentWatchLaterBinding
import eu.berngardt.filmssearch.utils.AnimationHelper
import kotlinx.android.synthetic.main.fragment_watch_later.*

class WatchLaterFragment : Fragment() {

    private var _binding: FragmentWatchLaterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FrameLayout {
        _binding = FragmentWatchLaterBinding.inflate(layoutInflater)
        return binding.root
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