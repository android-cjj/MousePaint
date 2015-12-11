package com.cjj.mousepaint.model;

import java.util.List;

/**
 * Created by Administrator on 2015/11/4.
 */
public class CategoryModel {


    /**
     * ErrCode :
     * ErrMsg :
     * Return : {"List":[{"Id":27,"Title":"一拳超人","FrontCover":"http://img02.ishuhui.com/mhcover/yiquan.jpg","RefreshTime":"/Date(1446399131000)/","RefreshTimeStr":"2015-11-02 01:32:11","Explain":"明天或以后的你难道一辈子都无法战胜今天的你吗？有意志消沉的功夫你还是选择继续前进比较好。","SerializedState":"未定义","Author":"村田雄介","LastChapterNo":0,"ClassifyId":4,"LastChapter":{"Id":4039,"Title":"横枪","Sort":82,"FrontCover":"http://img02.ishuhui.com/mhcover/yiquan.jpg","Images":null,"RefreshTime":"/Date(1446399131000)/","RefreshTimeStr":"2015-11-02","Book":null,"PostUser":"发布者","ChapterNo":82,"Reel":0,"BookId":27,"ChapterType":0,"DownLoadUrl":null,"Copyright":null,"Tencent":null,"ExceptionChapter":null,"CreateTime":"/Date(-59011459200000)/"},"Chapters":null,"MoreUrl":null,"Recommend":false,"Copyright":0},{"Id":36,"Title":"魔笛","FrontCover":"http://img01.ishuhui.com/mhcover/modi.jpg","RefreshTime":"/Date(1446398842000)/","RefreshTimeStr":"2015-11-02 01:27:22","Explain":"朋友，你是要跟我走一辈子的人，即使不在路上，你也永远住在我心里。","SerializedState":"未定义","Author":"大高忍","LastChapterNo":0,"ClassifyId":4,"LastChapter":{"Id":4038,"Title":"世界的变化","Sort":284,"FrontCover":"http://img01.ishuhui.com/mhcover/modi.jpg","Images":null,"RefreshTime":"/Date(1446398842000)/","RefreshTimeStr":"2015-11-02","Book":null,"PostUser":"发布者","ChapterNo":284,"Reel":0,"BookId":36,"ChapterType":0,"DownLoadUrl":null,"Copyright":null,"Tencent":null,"ExceptionChapter":null,"CreateTime":"/Date(-59011459200000)/"},"Chapters":null,"MoreUrl":null,"Recommend":true,"Copyright":0}]}
     */

    public String ErrCode;
    public String ErrMsg;
    public ReturnEntity Return;

    public static class ReturnEntity {
        /**
         * Id : 27
         * Title : 一拳超人
         * FrontCover : http://img02.ishuhui.com/mhcover/yiquan.jpg
         * RefreshTime : /Date(1446399131000)/
         * RefreshTimeStr : 2015-11-02 01:32:11
         * Explain : 明天或以后的你难道一辈子都无法战胜今天的你吗？有意志消沉的功夫你还是选择继续前进比较好。
         * SerializedState : 未定义
         * Author : 村田雄介
         * LastChapterNo : 0
         * ClassifyId : 4
         * LastChapter : {"Id":4039,"Title":"横枪","Sort":82,"FrontCover":"http://img02.ishuhui.com/mhcover/yiquan.jpg","Images":null,"RefreshTime":"/Date(1446399131000)/","RefreshTimeStr":"2015-11-02","Book":null,"PostUser":"发布者","ChapterNo":82,"Reel":0,"BookId":27,"ChapterType":0,"DownLoadUrl":null,"Copyright":null,"Tencent":null,"ExceptionChapter":null,"CreateTime":"/Date(-59011459200000)/"}
         * Chapters : null
         * MoreUrl : null
         * Recommend : false
         * Copyright : 0
         */

        public java.util.List<ListEntity> List;

        public static class ListEntity {
            public int Id;
            public String Title;
            public String FrontCover;
            public String RefreshTime;
            public String RefreshTimeStr;
            public String Explain;
            public String SerializedState;
            public String Author;
            public int LastChapterNo;
            public int ClassifyId;
            /**
             * Id : 4039
             * Title : 横枪
             * Sort : 82
             * FrontCover : http://img02.ishuhui.com/mhcover/yiquan.jpg
             * Images : null
             * RefreshTime : /Date(1446399131000)/
             * RefreshTimeStr : 2015-11-02
             * Book : null
             * PostUser : 发布者
             * ChapterNo : 82
             * Reel : 0
             * BookId : 27
             * ChapterType : 0
             * DownLoadUrl : null
             * Copyright : null
             * Tencent : null
             * ExceptionChapter : null
             * CreateTime : /Date(-59011459200000)/
             */

            public LastChapterEntity LastChapter;
            public Object Chapters;
            public Object MoreUrl;
            public boolean Recommend;
            public int Copyright;

            public static class LastChapterEntity {
                public int Id;
                public String Title;
                public int Sort;
                public String FrontCover;
                public Object Images;
                public String RefreshTime;
                public String RefreshTimeStr;
                public Object Book;
                public String PostUser;
                public int ChapterNo;
                public int Reel;
                public int BookId;
                public int ChapterType;
                public Object DownLoadUrl;
                public Object Copyright;
                public Object Tencent;
                public Object ExceptionChapter;
                public String CreateTime;
            }
        }
    }
}
