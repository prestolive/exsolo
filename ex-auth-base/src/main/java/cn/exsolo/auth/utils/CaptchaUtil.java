package cn.exsolo.auth.utils;

import cn.exsolo.auth.vo.CaptchaCheckVO;
import cn.exsolo.batis.core.utils.GenerateID;
import cn.hutool.core.codec.Base64;
import com.github.botaruibo.xvcode.generator.XRandoms;
import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Random;

/**
 * Created by wuby on 2018/4/4.
 *
 * @author prestolive
 */
public class CaptchaUtil {

    private float bkAlpha = 0.9F;
    private float fontAlpha = 0.7F;
    private float ovalSize = 4.0F;
    private int ovalCount = 5;
    protected Font font = new Font("Verdana", 3, 28);
    private int width = 160;
    private int height = 40;
    private int degreePeriod = 12;

    public CaptchaUtil(int width, int height, int degreePeriod) {
        this.width = width;
        this.height = height;
        this.degreePeriod = degreePeriod;
    }

    protected Color color(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }

        int r = fc + XRandoms.num(bc - fc);
        int g = fc + XRandoms.num(bc - fc);
        int b = fc + XRandoms.num(bc - fc);
        return new Color(r, g, b);
    }

    public BufferedImage getImage(char[] strs) {
        BufferedImage bi = new BufferedImage(width, height, 1);
        Graphics2D g2d = (Graphics2D) bi.getGraphics();
        int len = strs.length;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setStroke(new BasicStroke(ovalSize));
        AlphaComposite ac3 = AlphaComposite.getInstance(3, bkAlpha);
        g2d.setComposite(ac3);

        Color color;
        int h;
        for (h = 0; h < ovalCount; ++h) {
            color = color(150, 250);
            g2d.setColor(color);
            g2d.drawOval(XRandoms.num(width), XRandoms.num(height), 10 + XRandoms.num(10), 10 + XRandoms.num(10));
        }

        g2d.setFont(font);
        ac3 = AlphaComposite.getInstance(3, fontAlpha);
        g2d.setComposite(ac3);
        h = height - (height - font.getSize() >> 1);
        int w = width / len - 1;
        int size = w - font.getSize() + 2;

        for (int i = 0; i < len; ++i) {
            color = new Color(20 + XRandoms.num(110), 20 + XRandoms.num(110), 20 + XRandoms.num(110));
            g2d.setColor(color);
            int degree = XRandoms.num(degreePeriod);
            degree = XRandoms.num(2) == 0 ? -degree : degree;
            g2d.rotate(Math.toRadians((double) degree), (double) (width - (len - i) * w + w / 2), (double) (height / 2 + 2));
            g2d.drawString(strs[i] + "", width - (len - i) * w + size, h - 4);
            g2d.rotate(-Math.toRadians((double) degree), (double) (width - (len - i) * w + w / 2), (double) (height / 2 + 2));
        }

        g2d.dispose();
        return bi;
    }


    private static String STRING_RANDBEAN = "1234578123457812345781234578ABCDEFGHKMNPQRTXabcdefghkmnx";

    private static Pair<String, String> getRandomString(int num) {
        String re = "";
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            int randIdx = random.nextInt(STRING_RANDBEAN.length());
            re += String.valueOf(STRING_RANDBEAN.charAt(randIdx));
        }
        return Pair.of(re, re);
    }

    public static Pair<String, String> getRandomMath() {
        Random r = new Random();

        int mode = r.nextInt(99) % 4;
//        int mode = 3;
        //集中模式 全加、加减、先乘后加、先乘后减、
        if (mode == 0) {
            int value = r.nextInt(60);
            int value1, value2, value3;
            value2 = value == 0 ? 0 : r.nextInt(value);
            value3 = (value - value2) == 0 ? 0 : r.nextInt(value - value2);
            value1 = value - value2 - value3;
            return Pair.of(value1 + "+" + value2 + "+" + value3 + "=?", value + "");
        } else if (mode == 1) {
            int value = r.nextInt(45);
            int value1, value2, value3;
            value2 = value == 0 ? 0 : r.nextInt(value);
            value3 = (value - value2) == 0 ? 0 : r.nextInt(value - value2);
            value1 = value - value2 + value3;
            return Pair.of(value1 + "+" + value2 + "-" + value3 + "=?", value + "");
        } else if (mode == 2) {
            int value1 = r.nextInt(8) + 1;
            int value2 = r.nextInt(12) + 1;
            int value3 = r.nextInt(10);
            int value = value1 * value2 + value3;
            return Pair.of(value1 + "×" + value2 + "+" + value3 + "=?", value + "");
        } else if (mode == 3) {
            int value1 = r.nextInt(20) + 1;
            int value2 = r.nextInt(5) + 1;
            value1 = value1 * value2;
            int value3 = r.nextInt(10);
            int value = value1 / value2 + value3;
            return Pair.of(value1 + "÷" + value2 + "+" + value3 + "=?", value + "");
        }

        return null;

    }

    public static void main(String[] args) {
        Pair pair = CaptchaUtil.getRandomMath();
        System.out.println(pair.getLeft());
        System.out.println(pair.getRight());
    }

    public static CaptchaCheckVO generateCaptchaCheckVO() {
        CaptchaCheckVO vo = new CaptchaCheckVO();
        boolean mathMode = new Random().nextInt(999) % 5 == 1;
        BufferedImage bi;
        Pair pair;
        if (mathMode) {
            pair = getRandomMath();
            bi = new CaptchaUtil(140, 40, 8).getImage(pair.getLeft().toString().toCharArray());
        } else {
            pair = getRandomString(4);
            bi = new CaptchaUtil(140, 40, 36).getImage(pair.getLeft().toString().toCharArray());
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "png", stream);
            String base64 = "data:image/png;base64," + URLEncoder.encode(Base64.encode(stream.toByteArray()), "UTF-8");
            vo.setCaptchaImageBase64(base64);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        vo.setCaptchaValue(pair.getRight().toString());
        vo.setTicket(GenerateID.generateShortUuid());
        return vo;

    }

}
