package com.cjj.mousepaint.model;

import java.util.List;

/**
 * Created by Administrator on 2015/11/2.
 */
public class AdvModel {

    /**
     * Id : 1
     * title : 《海贼王》1-20卷日版封面，高清大图分享
     * Img : http://img02.ishuhui.com/guanggao/app3.jpg
     * Link : http://www.ishuhui.net/CMS/648
     */

    public List<ListEntity> list;

    public static class ListEntity {
        public int Id;
        public String title;
        public String Img;
        public String Link;
    }
}
