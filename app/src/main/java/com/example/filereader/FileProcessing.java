package com.example.filereader;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.View;

import java.util.Collection;
import java.util.List;

public interface FileProcessing {

    String readFile(Context context);
    String HasTitle(Context context);
    String FileTilte(String line);
    SpannableStringBuilder LinkClick(String line);
    List<String>ReadDectionaryLine(Context context);
    List<String>checklines(List<String> List);
    void processHeadlines(Context context,List<String> list,String color,float ratio);
}
