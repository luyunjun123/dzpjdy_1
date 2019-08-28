//package com.wzcsoft.dzpjdy;
//
//import javax.xml.bind.annotation.XmlElement;
//import java.io.Serializable;
//
///**
// * @author lyj
// * @date 2019/7/15 23:34
// */
//public class Bill implements Serializable {
//
//
//    private String invoiceNo;
//
//    private String invoiceSeq;
//
//    private String printNo;
//
//    private String oper;
//
//    private String name;
//
//    private String invoiceName;
//
//    private String eBillNo;
//
//    private String eBillBatchCode;
//
//    private String eRandom;
//
//    private String eCreateTime;
//
//    private String totalCost;
//
//    private String upTotCost;
//
//    private String feeItem;
//    private String seqNO;
//    private String qty;
//    private String unit;
//    private String specs;
//
//    private String Cost;
//    private String Price;
//    @XmlElement(name = "ERandom")
//    public String geteRandom() {
//        return eRandom;
//    }
//    @XmlElement(name = "ECreateTime")
//    public String geteCreateTime() {
//        return eCreateTime;
//    }
//    @XmlElement(name = "TotalCost")
//    public String getTotalCost() {
//        return totalCost;
//    }
//    @XmlElement(name = "UpTotCost")
//    public String getUpTotCost() {
//        return upTotCost;
//    }
//    @XmlElement(name = "FeeItem")
//    public String getFeeItem() {
//        return feeItem;
//    }
//    @XmlElement(name = "SeqNO")
//    public String getSeqNO() {
//        return seqNO;
//    }
//    @XmlElement(name = "Qty")
//    public String getQty() {
//        return qty;
//    }
//    @XmlElement(name = "Unit")
//    public String getUnit() {
//        return unit;
//    }
//    @XmlElement(name = "Specs")
//    public String getSpecs() {
//        return specs;
//    }
//    @XmlElement(name = "Cost")
//    public String getCost() {
//        return Cost;
//    }
//    @XmlElement(name = "Price")
//    public String getPrice() {
//        return Price;
//    }
//
//    @XmlElement(name = "InvoiceNo")
//    public String getInvoiceNo() {
//        return invoiceNo;
//    }
//
//    public void setInvoiceNo(String invoiceNo) {
//        this.invoiceNo = invoiceNo;
//    }
//
//    @XmlElement(name = "InvoiceSeq")
//    public String getInvoiceSeq() {
//        return invoiceSeq;
//    }
//
//    public void setInvoiceSeq(String invoiceSeq) {
//        this.invoiceSeq = invoiceSeq;
//    }
//
//    @XmlElement(name = "PrintNo")
//    public String getPrintNo() {
//        return printNo;
//    }
//
//    public void setPrintNo(String printNo) {
//        this.printNo = printNo;
//    }
//
//    @XmlElement(name = "Oper")
//    public String getOper() {
//        return oper;
//    }
//
//    public void setOper(String oper) {
//        this.oper = oper;
//    }
//
//    @XmlElement(name = "Name")
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    @XmlElement(name = "InvoiceName")
//    public String getInvoiceName() {
//        return invoiceName;
//    }
//
//    public void setInvoiceName(String invoiceName) {
//        this.invoiceName = invoiceName;
//    }
//
//    @XmlElement(name = "EBillNo")
//    public String geteBillNo() {
//        return eBillNo;
//    }
//
//    public void seteBillNo(String eBillNo) {
//        this.eBillNo = eBillNo;
//    }
//
//    @XmlElement(name = "EBillBatchCode")
//    public String geteBillBatchCode() {
//        return eBillBatchCode;
//    }
//
//    public void seteBillBatchCode(String eBillBatchCode) {
//        this.eBillBatchCode = eBillBatchCode;
//    }
//
//    public void seteRandom(String eRandom) {
//        this.eRandom = eRandom;
//    }
//
//    public void seteCreateTime(String eCreateTime) {
//        this.eCreateTime = eCreateTime;
//    }
//
//    public void setTotalCost(String totalCost) {
//        this.totalCost = totalCost;
//    }
//
//    public void setUpTotCost(String upTotCost) {
//        this.upTotCost = upTotCost;
//    }
//
//    public void setFeeItem(String feeItem) {
//        this.feeItem = feeItem;
//    }
//
//    public void setSeqNO(String seqNO) {
//        this.seqNO = seqNO;
//    }
//
//    public void setQty(String qty) {
//        this.qty = qty;
//    }
//
//    public void setUnit(String unit) {
//        this.unit = unit;
//    }
//
//    public void setSpecs(String specs) {
//        this.specs = specs;
//    }
//
//    public void setCost(String cost) {
//        Cost = cost;
//    }
//
//    public void setPrice(String price) {
//        Price = price;
//    }
//
//    @Override
//    public String toString() {
//        return "Bill{" +
//                "invoiceNo='" + invoiceNo + '\'' +
//                ", invoiceSeq='" + invoiceSeq + '\'' +
//                ", printNo='" + printNo + '\'' +
//                ", oper='" + oper + '\'' +
//                ", name='" + name + '\'' +
//                ", invoiceName='" + invoiceName + '\'' +
//                ", eBillNo='" + eBillNo + '\'' +
//                ", eBillBatchCode='" + eBillBatchCode + '\'' +
//                ", eRandom='" + eRandom + '\'' +
//                ", eCreateTime='" + eCreateTime + '\'' +
//                ", totalCost='" + totalCost + '\'' +
//                ", upTotCost='" + upTotCost + '\'' +
//                ", feeItem='" + feeItem + '\'' +
//                ", seqNO='" + seqNO + '\'' +
//                ", qty='" + qty + '\'' +
//                ", unit='" + unit + '\'' +
//                ", specs='" + specs + '\'' +
//                ", Cost='" + Cost + '\'' +
//                ", Price='" + Price + '\'' +
//                '}';
//    }
//}
