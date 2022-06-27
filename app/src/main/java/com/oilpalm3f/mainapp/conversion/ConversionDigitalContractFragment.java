package com.oilpalm3f.mainapp.conversion;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.areaextension.UpdateUiListener;
import com.oilpalm3f.mainapp.cloudhelper.Config;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.common.SignatureView;
import com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.DigitalContract;
import com.oilpalm3f.mainapp.dbmodels.Farmer;
import com.oilpalm3f.mainapp.ui.BaseFragment;
import com.oilpalm3f.mainapp.utils.ImageUtility;
import com.oilpalm3f.mainapp.utils.UiUtils;
import com.tom_roush.pdfbox.cos.COSDocument;
import com.tom_roush.pdfbox.pdfparser.PDFParser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.Base64;
import java.util.function.LongFunction;

/**
 * Created by skasam on 1/9/2017.
 */

//Digital Contract Screen
public class ConversionDigitalContractFragment extends BaseFragment implements OnPageChangeListener, OnLoadCompleteListener {

    private static final String LOG_TAG = ConversionDigitalContractFragment.class.getName();
    public static boolean isContractAgreed = false;
    private LinearLayout parentLayout;
    private PDFView dataView;
    private Button saveBtn;
    private CheckBox agreeChbk;
    private DataAccessHandler dataAccessHandler;
    private UpdateUiListener updateUiListener;
    private DigitalContract digitalContract;
    private File fileToDownLoad = null;
    private File rootDirectory;
    String plotcode;
    Button dialogButton;
    SignatureView signatureView;
    String parsedText="";
    private Farmer savedFarmerData = null;

    private File newrootDirectory;
    private File newfileToLoadd = null;
    private PDFView newpdfview;
    String base64String = "";
    public static final int REQUEST_UPDATE_PERSONAL_DETAILS = 0;
    private boolean isUpdateData = false;

    public ConversionDigitalContractFragment() {

    }

