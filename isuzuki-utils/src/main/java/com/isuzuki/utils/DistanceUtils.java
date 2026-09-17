package com.isuzuki.utils;

import org.apache.commons.lang3.StringUtils;

import java.awt.geom.Point2D;
import java.math.BigDecimal;

/**
 * 教材书中的规范是纬度在前，经度在后，中间用逗号隔开
 * N北纬(+90)  S南纬(-90)
 * E东经(+180) W西经(-180)
 * @author :
 * @date : 2021/11/21
 * @description :
 * @see {https://blog.csdn.net/jk940438163/article/details/83147557}
 * @see {百度查看经纬度 http://api.map.baidu.com/lbsapi/getpoint/index.html}
 *
 */
public class DistanceUtils {
    // 平均半径,单位：m
    private static final double EARTH_RADIUS = 6371393;

    /**
     * 单位: 米
     * @param lng1 经度
     * @param lat1 维度
     * @param lng2 经度
     * @param lat2 维度
     * @return
     */
    public static double getDistance(String lng1, String lat1, String lng2, String lat2) {
        if (StringUtils.isBlank(lat1) || StringUtils.isBlank(lng1) || StringUtils.isBlank(lat2) || StringUtils.isBlank(lng2)) {
            return 0.0;
        }
        return getDistance(Double.valueOf(lng1), Double.valueOf(lat1), Double.valueOf(lng2), Double.valueOf(lat2));
    }

    /**
     * 单位: 米
     * @param lng1 经度
     * @param lat1 维度
     * @param lng2 经度
     * @param lat2 维度
     * @return
     */
    public static double getDistance(Double lng1, Double lat1, Double lng2, Double lat2) {
        return getDistanceV1(new Point2D.Double(lng1, lat1), new Point2D.Double(lng2, lat2));
    }

    private static double getDistanceV1(Point2D pointA, Point2D pointB) {
        double radiansAX = Math.toRadians(pointA.getX());
        double radiansAY = Math.toRadians(pointA.getY());

        double radiansBX = Math.toRadians(pointB.getX());
        double radiansBY = Math.toRadians(pointB.getY());

        double cos = (Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)) + (Math.sin(radiansAY) * Math.sin(radiansBY));

        double acos = Math.acos(cos);

        return new BigDecimal(EARTH_RADIUS * acos).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double getDistanceV2(Double e1, Double n1, Double e2, Double n2) {
        double lat1 = (Math.PI / 180) * e1;
        double lat2 = (Math.PI / 180) * e2;

        double lon1 = (Math.PI / 180) * n1;
        double lon2 = (Math.PI / 180) * n2;

        double R = 6371;

        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;
        return d * 1000;
    }

}
