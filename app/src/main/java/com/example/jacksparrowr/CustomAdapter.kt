package com.example.jacksparrowr

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.view.animation.AnimationUtils


class CustomAdapter(private val context: Context, private val dataList: List<Any>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        if (item is Anime) {
            holder.bindAnime(item)
        } else if (item is Movie) {
            holder.bindMovie(item)
        } else if (item is TvShow) {
            holder.bindTvShow(item)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.text_view)
        private val imageView: ImageView = itemView.findViewById(R.id.image_view)

        fun bindAnime(anime: Anime) {
            titleTextView.text = anime.title
            Glide.with(context)
                .load(anime.thumbnail)
                .into(imageView)
            setClickListener(anime)
        }

        fun bindMovie(movie: Movie) {
            titleTextView.text = movie.title
            Glide.with(context)
                .load(movie.thumbnail)
                .into(imageView)
            setClickListener(movie)
        }

        fun bindTvShow(tvShow: TvShow) {
            titleTextView.text = tvShow.title
            Glide.with(context)
                .load(tvShow.thumbnail)
                .into(imageView)
            setClickListener(tvShow)
        }

        private fun setClickListener(item: Any) {
            itemView.setOnClickListener {
                val popupView = LayoutInflater.from(itemView.context).inflate(R.layout.popup_layout, null)
                val popupWindow = PopupWindow(
                    popupView,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    true
                )

                val slideUpAnimation = AnimationUtils.loadAnimation(itemView.context, R.anim.slide_up)
                popupView.startAnimation(slideUpAnimation)

                val titleTextView: TextView = popupView.findViewById(R.id.popup_title_textview)
                val imageView: ImageView = popupView.findViewById(R.id.popup_image_view)
                val detailsTextView: TextView = popupView.findViewById(R.id.popup_details_textview)
                val copyButton: Button = popupView.findViewById(R.id.copy_button)

                when (item) {
                    is Anime -> {
                        titleTextView.text = item.title
                        detailsTextView.text = item.details
                        Glide.with(itemView.context)
                            .load(item.thumbnail)
                            .into(imageView)
                        copyButton.setOnClickListener {
                            val magnetLink = item.magnet
                            val clipboardManager = itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clipData = ClipData.newPlainText("Magnet Link", magnetLink)
                            clipboardManager.setPrimaryClip(clipData)
                            Toast.makeText(itemView.context, "Magnet link copied. Paste it into a Torrent Client.", Toast.LENGTH_SHORT).show()
                            val slideDownAnimation = AnimationUtils.loadAnimation(itemView.context, R.anim.slide_down)
                            slideDownAnimation.setAnimationListener(object : Animation.AnimationListener {
                                override fun onAnimationStart(animation: Animation?) {}
                                override fun onAnimationEnd(animation: Animation?) {
                                    popupWindow.dismiss()
                                }
                                override fun onAnimationRepeat(animation: Animation?) {}
                            })
                            popupView.startAnimation(slideDownAnimation)
                        }
                    }
                    is Movie -> {
                        titleTextView.text = item.title
                        detailsTextView.text = item.details
                        Glide.with(itemView.context)
                            .load(item.thumbnail)
                            .into(imageView)
                        copyButton.setOnClickListener {
                            val magnetLink = item.magnet
                            val clipboardManager = itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clipData = ClipData.newPlainText("Magnet Link", magnetLink)
                            clipboardManager.setPrimaryClip(clipData)
                            Toast.makeText(itemView.context, "Magnet link copied. Paste it into a Torrent Client.", Toast.LENGTH_SHORT).show()
                            val slideDownAnimation = AnimationUtils.loadAnimation(itemView.context, R.anim.slide_down)
                            slideDownAnimation.setAnimationListener(object : Animation.AnimationListener {
                                override fun onAnimationStart(animation: Animation?) {}
                                override fun onAnimationEnd(animation: Animation?) {
                                    popupWindow.dismiss()
                                }
                                override fun onAnimationRepeat(animation: Animation?) {}
                            })
                            popupView.startAnimation(slideDownAnimation)
                        }
                    }
                    is TvShow -> {
                        titleTextView.text = item.title
                        detailsTextView.text = item.details
                        Glide.with(itemView.context)
                            .load(item.thumbnail)
                            .into(imageView)
                        copyButton.setOnClickListener {
                            val magnetLink = item.magnet
                            val clipboardManager = itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clipData = ClipData.newPlainText("Magnet Link", magnetLink)
                            clipboardManager.setPrimaryClip(clipData)
                            Toast.makeText(itemView.context, "Magnet link copied. Paste it into a Torrent Client.", Toast.LENGTH_SHORT).show()
                            val slideDownAnimation = AnimationUtils.loadAnimation(itemView.context, R.anim.slide_down)
                            slideDownAnimation.setAnimationListener(object : Animation.AnimationListener {
                                override fun onAnimationStart(animation: Animation?) {}
                                override fun onAnimationEnd(animation: Animation?) {
                                    popupWindow.dismiss()
                                }
                                override fun onAnimationRepeat(animation: Animation?) {}
                            })
                            popupView.startAnimation(slideDownAnimation)
                        }
                    }
                }
                popupWindow.showAtLocation(itemView, Gravity.BOTTOM, 0, 0)
            }
        }
    }
}
