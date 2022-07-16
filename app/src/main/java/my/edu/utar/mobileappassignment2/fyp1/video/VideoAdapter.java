package my.edu.utar.mobileappassignment2.fyp1.video;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import my.edu.utar.mobileappassignment2.fyp1.R;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    List<YouTubeVideos> youtubeVideoList;
    LinearLayout parent;
    RecyclerView content;

    public VideoAdapter() {
    }

    public VideoAdapter(List<YouTubeVideos> youtubeVideoList,LinearLayout parent,RecyclerView content) {
        this.youtubeVideoList = youtubeVideoList;
        this.parent = parent;
        this.content = content;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.video_view, parent, false);

        return new VideoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {

        holder.videoWeb.loadData( youtubeVideoList.get(position).getVideoUrl(), "text/html" , "utf-8" );

    }

    @Override
    public int getItemCount() {
        return youtubeVideoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        WebView videoWeb;

        public VideoViewHolder(View itemView) {
            super(itemView);

            videoWeb = itemView.findViewById(R.id.videoWebView);

            videoWeb.getSettings().setJavaScriptEnabled(true);
            //https://stackoverflow.com/questions/50101902/webview-and-iframe-video-full-screen-issue/64096361#64096361
            videoWeb.setWebChromeClient(new WebChromeClient() {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

                View customView;

                @Override
                public void onShowCustomView(View view, CustomViewCallback callback) {
                    super.onShowCustomView(view, callback);
                    customView = view;
                    view.setLayoutParams(layoutParams);
                    parent.addView(view);
                    content.setVisibility(View.GONE);
                }

                @Override
                public void onHideCustomView() {
                    super.onHideCustomView();

                    content.setVisibility(View.VISIBLE);
                    parent.removeView(customView);
                    customView = null;
                }

            });
        }
    }
}

