package com.sc.ynk.pint;

import java.awt.*;
import java.awt.print.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Janle on 2022/11/18
 */
public class PrintDemo implements Printable {

    private static int COUNT = 0;                   //待打印数据的条数，此变量需定义在数据集合之前

    private static List<DemoDto> DEMODTO_LIST = getDemoDto();  //待打印的文字数据

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        System.out.println("-----------------执行第" + (COUNT + 1) + "次打印-------------------");

        System.out.println("pageIndex = " + pageIndex);

        Component c = null;

        //转换成Graphics2D
        Graphics2D g2 = (Graphics2D) graphics;

        //设置打印颜色为黑色
        g2.setColor(Color.black);

        //打印起点坐标
        double x = pageFormat.getImageableX();
        double y = pageFormat.getImageableY();

        System.out.println("起点坐标x=" + x + ";y=" + y);


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

                //获取需要打印的图片，若是动态生成，直接传入绝对路径即可
                Image src = Toolkit.getDefaultToolkit().getImage("C:\\Users\\j_jan\\Desktop\\aa\\bbb.png");
                if (src == null) {
                    System.out.println("没有找到图像");
                }

                /**
                 * 参数2：打印的x坐标起点         参数3  打印的y坐标起点
                 * 参数4：打印图片的宽度           参数5：打印图片的高度
                 */
                g2.drawImage(src, (int) 80, (int) 15, (int) 48, (int) 48, c);

                //标题，固定不变
                g2.drawString(DEMODTO_LIST.get(COUNT).getTitle(), (float) 30, (float) 18);

                //以下为动态的文字内容
                font = new Font("新宋体", Font.PLAIN, 5);
                g2.setFont(font);
                g2.drawString("资产名称：" + DEMODTO_LIST.get(COUNT).getAssetName(), (float) 10, (float) 30);
                g2.drawString("型    号：" + DEMODTO_LIST.get(COUNT).getAssetType(), (float) 10, (float) 40);
                g2.drawString("部    门：" + DEMODTO_LIST.get(COUNT).getDeptName(), (float) 10, (float) 50);
                g2.drawString("责 任 人：" + DEMODTO_LIST.get(COUNT).getResponsible(), (float) 10, (float) 60);

                System.out.println("-----------------打印成功-------------------");
                return PAGE_EXISTS;

            default:
                return NO_SUCH_PAGE;

        }
    }

    /**
     * 封装测试数据
     *
     * @return
     */
    private static List<DemoDto> getDemoDto() {
        List<DemoDto> dtos = new ArrayList<DemoDto>();
        dtos.add(new DemoDto("戒毒所打印机01", "惠普打印机", "技术支持部", "责任人01", new Date(), "C:\\Users\\j_jan\\Desktop\\aa\\货位码.png", "宝丰戒毒所"));
        if (dtos.size() > 0) {
            COUNT = dtos.size() - 1;
            System.out.println("总共需打印" + (COUNT + 1) + "次");
        }
        return dtos;
    }

    public static void main(String[] args) {
        //    通俗理解就是书、文档
        Book book = new Book();

        //    设置成竖打
        PageFormat pf = new PageFormat();
        pf.setOrientation(PageFormat.PORTRAIT);

        //    通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
        Paper p = new Paper();
        p.setSize(142, 82);//纸张大小
        p.setImageableArea(10, 10, 142, 70);
        pf.setPaper(p);

        //    把 PageFormat 和 Printable 添加到书中，组成一个页面
        book.append(new PrintDemo(), pf);

    }
}
