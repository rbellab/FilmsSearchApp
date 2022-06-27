package eu.berngardt.filmssearch.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import eu.berngardt.filmssearch.utils.AnimationHelper
import kotlinx.android.synthetic.main.fragment_collections.*
import eu.berngardt.filmssearch.databinding.FragmentCollectionsBinding
import eu.berngardt.filmssearch.databinding.FragmentDetailsBinding

class CollectionsFragment : Fragment() {

    private var _binding: FragmentCollectionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FrameLayout? {
        _binding = FragmentCollectionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(fragment_collections, requireActivity(), 4)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}