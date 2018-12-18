package com.naman14.timberx.ui.albums

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.naman14.timberx.MediaItemFragment

import com.naman14.timberx.R
import com.naman14.timberx.databinding.FragmentSongsBinding;
import com.naman14.timberx.ui.widgets.RecyclerItemClickListener
import com.naman14.timberx.util.*
import kotlinx.android.synthetic.main.fragment_songs.*
import com.naman14.timberx.util.SpacesItemDecoration
import com.naman14.timberx.vo.Album

class AlbumsFragment : MediaItemFragment() {

    lateinit var viewModel: AlbumsViewModel

    var binding by AutoClearedValue<FragmentSongsBinding>(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_songs, container, false)

        return  binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = AlbumAdapter()

        recyclerView.layoutManager = GridLayoutManager(activity, 3)
        recyclerView.adapter = adapter

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.album_art_spacing)
        recyclerView.addItemDecoration(SpacesItemDecoration(spacingInPixels))

        viewModel = ViewModelProviders.of(this).get(AlbumsViewModel::class.java)

        mediaItemFragmentViewModel.mediaItems.observe(this,
                Observer<List<MediaBrowserCompat.MediaItem>> { list ->
                    val isEmptyList = list?.isEmpty() ?: true
                    if (!isEmptyList) {
                        adapter.updateData(list as ArrayList<Album>)
                    }
                })

        recyclerView.addOnItemClick(object: RecyclerItemClickListener.OnClickListener {
            override fun onItemClick(position: Int, view: View) {
                mainViewModel.mediaItemClicked(adapter.albums!![position], null)
            }
        })
    }

}
