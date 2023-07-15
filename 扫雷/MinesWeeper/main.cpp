//
// Created by 86178 on 2023/7/8.
//
#include <cstdio>
#include <cstdlib>
#include <ctime>

//实际棋盘的规格，用来给玩家排查雷
#define ROW 9 //行
#define COL 9 //列

//隐藏棋盘的规格，用于防止越界
#define ROWS (ROW + 2) //行
#define COLS (COL + 2) //列

//以下用于调节难度,即雷的个数
#define CURRENT_COUNT 10

//函数声明
void menu();                                                                    //打印主菜单
void game();                                                                    //游戏函数
void initboard(char board[11][11], char set);           //初始化棋盘
void print_board(char board[ROWS][COLS], int row, int col);                     //打印棋盘
void FineMine(char mine[ROWS][COLS], char check[ROWS][COLS], int row, int col); //排查雷
int Get_Mine_Count(char mine[ROWS][COLS], int x, int y);                        //计算坐标附近雷的总个数
void recur(char mine[ROWS][COLS], char check[ROWS][COLS], int x, int y);        //递归消除无雷区
void SetMine(char mine[ROWS][COLS], int row, int col);
//主函数
int main()
{
    srand((unsigned int)time(nullptr)); //初始化种子，作为乱数种子供rand()函数使用
    int input;                       // input接收用户选择
    do
    {
        menu(); //打印主菜单
        printf("请选择（1或0）：\n");
        scanf("%d", &input);
        switch (input)
        {
            case 1:
                game();
                break; //调用游戏函数
            case 0:
                exit(0); //终止程序运行
            default:
                printf("输入错误，请重新输入\n");
                break;
        }
    } while (input);

    return 0;
}

//打印菜单
void menu()
{
    printf("******************************\n");
    printf("****         1.play       ****\n");
    printf("****         0.exit       ****\n");
    printf("******************************\n");
}

//游戏函数
void game()
{
    //创建棋盘
    char mine[ROWS][COLS] = {0};
    char check[ROWS][COLS] = {0};
    //初始化棋盘
    initboard(mine, '0');
    initboard(check, '*');
    //打印棋盘
    print_board(check, ROW, COL);
    //布置雷
    SetMine(mine, ROW, COL);
    //以下棋盘用于检查雷的部署
    //print_board(mine, ROW, COL);
    //排查雷
    FineMine(mine, check, ROW, COL);
    // print_board(mine, ROW, COL);该函数用于检验棋盘布置雷的完整性，使用它将会在窗口打印出布置了雷的棋盘，即雷的分布图
}

//初始化棋盘
void initboard(char board[11][11], char set)
{
    int i, j;
    for (i = 0; i < ROWS; i++)
    {
        for (j = 0; j < COLS; j++)
        {
            board[i][j] = set;
        }
    }
}
/*在此函数中，先用双层循环遍历棋盘中的每个单元，然后将棋盘中每个位置设置成set参数指定的状态*/

//打印棋盘
void print_board(char board[ROWS][COLS], int row, int col)
{
    int i, j;
    for (i = 0; i <= row; i++)
    {
        printf("%d ", i); //打印顶坐标
    }
    printf("\n");
    for (i = 1; i <= row; i++)
    {
        printf("%d ", i); //打印竖坐标
        for (j = 1; j <= col; j++)
        {
            printf("%c ", board[i][j]); //如果传过来是Check，则打印 “ * ”
        }
        printf("\n");
    }
    printf("\n");
}

//布置雷
void SetMine(char mine[ROWS][COLS], int row, int col)
{
    int level_count = CURRENT_COUNT;
    while (level_count)
    {
        int x; //行
        int y; //列
        //生成 1到 row（行）和 1到col（列）的随机数作为雷，用于在棋盘上选择雷的位置。
        x = rand() % row + 1;
        y = rand() % col + 1;
        if (mine[x][y] != '1')
        {
            mine[x][y] = '1'; //将得到的随机数赋值为 “ 1 ”，即雷本身
            level_count--;    //当雷分配给随机数，雷数减一，直到变为0结束 while循环
        }
        else
        {
            continue; //放置雷只与 mine[x][y]有关，但 while循环仍继续
        }
    }
}
/*此代码用来布置雷。它会使用一个 CURRENT_COUNT 的变量来执行循环，
然后每次循环都会生成一个介于1到row(行)或col(列)之间的随机坐标。
如果坐标处没有雷(即不等于‘1’)，则会将此位置设定为'1',
否则继续执行循环，直到level_count变量变为0.*/

