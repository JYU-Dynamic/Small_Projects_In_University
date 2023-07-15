//
// Created by 86178 on 2023/7/8.
//
/*
学生成绩管理系统应该包含的功能

1.登录功能：允许管理员登录，登录成功后才能访问其他功能。
2.录入学生信息：可录入学生信息，例如：学号、姓名、班级、成绩。
3.删除学生信息：输入学号可删除该学生录入的全部信息。
4.更新学生信息：输入学号可修改该学生的姓名、班级、成绩。
5.查询学生成绩：输入学号可查询该学生该科成绩。
6.分析学生成绩：包括平均分、最高分、最低分、按分数高低打印及格与不及格名单及人数等。
7.导出报表：可将录入的学生信息导出到指定的文档文本中。
8.退出系统：输入指令可退出该系统。


能实现以上功能的C语言完整代码：
*/


#include <cstdio>
#include <cstdlib>
#include <cstring>
//定义学生数据结构
typedef struct Student
{
    int id;
    char name[20];
    char classes[20];
    float score;
} Student;

//定义学生数据数组
Student students[100];
int stuNum;//宏定义学生总数
float all;//宏定义总分
int pass;//宏定义及格线
int sumpass;//宏定义及格人数
int sumunpass;//宏定义不及格人数

//函数声明
void login();//管理者登录
void addStudent();//录入学生信息
void delStudent();//删除学生信息
void updateStudent();//更新学生信息
void queryStudent();//查询成绩
void analyzeStudent();//分析成绩
void outputStudent();//导出报表

int main()
{
    login();//引用登录函数
    printf("---------欢迎使用本系统！---------\n");//修饰
    printf("请输入该科总分：");
    scanf("%f",&all);//录入总分
    printf("及格线：");
    scanf("%d",&pass);//录入及格分数
    while (true)//打印主菜单
    {
        int sel;
        printf("\t\n");
        printf("请选择操作：\n");
        printf(" ---------------\n");
        printf("|  1. 添加学生  |\n");
        printf("|  2. 删除学生  |\n");
        printf("|  3. 更新学生  |\n");
        printf("|  4. 查询学生  |\n");
        printf("|  5. 分析学生  |\n");
        printf("|  6. 导出报表  |\n");
        printf("|  0. 退出      |\n");
        printf(" ---------------\n");
        scanf("%d", &sel);
        switch (sel) //根据输入引用对应功能
        {
            case 1: addStudent(); break;
            case 2: delStudent(); break;
            case 3: updateStudent(); break;
            case 4: queryStudent(); break;
            case 5: analyzeStudent(); break;
            case 6: outputStudent(); break;
            case 0: return 0;
            default: printf("输入有误，请重新输入！\n"); break;
        }
    }
}

//登录功能
void login()
{
    char password[20];
    printf("请输入管理员密码：\n");
    scanf("%s", password);
    if (strcmp(password, "123456") == 0)//用stringcompare比对密钥
    {
        printf("登录成功！\n");
        printf("\t\n");
    }
    else
    {
        printf("密码错误，登录失败！\n");
        exit(0);//终止程序运行，正常退出程序
    }
}

//添加学生
void addStudent()
{
    Student *stu = (Student *)malloc(sizeof(Student));
    //动态内存分配语句，为指针stu分配一块存储Student类型数据的内存空间。
    printf("请输入学生学号：");
    scanf("%d", &stu->id);
    ///学号具有唯一性，判断是否重复输入。
    int flag = 0;
    int i;
    for(i = 0; i < stuNum; i++)
    {
        if(students[i].id == stu->id)
        {
            flag = 1;
            break;
        }
    }
    if(flag == 1)
    {
        printf("学号重复，添加失败！\n");
        free(stu);
        return;
    }
    ///
    printf("请输入学生姓名：");
    scanf("%s", stu->name);
    printf("请输入学生班级：");
    scanf("%s", stu->classes);
    printf("请输入学生成绩(分)：");
    scanf("%f", &stu->score);
    if(stu->score > all)
    {
        printf("学生成绩大于总分！添加失败！\n");
        free(stu);//释放stu所指向的动态内存，即释放掉动态开辟的内存空间，避免内存泄漏。
        return;//中断函数执行，跳出addStudent()函数
    }
    else
    {
        students[stuNum] = *stu;//将stu所指向的结构体内容赋值给students[stuNum]，以添加新的学生信息
        stuNum++;//学生数量加一，记录学生总数量
        printf("\t\n");
        printf("添加成功！\n");
    }
    free(stu);//释放stu所指向的动态内存，即释放掉动态开辟的内存空间，避免内存被无限占用。
}

