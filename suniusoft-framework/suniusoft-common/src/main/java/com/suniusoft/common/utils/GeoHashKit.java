package com.suniusoft.common.utils;

/**
 *   
 *  @ProjectName: arcms  
 *  @Description: GeoHash经纬转换，经纬间距离
 *  @author zoujian  zoujian@suniusoft.com
 *  @date 15/11/25 下午9:01  
 */

import java.util.HashMap;
import java.util.Map;

public class GeoHashKit {

    private static char[] base32 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private final static Map<Character, Integer> decodemap = new HashMap<Character, Integer>();

    static {
        int sz = base32.length;
        for (int i = 0; i < sz; i++) {
            decodemap.put(base32[i], i);
        }
    }

    private static int precision = 10;
    private static int[] bits = {16, 8, 4, 2, 1};

    /**
     * 设置精度
     *
     * @param precision 设置精确位数，此参数决定了该算法的经度
     */
    public static void setPrecision(int precision) {
        GeoHashKit.precision = precision;
    }

    public static double getPrecision(double x, double precision) {
        double base = Math.pow(10, -precision);
        double diff = x % base;
        return x - diff;
    }


    public static String encode(double latitude, double longitude) {
        double[] lat_interval = {-90.0, 90.0};
        double[] lon_interval = {-180.0, 180.0};
        StringBuilder geohash = new StringBuilder();
        boolean is_even = true;
        int bit = 0, ch = 0;
        while (geohash.length() < precision) {
            double mid = 0.0;
            if (is_even) {
                mid = (lon_interval[0] + lon_interval[1]) / 2;
                if (longitude > mid) {
                    ch |= bits[bit];
                    lon_interval[0] = mid;
                } else {
                    lon_interval[1] = mid;
                }
            } else {
                mid = (lat_interval[0] + lat_interval[1]) / 2;
                if (latitude > mid) {
                    ch |= bits[bit];
                    lat_interval[0] = mid;
                } else {
                    lat_interval[1] = mid;
                }
            }
            is_even = is_even ? false : true;

            if (bit < 4) {
                bit++;
            } else {
                geohash.append(base32[ch]);
                bit = 0;
                ch = 0;
            }
        }
        return geohash.toString();
    }

    public static double[] decode(String geohash) {
        double[] ge = decode_exactly(geohash);
        double lat, lon, lat_err, lon_err;
        lat = ge[0];
        lon = ge[1];
        lat_err = ge[2];
        lon_err = ge[3];
        double lat_precision = Math.max(1, Math.round(-Math.log10(lat_err))) - 1;
        double lon_precision = Math.max(1, Math.round(-Math.log10(lon_err))) - 1;
        lat = getPrecision(lat, lat_precision);
        lon = getPrecision(lon, lon_precision);
        return new double[]{lat, lon};
    }

    public static double[] decode_exactly(String geohash) {
        double[] lat_interval = {-90.0, 90.0};
        double[] lon_interval = {-180.0, 180.0};
        double lat_err = 90.0;
        double lon_err = 180.0;
        boolean is_even = true;
        int sz = geohash.length();
        int bsz = bits.length;
        double latitude, longitude;
        for (int i = 0; i < sz; i++) {
            int cd = decodemap.get(geohash.charAt(i));
            for (int z = 0; z < bsz; z++) {
                int mask = bits[z];
                if (is_even) {
                    lon_err /= 2;
                    if ((cd & mask) != 0) {
                        lon_interval[0] = (lon_interval[0] + lon_interval[1]) / 2;
                    } else {
                        lon_interval[1] = (lon_interval[0] + lon_interval[1]) / 2;
                    }
                } else {
                    lat_err /= 2;

                    if ((cd & mask) != 0) {
                        lat_interval[0] = (lat_interval[0] + lat_interval[1]) / 2;
                    } else {
                        lat_interval[1] = (lat_interval[0] + lat_interval[1]) / 2;
                    }
                }
                is_even = is_even ? false : true;
            }
        }
        latitude = (lat_interval[0] + lat_interval[1]) / 2;
        longitude = (lon_interval[0] + lon_interval[1]) / 2;
        return new double[]{latitude, longitude, lat_err, lon_err};
    }

    private static final double EARTH_RADIUS = 6378137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算两个经纬度之间的距离（单位米）
     *
     * @param lng1 位置1的经度
     * @param lat1 位置1的维度
     * @param lng2 位置2的经度
     * @param lat2 位置2的维度
     * @return 距离（单位米）
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;

    }


    public static void main(String[] args) {
        GeoHashKit ghf = new GeoHashKit();
        String gc1 = ghf.encode(30.1110000, 120.1110000);
        String gc2 = ghf.encode(30.021, 119.621);

        System.out.println(gc1.substring(0,2));
        System.out.println(gc2);

        double[] gd1 = ghf.decode(gc1);
        double[] gd2 = ghf.decode(gc2);
        System.out.println(gd1[0] + ", " + gd1[1]);
        System.out.println(gd2[0] + ", " + gd2[1]);

        double d = getDistance(gd1[0], gd1[1], gd2[0], gd2[1]);
        System.out.print(d + "");
    }
}
