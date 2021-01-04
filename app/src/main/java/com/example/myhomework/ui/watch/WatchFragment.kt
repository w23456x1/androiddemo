package com.example.myhomework.ui.watch

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.myhomework.R
import kotlinx.android.synthetic.main.watch_fragment.*

class WatchFragment : Fragment() {

    companion object {
        var second=0
    }

    private lateinit var viewModel: WatchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.watch_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WatchViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.seconds.observe(viewLifecycleOwner, Observer {
            second=it
            val hours= second / 3600
            val minutes=(second % 3600) / 60
            val secs=second % 60
            textView_timer.text =String.format("%02d:%02d:%02d",hours,minutes,secs)
        })

        button_start.setOnClickListener {
            viewModel.start()
        }
        button_stop.setOnClickListener {
            viewModel.stop()
        }
        button_restart.setOnClickListener {
            viewModel.restart()
        }

    }
    override fun onStop() {
        super.onStop()
        viewModel.saveData()
    }

}