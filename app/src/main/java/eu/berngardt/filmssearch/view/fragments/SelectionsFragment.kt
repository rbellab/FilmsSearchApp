package eu.berngardt.filmssearch.view.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import eu.berngardt.filmssearch.utils.AnimationHelper
import kotlinx.android.synthetic.main.fragment_selections.*
import eu.berngardt.filmssearch.databinding.FragmentSelectionsBinding

class SelectionsFragment : Fragment() {

    private lateinit var binding: FragmentSelectionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(selections_fragment_root, requireActivity(), DECORATOR_PADDING)
    }

    companion object {
        private const val DECORATOR_PADDING = 4
    }
}
