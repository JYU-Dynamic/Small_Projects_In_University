#include <stdio.h>
#include <malloc.h>
#include<iostream>
#include<cstdio>
#include <string.h>
#define MaxSize 20
using namespace std;
typedef struct
{
	int num;						//学号
	char name[10];				    //姓名
	int classes;					//班级
	long int phone;				    //电话
    char address[20];               //地址
    char code[20];                  //邮箱
} EmpType;						    //联系人
typedef struct node
{
	EmpType data;				//存放通讯信息
	struct node *next;			//指向下一个结点的指针
}  EmpList;						//联系人结点类型
typedef struct
{	
	char data[MaxSize];		//串中字符
	int length;				//串长
} SqString;					//声明顺序串类型

void DestroyEmp(EmpList *&L)	//释放联系人单链表L
{
	EmpList *pre=L,*p=pre->next;
	while (p!=NULL)
	{
		free(pre);
		pre=p;
		p=p->next;
	}
	free(pre);
}
void DelAll(EmpList *&L)		//删除联系人文件中全部记录
{
	FILE *fp;
	if ((fp=fopen("emp.dat","wb"))==NULL)	//重写清空emp.dat文件
	{	
		printf(">>不能打开联系人文件\n");
		return;
	}
	fclose(fp);
	DestroyEmp(L);						//释放联系人单链表L
	L=(EmpList *)malloc(sizeof(EmpList));	
	L->next=NULL;						//建立一个空的联系人单链表L
	printf(">>联系人记录清除完毕\n");
}
void ReadFile(EmpList *&L)		//读emp.dat文件建立联系人单键表L
{
	FILE *fp;
	EmpType emp;
	EmpList *p,*r;
	int n=0;
	L=(EmpList *)malloc(sizeof(EmpList));	//建立头结点
	r=L;
	if ((fp=fopen("emp.dat","rb"))==NULL) //不存在emp.dat文件
	{	
		 if ((fp=fopen("emp.dat","wb"))==NULL) 
			 printf(">>不能创建emp.dat文件\n");
	}
	else		//若存在emp.dat文件
	{
		while (fread(&emp,sizeof(EmpType),1,fp)==1)
		{	//采用尾插法建立单链表L
			p=(EmpList *)malloc(sizeof(EmpList));
			p->data=emp;
			r->next=p;
			r=p;
			n++;
		}
	}
	r->next=NULL;
	printf(">>联系人单键表L建立完毕,有%d个记录\n",n);
	fclose(fp);
}
void SaveFile(EmpList *L)	//将联系人单链表数据存入数据文件
{
	EmpList *p=L->next;
	int n=0;
	FILE *fp;
	if ((fp=fopen("emp.dat","wb"))==NULL) 
	{	
		printf(">>不能创建文件emp.dat\n");
		return;
	}
	while (p!=NULL)
	{
		fwrite(&p->data,sizeof(EmpType),1,fp);
		p=p->next;
		n++;
	}
	fclose(fp);
	DestroyEmp(L);				//释放联系人单链表L
	if (n>0)
		printf(">>%d个联系人记录写入emp.dat文件\n",n);
	else
		printf(">>没有任何联系人记录写入emp.dat文件!\n");
}
void InputEmp(EmpList *&L)	//添加一个联系人记录
{
	EmpType p;
	EmpList *s;
	printf(">>输入学号(-1返回, 0代表没有学号):");
	int A;
	cin>>A;
	if(A==-1)   return;
	else if(A==0)   p.num=888888888;    //888888888是为了格式好看:）
	else p.num=A;
	printf(">>输入姓名 班级 电话 地址 邮箱(输入完毕摁ENTER回车):\n");
	scanf("%s%d%ld%s%s",&p.name,&p.classes,&p.phone,&p.address,&p.code);
	s=(EmpList *)malloc(sizeof(EmpList));
	s->data=p;
	s->next=L->next;		//采用头插法插入结点s
	L->next=s;
	printf("  提示:添加成功!\n");
}

