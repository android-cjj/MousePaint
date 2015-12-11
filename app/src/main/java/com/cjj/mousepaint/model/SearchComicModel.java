package com.cjj.mousepaint.model;

import java.util.List;

/**
 * Created by Administrator on 2015/11/9.
 */
public class SearchComicModel {

    /**
     * ErrCode :
     * ErrMsg :
     * Return : {"List":[{"Id":4,"Title":"火影忍者","FrontCover":"http://img02.ishuhui.com/mhcover/naruto.jpg","RefreshTime":"/Date(1442557829000)/","RefreshTimeStr":"2015-09-18 14:30:29","Explain":"只要有树叶飞舞的地方，火就会燃烧！","SerializedState":"已完结","Author":"岸本齐史","LastChapterNo":0,"ClassifyId":3,"LastChapter":{"Id":1433,"Title":"火影忍者710话","Sort":710,"FrontCover":"http://img02.ickeep.com/hy/end710/hys.jpg","Images":null,"RefreshTime":"/Date(1438269981000)/","RefreshTimeStr":"2015-07-30","Book":null,"PostUser":"发布者","ChapterNo":710,"Reel":0,"BookId":4,"ChapterType":0,"DownLoadUrl":null,"Copyright":null,"Tencent":null,"ExceptionChapter":null,"CreateTime":"/Date(-59011459200000)/"},"Chapters":null,"MoreUrl":null,"Recommend":false,"Copyright":1}],"ParentItem":null,"ListCount":1,"PageSize":30,"PageIndex":0}
     * Costtime : 0
     * IsError : false
     * Message : 请求参数:{"Id":null,"Favorites":false,"Days":0,"Recommended":0,"Sort":0,"ClassifyId":0,"Title":"火影","Size":30,"PageIndex":0,"Subscribe":0},排序参数:0
     */

    public String ErrCode;
    public String ErrMsg;
    /**
     * List : [{"Id":4,"Title":"火影忍者","FrontCover":"http://img02.ishuhui.com/mhcover/naruto.jpg","RefreshTime":"/Date(1442557829000)/","RefreshTimeStr":"2015-09-18 14:30:29","Explain":"只要有树叶飞舞的地方，火就会燃烧！","SerializedState":"已完结","Author":"岸本齐史","LastChapterNo":0,"ClassifyId":3,"LastChapter":{"Id":1433,"Title":"火影忍者710话","Sort":710,"FrontCover":"http://img02.ickeep.com/hy/end710/hys.jpg","Images":null,"RefreshTime":"/Date(1438269981000)/","RefreshTimeStr":"2015-07-30","Book":null,"PostUser":"发布者","ChapterNo":710,"Reel":0,"BookId":4,"ChapterType":0,"DownLoadUrl":null,"Copyright":null,"Tencent":null,"ExceptionChapter":null,"CreateTime":"/Date(-59011459200000)/"},"Chapters":null,"MoreUrl":null,"Recommend":false,"Copyright":1}]
     * ParentItem : null
     * ListCount : 1
     * PageSize : 30
     * PageIndex : 0
     */

    public ReturnEntity Return;
    public String Costtime;
    public boolean IsError;
    public String Message;

    public static class ReturnEntity {
        public Object ParentItem;
        public int ListCount;
        public int PageSize;
        public int PageIndex;
        /**
         * Id : 4
         * Title : 火影忍者
         * FrontCover : http://img02.ishuhui.com/mhcover/naruto.jpg
         * RefreshTime : /Date(1442557829000)/
         * RefreshTimeStr : 2015-09-18 14:30:29
         * Explain : 只要有树叶飞舞的地方，火就会燃烧！
         * SerializedState : 已完结
         * Author : 岸本齐史
         * LastChapterNo : 0
         * ClassifyId : 3
         * LastChapter : {"Id":1433,"Title":"火影忍者710话","Sort":710,"FrontCover":"http://img02.ickeep.com/hy/end710/hys.jpg","Images":null,"RefreshTime":"/Date(1438269981000)/","RefreshTimeStr":"2015-07-30","Book":null,"PostUser":"发布者","ChapterNo":710,"Reel":0,"BookId":4,"ChapterType":0,"DownLoadUrl":null,"Copyright":null,"Tencent":null,"ExceptionChapter":null,"CreateTime":"/Date(-59011459200000)/"}
         * Chapters : null
         * MoreUrl : null
         * Recommend : false
         * Copyright : 1
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
             * Id : 1433
             * Title : 火影忍者710话
             * Sort : 710
             * FrontCover : http://img02.ickeep.com/hy/end710/hys.jpg
             * Images : null
             * RefreshTime : /Date(1438269981000)/
             * RefreshTimeStr : 2015-07-30
             * Book : null
             * PostUser : 发布者
             * ChapterNo : 710
             * Reel : 0
             * BookId : 4
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
