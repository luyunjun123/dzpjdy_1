//package com.wzcsoft.dzpjdy;
//
//import com.wzcsoft.dzpjdy.domain.Bill;
//
//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlElementWrapper;
//import javax.xml.bind.annotation.XmlRootElement;
//import java.io.Serializable;
//import java.util.List;
//
///**
// * @author lyj
// * @date 2019/7/15 23:48
// */
//@XmlRootElement(name = "Root")
//public class Result implements Serializable{
//
//
//    private List<Bill> bills;
//
//    @XmlElementWrapper(name = "Data")
//    @XmlElement(name = "OutInvoice")
//    public List<Bill> getBills() {
//        return bills;
//    }
//
//    public void setBills(List<Bill> bills) {
//        this.bills = bills;
//    }
//}
