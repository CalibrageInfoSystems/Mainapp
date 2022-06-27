package com.oilpalm3f.mainapp.cropmaintenance;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.oilpalm3f.mainapp.common.CommonUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


//Few Commonly used mehtods are written here
public class CommonUtilsNavigation {
    public static Fragment mfragment ;
    public static String ActionBarpalmDetails= "";
    public  static boolean spinnerSelect(String spinner, int position, Context context) {
        if (position == 0 || position <= 0){
            Toast.makeText(context,"Please select "+spinner, Toast.LENGTH_SHORT).show();
            return false;
        }else
            return true;
    }

    public  static boolean listEmpty(ArrayList aa,String name, Context context) {
        if (aa.size() == 0){
            Toast.makeText(context,"Please add "+name, Toast.LENGTH_SHORT).show();
            return false;
        }else
            return true;
    }

    public  static ArrayAdapter<String> adapterHasMap(Context context, String selectName, LinkedHashMap<String, ArrayList<String>> DataMap) {

        ArrayAdapter<String> adapterSet = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, CommonUtils.fMap(DataMap, selectName));
        adapterSet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapterSet;
    }



    public  static boolean edittextSelect(Context context, EditText edittext, String name) {
        if (edittext.getText().toString().equalsIgnoreCase("")){
            edittext.setError("Please enter " + name);
            return false;
        }else
            return true;
    }

    public  static ArrayAdapter<String> adapterSetFromHashmap(Context context, String selectName, LinkedHashMap<String, String> DataMap) {

        ArrayAdapter<String> adapterSet = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, CommonUtils.fromMap(DataMap, selectName));
        adapterSet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapterSet;
    }

    public static String getKey(LinkedHashMap<String, String> DataMap, String value){
        for (Map.Entry<String, String> entry : DataMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                System.out.println(entry.getKey());
                return entry.getKey();
            }
        }
        return null;
    }

    public static int getvalueFromHashMap(LinkedHashMap<String, String> DataMap, int value){
        List<String> list = new ArrayList<String>(DataMap.keySet());
        for (int i = 0; i< list.size(); i++){
            Log.e("hash","hash Val is"+list.get(i).toString());
            if (list.get(i).toString().equalsIgnoreCase(String.valueOf(value))) {
                return i+1;
            }
        }
        return 0;
    }

    public static Dialog dialogIntialize(Context mContext, int xml){
        Dialog dialog;
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(xml);
        dialog.setCanceledOnTouchOutside(false);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        Double width = metrics.widthPixels * .95;
        Double height = metrics.heightPixels * .74;
        Window win = dialog.getWindow();
        win.setLayout(width.intValue(), height.intValue());

        return dialog;
    }

    public static String checkSpinerPosition(Spinner spin){
        return 0 != spin.getSelectedItemId() ? spin.getSelectedItem().toString() : "";
    }

    public static boolean selectAnySpinner(Spinner spin){
        return 0 != spin.getSelectedItemId();
    }

    public static boolean selectAnyEdittext(EditText edittext){
        return !edittext.getText().toString().equals("");
    }

    public static void hideKeyBoard(Context ctx){

        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public  static boolean compareDoubleValues(Context mcontext, double d1, double d2) {
        // compares the two specified double values
        int retval = Double.compare(d1, d2);
        if(retval > 0) {
            System.out.println("d1 is greater than d2");
            return true;
        }
        else if(retval < 0) {
            System.out.println("d1 is less than d2");
            CommonUtils.showToast("Area allocated should not exceeded GPS area.....",mcontext);
            return false;
        }
        else {
            System.out.println("d1 is equal to d2");
            return true;
        }
    }
}
