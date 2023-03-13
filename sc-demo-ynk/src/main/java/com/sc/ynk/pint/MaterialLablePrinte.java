package com.sc.ynk.pint;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.util.Hashtable;
import java.util.List;

/**
 * @author Janle on 2022/11/18
 */
public class MaterialLablePrinte implements Printable {

    public MaterialLablePrinte() {
    }

    private static List<MessageQrCodeVo> DEMODTO_LIST;  //待打印的文字数据

    public MaterialLablePrinte(List<MessageQrCodeVo> messageQrCodeVos) {
        DEMODTO_LIST = messageQrCodeVos;
        COUNT = messageQrCodeVos.size() - 1;
    }

    private static int COUNT;    //待打印数据的条数，此变量需定义在数据集合之前


    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        System.out.println("-----------------执行第" + (COUNT + 1) + "次打印-------------------");

        Component c = null;

        //转换成Graphics2D
        Graphics2D g2 = (Graphics2D) graphics;

        //设置打印颜色为黑色
        g2.setColor(Color.black);

        //打印起点坐标
        double x = pageFormat.getImageableX();
        double y = pageFormat.getImageableY();

        switch (pageIndex) {
            case 0:
                //设置打印字体（字体名称、样式和点大小）（字体名称可以是物理或者逻辑名称）
                //Java平台所定义的五种字体系列：Serif、SansSerif、Monospaced、Dialog 和 DialogInput
                Font font = new Font("新宋体", Font.PLAIN, 7);
                g2.setFont(font);//设置字体

                float[] dash1 = {2.0f};
                //设置打印线的属性。
                //1.线宽 2、3、不知道，4、空白的宽度，5、虚线的宽度，6、偏移量
                g2.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 2.0f, dash1, 0.0f));

                //二维码信息字符串
                String deviceId = String.valueOf(DEMODTO_LIST.get(COUNT).getMaterialLabelId());

                BufferedImage image = null;
                try {
                    //先创建一个图片编辑器，生成一个二维码，二维码生成图片后再创建另一个图片编辑器，用来编写表格
                    Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
                    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  // 矫错级别
                    QRCodeWriter qrCodeWriter = new QRCodeWriter();
                    //创建比特矩阵(位矩阵)的QR码编码的字符串
                    deviceId = new String(deviceId.getBytes("UTF-8"), "ISO-8859-1");
                    int qrCodeSize = 200;
                    BitMatrix byteMatrix = qrCodeWriter.encode(deviceId, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hintMap);
                    // 使BufferedImage勾画QRCode  (matrixWidth 是行二维码像素点)
                    int matrixWidth = byteMatrix.getWidth();
                    image = new BufferedImage(matrixWidth - 20, matrixWidth - 20, BufferedImage.TYPE_INT_RGB);
                    image.createGraphics();
                    Graphics2D graphics2 = (Graphics2D) image.getGraphics();
                    graphics2.setColor(Color.WHITE);
                    graphics2.fillRect(0, 0, matrixWidth, matrixWidth);
                    // 使用比特矩阵画并保存图像
                    graphics2.setColor(Color.BLACK);
                    for (int i = 0; i < matrixWidth; i++) {
                        for (int j = 0; j < matrixWidth; j++) {
                            if (byteMatrix.get(i, j)) {
                                graphics2.fillRect(i - 10, j - 10, 1, 1);
                            }
                        }
                    }
                    image.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /**
                 * 参数2：打印的x坐标起点         参数3  打印的y坐标起点
                 * 参数4：打印图片的宽度           参数5：打印图片的高度
                 */
                g2.drawImage(image, (int) 120, (int) 70, (int) 66, (int) 66, c);

                String titleStr = "1001";
                String projectNo = "1001";//项目号
                int amount = 123;
                String supplier = "宁德";//供应商
                String spec = "小一整箱";//规格型号
                String name = "电机";//物料名称信息
                String orderNo = "D0001";
                String packedNo = "010101";
                String version = "v2.1";

                // 设置标题的字体,字号,大小
                Font titleFont = new Font("黑体", Font.BOLD, 5);
                FontMetrics fm = g2.getFontMetrics(titleFont);
                int titleWidth = fm.stringWidth("物料标签码");
                int titleWidthX = 120;// 感觉不居中,向左移动35个单位
                g2.setFont(new Font("黑体", Font.BOLD, 10));

                //标题，固定不变
                g2.drawString("物料标签码", titleWidthX, (float) 18);

                int firColumnX = 50;

                // 项目号
                g2.setFont(new Font("黑体", Font.BOLD, 8));
                g2.drawString("项目号：" + DEMODTO_LIST.get(COUNT).getProjectNumber(), firColumnX, 32);
                // 数量
                g2.drawString("数量：" + DEMODTO_LIST.get(COUNT).getHadQty(), firColumnX, 44);
                //供应商
                g2.drawString("供应商：" + DEMODTO_LIST.get(COUNT).getSupplierName(), firColumnX, 54);
                // 规格型号信息
                g2.drawString("规格型号：" + DEMODTO_LIST.get(COUNT).getOriginalModel(), firColumnX, 64);

                int secColumnX = 170;
                // 物料名称信息
                g2.drawString("物料名称：" + DEMODTO_LIST.get(COUNT).getMaterialName(), secColumnX, 32);
                // 订单号
                g2.drawString("订单号：" + DEMODTO_LIST.get(COUNT).getBillNumber(), secColumnX, 44);
                // 版本号
                g2.drawString("版本号：" + DEMODTO_LIST.get(COUNT).getVersion(), secColumnX, 54);
                // 组件号
                g2.drawString("组件号：" + DEMODTO_LIST.get(COUNT).getParentItemId(), secColumnX, 64);

                return PAGE_EXISTS;
            default:
                return NO_SUCH_PAGE;
        }
    }

    /**
     * @Author: Mr.ZJW
     * @Description: 打印
     * @Date: 2022/4/13 18:49
     **/
    public void printMaterial() {
        synchronized (MaterialLablePrinte.class) {
            //    通俗理解就是书、文档
            Book book = new Book();

            //    设置成竖打
            PageFormat pf = new PageFormat();
            pf.setOrientation(PageFormat.PORTRAIT);

            //    通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
            Paper p = new Paper();
            p.setSize(566, 144);//纸张大小
            p.setImageableArea(0, 0, 566, 144);
            pf.setPaper(p);

            //    把 PageFormat 和 Printable 添加到书中，组成一个页面
            book.append(new MaterialLablePrinte(), pf);

            //获取打印服务对象
            PrinterJob job = PrinterJob.getPrinterJob();
            System.out.println("job.getJobName() = " + job.getJobName());

            // 设置打印类
            job.setPageable(book);

            try {
                //直接打印 ,不显示对话框
                if (DEMODTO_LIST.size() > 0) {
                    for (int i = 0; i < DEMODTO_LIST.size(); i++) {
                        job.print();
                        --COUNT;
                    }
                }
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
    }
}
