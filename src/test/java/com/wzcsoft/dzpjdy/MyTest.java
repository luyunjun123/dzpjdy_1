//package com.wzcsoft.dzpjdy;
//
//import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
//import com.wzcsoft.dzpjdy.domain.Bill;
//import com.wzcsoft.dzpjdy.websocket.MyWebSocket;
//import org.apache.commons.io.IOUtils;
//import org.dom4j.DocumentHelper;
//import org.junit.Test;
//import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
//import org.dom4j.Element;
//
//import org.dom4j.Document;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//import javax.xml.bind.Unmarshaller;
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Stream;
//
///**
// * @author lyj
// * @date 2019/7/9 14:52
// */
//public class MyTest {
//
//
//    @Test
//    public void mytest() throws Exception {
////        String sendStr ="<?xml version=\"1.0\" encoding=\"utf-16\"?><RequestMarkInfo><TransCode>001DQKP</TransCode><MarkType>1</MarkType><MarkNO>" + "12312" + "</MarkNO><BankTransNO></BankTransNO><SelfMachine><SelfIP>" + "192.192.220.16" + "</SelfIP><SelfTransNo>" + "192.192.220.16" + "</SelfTransNo></SelfMachine></RequestMarkInfo>";
////        byte[] buff = sendStr.getBytes("GB2312");
////        int len = buff.length;
////        System.out.println(len);
////        String format = String.format("%08d", len);
////        System.out.println(format);
////        sendStr = String.format("%08d", len) + sendStr;
////        System.out.println(sendStr);
//
//        String sendStr = "<?xml version=\"1.0\" encoding=\"utf-16\"?><RequestOutInvoicePrint><TransCode>052MZFPDY</TransCode><InvoiceSeq>" + "1231" + "</InvoiceSeq><InvoiceSeq>" + "123123" + "</InvoiceSeq><SelfMachine> <SelfIP>" + "13123" + "</SelfIP> <SelfTransNo>" + "13123123" + "</SelfTransNo> </SelfMachine> <Reserved1/> <Reserved2/> <Reserved3/> <Branch>A</Branch> <Producer>HW</Producer> <RequestOutInvoicePrint> <?xml version=\"1.0\" encoding=\"utf-16\"?> <ResultInfo> <Result>1</Result> <Err>更新成功<Err> </ResultInfo>";
//        byte[] buff = sendStr.getBytes("GB2312");
//        int len = buff.length;
//        sendStr = String.format("%08d", len) + sendStr;
//        System.out.println("发送报文：" + sendStr);
//    }
////    @Test
////    public void mytest1()throws Exception{
////        createXml();
////    }
//
//    /**
//     * 生成xml方法
//     */
////    public static void createXml(){
////        try {
////            // 创建解析器工厂
////            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
////            DocumentBuilder db = factory.newDocumentBuilder();
////            Document document = db.newDocument();
////            // 不显示standalone="no"
////            document.setXmlStandalone(true);
////            Element bookstore = document.createElement("bookstore");
////            // 向bookstore根节点中添加子节点book
////            Element book = document.createElement("book");
////
////            Element name = document.createElement("name");
////            // 不显示内容 name.setNodeValue("不好使");
////            name.setTextContent("雷神");
////            book.appendChild(name);
////            // 为book节点添加属性
////            book.setAttribute("id", "1");
////            // 将book节点添加到bookstore根节点中
////            bookstore.appendChild(book);
////            // 将bookstore节点（已包含book）添加到dom树中
////            document.appendChild(bookstore);
////
////            // 创建TransformerFactory对象
////            TransformerFactory tff = TransformerFactory.newInstance();
////            // 创建 Transformer对象
////            Transformer tf = tff.newTransformer();
////
////            // 输出内容是否使用换行
////            tf.setOutputProperty(OutputKeys.INDENT, "yes");
////            // 创建xml文件并写入内容
////            tf.transform(new DOMSource(document), new StreamResult(new File("book1.xml")));
////            System.out.println("生成book1.xml成功");
////        } catch (Exception e) {
////            e.printStackTrace();
////            System.out.println("生成book1.xml失败");
////        }
////    }
////    self.ipaddress=192.192.220.16
////    self.transno=611003
//    @Test
//    public void testSocket() throws Exception {
//        ServerSocket serverSocket = new ServerSocket(9090);
//        Socket socket = serverSocket.accept();
//        InputStream inputStream = socket.getInputStream();
//        InputStreamReader isr = new InputStreamReader(inputStream, "GB2312");
//        BufferedReader br = new BufferedReader(isr);
//
//
////        Socket client = new Socket("127.0.0.1", Integer.parseInt("9090"));
////        OutputStream outputStream = client.getOutputStream();
////        DataOutputStream out = new DataOutputStream(outputStream);
////        String sendStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ReponseOutInoice><Result>1</Result><Err>" + "成功" + "</Err></ReponseOutInoice> <OutInvoivc> <InvoiceNo>" + "12312312" + "</InvoiceNo> <InvoiceSeq>" + "12312312" + "</InvoiceSeq> </OutInvoivc>";
////        byte[] buff = sendStr.getBytes("GB2312");
////        int len = buff.length;
////        sendStr = String.format("%08d", len) + sendStr;
////        System.out.println("模拟报文：" + sendStr);
////        PrintWriter pw = new PrintWriter(outputStream);
////        out.write(sendStr.getBytes("GB2312"));
//        while (true) {
//            Stream<String> lines = br.lines();
//            lines.forEach(System.out::println);
//
//        }
//
//    }
//
//    private static ScheduledExecutorService executor =
//            Executors.newSingleThreadScheduledExecutor(new CustomizableThreadFactory("线程一号"));
//
//    public void onOpen() {
//
//        executor.scheduleWithFixedDelay(() -> {
//            System.out.println("Thread:" + Thread.currentThread().getName());
////                System.out.println("decodestate:" + decodestate);
//            try {
//                Thread.sleep(1000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }, 1000, 1000, TimeUnit.MILLISECONDS);
//    }
//
//    @Test
//    public void mytest3() throws Exception {
//        onOpen();
//    }
//
//    public void mySouket() {
//    }
//
//
//    @Test
//    public void mytest4() throws Exception {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DATE, -7);
//        Date time = c.getTime();
//        //七天前时间
//        String preDay = sdf.format(time);
//        Date date = new Date();
//        //当前时间
//        String timeStr = sdf.format(date);
//
//    }
//
//    @Test
//    public void mytest5() throws Exception {
//        Document doc = null;
//        String responseXml = "<?xml version=\"1.0\" encoding=\"utf-16\"?>" +
//                " <ReponseOutInoice>" +
//                "<Result>1</Result>" +
//                " <Err>" + "成功" + "</Err> " +
//                "</ReponseOutInoice>" +
//                "</OutInvoivc>" +
//                " <InvoiceNo>" + "23123123" + "</InvoiceNo> " +
//                " <InvoiceSeq>" + "12312312" + "</InvoiceSeq> " +
//                " <OutInvoivc>" +
//                " </OutInvoivc>";
//
////                String responseXml ="<ReponseOutInoice>" +
////                        "<Result>1</Result>" +
////                        " <Err>"+"成功"+ "</Err> " +
////                        "</ReponseOutInoice> " +
////                        "<OutInvoivc> " +
////                        "<InvoiceNo>"+"12312312"+"</InvoiceNo> " +
////                        "<InvoiceSeq>"+"12312312"+"</InvoiceSeq> " +
////                        "</OutInvoivc>";
//
//        doc = DocumentHelper.parseText(responseXml);
//        Element rootElt = doc.getRootElement();
//        // Iterator items = rootElt.elementIterator("ReponseOutInoice");
//        //  Element RequestOutInvoice_1 = (Element) items.next();
//
//        //   System.out.println("items"+RequestOutInvoice_1);
//
////                String RequestOutInvoice = rootElt.elementTextTrim("RequestOutInvoice");
////                System.out.println("RequestOutInvoice+:"+RequestOutInvoice);
////                String InvoiceSeq = rootElt.elementTextTrim("InvoiceSeq");
////                System.out.println("InvoiceSeq:"+InvoiceSeq);
////                String CardNo = rootElt.elementTextTrim("CardNo");
////                System.out.println("CardNo:"+CardNo);
//        String InvoiceSeq = rootElt.elementTextTrim("InvoiceSeq");
//        System.out.println("InvoiceSeq:" + InvoiceSeq);
//    }
//
//    @Test
//    public void mytest6() throws Exception {
//        String str = "01190002081067";
//        String substring = str.substring(4);
//        System.out.println(substring);
//    }
//
//    @Test
//    public void mytest7() throws Exception {
//        FileInputStream fis = new FileInputStream("F:\\idea\\idea\\dzpjdy\\dzpjdy\\src\\main\\resources\\test");
//        InputStreamReader inputStreamReader = new InputStreamReader(fis, "GB2312");
//        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//加入缓冲区
//
//        char[] lenbuff = new char[1024];
//        bufferedReader.read(lenbuff, 0, 8);
//        String lenStr = new String(lenbuff);
//        int resplen = Integer.parseInt(lenStr);
//        lenbuff = new char[resplen];
//        bufferedReader.read(lenbuff, 0, resplen);
//        String responseXml = new String(lenbuff).trim();
//        Document doc;
//        doc = DocumentHelper.parseText(responseXml);
//        Element rootElt = doc.getRootElement();
//        String resultCode = rootElt.elementTextTrim("Result");
//        System.out.println(resultCode);
//    }
//
//
//    public String getXmlString11() {
//        String xmlString;
//        byte[] strBuffer = null;
//        int flen = 0;
//        File xmlfile = new File("F:\\idea\\idea\\dzpjdy\\dzpjdy\\src\\main\\resources\\test");
//        try {
//            InputStream in = new FileInputStream(xmlfile);
//            flen = (int) xmlfile.length();
//            strBuffer = new byte[flen];
//            in.read(strBuffer, 0, flen);
//        } catch (FileNotFoundException e) {
//// TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//// TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        xmlString = new String(strBuffer); //构建String时，可用byte[]类型，
//        return xmlString;
//    }
//
//
//
////            @Test
////            public void testReadInputStream() throws Exception{
////               File file = new File("F:\\idea\\idea\\dzpjdy\\dzpjdy\\src\\main\\resources\\test.xml");
////
//////                String s = getXmlString11();
//////                System.out.println(s);
//////                byte[] bytes = read(new FileInputStream(file));
//////                String s = new String(bytes);
//////                System.out.println(s);
////                String s="<?xml version=\"1.0\" encoding=\"utf-16\"?>" +
//////                        "<ReponseOutInvoice>" +
//////                        "<Result>1</Result>" +
//////                        "<Err>成功</Err>" +
//////                        "<SumRows>5</SumRows> " +
//////                        "<OutInvoice>" +
//////                        "<InvoiceNO>01190002111278</InvoiceNO>" +
//////                        "<InvoiceSeq>200305191</InvoiceSeq>" +
//////                        "<PrintNo>1/1</PrintNo>" +
//////                        "<Oper>YY1017</Oper>" +
//////                        "<Name>何攀</Name>" +
//////                        "<InvoiceName>四川省医疗卫生单位门诊结算票据（电子）</InvoiceName>" +
//////                        "<EBillNo>0002111278</EBillNo>" +
//////                        "<EBillBatchCode>51060119</EBillBatchCode>" +
//////                        "<ERandom>cb3e03</ERandom>" +
//////                        "<ECreateTime>20190717</ECreateTime>" +
//////                        "<TotalCost>￥261.10</TotalCost>" +
//////                        "<UpTotCost>贰佰陆拾壹元壹角整</UpTotCost>" +
//////                        "<ItemTypeTotal>" +
//////                        "<ItemTypeName>西药费</ItemTypeName>" +
//////                        "<ItemTypeCost>261.10</ItemTypeCost>" +
//////                        "<ItemTypeCode>01</ItemTypeCode>" +
//////                        "</ItemTypeTotal>" +
//////                        "<FeeItem>" +
//////                        "<RecipeNO>60586384</RecipeNO>" +
//////                        "<SeqNO>1</SeqNO>" +
//////                        "<ItemName>▲阿魏酸哌嗪片|宝盛康(亨达盛康)(基)(川)</ItemName>" +
//////                        "<Qty>2.00</Qty>" +
//////                        "<Unit>瓶</Unit>" +
//////                        "<Specs>50mg*180片/瓶</Specs>" +
//////                        "<Cost>85.72</Cost>" +
//////                        "<Price>42.86</Price> " +
//////                        "</FeeItem> " +
//////                        "<FeeItem> " +
//////                        "<RecipeNO>60586384</RecipeNO> " +
//////                        "<SeqNO>2</SeqNO>" +
//////                        " <ItemName>▲培哚普利叔丁胺片|雅施达(基)</ItemName>" +
//////                        " <Qty>2.00</Qty>" +
//////                        " <Unit>盒</Unit>" +
//////                        " <Specs>8mg*15片/盒</Specs>" +
//////                        " <Cost>175.38</Cost>" +
//////                        " <Price>87.69</Price>" +
//////                        " </FeeItem> " +
////                        "</OutInvoice>";
//////                               FileInputStream fileInputStreama = new FileInputStream(file);
//////                IOUtils.copy(fileInputStreama, new FileOutputStream("C:\\myfile"));
////
////
////                try {
////                    String substring = s.substring(s.indexOf("<OutInvoice>"), s.lastIndexOf("</OutInvoice>") + "</OutInvoice>".length());
////                    String format = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Root><Data>%s</Data></Root>", substring);
////                    Result r = convertXmlStrToObject(Result.class,format);
////                    com.wzcsoft.dzpjdy.Bill bill = r.getBills()
////                            .stream()
////                            .filter(i -> i.geteBillNo().equals("0002111278"))
////                            .findFirst()
////                            .orElse(null);
////
////                    try {
////                        //返回his开票数据
////                        String invoiceSeq = bill.getInvoiceSeq();
////                        System.out.println("对比数据"+invoiceSeq);
////                        System.out.println("2222222"+bill);
////                        String xml = convertToXml(r);
////                        System.out.println(xml);
////                    }catch (Exception e){
////                        System.out.println("该数据已经回传");
////                        return;
////                    }
////                }catch (Exception e){
////                    String message = e.getMessage();
////                    String localizedMessage = e.getLocalizedMessage();
////                    System.out.println(message);
////
////                    System.out.println("xml解析失败");
////
////                }
//////                System.out.println(s);
//////                String substring =s.substring()
////
////
////
////
////
////
////            }
//
//            private byte[] read(InputStream inputStream) throws Exception{
//
//                int readed;
//                try ( ByteArrayOutputStream bos = new ByteArrayOutputStream()){
//                    while( (readed = inputStream.read()) != -1) {
//                        System.out.println(readed);
//                        bos.write(readed);
//                    }
//                    return bos.toByteArray();
//                }
//
//
//            }
//
//    /**
//     * 将对象直接转换成String类型的 XML输出
//     *
//     * @param obj
//     * @return
//     */
//    public static String convertToXml(Object obj) {
//        // 创建输出流
//        StringWriter sw = new StringWriter();
//        try {
//            // 利用jdk中自带的转换类实现
//            JAXBContext context = JAXBContext.newInstance(obj.getClass());
//
//            Marshaller marshaller = context.createMarshaller();
//            // 格式化xml输出的格式
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
//                    Boolean.TRUE);
//            // 将对象转换成输出流形式的xml
//            marshaller.marshal(obj, sw);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
//        return sw.toString();
//    }
//
//    /**
//     * 将String类型的xml转换成对象
//     */
//    public static <T> T convertXmlStrToObject(Class<T> clazz, String xmlStr) {
//        Object xmlObject = null;
//        try {
//            JAXBContext context = JAXBContext.newInstance(clazz);
//            // 进行将Xml转成对象的核心接口
//            Unmarshaller unmarshaller = context.createUnmarshaller();
//            StringReader sr = new StringReader(xmlStr);
//            xmlObject = unmarshaller.unmarshal(sr);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//            System.out.println("转换异常");
//        }
//        return (T) xmlObject;
//    }
//
//            @Test
//            public void mytest8()throws Exception{
//                File file = new File("F:\\idea\\idea\\dzpjdy\\dzpjdy\\src\\main\\resources\\test");
//
//                FileInputStream fileInputStream = new FileInputStream(file);
//                IOUtils.copy(fileInputStream, new FileOutputStream("C:\\myfile\\myfile.txt"));
//            }
//
//            @Test
//            public void mytest9()throws Exception{
//                SimpleDateFormat dd = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//                Date inputDate = null;
//                try {
//                    inputDate = (new SimpleDateFormat("yyyy-MM-dd")).parse("2019-07-16");
//                } catch (Exception var19) {
//
//
//                }
//                Date beginDate = new Date(inputDate.getTime() + -604800000L);
//                Date endDate = new Date(inputDate.getTime() + 86400000L - 1L);
//                System.out.println(beginDate);
//                System.out.println(endDate);
//            }
//            @Test
//            public void mytest10()throws Exception{
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//                Calendar c = Calendar.getInstance();
//                c.add(Calendar.DATE, - 7);
//                Date time = c.getTime();
//                String preDay = sdf.format(time);
//                System.out.println(preDay);
//
//                Date date = new Date();
//                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMdd");
//                System.out.println(dateFormat.format(date));
//
//            }
//
//
//            @Test
//            public void mytest11()throws Exception{
//                SimpleDateFormat dd = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//                String timeStr="2019-07-16";
//                Date inputDate = null;
//                try {
//                    inputDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(timeStr);
//                } catch (Exception var19) {
//
//                }
//
//                Date startDate = new Date(inputDate.getTime() + -604800000L);
//                Date endDate = new Date(inputDate.getTime() + 86400000L - 1L);
//                System.out.println(startDate);
//                System.out.println(endDate);
//            }
//
//            @Test
//            public void mytest111()throws Exception{
//                WriteStringToFile();
//            }
//             public void WriteStringToFile() {
//        try {
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//            String format = df.format(new Date());
//            File file = new File("C:\\myfile");
//            PrintStream ps = new PrintStream(new FileOutputStream(file));
//            ps.println("打印票据回传信息："+format);// 往文件里写入字符串
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    private static String readString2()
//    {
//        StringBuffer str=new StringBuffer("");
//        File file=new File("C:\\Users\\Administrator\\Desktop\\test.txt");
//        try {
//            FileReader fr=new FileReader(file);
//            int ch = 0;
//            while((ch = fr.read())!=-1 )
//            {
//                System.out.print((char)ch+" ");
//            }
//            fr.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            System.out.println("File reader出错");
//        }
//        return str.toString();
//    }
//
//    public static void WriteStringToFile(String invoiceSeq,String patientid,String responseXml) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        String format = df.format(new Date());
//        // 第1步、使用File类找到一个文件
//        File f= new File("C:\\myfile") ;	// 声明File对象
//        // 第2步、通过子类实例化父类对象
//        Writer out = null	;// 准备好一个输出的对象
//        try {
//            // 通过对象多态性，进行实例化
//            out = new FileWriter(f,true) ;
//            // 第3步、进行写操作
//            // 准备要写入的字符串
//            out.write("返回状态："+responseXml+"，时间:"+format+"，返回号码：("+invoiceSeq+")，病人id："+patientid+"\r\n") ;// 将内容输出，保存文件
//            // 第4步、关闭输出流
//            out.close() ;
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            System.out.println("写入异常");
//        }
//    }
//    @Test
//    public void mytest123()throws Exception {
//        String sql="00028199821";
//        String patientid="98080800";
////        String responseXml="<?xml version=\"1.0\" encoding=\"utf-16\"?><ResultInfo><Result>1</Result><Err>成功<Err/></ResultInfo>";
//        String responseXml=null;
//        if(responseXml==null){
//            responseXml="发生异常无状态";
//        }
//        WriteStringToFile(sql,patientid,responseXml);
//    }
//@Test
//public void mytest456()throws Exception{
//    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//    String format = df.format(new Date());
//    System.out.println(format);
//}
//@Test
//public void mytest12321()throws Exception{
//    cn.hutool.json.JSONObject basic_1 = new cn.hutool.json.JSONObject();
//    basic_1.put("hospitalCode","450754387");
//    basic_1.put("branchCode","01");
//    basic_1.put("medicalCombo","1");
//    basic_1.put("operatorNo","009");
//    basic_1.put("vendorCode","ASP0006");
//    basic_1.put("transactionCode","opt_0001");
//    basic_1.put("needPage", "true");
//    basic_1.put("pageNo", 1);
//    basic_1.put("pageSize", 10);
//    System.out.println(basic_1);
//}
//}
