package com.router.utils;

import android.os.Build;
import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/************************************************************
 * Description: ...Gson處理Json、Model互轉時的{java.util.Date}類型處理
 ***********************************************************/
public class GsonDateTypeAdapter extends TypeAdapter<Date> {

    private final String TAG = "DateAdapterTAG";

    @Override
    public Date read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        return StringFormatDate(in.nextString());
    }

    private synchronized Date StringFormatDate(String json) {
        if (!TextUtils.isEmpty(json)) {
            try {
                int index = json.indexOf("+");
                if (index != -1) {
                    json = json.substring(0, index);
                }
                String format;
                if (json.contains("T")) {
                    json = json.replace("T", " ");
                }
                format = "yyyy-MM-dd HH:mm:ss";
                int indexd = json.indexOf(".");
                if (indexd != -1) {
                    format += ".SSS";
                    // 如果低於5.0，補位
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && (indexd + 1 < json.length())) {
                        String millisecond = json.substring(indexd + 1);
                        if (millisecond.length() == 1) {
                            LogUtils.e(TAG, "補位--->" + json + "--00");
                            json += "00";
                        } else if (millisecond.length() == 2) {
                            LogUtils.e(TAG, "補位--->" + json + "--0");
                            json += "0";
                        }
                    }
                }

                json = json.replace("/", "-");
                // 如果原字符串太長，則截斷
                if (json.length() > format.length())
                    json = json.substring(0, format.length());
                Date date = null;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    // sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    date = sdf.parse(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return date;
            } catch (Exception e) {
                e.printStackTrace();
                throw new JsonSyntaxException(json, e);
            }
        }
        return null;
    }

    @Override
    public synchronized void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();//設置null值
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String dateFormatString = dateFormat.format(value);
        out.value(dateFormatString);
    }
}
