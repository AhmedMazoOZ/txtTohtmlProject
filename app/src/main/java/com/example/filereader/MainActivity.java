package com.example.filereader;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    TextView txt_tv, StoryTitles, txt_test,txt_link;
    ImageView zoomin, zoomout, textcolor, backgroundcolor, confirm_color;
    TextFile textFile;
    LinearLayout edit_color_lay;
    float ratio = 30f;
    boolean Tcolor = false, isFont = false;
    SeekBar progressbar_red, progressbar_green, progressbar_blue;
    int red_value, green_value, blue_value, color_value;
    RelativeLayout main;
    String hex_color,Link;
    List<String> Headlines, newHeadlines;
    ScrollView MainScroll;
    static Context context;
    public static LinearLayout hLinesCont, sections;
WebView my_web_view;
    public static String htmlContent,end_htmlContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        textFile = new TextFile("testfile.txt");
        txt_tv.setText(textFile.readFile(context));
        htmlContent="<html dir=\"rtl\" lang=\"ar\"><body>";
        end_htmlContent="</html></body>";
        my_web_view.setWebViewClient(new MyBrowser());
        my_web_view.getSettings().setLoadsImagesAutomatically(true);
        my_web_view.getSettings().setJavaScriptEnabled(true);
        my_web_view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        if (textFile.HasTitle(context) != null) {
            htmlContent+="<div id=\"target-section\">"+"<h1 style=\"color:red; text-decoration: underline; text-align:center;\">"+
                    textFile.FileTilte(textFile.HasTitle(context))+"</h1>"+"</div>";


            Log.d("rtertretert","150");

            //StoryTitles.setText(Html.fromHtml(textFile.FileTilte(textFile.HasTitle(context)), Html.FROM_HTML_MODE_COMPACT));
            //StoryTitles.setText(textFile.FileTilte(textFile.HasTitle(context)));
        }

        Log.d("sdgdfgdfgd",htmlContent);
        htmlContent+="<a href=\"https://mawdoo3.com/\" style=\"color: blue; text-align:right; font-weight: bold;\">"+Link+"</a>\n";
        //txt_link.setText(textFile.LinkClick(Link));
        Headlines = textFile.ReadDectionaryLine(context);
        if (textFile.checklines(Headlines) != null) {
            newHeadlines = textFile.checklines(Headlines);
            textFile.processHeadlines(context, newHeadlines, hex_color, ratio);
        }


        htmlContent+=end_htmlContent;
        Log.d("sdgdfgdfgd",htmlContent);
        my_web_view.loadData(htmlContent, "text/html", "UTF-8");
        my_web_view.getSettings().setSupportZoom(true);
        Onclick();
    }

    public void init() {
        context = this;
        txt_test = (TextView) findViewById(R.id.txt_test);
        StoryTitles = (TextView) findViewById(R.id.txt_header);
        txt_tv = (TextView) findViewById(R.id.txt_tv);
        zoomin = (ImageView) findViewById(R.id.zoomin);
        zoomout = (ImageView) findViewById(R.id.zoomout);
        textcolor = (ImageView) findViewById(R.id.textcolor);
        backgroundcolor = (ImageView) findViewById(R.id.backgroundcolor);
        edit_color_lay = (LinearLayout) findViewById(R.id.edit_color_lay);
        progressbar_red = (SeekBar) findViewById(R.id.progressbar_red);
        progressbar_green = (SeekBar) findViewById(R.id.progressbar_green);
        progressbar_blue = (SeekBar) findViewById(R.id.progressbar_blue);
        confirm_color = (ImageView) findViewById(R.id.confirm_color);
        main = (RelativeLayout) findViewById(R.id.main);
        MainScroll = (ScrollView) findViewById(R.id.MainScroll);
        hLinesCont = findViewById(R.id.hLinesCont);
        sections = findViewById(R.id.sections);
        txt_link= findViewById(R.id.txt_link);
        Headlines = new ArrayList<>();
        newHeadlines = new ArrayList<>();
        my_web_view= findViewById(R.id.my_web_view);
        Headlines.clear();
        newHeadlines.clear();

        red_value = progressbar_red.getProgress();
        green_value = progressbar_green.getProgress();
        blue_value = progressbar_blue.getProgress();
        hex_color = String.format("#%02X%02X%02X", red_value, green_value, blue_value);
        Link="أجمل القصص القصيرة/https://mawdoo3.com";

    }

    public void Onclick() {
        my_web_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //showPopupMenu((TextView) view);
                return false;
            }
        });
        txt_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_web_view.loadData(htmlContent, "text/html", "UTF-8");
                String url = "https://mawdoo3.com/%D8%A3%D8%AC%D9%85%D9%84_%D8%A7%D9%84%D9%82%D8%B5%D8%B5_%D8%A7%D9%84%D9%82%D8%B5%D9%8A%D8%B1%D8%A9";