//排查雷
void FineMine(char mine[ROWS][COLS], char check[ROWS][COLS], int row, int col)
{
    int x, y;
    int win = row * col - CURRENT_COUNT; // win为排查次数
    while (win >= 0)
    {
        printf("请输入需要排查的坐标（格式：行<空格>列）:");
        scanf("%d %d", &x, &y);
        printf("\n");
        //先判断这个坐标是否合法，再判断这个是不是雷，最后判断这个位置有没有被排查过
        if (x >= 1 && x <= ROW && y >= 1 && y <= COL) //判断是否合法
        {
            if (mine[x][y] == '1') //判断是否踩雷
            {
                printf("很遗憾，你踩到雷了\n");
                break;
            }
            else if (check[x][y] != '*') //判断有没有被排查过
            {
                printf("这个坐标已经排查过了，请选择其他坐标\n");
            }
            else //正常情况
            {
                win--;                                //排查次数减一
                int num = Get_Mine_Count(mine, x, y); // num为该坐标周围8个位雷的总个数
                check[x][y] = num + '0';              //将计算出的雷的数量转换成字符形式，并存储在 check 数组中。
                if (check[x][y] == '0')
                {
                    recur(mine, check, x, y);
                }
                print_board(check, ROW, COL);
            }
        }
        else
            printf("坐标非法，请在指定范围内进行排查\n");
        if (win == 0)
        {
            printf("恭喜你，你排完了所有的雷\n");
        }
    }
}

//计算某坐标附近有多少雷（属于‘排查雷’函数中的嵌套函数）
int Get_Mine_Count(char mine[ROWS][COLS], int x, int y)
{
    return (mine[x - 1][y - 1] + mine[x - 1][y] + mine[x - 1][y + 1] +
            mine[x][y - 1] + mine[x][y + 1] +
            mine[x + 1][y - 1] + mine[x + 1][y] + mine[x + 1][y + 1] -
            8 * '0');
}
/*这段代码的功能是计算某坐标附近有多少个雷。
它的原理是：用输入的 (x, y) 坐标定位出这个坐标
所在的周围八个格子（上、下、左、右四个方向及其四个对角线上的四个格子），
将这八个格子的雷的数量加在一起，然后再减去 8 个'0'（减去8个'0'是因为这8个位置都会被累计，
即使它们没有雷也会被累计(数量加1)。如果8个位置都没有雷，返回值就是0。）。
这样就可以计算出周围有多少个雷。*/

//递归排查坐标周围
//停止条件：排查的坐标为非法坐标
//递归过程：x和y不断变大或变小，当那个被排查的坐标为雷时，停止该坐标的递归，
//若该坐标为已经排查过的坐标，则停止该坐标的递归
//（属于‘排查雷’函数中的嵌套函数）
void recur(char mine[ROWS][COLS], char check[ROWS][COLS], int x, int y)
{
    int count = Get_Mine_Count(mine, x, y); // count为该坐标周围8个位雷的总个数
    if (count == 0)
    {
        check[x][y] = ' ';
        if (x - 1 >= 0 && x - 1 <= ROW && y >= 0 && y <= COL && check[x - 1][y] == '*')
            recur(mine, check, x - 1, y);
        if (x + 1 >= 0 && x + 1 <= ROW && y >= 0 && y <= COL && check[x + 1][y] == '*')
            recur(mine, check, x + 1, y);
        if (x >= 0 && x <= ROW && y - 1 >= 0 && y - 1 <= COL && check[x][y - 1] == '*')
            recur(mine, check, x, y - 1);
        if (x >= 0 && x <= ROW && y + 1 >= 0 && y + 1 <= COL && check[x][y + 1] == '*')
            recur(mine, check, x, y + 1);
    }
    else
    {
        check[x][y] = count + '0';
    }
}
/*这个代码段是用于检查一个特定的坐标（由x, y表示）附近有多少雷的。
如果count等于0，说明该位置附近没有雷，则将essay代写，check[x][y]设为' '（半角空格字符），
并继续检查它的上、下、左、右四个方向的坐标；如果count不等于0，说明该位置附近有雷，
则将check[x][y]设置为count+'0'。因为在 ASCII 码中，‘0’ - 8 = ‘8’，
所以减去 8 个‘0’就可以把返回值调整为 0 - 8 之间。若 8 个位置都没有雷，返回值应该是 0。
代码中的 check[x][y] = num + '0' 是将 Get_Mine_Count()函数返回的 count 加上 '0'的ASCII码值
并赋值给 check[x][y]，以表示该位置有多少雷。*/