package com.wzcsoft.dzpjdy.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.qrcode.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by taowa on 2019/5/13.
 */
public class PdfUtil {

//    public static void main(String[] args){
//        PdfUtil.writeFaPiaoPdf(null,"E:\\wangtao\\pdf");
//    }

    public static String writeFaPiaoPdf(JSONObject jsonObj, String pdf_template, String pdf_dir,String billName,String billBatchCode,String billNo,String payer,String random,String ivcDateTime, String payCompany) {
        String uuidname = UUID.randomUUID().toString();
        String pdfFileName = pdf_dir + File.separator + uuidname + ".pdf";

        FileOutputStream out;// 输出流;
        PdfReader reader;
        PdfStamper stamper;
//        String templatePath =  "E:\\wangtao\\pdf\\fp_template.pdf";
        String templatePath =  pdf_template;
        try {
            out = new FileOutputStream(pdfFileName);
            reader = new PdfReader(templatePath);// 读取pdf模板
            stamper = new PdfStamper(reader, out);

            //BaseFont bf = BaseFont.createFont("c:/windows/fonts/simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            BaseFont bf = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            AcroFields form = stamper.getAcroFields();
            ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
            fontList.add(bf);

            for (String key : form.getFields().keySet()) {
                form.setFieldProperty(key, "textfont", bf, null);
                form.setFieldProperty(key, "textsize", new Float(40), null);
            }

            form.setField("billName", billName);
            form.setField("billBatchCode", billBatchCode);
            form.setField("billNo", billNo);
            form.setField("payer", jsonObj.get("payer").toString());
            form.setField("random", random);
            form.setField("ivcDateTime", ivcDateTime);

            form.setField("busNo", "业务流水号：" + jsonObj.get("busNo").toString());

            String tmp_busType = jsonObj.get("busType").toString();
            switch(jsonObj.get("busType").toString()){
                case "01":
                    tmp_busType = "住院";
                    break;
                case "02":
                    tmp_busType = "门诊";
                    break;
                case "03":
                    tmp_busType = "急诊";
                    break;
                case "04":
                    tmp_busType = "体检中心";
                    break;
                case "05":
                    tmp_busType = "门特";
                    break;
                case "06":
                    tmp_busType = "挂号";
                    break;
                default:
                    break;
            }
            form.setField("busType", "业务标识：" + tmp_busType);

            form.setField("busDateTime", "业务发生时间：" + jsonObj.get("busDateTime").toString());


            form.setField("chargenameleft", "费别");
            form.setField("chargeamtleft", "金额");
            form.setField("chargenameright", "费别");
            form.setField("chargeamtright", "金额");
            JSONArray chargeDetailArr = JSON.parseArray(jsonObj.get("chargeDetail").toString());
            for(int i = 0;i < chargeDetailArr.size() && i <= 13;i++){
                form.setField("chargeName"+i, chargeDetailArr.getJSONObject(i).get("chargeName").toString());
                form.setField("chargeAmt"+i, chargeDetailArr.getJSONObject(i).get("amt").toString());
            }


            form.setField("listname", "项目");
            form.setField("listunit", "单位");
            form.setField("listprice", "单价");
            form.setField("listcount", "数量");
            form.setField("listamt", "金额");
            JSONArray listDetailArr = JSON.parseArray(jsonObj.get("listDetail").toString());
            for(int i = 0;i < listDetailArr.size() && i <= 9;i++){
                form.setField("name"+i, listDetailArr.getJSONObject(i).get("name").toString());

                Object tmp_unit = listDetailArr.getJSONObject(i).get("unit");
                if(tmp_unit == null || tmp_unit.toString().equals("null")){
                    tmp_unit = "元";
                }
                form.setField("unit"+i, tmp_unit.toString());

                form.setField("price"+i, listDetailArr.getJSONObject(i).get("price").toString());
                form.setField("count"+i, listDetailArr.getJSONObject(i).get("count").toString());
                form.setField("amt"+i, listDetailArr.getJSONObject(i).get("amt").toString());
            }



            form.setField("totalamtlabel", "合 计：");
            form.setField("totalAmt", jsonObj.get("totalAmt").toString());
            form.setField("totalamtupperlabel", "合计(大写)：");
            Double amtdouble = Double.parseDouble(jsonObj.get("totalAmt").toString());
            BigDecimal numberOfMoney = new BigDecimal(amtdouble);
            String s = NumberToCN.number2CNMontrayUnit(numberOfMoney);
            form.setField("totalAmtUpper", s);



            form.setField("payCompany", payCompany);
            form.setField("payee", jsonObj.get("payee").toString());


            stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.close();
            reader.close();
            return pdfFileName;
        }
         catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
//        } catch (WriterException e) {
//            e.printStackTrace();
//            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