//
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
                my_web_view.loadUrl(url);
            }
        });
        confirm_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_value = Color.parseColor(hex_color);
                Log.d("sdfsdfsdf", String.valueOf(color_value));
                if (isFont) {
                    textFile.processHeadlines(context, newHeadlines, hex_color, ratio);

                } else {
                    my_web_view.setBackgroundColor(color_value);
                }
            }
        });
        zoomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                textFile.processHeadlines(context, newHeadlines, hex_color, ratio);
//                //txt_tv.setTextSize(ratio);
//                ratio += 5;
                my_web_view.zoomIn();
            }
        });
        zoomout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                textFile.processHeadlines(context, newHeadlines, hex_color, ratio);
//                ratio -= 5;
                my_web_view.zoomOut();
            }
        });
        textcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFont = true;
                if (Tcolor) {
                    edit_color_lay.setVisibility(View.VISIBLE);
                    Tcolor = false;
                } else {
                    edit_color_lay.setVisibility(View.GONE);
                    Tcolor = true;
                }

            }
        });
        backgroundcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFont = false;
                if (Tcolor) {
                    edit_color_lay.setVisibility(View.VISIBLE);
                    Tcolor = false;
                } else {
                    edit_color_lay.setVisibility(View.GONE);
                    Tcolor = true;
                }
            }
        });

        progressbar_red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int seekvalue, boolean b) {
                red_value = seekvalue;
                Log.d("sdfsdfsdf", "red_" + String.valueOf(red_value));
                hex_color = String.format("#%02X%02X%02X", red_value, green_value, blue_value);
                color_value = Color.parseColor(hex_color);
                if (isFont) {
                    txt_test.setTextColor(color_value);
                } else {
                    txt_test.setBackgroundColor(color_value);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        progressbar_green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int seekvalue, boolean b) {
                green_value = seekvalue;
                hex_color = String.format("#%02X%02X%02X", red_value, green_value, blue_value);
                Log.d("sdfsdfsdf", "red_" + String.valueOf(green_value));
                color_value = Color.parseColor(hex_color);
                if (isFont) {
                    txt_test.setTextColor(color_value);
                } else {
                    txt_test.setBackgroundColor(color_value);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        progressbar_blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int seekvalue, boolean b) {
                blue_value = seekvalue;
                hex_color = String.format("#%02X%02X%02X", red_value, green_value, blue_value);
                Log.d("sdfsdfsdf", "red_" + String.valueOf(blue_value));
                color_value = Color.parseColor(hex_color);
                if (isFont) {
                    txt_test.setTextColor(color_value);
                } else {
                    txt_test.setBackgroundColor(color_value);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private static void highlightSelectedText(TextView textView, int start, int end) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(textView.getText());
        ssb.setSpan(new BackgroundColorSpan(Color.YELLOW), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // Set background color for highlighting
        textView.setText(ssb);
    }

    public static void showPopupMenu(final TextView textView) {
        PopupMenu popupMenu = new PopupMenu(context, textView);
        popupMenu.getMenuInflater().inflate(R.menu.text_selection, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                int startSelection = textView.getSelectionStart();
                int endSelection = textView.getSelectionEnd();
                if (id == R.id.action_copy) {
                    // Get the selected text
                    String selectedText = textView.getText().toString().substring(startSelection, endSelection);

                    // Copy to clipboard
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                    clipboard.setText(selectedText);
                    Toast.makeText(context, "Text copied!", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.action_highlight) {
                    highlightSelectedText(textView, startSelection, endSelection);
                } else if (id == R.id.action_share) {
                    String selectedText = textView.getText().toString().substring(textView.getSelectionStart(), textView.getSelectionEnd());

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, selectedText);
                    context.startActivity(Intent.createChooser(shareIntent, "Share to:"));
                } else if (id == R.id.action_selectall) {
                    highlightSelectedText(textView, 0, textView.getText().length());
                } else if (id == R.id.action_bookmark) {

                }
                return false;
            }
        });

        popupMenu.show();
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // Inject JavaScript after the page has loaded
            injectJavaScript(view);
        }

        private void injectJavaScript(WebView view) {
            String javascript = "javascript:(function() {" +
                    "window.scrollToTargetString = scrollToTargetString;" + // Make function accessible
                    "})();";
            Log.d("sdfsdfsdfsdf","10");
            view.loadUrl(javascript);
        }
    }


}