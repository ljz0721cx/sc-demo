package com.sc.ynk.pint;

import gui.ava.html.parser.HtmlParser;
import gui.ava.html.parser.HtmlParserImpl;
import gui.ava.html.renderer.ImageRenderer;
import gui.ava.html.renderer.ImageRendererImpl;

import javax.imageio.ImageIO;
import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.math.BigDecimal;
import java.net.URL;

/**
 * @author Janle on 2022/11/18
 */
public class Html {
    protected static void generateOutput() throws Exception {
        String  aa="100";
        System.out.println(Double.parseDouble(aa));
       /* JEditorPane ed = new JEditorPane(new URL("https://baijiahao.baidu.com/s?id=1749825322948786717"));
        ed.setSize(2000, 2000);

        //create a new image
        BufferedImage image = new BufferedImage(ed.getWidth(), ed.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        //paint the editor onto the image
        SwingUtilities.paintComponent(image.createGraphics(),
                ed,
                new JPanel(),
                0, 0, image.getWidth(), image.getHeight());
        //save the image to file
        ImageIO.write((RenderedImage) image, "png", new File("C:\\Users\\j_jan\\Desktop\\aa\\html.png"));


        String HtmlTemplateStr = "  ";
        HtmlParser htmlParser = new HtmlParserImpl();
        htmlParser.loadHtml(HtmlTemplateStr);
        // html 是我的html代码
        ImageRenderer imageRenderer = new ImageRendererImpl(htmlParser);
        imageRenderer.saveImage("C:\\Users\\j_jan\\Desktop\\aa\\lcxq1.png");*/
    }

    public static void main(String[] args) {
        try {
            generateOutput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
