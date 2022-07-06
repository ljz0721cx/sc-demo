package com.sc.ynk.antiepidemic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Janle on 2021/10/26
 */
public class Antiepidemic {
    public static void main(String[] args) throws IOException {
        /**
         * 要处理的图片目录
         */
        File dir = new File("E:\\a");
        /**
         * 列出目录中的图片，得到数组
         */
        File[] files = dir.listFiles();
        /**
         * 遍历数组
         */
        for (int x = 0; x < files.length; x++) {
            /**
             * 定义一个RGB的数组，因为图片的RGB模式是由三个 0-255来表示的 比如白色就是(255,255,255)
             */
            int[] rgb = new int[3];
            /**
             * 用来处理图片的缓冲流
             */
            BufferedImage bi = null;
            try {
                /**
                 * 用ImageIO将图片读入到缓冲中
                 */
                bi = ImageIO.read(files[x]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            /**
             * 得到图片的长宽
             */
            int width = bi.getWidth();
            int height = bi.getHeight();
            int minx = bi.getMinX();
            int miny = bi.getMinY();
            int count = 0;
            System.out.println("正在处理：" + files[x].getName());
            /**
             * 这里是遍历图片的像素，因为要处理图片的背色，所以要把指定像素上的颜色换成目标颜色 这里 是一个二层循环，遍历长和宽上的每个像素
             */
            for (int i = minx; i < width; i++) {
                for (int j = miny; j < height; j++) {
                    /**
                     * 得到指定像素（i,j)上的RGB值，
                     */
                    int pixel = bi.getRGB(i, j);
                    /**
                     * 分别进行位操作得到 r g b上的值
                     */
                    rgb[0] = (pixel & 0xff0000) >> 16;
                    rgb[1] = (pixel & 0xff00) >> 8;
                    rgb[2] = (pixel & 0xff);
                    /**
                     * 进行换色操作，我这里是要把蓝底/红底换成白底，那么就判断图片中rgb值是否在蓝色范围的像素
                     */

                    if ((rgb[0] < 110 && rgb[2] > 100) || (rgb[0] > 130 && rgb[1] < 40 && rgb[2] < 40)) {

                        /**
                         * 这里是判断通过，则把该像素换成白色
                         */
                        // bi.setRGB(i, j, 0xffffff);
                        count++;
                    }

                }
            }
            if ((float) count / (height * width) > 0.3) {
                System.out.println("============蓝色");
            }
            // System.out.println("\t处理完毕：" + files[x].getName());
            System.out.println();
            /**
             * 将缓冲对象保存到新文件中
             */
            FileOutputStream ops = new FileOutputStream(new File("F:/img/new_" + files[x].getName()));
            ImageIO.write(bi, "jpg", ops);
            ops.flush();
            ops.close();

        }

    }
}
