# lab4新接口

#### 1.获得所有教学楼及其所属教室

> /admin/allClassrooms

同样记得在Headers中携带token即可

请求体可以为空

==后端==，相关Service未开发。注意直接给一个==**BuildAndClassroomsData**==类型的**List**作为结果

==前端==，记得拿去用，主要是用在添加课程的时候，选择对应的教室

![allClassrooms.png](https://s2.loli.net/2022/04/28/pLGzbKYqF8iHhT5.png)

#### 2.1获得学生/老师的信息

>/admin/findStudentPage
>
>/admin/findTeacherPage

==后端==，**学生**添加 grade 和 registerTime。**老师** 添加 registerTime

==前端==，查看所有**学生**界面添加两列“年级”和“注册时间”，查看所有**老师**界面添加一列“注册时间”，从后端分页查询获得的变量名如上。

#### 2.2注册学生和老师

==后端==：根据系统时间判断这是哪一年，自动补上registerTime，有年月日即可，学生的话要额外自动补上年级grade。

前端不需要改

#### 2.3管理员修改学生或者老师的信息

==前端==：注册时间是不可以修改的！年级是可以修改的，考虑做成一个下拉框，直接写死好了，简单一点只有2020级和2021级。反正这个地方不涉及CSV。

==后端==：老师修改信息的Service函数不需要修改，学生修改信息的Service会多一个年级的数据。

#### 3.添加(最后一节)上课时间段

> /admin/addTime

我们对课程的管理默认使用栈的结构，添加课程的时候只会**添加最后一节课**，删除课程的时候也是**删除最后一节课**。

==前端==，addTime时我们只需要开始时间和结束时间，不需要多余的TimeName(后端会自动生成)，时间能不能搞成一个点击上下调整的东西或者让用户选，尽量保证格式为08:00这样，中间是英文的冒号

==后端==，1.格式检查，且开始时间早于结束时间。2.数字的栈式管理。3.检查相邻两个上课时间段是否有重合。

#### 4.1删除(最后一节)上课时间段

> /admin/deleteTime

前端不需要参数，直接访问接口即可。

==后端==，检查有没有课在这个时间段，有的话要抛出异常说明，以及栈是否为空。

#### 4.2获得所有上课时间段(分页查询)

>/admin/findTimePage

这个同以前一样的，只不过TimeName是我们后端自动生成的第几节了。

==后端==，直接把数字给Controller层即可，Controller层进行包装再传给前端。

#### 4.3获得所有上课时间段

> /common/allTime

相比于前者，就是没有分页了，直接就是一个数组

#### 5对教室的增删改查

> /admin/addClassroom
>
> /admin/updateBuildingInfo
>
> /admin/deleteClassroom

==前端==：记得多给一个capacity属性，表示容量，要求是一个正数。

==后端==：数据库记得改,teachingAffairsService.insertClassroom记得多要一个capacity参数。delete的时候看看有没有课在这里上。update的时候要看看在这上课的课程的容量设计。**修改教室容量时的检查可以放到后面能添加课程了再检查**



#### 6.获得某个教室的所有可用时间段信息

> /admin/getClassroomSpareTime

==前端==，传参的时候直接传一个String就好，**不需要包装成JSON**的格式。true表示已经有别的课了。

==后端==：注意，true表示该时间段已经有别的课了，false表示没有课。

下面这是一个七天，每天只有三节课的例子，days[0]代表周一的内容。

```JSON
{
    "code": 200,
    "msg": "succ",
    "data": {
        "days": [
            [
                true,
                true,
                true
            ],
            [
                true,
                true,
                true
            ],
            [
                true,
                true,
                true
            ],
            [
                true,
                true,
                true
            ],
            [
                true,
                true,
                true
            ],
            [
                true,
                true,
                true
            ],
            [
                true,
                true,
                true
            ]
        ]
    }
}
```

![getClassroomSpareTime.png](https://s2.loli.net/2022/04/28/Rr8Vw9sCNcnaoz6.png)

#### 7.获得所有学期

> /common/allSemesters

获得所有可供选择的学期。

==前端==，管理员添加课程或者学生查看自己某个学期的课表是会用到这个功能，会给一个数组和一个默认值。

==后端==，目前的打算是利用枚举类型写死，而不是写进数据库(理想的情况当然是写进去，并且可以维护，但是目前没有这个必要)。这个东西Service层和Controller层实现都可以。目前交给Service层实现。我的个人建议是：写两个小函数可以实现枚举类型和String的转换(从String)，写一个函数可以获得所有的学期的String的List，再写一个函数判断某个String是不是合法的可转换的。defaulSemester是根据当前**系统时间**得到的，上面这几个函数感觉可以重复利用，要不要**写成一个工具类**？

![allSemesters.png](https://s2.loli.net/2022/04/30/clDFWgirSknf2sd.png)

****

#### 8.管理员添加课程

> /admin/addCourse

相比以前，我们需要额外**添加**：开课学年year和学期semester（二级联级选择器），选课类型selectTypeString，专业限制majorLimits,这是一个**List<String>**，上课时间。

选课类型有三种：通识课程，专业限制课程。

专业限制是一个二级级联多选框，通识->全无法勾选，专业限制->多选

需要**修改**：教室（变成二级级联），上课时间（变成一个大大的表格，给后端传一个数组）。

==后端==：做同类课程信息一致的检查。

**记得回过头检查修改教室容量是否可以正常修改**

#### 9.管理员CSV添加课程

> /admin/csvAddCourse

这里需要我们的前端人员模仿添加课程时候的格式设计一个新的CSV,以及修改一下对应的处理CSV的函数

#### 10.管理员修改课程信息

> /admin/updateCourseInfo

==前端==：最好是一个和添加课程比较类似的界面，然后有一些信息不许修改，比如开课院系和哪个老师上课，课程编号和课程名允许改，像以前一样记得把id给后端。

==后端==:改课程编号和名字、学分、学时、简介的时候注意是所有同类课程的修改，上课地点和上课时间是单独这门课的修改。

**注意**：如果是这门课自己的上课时间，后端记得给false，表示不冲突，然后后端还会给这门课的上课时间，前端那个时间要设置成被选中。

#### 11.老师提交开课申请

>/teacher/addCourse

这个界面也需要模仿管理员添加课程的界面。区别在于，任课老师和开课院系默认的是这个老师自己和他对应的院系。

==后端==，新课程要类似地做同类课程信息一致的检查。同类课程需要一致的信息：学分、学时、介绍。这个检查可以晚点做

#### 12.老师提交修改课程信息的申请

模仿管理员修改课程信息的界面

#### 13.老师提交删除课程的申请





## 第二阶段

#### 1.界面修改：选课管理界面

以前只有简单的选课开放or关闭，现在的话有了一轮选课二轮选课。

==前端==：目前的想法：四个按钮，开放选课，关闭选课，下一轮选课，随机筛选。两个现实的内容：第几轮选课，和当前选课是否开放。

> /admin/isSelectCourseOpen  获得当前选课是否开放，一个String
>
> /admin/whichTurn 获得当前是第几轮选课，一个String
>
> /admin/openSelectCourse  继续选课
>
> /admin/closeSelectCourse  暂停选课
>
> /admin/nextTurn  下一轮选课
>
> /admin/randomSelect  随机筛选
>
> /admin/startThisSemesterSelectCourse 开始本学期的选课
>
> /admin/ednThisSemesterSelectCourse 结束本学期的选课

**一个想法**，点击随机筛选后，我们可以用同步的方式等待筛选结束给一个反馈。

==后端==：三个数据库里面的量：(枚举类型)目前几轮选课，当前选课是否开放，当前是否完成了随机筛选。

下一轮选课：

如果是1轮到2轮，后端要检查是否已经完成了随机筛选，如果未完成，则抛出异常。

如果当前选课正在开放，也抛出异常提示应该先关闭选课。

随机筛选：

检查当前是不是一轮且关闭选课的状态。

开放选课：

一轮开放的话会导致已完成随机筛选变成false。

#### 2.界面修改：查看课程界面

管理员查看课程，老师查看课程，学生查看自己已选/已修课程，这三个界面的分页查询需要学年和学期信息

多添加一个按钮，查看修读名单。

显示额外的信息：学年和学期

上课时间是一个String，这里不再是表格的形式







#### 3.新的界面，学生：提交选课申请，目前让前端开工。

> /student/selectCourseApply

提交选课申请需要的信息：课程id(courseId)，申请理由applyReason就可以了。学生自己的stuNumber后端可以自动获得。

这门课的具体信息可以通过

> /common/CourseInfo

只需要给后端一个Integer类型的courseId，这个端口获得这门课的具体信息，具体信息有：课程编号，courseNum，课程名称courseName，任课老师teacherName，开课学年year、开课学期semester、上课时间occupyTime，**等等信息**。都是String。



输入完courseId点击确定(或者设定成每次修改后自动刷新)获得对应的信息，自动填入。然后再填申请理由

前端可以在下方文字提示一下，学生要把这个课的时间段空着，不然管理员不会通过。打申请的时候检查课程是否存在。



如何避免时间的冲突？放在管理员点击通过的时候，这样学生打申请的时候对应的时间就算有课也可以打，在管理员审批前把时间空出来即可。

==后端==：删课的时候要检查一下，这门课是不是已经有学生选了。后端**可以先把申请打上去**，但是管理员的通过按钮不让他按。

后端会自动根据courseId补充进去一些信息，比如课程序号，上课老师。

#### 3.新的界面，学生：查看选课申请，目前让前端开工。

可以查看自己所有的选课申请，选课申请的信息相比提交时多了两个很多后端补充的值，审批意见Notes和状态Status。申请理由和审批意见可以做大一点，在点击查看详情里面查看。

只有三个信息，课程id，审批状态，审批意见。具体这门课的信息要点击右边的查看详情获得。同样地访问这个端口

> /common/CourseInfo



#### 4.新界面，学生查看自己的课表

可以选择学年学期，今年的话相当于是已选，往年的话相当于是已修。

考虑使用Table，我们就不分页展示了

给你啥展示啥。会显示课程名称，课程编号，成绩，上课时间。



#### 5.端口：获得学生某个学期的课表

其实这个端口难度还好



#### 6.老师和管理员查看某门课的名单

> /common/getStudentListOfOneCourse 给我一个courseId

返回的结果适合用来分页显示，有res.data.data.records和res.data.data.total

## 5.2任务

张佳洵：

完成PageTool类，实现一个MyPage类

在工具类中完成学年学期之间的比较。

杨添淇：

1.删课，删除课程的时候顺带修改课程对应教室的课表

2.改课信息，修改课程信息的时候，哪些可以改，哪些不能改可以参考这个

>课程的信息有这些不可修改：开课院系，开课专业，任课老师,开课学年，开课学期，
>
>课程的可以修改信息：专业限制类型，可选专业(List)，注意，只有当这门课的开课学年和学期在当前学期之后的时候才可以修改这两个信息

3.老师申请开课

4.老师申请删课

5.老师申请改课信息

6.管理员拒绝老师的申请

7.管理员通过老师的申请

于康：

进一步完成修改课程界面，用上那两个端口，

> /common/getClassroomOccupyByOneCourse
>
> /common/getClassroomSpareTimeExceptOneCourse

完成老师打申请的界面

王骏飞：

把所有的延迟改成1秒，也就是我们进行修改之后界面的更新的延迟。

验收管理员开课，改课，删课，老师开课，改课，删课，以及已完成的功能

学生打申请的界面，可以利用courseId访问

> /common/findOneCourseInfo

这个端口或者这么课的所有信息,会有这些信息。现实出来就好，不用给学生修改，这是disable

```java
    private Integer courseId;
    private String courseNumber;
    private String courseName;
    private String teacherNum;
    private String teacherName;
    private String school;
    private String major;
    private String building;
    private String classroom ;
    private Integer creditHours;
    private Integer credits;
    private Integer capacity;
    private String introduction;
    private String selectTypeString;
    private String majorLimits;
    private String year;
    private String semester;
    private String occupyTime;
```



## 后端后期需要补上的检查

添加新时间段的时候，检查和上一个时间段的关系



添加课程的时候，检查课程容量和教室容量的关系

修改教室容量的时候，检查所有在这个教室上课的课程的容量，保证大于这些课程的容量。



