void DelEmp(EmpList *&L)	//删除一个职工记录
{
	EmpList *pre=L,*p=L->next;
	int num;
	printf(">>输入学号(-1返回):");
	scanf("%d",&num);
	if (num==-1) return;
	while (p!=NULL && p->data.num!=num)
	{
		pre=p;
		p=p->next;
	}
	if (p==NULL)
		printf(">>指定的联系人记录不存在！\n");
	else
	{
		pre->next=p->next;
		free(p);
		printf(">>删除成功！\n");
	}
}
void Sortno(EmpList *&L)	//采用直接插入法单链表L按学号递增有序排序
{
	EmpList *p,*pre,*q;
	p=L->next->next;
	if (p!=NULL)
	{
		L->next->next=NULL;
		while (p!=NULL)
		{
			q=p->next;
			pre=L;
			while (pre->next!=NULL && pre->next->data.num<p->data.num)
				pre=pre->next;
			p->next=pre->next;
			pre->next=p;
			p=q;
		}
	}
	printf(">>按学号递增排序完毕.\n");
}
void Sortdepno(EmpList *&L) //采用直接插入法单链表L按班级递增有序排序
{
	EmpList *p,*pre,*q;
	p=L->next->next;
	if (p!=NULL)
	{
		L->next->next=NULL;
		while (p!=NULL)
		{
			q=p->next;
			pre=L;
			while (pre->next!=NULL && pre->next->data.classes<p->data.classes)
				pre=pre->next;
			p->next=pre->next;
			pre->next=p;
			p=q;
		}
	}
	printf(">>按班级递增排序完毕.\n");
}
void Sortsalary(EmpList *&L) //采用直接插入法单链表L按电话递增有序排序
{
	EmpList *p,*pre,*q;
	p=L->next->next;
	if (p!=NULL)
	{
		L->next->next=NULL;
		while (p!=NULL)
		{
			q=p->next;
			pre=L;
			while (pre->next!=NULL && pre->next->data.phone<p->data.phone)
				pre=pre->next;
			p->next=pre->next;
			pre->next=p;
			p=q;
		}
	}
	printf(">>按电话递增排序完毕.\n");
}
long int DispEmp(EmpList *L)	//输出所有联系人记录
{
	EmpList *p=L->next;
	if (p==NULL)
		printf("  提示:没有任何联系人记录！\n");
	else
	{
		printf("\t\t学号\t\t姓名\t\t班级\t电话\t\t地址\t\t邮箱\n");
		printf("\t\t----------------------------------------------------------------------------------------------\n");
		while (p!=NULL)
		{
			printf("\t\t%d\t%-10s\t%d\t%ld\t%-s\t%-s\n",p->data.num,p->data.name,p->data.classes,p->data.phone,p->data.address,p->data.code);
			p=p->next; 
		}
		printf("\t\t----------------------------------------------------------------------------------------------\n");
	}
}

//需添加函数
void Find(EmpList *&L)     //查找记录
{
	
    EmpList *pre=L,*p1=L->next;
    printf("请输入你要查找的方式，1按学号查找，2按姓名查找。输入-1返回\n");
    int bb;
    scanf("%d",&bb);
    if(bb==-1) return;
    else if(bb==1)
    {
        long int num;
        printf("  >>输入需要查找的学生号:");
        scanf("%ld",&num);
        while (p1!=NULL&&p1->data.num!=num)
        {
           pre=p1;
           p1=p1->next;
        }
        if (p1==NULL)
        {
            printf("该学生不存在");
            return;
        }
        else
        printf("%ld\t%-10s%d  %ld\t%-s\t%-s\n",p1->data.num,p1->data.name,p1->data.classes,p1->data.phone,p1->data.address,p1->data.code);
        return;
    }
    else if (bb==2);
    {
      char name[10];
      printf("请输入要查找学生的名字\n");
      scanf("%s",&name);
      while (p1!=NULL&&strcmp(p1->data.name,name)!=0)
      {
        pre=p1;
        p1=p1->next;
      }
      if (p1==NULL)
      {
        printf("未查找到该学生");
        return;
      }
      else
      printf("%ld\t%-10s%d  %ld\t%-s\t%-s\n",p1->data.num,p1->data.name,p1->data.classes,p1->data.phone,p1->data.address,p1->data.code);
      return;
    }
}

