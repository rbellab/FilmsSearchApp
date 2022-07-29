package eu.berngardt.filmssearch.view.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import eu.berngardt.filmssearch.utils.AnimationHelper
import kotlinx.android.synthetic.main.fragment_watch_later.*
import eu.berngardt.filmssearch.databinding.FragmentWatchLaterBinding

class WatchLaterFragment : Fragment() {
    private lateinit var binding: FragmentWatchLaterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchLaterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(watch_later_fragment_root, requireActivity(), ANIMATION_POSITION)
    }

    companion object {
        private const val ANIMATION_POSITION = 3
    }
}
