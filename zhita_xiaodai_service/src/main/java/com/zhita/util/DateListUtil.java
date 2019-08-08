package com.zhita.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.chainsaw.Main;

import com.zhita.model.manage.HomepageTongji;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.TongjiSorce;

public class DateListUtil {
	
	//获取两个日期之间的所有日期
	public static List<String> getDays(String startTime, String endTime) {
        // 返回的日期集合
        List<String> dayslist = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                dayslist.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayslist;
    }
	
	//获取两个list的不同元素
    public static List<String> getDiffrent2(List<String> list1, List<String> list2) {
        long st = System.nanoTime();
        List<String> diff = new ArrayList<String>();
        List<String> maxList = list1;
        List<String> minList = list2;
        if(list2.size()>list1.size())
        {
            maxList = list2;
            minList = list1;
        }
        Map<String,Integer> map = new HashMap<String,Integer>(maxList.size());
        for (String string : maxList) {
            map.put(string, 1);
        }
        for (String string : minList) {
            if(map.get(string)!=null)
            {
                map.put(string, 2);
                continue;
            }
            diff.add(string);
        }
        for(Map.Entry<String, Integer> entry:map.entrySet())
        {
            if(entry.getValue()==1)
            {
                diff.add(entry.getKey());
            }
        }
       System.out.println("getDiffrent5 total times "+(System.nanoTime()-st));
       return diff;

    }
    
    //将集合按照集合里对象的日期属性进行正排序
/*    public static void ListSort(List<JiaFangTongji> list) {
        Collections.sort(list, new Comparator<JiaFangTongji>() {
            @Override
            public int compare(JiaFangTongji o1, JiaFangTongji o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date dt1 = format.parse(o1.getDate());
                    Date dt2 = format.parse(o2.getDate());
                    if (dt1.getTime() > dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }*/
    
    
    //将集合按照集合里对象的日期属性进行倒排序
    public static void ListSort1(List<TongjiSorce> list) {
        Collections.sort(list, new Comparator<TongjiSorce>() {
            @Override
            public int compare(TongjiSorce o1, TongjiSorce o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date dt1 = format.parse(o1.getDate());
                    Date dt2 = format.parse(o2.getDate());
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
    
    //将集合按照集合里对象的日期属性进行倒排序
    public static void ListSort2(List<Orders> list) {
        Collections.sort(list, new Comparator<Orders>() {
            @Override
            public int compare(Orders o1, Orders o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = format.parse(o1.getOrderCreateTime());
                    Date dt2 = format.parse(o2.getOrderCreateTime());
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
    
    //将集合按照集合里对象的日期属性进行倒排序
    public static void ListSort3(List<HomepageTongji> list) {
        Collections.sort(list, new Comparator<HomepageTongji>() {
            @Override
            public int compare(HomepageTongji o1, HomepageTongji o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date dt1 = format.parse(o1.getShouldtime());
                    Date dt2 = format.parse(o2.getShouldtime());
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
    
    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
    
    
    public static void main(String[] args) {
		List<String> times = DateListUtil.getDays("2019-01-01", "2019-01-05");
		for(int i=0;i<times.size();i++){
			System.out.println(times.get(i));
		}
	}
}
