package com.example.filereader;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TextFile implements FileProcessing {
    String name, line = "", StoryTitle = "";
    List<String> Headlines, newHeadlines;
    StringBuilder Paragraph;
    int customId = 0;

    public TextFile(String name) {
        this.name = name;
        Headlines = new ArrayList<>();
        newHeadlines = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String readFile(Context context) {
        String data = null;
        try {
            InputStream is = context.getAssets().open(name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            data = new String(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    @Override
    public String HasTitle(Context context) {
        String FoundedLine;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open(name)))) {
            boolean firstHeadingFound = false;
            FoundedLine = "";
            while ((line = br.readLine()) != null) {
                if (!firstHeadingFound) {
                    firstHeadingFound = true;
                    FoundedLine = line;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return FoundedLine;
    }

    @Override
    public String FileTilte(String line) {

        SpannableStringBuilder sb = new SpannableStringBuilder(line);
        sb.setSpan(new ForegroundColorSpan(Color.RED), 0, line.length(), 0);
        sb.setSpan(new UnderlineSpan(), 0, line.length(), 0);
        sb.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, line.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        return line;

    }

    @Override
    public SpannableStringBuilder LinkClick(String link) {

        SpannableStringBuilder sb = new SpannableStringBuilder(link);
        sb.setSpan(new UnderlineSpan(), 0, link.length(), 0);
        sb.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, link.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;

    }

    @Override
    public List<String> ReadDectionaryLine(Context context) {
        Headlines.clear();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open(name)))) {
            while ((line = br.readLine()) != null) {
                Headlines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Headlines;
    }

    public List<String> checklines(List<String> headlines) {
        Log.d("ewrwerwerwer", String.valueOf(headlines.size()));
        newHeadlines.clear();
        for (int i = 0; i < headlines.size(); i++) {
            if (startsWithNumber(headlines.get(i))) {
                StoryTitle = headlines.get(i);
                newHeadlines.add(StoryTitle);
            }
        }
        return newHeadlines;
    }

    public static boolean startsWithNumber(String str) {
        return str != null && str.length() > 0 && Character.isDigit(str.charAt(0));
    }

    @Override
    public void processHeadlines(Context context, List<String> line, String hexColor, float ratio) {
        MainActivity.hLinesCont.removeAllViews();
        MainActivity.sections.removeAllViews();
        Log.d("sdfsdfsdf", String.valueOf(line.size()));
        int color = Color.parseColor(hexColor);
        String listhtml="<ol>";
        String TitleStory="";
        for (int i = 0; i < line.size(); i++) {

            // Create a new TextView
            listhtml+="<div id=\"target-list-item\">"+"<li style=\"color: blue; text-align:right; text-decoration: underline; font-weight: bold;\">"+line.get(i).substring(3, line.get(i).length()).trim()+"</li>"+"</div>";
            SpannableStringBuilder Span_dec_title = new SpannableStringBuilder(line.get(i));
            Span_dec_title.setSpan(new ForegroundColorSpan(Color.BLUE), 0, line.get(i).length(), 0);
            Span_dec_title.setSpan(new UnderlineSpan(), 0, line.get(i).length(), 0);
            Span_dec_title.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, line.get(i).length(), SPAN_EXCLUSIVE_EXCLUSIVE);


            String StoryTitle = line.get(i).substring(3, line.get(i).length()).trim();
            SpannableStringBuilder Span_Story_title = new SpannableStringBuilder(StoryTitle);
            Span_Story_title.setSpan(new ForegroundColorSpan(Color.RED), 0, StoryTitle.length(), 0);
            Span_Story_title.setSpan(new AbsoluteSizeSpan((int) ratio), 0, StoryTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            try {
                Paragraph = new StringBuilder(getParagraphforTitle(context, StoryTitle));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            SpannableStringBuilder Span_Story_paragraph = new SpannableStringBuilder(Paragraph);
            Span_Story_paragraph.setSpan(new ForegroundColorSpan(color), 0, Paragraph.length(), 0);
            Span_Story_paragraph.setSpan(new AbsoluteSizeSpan((int) ratio), 0, Span_Story_paragraph.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            TitleStory+="<h4 style=\"color:red;  text-align:center;\">"+StoryTitle+"</h4>";
            TitleStory+="<h5 style=\"color:black;  text-align:center;\">"+Paragraph+"</h5>";
            TextView textView = new TextView(context);
            TextView subTitle = new TextView(context);
            TextView paragraph = new TextView(context);

            subTitle.setGravity(Gravity.CENTER);
            paragraph.setGravity(Gravity.CENTER);
            // Set the text for the TextView
            textView.setText(Span_dec_title);
            customId = i;
            textView.setId(customId);

            subTitle.setText(Span_Story_title);
            subTitle.setId(customId);

            paragraph.setText(Span_Story_paragraph);

            // Set layout params (optional)
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            textView.setLayoutParams(params);


            subTitle.setLayoutParams(params);
            subTitle.setTextIsSelectable(true);
            subTitle.setHighlightColor(context.getResources().getColor(R.color.yellow));


            subTitle.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    MainActivity.showPopupMenu((TextView) view);
                    return false;
                }
            });
            paragraph.setLayoutParams(params);
            paragraph.setTextIsSelectable(true);
            paragraph.setHighlightColor(context.getResources().getColor(R.color.yellow));

            paragraph.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    MainActivity.showPopupMenu((TextView) view);
                    return false;
                }
            });

            // Add the TextView to the LinearLayout
            MainActivity.hLinesCont.addView(textView);
            MainActivity.sections.addView(subTitle);
            MainActivity.sections.addView(paragraph);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("werwedsfsd",
                            String.valueOf(view.getId()));
                    // TextView targetTxv = (TextView) findViewById(subTitle.getId());
                    //scrollToTextViewPosition(targetTxv);
                }
            });

        }
        //scrl1.addView(sections);
        listhtml+="</ol>";
        MainActivity.htmlContent+=listhtml;
        MainActivity.htmlContent+=TitleStory;
    }

    public String getParagraphforTitle(Context context, String title) throws IOException {
        StringBuilder paragraph = new StringBuilder();
        boolean foundTitle = false;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(name)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the line matches the title

                if (line.trim().equalsIgnoreCase(title)) {
                    foundTitle = true;
                } else if (foundTitle) {
                    // Skip empty lines after the title
                    if (!line.isEmpty()) {
                        if(line.matches(".*\\n\\s*")){
                            foundTitle=true;
                        }else {
                            foundTitle=false;
                            paragraph.append(line).append("\n");
                        }
                         // Add line with newline
                    }
                }

            }
        }

        // Return paragraph or empty string if title not found or no paragraph after
        return paragraph.toString().trim();
    }


}
