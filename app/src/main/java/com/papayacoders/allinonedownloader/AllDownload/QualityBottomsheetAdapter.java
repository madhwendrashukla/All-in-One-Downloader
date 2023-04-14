package com.papayacoders.allinonedownloader.AllDownload;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.papayacoders.allinonedownloader.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class QualityBottomsheetAdapter extends RecyclerView.Adapter<QualityBottomsheetAdapter.ViewHolder> {

    boolean issingle = false;
    boolean hasMultiAlbumb = false;
    private Context context;
    private List<Format> filesList;
    private List<Video> filesVideoList;
    private String vidSource;
    private String vidurl;

    public QualityBottomsheetAdapter(Context context, List<Format> filesList, String source, boolean issingle) {
        this.context = context;
        this.filesList = addtoarrayno_m3u8(filesList);
        this.issingle = issingle;
        this.vidSource = source;
    }

    public QualityBottomsheetAdapter(Context context, String source, boolean issingle, List<Video> filesList, boolean hasMultiAlbumb) {
        this.context = context;
        this.filesVideoList = filesList;
        this.issingle = issingle;
        this.hasMultiAlbumb = hasMultiAlbumb;
        this.vidSource = source;
    }

    public QualityBottomsheetAdapter(Context context, String url, String source, boolean issingle) {
        this.context = context;
        this.vidurl = url;
        this.issingle = issingle;
        this.vidSource = source;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quality_bottomfragment, null, false);
        return new ViewHolder(view);


    }


    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, final int position) {


        if (issingle) {
            System.out.println("reccccc VVKK working0000 ");

            holder.resolution.setText("HD");

            holder.fileSize.setText("undefined");
            holder.downloadbtnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DownloadFileMain.startDownloading(context, vidurl, vidSource + "_" + System.currentTimeMillis(), ".mp4");


                }
            });

        } else {

            if (hasMultiAlbumb) {

                final Video files = (Video) filesVideoList.get(position);

                System.out.println("reccccc VVKK working1111 " + files.getFormat() + files.getProtocol());

                if (!files.getURL().contains(".m3u8") && files.getProtocol().contains("http")) {
                    holder.resolution.setText(String.format("%s", files.getFormat().length() > 20 && files.getFormatID() != null ? files.getFormatID() : files.getFormat()));

                    String formatedsizew = "NaN";


                    holder.fileSize.setText(formatedsizew);

                    holder.downloadbtnd
                            .setOnClickListener(
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (files.getEXT().equals("com") || files.getEXT().equals("")) {
                                                DownloadFileMain.startDownloading(context, files.getURL(), vidSource + "_" + files.getTitle(), ".mp4");
                                            } else if (files.getEXT().equals("gif")) {
                                                DownloadFileMain.startDownloading(context, files.getURL(), vidSource + "_" + files.getTitle(), "." + files.getEXT());
                                            } else {
                                                DownloadFileMain.startDownloading(context, files.getURL(), vidSource + "_" + files.getTitle(), "." + files.getEXT());
                                            }
                                        }
                                    });
                }

            } else {
                final Format files = (Format) filesList.get(position);

//                    if (filesList.size() > 1) {
                System.out.println("reccccc VVKK working1111 06 " + files.getFormat() + files.getProtocol());


                //  if (files.getAcodec() != null && !files.getAcodec().equals("none")) {

                if (!files.getURL().contains(".m3u8") && files.getProtocol().contains("http")) {


                    holder.resolution.setText(String.format("%s", files.getFormat().length() > 20 && files.getFormatNote() != null ? files.getFormatNote() : files.getFormat()));

                    //  holder.fileSize.setText(String.format("%.2f", (files.getFilesize() * 0.000001)));
                    // String formatedsizew = formatesizefromapi(files.getFilesize());
                    String formatedsizew = iUtils.getStringSizeLengthFile(files.getFilesize());
                    formatedsizew = formatedsizew.replace(",", ".");
                    // holder.fileSize.setText(!formatedsizew.equals("") ? files.getFilesize() + "" : "undefined");
                    holder.fileSize.setText(!formatedsizew.equals("") ? formatedsizew + "" : "undefined");
                    holder.downloadbtnd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (files.getEXT().equals("com") || files.getEXT().equals("")) {
                                DownloadFileMain.startDownloading(context, files.getURL(), vidSource + "_" + System.currentTimeMillis(), ".mp4");
                            } else if (files.getEXT().equals("gif")) {
                                DownloadFileMain.startDownloading(context, files.getURL(), vidSource + "_" + System.currentTimeMillis(), "." + files.getEXT());
                            } else {
                                DownloadFileMain.startDownloading(context, files.getURL(), vidSource + "_" + System.currentTimeMillis(), "." + files.getEXT());
                            }

                        }
                    });
                }

            }

            // }
        }

    }


    String formet_size(long bytes) {

        System.out.println("fhsdjfsdjfsfsdfk addd long=" + bytes);

        try {

            if (bytes < 1024) {
                return bytes + "B";
            } else if (bytes < 1048576L) {
                return Math.round(bytes / 1024) + "KB";
            } else if (bytes < 1073741824) {
                return Math.round(bytes / 1048576) + "MB";
            } else if (bytes > 1073741824) {
                System.out.println("fhsdjfsdjfsfsdfk addd long=" + bytes);

                return Math.round(bytes / 1073741824) + "GB";

            }

        } catch (Exception e) {
            System.out.println("fhsdjfsdjfsfsdfk addd long eee=" + e.getMessage());

            return "NaN";

        }

        return "NaN";
    }


    @Override
    public int getItemCount() {
        if (hasMultiAlbumb) {

            return filesVideoList.size();
        }
        return issingle ? 1 : filesList.size();
    }

    List<Format> addtoarrayno_m3u8(List<Format> fromList) {
        if (fromList != null) {
            List<Format> toList = new ArrayList<>();
            for (int i = 0; i < fromList.size(); i++) {
                System.out.println("fhsdjfsdjfk " + fromList.get(i).getURL());

                if (!fromList.get(i).getURL().contains(".m3u8")) {
                    System.out.println("fhsdjfsdjfk addd" + fromList.get(i).getURL());

                    toList.add(fromList.get(i));
                }
            }
            return toList;
        }
        return new ArrayList<>();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView resolution;
        TextView fileSize;
        Button downloadbtnd;


        public ViewHolder(View itemView) {
            super(itemView);
            resolution = itemView.findViewById(R.id.resolution);
            fileSize = itemView.findViewById(R.id.fileSize);
            downloadbtnd = itemView.findViewById(R.id.downloadqua);
        }
    }
}