    //Initializing the Class and assigning digital contract based on state code
    @Override
    public void Initialize() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View parentView = inflater.inflate(R.layout.frag_conversion_digitalcontract, null);
        baseLayout.addView(parentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setTile(getActivity().getResources().getString(R.string.digitalcontracttitle));
        dataAccessHandler = new DataAccessHandler(getActivity());
        CommonConstants.fromDigitalContract = true;

        savedFarmerData = (Farmer) DataManager.getInstance().getDataFromManager(DataManager.FARMER_PERSONAL_DETAILS);
        Log.d("savedFarmerData", savedFarmerData.getCode());

        if (savedFarmerData != null) {
            isUpdateData = true;
            Log.d("savedFarmerData1", savedFarmerData.getCode());

        }

        plotcode = CommonConstants.PLOT_CODE;
        Log.d("Plotcode is",plotcode + "");

        if (plotcode.startsWith("AP")){

            digitalContract = (DigitalContract) dataAccessHandler.getDigitalContractData(Queries.getInstance().getAPDigitalContract(), 0);
        }else if(plotcode.startsWith("TE")){

            digitalContract = (DigitalContract) dataAccessHandler.getDigitalContractData(Queries.getInstance().getTEDigitalContract(), 0);
        }
        else if(plotcode.startsWith("CK")){

            digitalContract = (DigitalContract) dataAccessHandler.getDigitalContractData(Queries.getInstance().getCKDigitalContract(), 0);
        }
        else if(plotcode.startsWith("AR")){

            digitalContract = (DigitalContract) dataAccessHandler.getDigitalContractData(Queries.getInstance().getARDigitalContract(), 0);
        }
        else if(plotcode.startsWith("CG")){

            digitalContract = (DigitalContract) dataAccessHandler.getDigitalContractData(Queries.getInstance().getCGDigitalContract(), 0);
        }
        else if(plotcode.startsWith("NK")){

            digitalContract = (DigitalContract) dataAccessHandler.getDigitalContractData(Queries.getInstance().getNKDigitalContract(), 0);
        }
        else if(plotcode.startsWith("SK")){

            digitalContract = (DigitalContract) dataAccessHandler.getDigitalContractData(Queries.getInstance().getSKDigitalContract(), 0);
        }
        else{
            digitalContract = (DigitalContract) dataAccessHandler.getDigitalContractData(Queries.getInstance().getAPDigitalContract(), 0);
        }



        saveBtn = (Button) parentView.findViewById(R.id.digitalSaveBtn);
        agreeChbk = (CheckBox) parentView.findViewById(R.id.agreedView);

        parentLayout = (LinearLayout) parentView.findViewById(R.id.parent_layout);
        parentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                CommonUtilsNavigation.hideKeyBoard(getActivity());
                return false;
            }
        });

        dataView = (PDFView) parentView.findViewById(R.id.agreementView);

        agreeChbk.setChecked(isContractAgreed);

        if (!isContractAgreed) {
            saveBtn.setEnabled(false);
            saveBtn.setFocusable(false);
            saveBtn.setClickable(false);
            saveBtn.setAlpha(0.5f);
        } else {
            saveBtn.setEnabled(true);
            saveBtn.setFocusable(true);
            saveBtn.setClickable(true);
            saveBtn.setAlpha(1.0f);
        }


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //signature_popup();
                if (null != digitalContract) {
                    signature_popup();
                    Log.d("digitalContracttt", "isnotnull");
                }else{
                    isContractAgreed = true;
                    updateUiListener.updateUserInterface(0);
                    getFragmentManager().popBackStack();
                }

            }
        });

        agreeChbk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveBtn.setAlpha(1.0f);
                } else {
                    saveBtn.setAlpha(0.5f);
                }

                saveBtn.setEnabled(isChecked);
                saveBtn.setFocusable(isChecked);
                saveBtn.setClickable(isChecked);
            }
        });


        if (null != digitalContract) {
            Log.d("digitalContracttt", "isnotnull");
        }else{

            Log.d("digitalContracttt", "isnull");
        }


        rootDirectory = new File(CommonUtils.get3FFileRootPath() + "3F_DigitalContract/");
        if (null != digitalContract) {
            fileToDownLoad = new File(rootDirectory + digitalContract.getFILENAME() + digitalContract.getFileExtension());
            if (null != fileToDownLoad && fileToDownLoad.exists()) {
                dataView.fromFile(fileToDownLoad)
                        .defaultPage(0)
                        .enableAnnotationRendering(true)
                        .onPageChange(this)
                        .onLoad(this)
                        .scrollHandle(new DefaultScrollHandle(getActivity()))
                        .load();

//                try {
//                    PdfReader reader = new PdfReader(String.valueOf(fileToDownLoad));
//                    int n = reader.getNumberOfPages();
//                    for (int i = 0; i <n ; i++) {
//                        parsedText   = parsedText+ PdfTextExtractor.getTextFromPage(reader, i+1).trim()+"\n"; //Extracting the content from the different pages
//                        Log.d("parsedText", parsedText + "");
//                    }
//                    System.out.println(parsedText);
//                    reader.close();
//                } catch (Exception e) {
//                    System.out.println(e);
//                }
            } else {
                String url = Config.image_url + "/" + digitalContract.getFileLocation() + "/" + digitalContract.getFILENAME() + digitalContract.getFileExtension();
                new DownloadFileFromURL().execute(url);
            }
        }

    }

    private void signature_popup() {
        final Dialog dialog = new Dialog(getContext());

        dialog.setContentView(R.layout.signature_view);
        dialog.setCancelable(false);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        TextView clear = (TextView) dialog.findViewById(R.id.clear);
        signatureView = (SignatureView) dialog.findViewById(R.id.signature_view);
        //  text.setText("Android custom dialog example!");
        //  ImageView image = (ImageView) dialog.findViewById(R.id.image);
        // image.setImageResource(R.drawable.ic_launcher);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signatureView.clearCanvas();

            }
        });
        dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (signatureView.isBitmapEmpty()) {
                    showDialog(getContext(), "Please Write Signature");
                    return ;
                }else{
//                    updateUiListener.updateUserInterface(0);
//                    getFragmentManager().popBackStack();

                    if (signatureView.getSignatureBitmap() == null){
                        Log.d("SignatureBitmap",   "Isnull");
                    }else{
                        Log.d("SignatureBitmap",   "Isnotnull");
                    }

                    //Log.d("SignatureBitmap",  signatureView.getSignatureBitmap() + "");

                    //createandDisplayPdf(parsedText, signatureView.getSignatureBitmap());
                    try {
                        newpdf_popup();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void newpdf_popup() throws IOException {
        final Dialog dialog = new Dialog(getContext());

        dialog.setContentView(R.layout.pdf_popup);
        dialog.setCancelable(true);

        newpdfview =  dialog.findViewById(R.id.signedcontractpdfView);
        Button submitpdf = dialog.findViewById(R.id.signedcontractSaveBtn);

        newrootDirectory = new File(CommonUtils.get3FFileRootPath() + "DigitalContract");
        // newfileToLoad = new File(newrootDirectory +"/"+ CommonConstants.FARMER_CODE + ".pdf");
        newfileToLoadd = new File(newrootDirectory +"/"+ CommonConstants.FARMER_CODE + ".pdf");

        File dir = new File(String.valueOf(newrootDirectory));
        if(!dir.exists())
            dir.mkdirs();

        PdfReader reader = new PdfReader(String.valueOf(fileToDownLoad));
        PdfContentByte content = null;
        PdfStamper stamper = null;

        Font f = new Font(Font.FontFamily.HELVETICA, 30);
        Phrase p = new Phrase("My watermark (text)", f);
        try {
            stamper = new PdfStamper(reader, new FileOutputStream(String.valueOf(newfileToLoadd)));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        // content = stamper.getOverContent(1);
        int n = reader.getNumberOfPages();
//        for(int i = 1;i<=n;i++) {
//            content = stamper.getOverContent(i);
//        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        signatureView.getSignatureBitmap().compress(Bitmap.CompressFormat.JPEG, 100 , stream);
        Image myImg = null;
        try {
            myImg = Image.getInstance(stream.toByteArray());
        } catch (BadElementException e) {
            e.printStackTrace();
        }

//        myImg.scaleAbsoluteHeight(100);
//        myImg.scaleAbsoluteWidth((myImg.getWidth() * 100) / myImg.getHeight());
        float w = myImg.getScaledWidth()/2;
        float h = myImg.getScaledHeight()/2;

        //myImg.setAbsolutePosition(submitpdf.getX() +230, submitpdf.getY() + 100);

        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(1f);
        Rectangle pagesize;
        float x, y;

        for (int i = 1; i <= n; i++) {
            pagesize = reader.getPageSizeWithRotation(i);
            x = (pagesize.getLeft() + pagesize.getRight())/ 2;
            y = (pagesize.getTop() + pagesize.getBottom())/ 2;
            content = stamper.getOverContent(i);
            content.saveState();
            content.setGState(gs1);
            if (i % 2 == 1)
            //ColumnText.showTextAligned(content, Element.ALIGN_CENTER, p, x, y, 0);
            {
                try {
                    content.addImage(myImg, w, 0, 0, h, x - (w/3), y - (h*3));
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    content.addImage(myImg, w, 0, 0, h, x - (w/3), y - (h*3));
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
            content.restoreState();
        }

//        try {
//            content.addImage(myImg);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
        try {
            stamper.close();
            reader.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        newpdfview.fromFile(newfileToLoadd)
                .defaultPage(0)
                .enableAnnotationRendering(true)
                .onPageChange(this)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(getActivity()))
                .load();



//        Document doc = new Document();
//        doc.open();
//
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        signatureView.getSignatureBitmap().compress(Bitmap.CompressFormat.JPEG, 100 , stream);
//        Image myImg = null;
//        try {
//            myImg = Image.getInstance(stream.toByteArray());
//        } catch (BadElementException e) {
//            e.printStackTrace();
//        }
//        myImg.setAlignment(Image.ALIGN_RIGHT);
//        try {
//            doc.add(myImg);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        doc.close();



        // if button is clicked, close the custom dialog
        submitpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                isContractAgreed = true;
                savedFarmerData.setFileName(CommonConstants.FARMER_CODE + ".pdf");
                savedFarmerData.setFileLocation(newfileToLoadd + "");
                savedFarmerData.setFileExtension(".pdf");

                DataManager.getInstance().addData(DataManager.FARMER_PERSONAL_DETAILS, savedFarmerData);

                if (isUpdateData) {
                    DataManager.getInstance().addData(DataManager.IS_FARMER_DATA_UPDATED, true);
                } else {
                    DataManager.getInstance().addData(DataManager.IS_FARMER_DATA_UPDATED, false);
                }
                CommonConstants.Flags.isFarmersDataUpdated = true;

                updateUiListener.updateUserInterface(0);
                getFragmentManager().popBackStack();
            }
        });

        dialog.show();
    }


   /* @RequiresApi(api = Build.VERSION_CODES.O)
    private void newpdf_popup() throws IOException {
        final Dialog dialog = new Dialog(getContext());

        dialog.setContentView(R.layout.pdf_popup);
        dialog.setCancelable(true);

        newpdfview =  dialog.findViewById(R.id.signedcontractpdfView);
        Button submitpdf = dialog.findViewById(R.id.signedcontractSaveBtn);

        newrootDirectory = new File(CommonUtils.get3FFileRootPath() + "DigitalContract");
           // newfileToLoad = new File(newrootDirectory +"/"+ CommonConstants.FARMER_CODE + ".pdf");
        newfileToLoadd = new File(newrootDirectory +"/"+ CommonConstants.FARMER_CODE + ".pdf");

        File dir = new File(String.valueOf(newrootDirectory));
        if(!dir.exists())
            dir.mkdirs();

        PdfReader reader = new PdfReader(String.valueOf(fileToDownLoad));
        PdfContentByte content = null;
        PdfStamper stamper = null;
        try {
            stamper = new PdfStamper(reader, new FileOutputStream(String.valueOf(newfileToLoadd)));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
       // content = stamper.getOverContent(1);
        int n = reader.getNumberOfPages();
        for(int i = 1;i<=n;i++) {
            content = stamper.getOverContent(i);
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        signatureView.getSignatureBitmap().compress(Bitmap.CompressFormat.JPEG, 100 , stream);
        Image myImg = null;
        try {
            myImg = Image.getInstance(stream.toByteArray());
        } catch (BadElementException e) {
            e.printStackTrace();
        }

        myImg.scaleAbsoluteHeight(100);
        myImg.scaleAbsoluteWidth((myImg.getWidth() * 100) / myImg.getHeight());
        myImg.setAbsolutePosition(submitpdf.getX() +230, submitpdf.getY() + 100);
        try {
            content.addImage(myImg);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        try {
            stamper.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        newpdfview.fromFile(newfileToLoadd)
                .defaultPage(0)
                .enableAnnotationRendering(true)
                .onPageChange(this)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(getActivity()))
                .load();



//        Document doc = new Document();
//        doc.open();
//
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        signatureView.getSignatureBitmap().compress(Bitmap.CompressFormat.JPEG, 100 , stream);
//        Image myImg = null;
//        try {
//            myImg = Image.getInstance(stream.toByteArray());
//        } catch (BadElementException e) {
//            e.printStackTrace();
//        }
//        myImg.setAlignment(Image.ALIGN_RIGHT);
//        try {
//            doc.add(myImg);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        doc.close();



        // if button is clicked, close the custom dialog
        submitpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                isContractAgreed = true;
                savedFarmerData.setFileName(CommonConstants.FARMER_CODE + ".pdf");
                savedFarmerData.setFileLocation(newfileToLoadd + "");
                savedFarmerData.setFileExtension(".pdf");

                DataManager.getInstance().addData(DataManager.FARMER_PERSONAL_DETAILS, savedFarmerData);

                if (isUpdateData) {
                    DataManager.getInstance().addData(DataManager.IS_FARMER_DATA_UPDATED, true);
                } else {
                    DataManager.getInstance().addData(DataManager.IS_FARMER_DATA_UPDATED, false);
                }
                CommonConstants.Flags.isFarmersDataUpdated = true;

                updateUiListener.updateUserInterface(0);
                getFragmentManager().popBackStack();
            }
        });

        dialog.show();
    }*/


    public void createandDisplayPdf(String text, Bitmap bm) {

        Document doc = new Document();

        try {
            String path = CommonUtils.get3FFileRootPath() + "DigitalContracttt/";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            File file = new File(dir, CommonConstants.FARMER_CODE + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Paragraph p1 = new Paragraph(text);
            p1.setAlignment(Paragraph.ALIGN_LEFT);
            doc.add(p1);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
            Image myImg = Image.getInstance(stream.toByteArray());
            myImg.setAlignment(Image.ALIGN_RIGHT);
            doc.add(myImg);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            doc.close();
        }

        //viewPdf(CommonConstants.FARMER_CODE + ".pdf");
    }

    // Method for opening a pdf file
    private void viewPdf(String file) {

        File pdfFile = new File(CommonUtils.get3FFileRootPath() + "DigitalContracttt" + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }

    public void showDialog(Context activity, String msg) {
        final Dialog dialog = new Dialog(activity, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        final ImageView img = dialog.findViewById(R.id.img_cross);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((Animatable) img.getDrawable()).start();
            }
        }, 500);
    }


    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    public UpdateUiListener getUpdateUiListener() {
        return updateUiListener;
    }

    public void setUpdateUiListener(UpdateUiListener updateUiListener) {
        this.updateUiListener = updateUiListener;
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        public boolean downloadSuccess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                OutputStream output = new FileOutputStream(rootDirectory + digitalContract.getFILENAME() + digitalContract.getFileExtension());

                byte data[] = new byte[1024];

                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
                downloadSuccess = true;
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                downloadSuccess = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            if (downloadSuccess && !getActivity().isFinishing()) {
                fileToDownLoad = new File(rootDirectory + digitalContract.getFILENAME() + digitalContract.getFileExtension());
                if (null != fileToDownLoad && fileToDownLoad.exists()) {
                    dataView.fromFile(fileToDownLoad)
                            .defaultPage(0)
                            .enableAnnotationRendering(true)
                            .onPageChange(ConversionDigitalContractFragment.this)
                            .onLoad(ConversionDigitalContractFragment.this)
                            .scrollHandle(new DefaultScrollHandle(getActivity()))
                            .load();
                } else {
                    UiUtils.showCustomToastMessage("File not exist", getActivity(), 1);
                }
            }

        }
    }

}