void modifyList(EmpList* head) //修改记录
{
    char name[10];
    cout << "请输入要修改的联系人姓名:";
    cin >> name;
    EmpList* p = head;
    while (p != nullptr) {
        if (strcmp(p->data.name, name) == 0) 
		{
            cout << "找到联系人:" << p->data.name << endl;
            cout << "有以下信息可供修改:" << endl;
            cout << "     1. 电话号码" << endl;
            cout << "     2. 地址" << endl;
            cout << "     3. 邮箱" << endl;
            cout << "     4. 学号" << endl;
			cout<<"请选择：";
            int choice;
            cin >> choice;
            switch (choice) {
                case 1: 
                    cout << "请输入新的电话号码:";
                    cin >> p->data.phone;
                    break;
                case 2:
                    cout << "请输入新的地址:";
                    cin >> p->data.address;
                    break;
                case 3:
                    cout << "请输入新的邮箱:";
                    cin >> p->data.code;
                    break;
				case 4:
                    cout << "请输入新的学号:";
                    cin >> p->data.num;
                    break;
            }
            cout << ">>修改成功." << endl;
            return;
        }
        p = p->next;
    }
    cout << "未找到该联系人!" << endl;
}

//半记忆查找
void StrAssign(SqString &s,char cstr[])	//字符串常量赋给串s
{
	int i;
	for (i=0;cstr[i]!='\0';i++)
		s.data[i]=cstr[i];
	s.length=i;
}


long int DispStr(SqString s)    //返回串s的值 
{
    long int value = 0;
    if (s.length>0)
    {    
        for (int i=0;i<s.length;i++)
        {
            value = value*10 + s.data[i] - '0';  //计算字符串对应的整数值
        }
    }
    return value;  //返回整数值
}


void GetNext(SqString t,int next[])	//由模式串t求出next值
{	int j,k;
	j=0;k=-1;next[0]=-1;
	while (j<t.length-1)
	{	if (k==-1 || t.data[j]==t.data[k]) 	//k为-1或比较的字符相等时
		{	j++;k++;
			next[j]=k;
		}
		else  k=next[k];
	}
}

void GetNextval(SqString t,int nextval[])  //由模式串t求出nextval值
{
	int j=0,k=-1;
	nextval[0]=-1;
	while (j<t.length)
	{	if (k==-1 || t.data[j]==t.data[k])
		{	j++;k++;
			if (t.data[j]!=t.data[k])
				nextval[j]=k;
			else
				nextval[j]=nextval[k];
		}
		else
			k=nextval[k];
	}
}


int KMPIndex1(SqString s,SqString t)	//修正的KMP算法
{
	int nextval[MaxSize],i=0,j=0;
	GetNextval(t,nextval);
	while (i<s.length && j<t.length) 
	{	if (j==-1 || s.data[i]==t.data[j]) 
		{	i++;
			j++;
		}
		else
			j=nextval[j];
	}
	if (j>=t.length)
		return(i-t.length);
	else
		return(-1);
}


