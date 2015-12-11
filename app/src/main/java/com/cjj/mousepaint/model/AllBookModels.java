package com.cjj.mousepaint.model;

import java.util.ArrayList;
import java.util.List;

/**
 *  {"Id":2,
 "Title":"海贼王",
 "FrontCover":"",
 "RefreshTime":"\/Date(1427198484000)\/",
 "Explain":"还需要介绍吗？我是要成为海贼王的男人！",
 "SerializedState":"连载状态",
 "Author":"作者名",
 "LastChapterNo":0,
 "ClassifyId":0,
 "LastChapter":null}
 {"ErrCode":"","ErrMsg":"","Return":{"List":[{"Id":41,"Title":"镇魂街",
 "FrontCover":"http://img02.ishuhui.com/mhcover/zhhj.jpg","RefreshTime":"\/Date(1442557054000)\/",
 "RefreshTimeStr":"2015-09-18 14:17:34","Explain":"","SerializedState":"未定义","Author":"许辰",
 "LastChapterNo":0,"ClassifyId":2,"LastChapter":null,"Chapters":null,"MoreUrl":null,"Recommend":false,"Copyright":0},
 {"Id":40,"Title":"拜见女皇陛下","FrontCover":"http://img02.ishuhui.com/mhcover/nh.jpg","RefreshTime"
 :"\/Date(1442545127000)\/",
 "RefreshTimeStr":"2015-09-18 10:58:47","Explain":"","SerializedState":"未定义","Author":"ZCloud","LastChapterNo":0,"ClassifyId":2,"LastChapter":{"Id":2975,"Title":"明的计划","Sort":128,"FrontCover":"http://img02.ishuhui.com/mhcover/nh.jpg","Images":null,"RefreshTime":"\/Date(1442545236000)\/","RefreshTimeStr":"2015-09-18","Book":null,"PostUser":"发布者","ChapterNo":128,"Reel":0,"BookId":40,"ChapterType":0,"DownLoadUrl":null,"Copyright":null,"Tencent":null,"ExceptionChapter":null,"CreateTime":"\/Date(-59011459200000)\/"},"Chapters":null,"MoreUrl":"http://www.u17.com/comic/190.html","Recommend":false,"Copyright":0},{"Id":23,"Title":"bleach","FrontCover":"http://img02.ishuhui.com/mhcover/bleach.jpg","RefreshTime":"\/Date(1442478768000)\/","RefreshTimeStr":"2015-09-17 16:32:48","Explain":"如果手上没有剑，我就不能保护你。 如果我一直握着剑，我就无法抱紧你。","SerializedState":"未定义","Author":"久保带人","LastChapterNo":0,"ClassifyId":4,"LastChapter":{"Id":2946,"Title":"baby，hand your hand 6","Sort":643,"FrontCover":"http://img02.ishuhui.com/mhcover/bleach.jpg","Images":null,"RefreshTime":"\/Date(1442424576000)\/","RefreshTimeStr":"2015-09-17","Book":null,"PostUser":"发布者","ChapterNo":643,"Reel":0,"BookId":23,"ChapterType":0,"DownLoadUrl":null,"Copyright":null,"Tencent":null,"ExceptionChapter":null,"CreateTime":"\/Date(-59011459200000)\/"},"Chapters":null,"MoreUrl":null,"Recommend":true,"Copyright":0},{"Id":1,"Title":"名侦探柯南","FrontCover":"http://img02.ishuhui.com/mhcover/kn.jpg?1=2","RefreshTime":"\/Date(1442478749000)\/","RefreshTimeStr":"2015-09-17 16:32:29","Explain":"真相只有一个","SerializedState":"未定义","Author":"青山刚昌","LastChapterNo":0,"ClassifyId":4,"LastChapter":{"Id":2819,"Title":"死靈的弔唁行列","Sort":932,"FrontCover":"http://img02.ishuhui.com/mhcover/kn.jpg?1=2","Images":null,"RefreshTime":"\/Date(1442272784000)\/","RefreshTimeStr":"2015-09-15","Book":null,"PostUser":"发布者","ChapterNo":932,"Reel":0,"BookId":1,"ChapterType":0,"DownLoadUrl":null,"Copyright":null,"Tencent":null,"ExceptionChapter":null,"CreateTime":"\/Date(-59011459200000)\/"},"Chapters":null,"MoreUrl":null,"Recommend":false,"Copyright":0},{"Id":2,"Title":"海贼王","FrontCover":"http://img01.ishuhui.com/mhcover/op.jpg","RefreshTime":"\/Date(1442478715000)\/","RefreshTimeStr":"2015-09-17 16:31:55","Explain":"人的梦想是不会完结的！","SerializedState":"未定义","Author":"尾田荣一郎","LastChapterNo":0,"ClassifyId":3,"LastChapter":{"Id":2894,"Title":"义子之酒","Sort":800,"FrontCover":"http://img01.ishuhui.com/op/he800/ops.png","Images":null,"RefreshTime":"\/Date(1442381364000)\/","RefreshTimeStr":"2015-09-16","Book":null,"PostUser":"发布者","ChapterNo":800,"Reel":0,"BookId":2,"ChapterType":0,"DownLoadUrl":null,"Copyright":null,"Tencent":null,"ExceptionChapter":null,"CreateTime":"\/Date(-59011459200000)\/"},"Chapters":null,"MoreUrl":null,"Recommend":false,"Copyright":1},{"Id":4,"Title":"火影忍者","FrontCover":"http://img02.ishuhui.com/mhcover/naruto.jpg","RefreshTime":"\/Date(1442478695000)\/","RefreshTimeStr":"2015-09-17 16:31:35","Explain":"只要有树叶飞舞的地方，火就会燃烧！","SerializedState":"未定义","Author":"岸本齐史","LastChapterNo":0,"ClassifyId":3,"LastChapter":{"Id":1433,"Title":"火影忍者710话","Sort":710,"FrontCover":"http://img02.ickeep.com/hy/end710/hys.jpg","Images":null,"RefreshTime":"\/Date(1438269981000)\/","RefreshTimeStr":"2015-07

 */
public class AllBookModels {
    public ReturnClazz Return;

    public class ReturnClazz
    {
        public ArrayList<AllBook> List;

        public class AllBook
        {
            public String Id;
            public String Title;
            public String FrontCover;
            public String RefreshTime;
            public String Explain;
            public String SerializedState;
            public String Author;
            public String LastChapterNo;
            public String ClassifyId;
            public Chapter LastChapter;

            public class Chapter
            {
                public String Id;
                public String Title;
                public String FrontCover;
                public String Sort;
                public String Images;
                public String RefreshTimeStr;
                public String ChapterNo;
                public String LastChapterNo;
                public String BookId;
            }

        }


    }


}