//删除学生
void delStudent()
{
    int i, del_id;
    printf("请输入要删除的学生学号：");
    scanf("%d", &del_id);
    for (i = 0; i < stuNum; i++)
    {
        // 找到学号相同的学生
        if (students[i].id == del_id)
        {
            // 将最后一个学生数据覆盖到当前位置
            students[i] = students[stuNum - 1];
            //学生的索引号从0开始，所以最后一个学生的索引号应该是stuNum-1，而不是stuNum。
            stuNum--;// 学生数量减一
            printf("\t\n");
            printf("删除学生成功！\n");
            return;//中断函数执行，跳出delStudent()函数
        }
    }
    printf("未找到该学号！\n");
}

//更新学生
void updateStudent()
{
    int i, update_id;
    printf("请输入要更新的学生学号：");
    scanf("%d", &update_id);
    for (i = 0; i < stuNum; i++)
    {
        // 找到学号相同的学生,重新输入即可
        if (students[i].id == update_id)
        {
            printf("请输入更新后的学生姓名：");
            scanf("%s", students[i].name);
            printf("请输入更新后的学生班级：");
            scanf("%s", students[i].classes);
            printf("请输入更新后的学生成绩：");
            scanf("%f", &(students[i].score));
            printf("\t\n");
            printf("更新学生成功！\n");
            return;//中断函数执行，跳出updateStudent()函数
        }
    }
    printf("未找到该学号！\n");
}

//查询学生
void queryStudent()
{
    int i, query_id;
    printf("请输入要查询的学生学号：");
    scanf("%d", &query_id);
    for (i = 0; i < stuNum; i++)
    {
        // 找到学号相同的学生
        if (students[i].id == query_id)
        {
            printf("\t\n");
            printf("学号：%d\n", students[i].id);
            printf("姓名：%s\n", students[i].name);
            printf("班级：%s\n", students[i].classes);
            printf("成绩：%.2f分\n", students[i].score);
            return;//中断函数执行，跳出queryStudent()函数
        }
    }
    printf("未找到该学号！\n");
}

//分析学生
void analyzeStudent()
{
    if(stuNum!=0)//判断是否已经录入学生信息
    {
        int i;
        float sum = 0;
        float max = 0;
        for (i = 0; i < stuNum; i++)
        {
            sum += students[i].score;//统计总分
            if (students[i].score > max)
            {
                max = students[i].score;//找出最高分
            }
            if (students[i].score < all)
            {
                all = students[i].score;//找出最低分
            }
        }
        printf("\t\n");
        printf("平均成绩：%.2f分\n", sum / stuNum);
        printf("最高成绩：%.2f分\n", max);
        printf("最低成绩：%.2f分\n", all);
        //冒泡排序成绩
        int j;
        for (i = 0; i < stuNum - 1; i++)
        {
            for (j = 0; j < stuNum - i - 1; j++)
            {
                if (students[j].score < students[j + 1].score)
                {
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
        printf("从高到低排序后的学生成绩：\n");
        printf("及格名单\n");
        printf("学号\t姓名\t班级\t成绩\n");
        sumpass=0;
        sumunpass=0;
        for (i = 0; i < stuNum; i++)
        {
            if(students[i].score >= pass)//打印合格名单
            {
                sumpass = sumpass + 1;
                printf("%d\t%s\t%s\t%.2f\n",students[i].id,students[i].name,students[i].classes,students[i].score);
            }
        }
        printf("不及格名单\n");
        printf("学号\t姓名\t班级\t成绩\n");
        for (i = 0; i < stuNum; i++)
        {
            if(students[i].score < pass)//打印不合格名单
            {
                sumunpass = sumunpass + 1;
                printf("%d\t%s\t%s\t%.2f\n",students[i].id,students[i].name,students[i].classes,students[i].score);
            }
        }
        printf("及格人数：%d 人\n",sumpass);
        printf("不及格人数：%d 人\n",sumunpass);
        printf("及格率为：%.2f%%\n",(float)sumpass/stuNum * 100);
    }
    else printf("您还未录入学生成绩！请录入学生后再分析！\n");
}

//导出报表
void outputStudent()
{
    if(stuNum!=0)//判断是否已经录入学生信息
    {
        FILE *fp = fopen("student.txt", "w");//只写形式创建student.txt文本文档
        if (fp == nullptr)
        {
            printf("创建文件失败！\n");
            return;//中断函数执行，跳出outputStudent()函数
        }
        int i;
        fprintf(fp, "学号\t姓名\t班级\t成绩\n");
        for (i = 0; i < stuNum; i++)
        {
            fprintf(fp, "%d\t%s\t%s\t%.2f\n", students[i].id, students[i].name, students[i].classes, students[i].score);
        }
        printf("报表已导出！\n");
        fclose(fp);
    }
    else printf("请录入学生信息再导出报表！\n");
}