int KMP(EmpList *L,char *T)
{
    // <1>定义声明
	cout<<"您可能想找以下联系人："<<endl;
	printf("\t\t学号\t\t姓名\t\t班级\t电话\t\t地址\t\t邮箱\n");
	int j;
	char S[MaxSize];
	int next[MaxSize],nextval[MaxSize];
	SqString s,t;
    // <2>T输入
	StrAssign(t,T);
	// <3>S输入
    EmpList *p=L->next;
	while (p!=NULL)
	{
        sprintf(S,"%ld",p->data.phone);
	    StrAssign(s,S);
	    // <5>求nextval
	    GetNext(t,next);			//由模式串t求出next值
	    GetNextval(t,nextval);		//由模式串t求出nextval值
	    // <6>输出结果
		if(KMPIndex1(s,t)!=-1)
		{
		    if(p->data.phone==DispStr(s))
		    {
			    printf("\t\t%d\t%-10s\t%d\t%ld\t%-s\t%-s\n",p->data.num,p->data.name,p->data.classes,p->data.phone,p->data.address,p->data.code);
		    }
		}
		p=p->next;
	}
	cout<<"(PS:如果没有显示任何个人信息，则无法匹配到相似的手机号，请您再回忆手机号.)"<<endl;
}
// 以上属半记忆查找

void swap(char* a, char* b) 
{ 
    char temp[10];
    strcpy(temp, a);
    strcpy(a, b);
    strcpy(b, temp);
}

void sortList(EmpList* head) 
{
    if (head == nullptr || head->next == nullptr) return;
    EmpList* p = head;
    EmpList* q = head->next;
    while (p != nullptr && q != nullptr) 
	{
        if (strcmp(p->data.name, q->data.name) > 0) 
		{
            swap(p->data.name, q->data.name);
        } 
        p = p->next;
        q = q->next;
    }
    sortList(head->next);  
}

void printList(EmpList* head) 
{
    EmpList* p = head->next;
	printf(">>按姓名排序完毕.\n");
	printf("    学号\t姓名\t  班级\t电话\t\t地址\t\t邮箱\n");
    while (p != nullptr) 
	{
        printf("  %d\t%-10s%d    %ld    %-s   %-s\n", 
                p->data.num, p->data.name, p->data.classes, 
                p->data.phone, p->data.address, p->data.code);
        p = p->next;
    }
}


int main()
{
	EmpList *L;
	EmpList* head = nullptr;  // 链表头节点
	int sel,sle;
	printf("由emp.dat文件建立联系人单键表L\n");
	ReadFile(L);
	do
	{	
		printf("[---------------------\n");
		printf("\t1:添加记录\n\t2:显示记录\n\t3:排序记录\n\t4:删除记录\n\t5:清空记录\n\t6:查找记录\n\t7:修改记录\n\t8:弱记忆查找\n\t0:保存并退出\n");
		printf("----------------------]\n请选择:");
		scanf("%d",&sel);
		switch(sel)
		{
		case 1:
			InputEmp(L);
			break;
		case 2:
			DispEmp(L);
			break;
		case 3:
		    do
		    {
			cout<<"有以下排序方式"<<endl<<"         9.按学号排序    10.按班级排序    11.按电话排序    12.按姓名排序(a~z)(函数有误，需更改)"<<endl<<"请选择：";
			scanf("%d",&sle);
			switch(sle)
			   {
				case 9:
			    Sortno(L);
				DispEmp(L);
			    break;
		        case 10:
			    Sortdepno(L);
				DispEmp(L);
			    break;
		        case 11:
			    Sortsalary(L);
				DispEmp(L);
		    	break;
				case 12:
				sortList(L);
				printList(L);
				break;
				default:
		        cout<<">>输入有误，请重新输入."<<endl;
			    break;
			   }
		    }while(0);
		    break;
		case 4:
			DelEmp(L);
			break;
		case 5:
			DelAll(L);
			break;
		case 6:
			Find(L);
			break;
		case 7:
			modifyList(L);
			break;
		case 8:
			char T[MaxSize];
            int num;
            cout<<"请输入您记住的电话号码：";
            cin>>T;
            KMP(L,T);
			break;
		default:
		    cout<<">>输入有误，请重新输入."<<endl;
			break;
		}
	} while (sel!=0);
	SaveFile(L);
	return 1;
}