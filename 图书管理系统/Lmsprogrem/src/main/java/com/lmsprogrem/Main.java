package com.lmsprogrem;
import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 读取控制台输入的对象
        Scanner scanner = new Scanner(System.in);

        // 1. 注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        // 2. 获取连接对象
        // 备注：characterEncoding=utf-8 解决JDBC和数据库传输中文乱码问题
        String url = "jdbc:mysql://127.0.0.1:3306/db_lms?useSSL=false&characterEncoding=utf-8";
        Connection connection = DriverManager.getConnection(url, "root", "123456");
        // 3. 获取执行SQL的对象
        Statement statement = connection.createStatement();

        // 1. 登录
        while (true) {
            System.out.println("********** 欢迎访问图书管理系统 **********");
            System.out.print("请输入账号：");
            String account = scanner.nextLine();
            System.out.print("请输入密码：");
            String password = scanner.nextLine();

            // 拼接SQL
            String sql = "select * from tb_user where account = '" + account + "' and password = '" + password + "';";

            // 执行SQL并且获取结果
            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                if (account.equals("admin")&& Objects.equals(password, password)){
                    System.out.println("欢迎管理员！");
                    //2.菜单
                    hh: while (true) {
                        System.out.println("**********************************************");
                        System.out.println("* 1. 人员管理");
                        System.out.println("* 2. 图书管理");
                        System.out.println("* 3. 借阅管理");
                        System.out.println("* 4. 退出登录");
                        System.out.println("**********************************************");
                        System.out.print("请输入指令：");
                        String input = scanner.nextLine();
                        switch (input) {
                            case "1": // 人员管理：增删改查
                                while (true) {
                                    System.out.println("**********************************************");
                                    System.out.println("* 1. 新增人员");
                                    System.out.println("* 2. 删除人员");
                                    System.out.println("* 3. 修改人员");
                                    System.out.println("* 4. 查询人员");
                                    System.out.println("* 5. 显示全部");
                                    System.out.println("* 6. 返回上一级");
                                    System.out.println("* 7. 退出登录");
                                    System.out.println("**********************************************");
                                    System.out.print("请输入指令：");
                                    input = scanner.nextLine();
                                    // 是否返回上一级
                                    boolean isGoBack = false;
                                    switch (input) {
                                        case "1": // 新增人员
                                            System.out.print("请输入姓名：");
                                            String addUserName = scanner.nextLine();
                                            System.out.print("请输入性别：");
                                            String addUserGender = scanner.nextLine();
                                            System.out.print("请输入年龄：");
                                            int addUserAge = Integer.parseInt(scanner.nextLine()); // Integer.valueOf() ==> 可以将字符串转换成整型
                                            System.out.print("请输入账号：");
                                            String addUserAccount = scanner.nextLine();
                                            System.out.print("请输入密码：");
                                            String addUserPassword = scanner.nextLine();

                                            String addUserSQL = "insert into tb_user values(null, '" + addUserName + "', '" + addUserGender +"', " + addUserAge +", '" + addUserAccount +"', '" + addUserPassword +"');";

                                            statement.executeUpdate(addUserSQL);
                                            System.out.println("新增成功！");
                                            break;
                                        case "2": // 删除人员
                                            System.out.print("请输入要删除人员的ID：");
                                            String deleteUserId = scanner.nextLine();

                                            String deleteUserSQL = "delete from tb_user where id = " + deleteUserId;

                                            statement.executeUpdate(deleteUserSQL);
                                            System.out.println("删除成功！");
                                            break;
                                        case "3": // 修改人员
                                            System.out.print("请输入ID：");
                                            String updateUserId = scanner.nextLine();
                                            System.out.print("请输入姓名：");
                                            String updateUserName = scanner.nextLine();
                                            System.out.print("请输入性别：");
                                            String updateUserGender = scanner.nextLine();
                                            System.out.print("请输入年龄：");
                                            int updateUserAge = Integer.parseInt(scanner.nextLine()); // Integer.valueOf() ==> 可以将字符串转换成整型
                                            System.out.print("请输入账号：");
                                            String updateUserAccount = scanner.nextLine();
                                            System.out.print("请输入密码：");
                                            String updateUserPassword = scanner.nextLine();

                                            String updateUserSQL = "update tb_user set name = '" + updateUserName + "', gender = '" + updateUserGender + "', age = " + updateUserAge + ", account = '" + updateUserAccount + "', password = '" + updateUserPassword + "' where id = " + updateUserId + ";";

                                            statement.executeUpdate(updateUserSQL);
                                            System.out.println("修改成功！");
                                            break;
                                        case "4": // 查询人员
                                            System.out.print("请输入要查询人员的ID:");
                                            String searchUserId = scanner.nextLine();
                                            String searchUserSQL = "select * from tb_user where id = " + searchUserId;
                                            ResultSet searchUsersResult = statement.executeQuery(searchUserSQL);
                                            System.out.println("该用户信息如下:");
                                            if(searchUsersResult.next()) {
                                                System.out.println("ID:" + searchUsersResult.getInt("id")
                                                        + ",姓名:" + searchUsersResult.getString("name")
                                                        + ",性别:" + searchUsersResult.getString("gender")
                                                        + ",年龄:" + searchUsersResult.getInt("age")
                                                        + ",账号:" + searchUsersResult.getString("account")
                                                        + ",密码:" + searchUsersResult.getString("password"));
                                            }
                                            break;
                                        case "5": // 显示全部
                                            ResultSet selectUsersResult = statement.executeQuery("select * from tb_user;");
                                            System.out.println("数据库中用户信息如下：");
                                            while (selectUsersResult.next()) {
                                                System.out.println("ID：" + selectUsersResult.getInt(1)
                                                        + "，姓名：" + selectUsersResult.getString(2)
                                                        + "，性别：" + selectUsersResult.getString(3)
                                                        + "，年龄：" + selectUsersResult.getInt(4)
                                                        + "，账号：" + selectUsersResult.getString(5)
                                                        + "，密码：" + selectUsersResult.getString(6));
                                            }
                                            break;
                                        case "6": // 返回上一级
                                            isGoBack = true;
                                            break;
                                        case "7": // 退出登录
                                            break hh;
                                        default:
                                            System.out.println("输入有误，请重新输入！");
                                    }
                                    if (isGoBack) {
                                        break;
                                    }
                                }
                                break;
                            case "2": // 图书管理：增删改查
                                while(true) {
                                    System.out.println("**********************************************");
                                    System.out.println("* 1. 新增图书");
                                    System.out.println("* 2. 删除图书");
                                    System.out.println("* 3. 修改图书");
                                    System.out.println("* 4. 查询图书");
                                    System.out.println("* 5. 显示全部");
                                    System.out.println("* 6. 返回上一级");
                                    System.out.println("* 7. 退出登录");
                                    System.out.println("**********************************************");
                                    System.out.print("请输入指令：");
                                    input = scanner.nextLine();
                                    boolean isGoBack = false;
                                    switch (input) {
                                        case "1"://新增图书
                                            System.out.print("请输入书名：");
                                            String addBookName = scanner.nextLine();
                                            System.out.print("请输入书的价格：");
                                            float addBookPrice = Float.parseFloat(scanner.nextLine());
                                            System.out.print("请输入书的类型：");
                                            String addBookType = scanner.nextLine();
                                            System.out.print("请输入书的作者：");
                                            String addBookAuthor = scanner.nextLine();
                                            System.out.print("请输入书的出版社：");
                                            String addBookPress = scanner.nextLine();
                                            System.out.print("请输入书的借阅状态（可借阅 or 已借阅）：");
                                            String addBookState = scanner.nextLine();

                                            //把 ”已借阅 “ 的图书移至 borrow 表
                                            String borrowCheckISBN = null;
                                            if (addBookState.equals("已借阅")) {
                                                System.out.print("请输入该图书的ISBN，注意不能与已有图书的ISBN重复！：");
                                                borrowCheckISBN = scanner.nextLine();
                                                //确认是否重复
                                                String s = "select * from tb_book where ISBN = '" + borrowCheckISBN + "' ;";
                                                ResultSet resultSet = statement.executeQuery(s);
                                                if (resultSet.next()) {
                                                    System.out.println("您输入的ISBN号重复！此ISBN已被使用，请再次输入！");
                                                    break;
                                                } else {
                                                    System.out.println("该ISBN没有重复，可以使用！");
                                                    // 输入借阅时间
                                                    System.out.print("请输入您借阅时的时间，即借阅当天时间(yyyy-MM-dd HH:mm:ss):");
                                                    String borrowTimeInput = scanner.nextLine();
                                                    Timestamp borrowTime = Timestamp.valueOf(borrowTimeInput);

                                                    // 输入归还时间
                                                    System.out.print("请输入约定归还时间，即截止时间(yyyy-MM-dd HH:mm:ss):");
                                                    String returnDueTimeInput = scanner.nextLine();
                                                    Timestamp returnDueTime = Timestamp.valueOf(returnDueTimeInput);

                                                    // 插入语句使用 Timestamp 类型
                                                    String insertSql = "insert into tb_borrow values ('" + borrowCheckISBN + "','已借阅','" + borrowTime + "','" + returnDueTime + "',null)";
                                                    statement.executeUpdate(insertSql);

                                                    //将新增加图书录入 book 表中
                                                    String addBookSQL = "insert into tb_book values('" + borrowCheckISBN + "', '" + addBookName + "', "
                                                            + addBookPrice + ", '" + addBookType + "','" + addBookAuthor + "','" + addBookPress + "','" + addBookState + "');";

                                                    statement.executeUpdate(addBookSQL);
                                                }
                                            }else {
                                                //将新增加图书录入 book 表中
                                                String addBookSQL = "insert into tb_book values(null, '" + addBookName + "', "
                                                        + addBookPrice + ", '" + addBookType + "','" + addBookAuthor + "','" + addBookPress + "','" + addBookState + "');";

                                                statement.executeUpdate(addBookSQL);
                                            }
                                            System.out.println("新增成功！");
                                            break;
                                        case "2"://删除图书
                                            System.out.print("请输入要图书的ISBN：");
                                            String deleteBookISBN = scanner.nextLine();

                                            String deleteBookSQL = "delete from tb_book where ISBN = " + deleteBookISBN;

                                            statement.executeUpdate(deleteBookSQL);
                                            System.out.println("删除成功！");
                                            break;
                                        case "3"://修改图书
                                            System.out.print("请输入ISBN：");
                                            String updateBookISBN = scanner.nextLine();
                                            System.out.print("请输入书名：");
                                            String updateBookName = scanner.nextLine();
                                            System.out.print("请输入价格：");
                                            float updateBookPrice = Float.parseFloat(scanner.nextLine());
                                            System.out.print("请输入类型：");
                                            String updateBookType = scanner.nextLine();
                                            System.out.print("请输入作者：");
                                            String updateBookAuthor = scanner.nextLine();
                                            System.out.print("请输入出版社：");
                                            String updateBookPress = scanner.nextLine();
                                            System.out.print("请输入借阅状态（可借阅 or 已借阅）：");
                                            String updateBookState = scanner.nextLine();

                                            String updateBookSQL = "update tb_book set book_name = '" + updateBookName + "', price = " + updateBookPrice + ", type = '" + updateBookType + "'," +
                                                    "author='" + updateBookAuthor + "',press='" + updateBookPress + "',state='" + updateBookState + "' where ISBN = " + updateBookISBN + ";";

                                            statement.executeUpdate(updateBookSQL);
                                            System.out.println("修改成功！");
                                            break;
                                        case "4": // 查找图书
                                            System.out.print("请输入要查询书本的ISBN:");
                                            String searchBookISBN = scanner.nextLine();
                                            String searchBookSQL = "select * from tb_book where ISBN = " + searchBookISBN;
                                            ResultSet searchBookResult = statement.executeQuery(searchBookSQL);
                                            System.out.println("该图书信息如下:");
                                            if (searchBookResult.next()) {
                                                System.out.println("ISBN:" + searchBookResult.getInt(1)
                                                        + ",书名:" + searchBookResult.getString(2)
                                                        + ",价格:" + searchBookResult.getString(3)
                                                        + ",类型:" + searchBookResult.getString(4)
                                                        + ",作者:" + searchBookResult.getString(5)
                                                        + ",出版社:" + searchBookResult.getString(6)
                                                        + ",状态:" + searchBookResult.getString(7));
                                            }
                                            break;
                                        case "5"://查看全部
                                            ResultSet selectBookResult = statement.executeQuery("select * from tb_book;");
                                            System.out.println("数据库中图书信息如下：");
                                            while (selectBookResult.next()) {
                                                System.out.println("ISBN：" + selectBookResult.getInt(1)
                                                        + "，书名：" + selectBookResult.getString(2)
                                                        + "，价格：" + selectBookResult.getFloat(3)
                                                        + "，类型：" + selectBookResult.getString(4)
                                                        + "，作者：" + selectBookResult.getString(5)
                                                        + "，出版社：" + selectBookResult.getString(6)
                                                        + "，状态：" + selectBookResult.getString(7));
                                            }
                                            break;
                                        case "6"://返回上一级
                                            isGoBack = true;
                                            break;
                                        case "7"://退出登录
                                            System.out.println("您已成功退出登录");
                                            break hh;// ;
                                        default:
                                            System.out.println("输入有误，请重新输入！");
                                    }
                                    if (isGoBack) {
                                        break;
                                    }
                                }
                                break;
                            case "3": //借阅管理
                                while(true) {
                                    System.out.println("****** 借阅系统 ******");
                                    System.out.println("1. 借阅查询");
                                    System.out.println("2. 显示全部");
                                    System.out.println("3. 借阅书籍");
                                    System.out.println("4. 归还书籍");
                                    System.out.println("5. 返回上一级");
                                    System.out.println("6. 退出");
                                    System.out.println("*********************");

                                    System.out.print("请选择:");
                                    String choice = scanner.nextLine();
                                    boolean isGoBack = false;
                                    switch(choice) {
                                        case "1":  // 借阅查询
                                            System.out.print("请输入要查询的ISBN:");
                                            String ISBN =scanner.nextLine();  // 等待用户输入ISBN
                                            String s = ("select * from tb_borrow where ISBN = " + ISBN);
                                            ResultSet searchBookResult = statement.executeQuery(s);
                                            // 查询逻辑
                                            if(searchBookResult.next()) {
                                                System.out.println("ISBN:" + searchBookResult.getString(1) +
                                                        ",状态:" + searchBookResult.getString(2) +
                                                        ",借阅时间:" + searchBookResult.getTimestamp(3) +
                                                        ",应归还时间:" + searchBookResult.getTimestamp(4) +
                                                        ",实际归还时间:" + searchBookResult.getTimestamp(5));
                                            }else {
                                                String checkBookSql = ("select * from tb_book where ISBN = " + ISBN);
                                                ResultSet searchBookResult1 = statement.executeQuery(checkBookSql);
                                                if(searchBookResult1.next()) {
                                                    System.out.print("该书存在库中！");
                                                    String searchBookSQL = "select * from tb_book where ISBN = " + ISBN;
                                                    ResultSet checkBookSql1 = statement.executeQuery(searchBookSQL);
                                                    System.out.println("该图书信息如下:");
                                                    if(checkBookSql1.next()) {
                                                        System.out.println("ISBN:" + checkBookSql1.getInt(1)
                                                                + ",书名:" + checkBookSql1.getString(2)
                                                                + ",价格:" + checkBookSql1.getString(3)
                                                                + ",类型:" + checkBookSql1.getString(4)
                                                                + ",作者:" + checkBookSql1.getString(5)
                                                                + ",出版社:" + checkBookSql1.getString(6)
                                                                + ",状态:" + checkBookSql1.getString(7));
                                                    }
                                                }else {
                                                    System.out.println("您要查阅的书籍在没有库中！请确保您要查阅的书籍在本馆里存在！");
                                                }
                                            }
                                            break;
                                        case "2":
                                            // 显示全部
                                            ResultSet selectBookResult = statement.executeQuery("select * from tb_borrow;");
                                            System.out.println("数据库中书籍借阅信息如下：");
                                            while (selectBookResult.next()) {
                                                System.out.println("ISBN:" + selectBookResult.getString(1) +
                                                        ",状态:" + selectBookResult.getString(2) +
                                                        ",借阅时间:" + selectBookResult.getTimestamp(3) +
                                                        ",应归还时间:" + selectBookResult.getTimestamp(4) +
                                                        ",实际归还时间:" + selectBookResult.getTimestamp(5));
                                            }
                                            break;
                                        case "3":
                                            // 借阅书籍
                                            //是否存在
                                            System.out.print("请输入要借阅书籍的ISBN:");
                                            String borrowBookIsbn =scanner.nextLine();  // 等待用户输入ISBN
                                            String borrowBookSql = ("select * from tb_book where ISBN = " + borrowBookIsbn);
                                            ResultSet searchBookResult1 = statement.executeQuery(borrowBookSql);
                                            if(searchBookResult1.next()) {
                                                System.out.println("该书存在！");
                                                //是否已借阅
                                                String checkBorrowedSql = "select state from tb_book where ISBN = '" + borrowBookIsbn + "'";
                                                ResultSet checkResult = statement.executeQuery(checkBorrowedSql);
                                                if (checkResult.next()) {
                                                    String state = checkResult.getString(1);
                                                    if (state.equals("已借阅")) {
                                                        System.out.println("不幸的是，该书籍已被借阅！请等该书籍归还后再借阅！");
                                                    } else {
                                                        // 输入借阅时间
                                                        System.out.print("请输入您借阅时的时间，即借阅当天时间(yyyy-MM-dd HH:mm:ss):");
                                                        String borrowTimeInput = scanner.nextLine();
                                                        Timestamp borrowTime = Timestamp.valueOf(borrowTimeInput);

                                                        // 输入归还时间
                                                        System.out.print("请输入约定归还时间，即截止时间(yyyy-MM-dd HH:mm:ss):");
                                                        String returnDueTimeInput = scanner.nextLine();
                                                        Timestamp returnDueTime = Timestamp.valueOf(returnDueTimeInput);

                                                        // 插入语句使用 Timestamp 类型
                                                        String insertSql = "insert into tb_borrow values ('" + borrowBookIsbn +"','已借阅','" + borrowTime + "','" + returnDueTime + "',null)";
                                                        statement.executeUpdate(insertSql);

                                                        //确认图书信息
                                                        System.out.println("请确认您借阅书籍的信息:");
                                                        String searchBookSQL = "select * from tb_book where ISBN = '" + borrowBookIsbn + "'";
                                                        ResultSet resultSet = statement.executeQuery(searchBookSQL);
                                                        if(resultSet.next()) {
                                                            System.out.println("ISBN:" + resultSet.getInt(1)
                                                                    + ",书名:" + resultSet.getString(2)
                                                                    + ",价格:" + resultSet.getString(3)
                                                                    + ",类型:" + resultSet.getString(4)
                                                                    + ",作者:" + resultSet.getString(5)
                                                                    + ",出版社:" + resultSet.getString(6)
                                                                    + ",状态:" + resultSet.getString(7));
                                                        }

                                                        // 更新状态
                                                        String updateStateSql = "update tb_book set state = '已借阅' where ISBN = '" + borrowBookIsbn + "'";
                                                        statement.executeUpdate(updateStateSql);
                                                        System.out.println("ISBN为"+borrowBookIsbn+"的书籍，借阅成功!");

                                                        //逾期每天需支付书籍价格5%的费用
                                                        String priceSql = "select price from tb_book where ISBN = '" + borrowBookIsbn + "'";
                                                        ResultSet priceResult = statement.executeQuery(priceSql);
                                                        if(priceResult.next()) {
                                                            //获取价格
                                                            int price = priceResult.getInt("price");
                                                            // 转为小数计算
                                                            float penalty = price * 0.05f;
                                                            System.out.println("温馨提醒：逾期未还,需支付书籍价格5%的逾期费用:" + penalty + "元/天");
                                                        }
                                                    }
                                                }
                                            }else {
                                                System.out.println("该书不存在！");
                                            }
                                            break;
                                        case "4":
                                            // 输入归还书籍 ISBN
                                            System.out.print("请输入要归还的书籍的ISBN:");
                                            String returnBookISBN = scanner.nextLine();
                                            System.out.print("请输入归还书籍的当天日期，即实际归还时间(yyyy-MM-dd HH:mm:ss):");
                                            String returnRealTime = scanner.nextLine();
                                            Timestamp returnTime = Timestamp.valueOf(returnRealTime);

                                            //判断是否逾期归还，是否要支付逾期费用
                                            String priceSql = "select price from tb_book where ISBN = '" + returnBookISBN + "'";
                                            ResultSet priceResult = statement.executeQuery(priceSql);
                                            if(priceResult.next()) {
                                                //获取价格
                                                int price = priceResult.getInt("price");
                                                // 转为小数计算
                                                float penalty = price * 0.05f;

                                                //从数据库读取应归还时间
                                                String dueTimeSql = "select return_due_time from tb_borrow where ISBN = " + returnBookISBN;
                                                ResultSet dueTimeResult = statement.executeQuery(dueTimeSql);
                                                Timestamp returnDueTime = null;//声明
                                                if(dueTimeResult.next()) {//真正赋值
                                                    returnDueTime = dueTimeResult.getTimestamp("return_due_time");
                                                }
                                                //计算逾期天数
                                                long diff = 0;
                                                if (returnDueTime != null) {
                                                    diff = returnTime.getTime() - returnDueTime.getTime();
                                                }
                                                long daysOverdue = diff / (1000 * 60 * 60 * 24);

                                                //输出逾期归还天数
                                                System.out.println("很遗憾！您已逾期"+daysOverdue+"天！按照书籍价格5%的逾期费用计算");

                                                //输出总支付费用
                                                float totalPenalty = daysOverdue * penalty;
                                                System.out.println("您需支付逾期费用:" + totalPenalty +"元！请您尽快到图书管理中心缴纳，否则会影响您的借阅信誉！谢谢配合。");

                                                // 从 tb_borrow 删除记录
                                                String deleteSql = "delete from tb_borrow where ISBN = '" + returnBookISBN + "'";
                                                statement.executeUpdate(deleteSql);

                                                // 更新书籍状态
                                                String updateStateSql = "update tb_book set state = '可借阅' where ISBN = '" + returnBookISBN + "'";
                                                statement.executeUpdate(updateStateSql);

                                            } else {
                                                //如果不逾期
                                                System.out.println("您在约定归还日期截止前归还此书，无需支付任何费用！");
                                                System.out.println("ISBN为"+returnBookISBN+"的书籍,归还成功!");
                                                System.out.println("感谢您的使用！");

                                                // 从 tb_borrow 删除记录
                                                String deleteSql = "delete from tb_borrow where ISBN = '" + returnBookISBN + "'";
                                                statement.executeUpdate(deleteSql);

                                                // 更新书籍状态
                                                String updateStateSql = "update tb_book set state = '可借阅' where ISBN = '" + returnBookISBN + "'";
                                                statement.executeUpdate(updateStateSql);
                                            }
                                            break;
                                        case "5":
                                            isGoBack =true;
                                            break;
                                        case "6":
                                            break hh;
                                        default:
                                            System.out.println("无效的选择,请重试。");
                                    }
                                    if (isGoBack)break;
                                }
                                break ;
                            case "4": // 退出登录
                                break hh;
                            default:
                                System.out.println("输入有误，请重新输入！");
                        }
                    }
                }else {
                    System.out.println("" + result.getString(2) + "同学，登录成功！");
                    hh:while(true) {
                        System.out.println("****** 借阅系统 ******");
                        System.out.println("1. 借阅查询");
                        System.out.println("2. 显示全部已借阅书籍");
                        System.out.println("3. 借阅书籍");
                        System.out.println("4. 归还书籍");
                        System.out.println("5. 显示全部书籍信息");
                        System.out.println("6. 返回上一级");
                        System.out.println("7. 退出");
                        System.out.println("*********************");

                        System.out.print("请选择:");
                        String choice = scanner.nextLine();
                        boolean isGoBack = false;
                        switch(choice) {
                            case "1":  // 借阅查询
                                System.out.print("请输入要查询的ISBN:");
                                String ISBN =scanner.nextLine();  // 等待用户输入ISBN
                                String s = ("select * from tb_borrow where ISBN = " + ISBN);
                                ResultSet searchBookResult = statement.executeQuery(s);
                                // 查询逻辑
                                if(searchBookResult.next()) {
                                    System.out.println("ISBN:" + searchBookResult.getString(1) +
                                            ",状态:" + searchBookResult.getString(2) +
                                            ",借阅时间:" + searchBookResult.getTimestamp(3) +
                                            ",应归还时间:" + searchBookResult.getTimestamp(4) +
                                            ",实际归还时间:" + searchBookResult.getTimestamp(5));
                                }else {
                                    String checkBookSql = ("select * from tb_book where ISBN = " + ISBN);
                                    ResultSet searchBookResult1 = statement.executeQuery(checkBookSql);
                                    if(searchBookResult1.next()) {
                                        System.out.print("该书存在库中！");
                                        String searchBookSQL = "select * from tb_book where ISBN = " + ISBN;
                                        ResultSet checkBookSql1 = statement.executeQuery(searchBookSQL);
                                        System.out.println("该图书信息如下:");
                                        if(checkBookSql1.next()) {
                                            System.out.println("ISBN:" + checkBookSql1.getInt(1)
                                                    + ",书名:" + checkBookSql1.getString(2)
                                                    + ",价格:" + checkBookSql1.getString(3)
                                                    + ",类型:" + checkBookSql1.getString(4)
                                                    + ",作者:" + checkBookSql1.getString(5)
                                                    + ",出版社:" + checkBookSql1.getString(6)
                                                    + ",状态:" + checkBookSql1.getString(7));
                                        }
                                    }else {
                                        System.out.println("您要查阅的书籍在没有库中！请确保您要查阅的书籍在本馆里存在！");
                                    }
                                }
                                break;
                            case "2":
                                // 显示全部
                                ResultSet selectBookResult = statement.executeQuery("select * from tb_borrow;");
                                System.out.println("数据库中书籍借阅信息如下：");
                                while (selectBookResult.next()) {
                                    System.out.println("ISBN:" + selectBookResult.getString(1) +
                                            ",状态:" + selectBookResult.getString(2) +
                                            ",借阅时间:" + selectBookResult.getTimestamp(3) +
                                            ",应归还时间:" + selectBookResult.getTimestamp(4) +
                                            ",实际归还时间:" + selectBookResult.getTimestamp(5));
                                }
                                break;
                            case "3":
                                // 借阅书籍
                                //是否存在
                                System.out.print("请输入要借阅书籍的ISBN:");
                                String borrowBookIsbn =scanner.nextLine();  // 等待用户输入ISBN
                                String borrowBookSql = ("select * from tb_book where ISBN = " + borrowBookIsbn);
                                ResultSet searchBookResult1 = statement.executeQuery(borrowBookSql);
                                if(searchBookResult1.next()) {
                                    System.out.println("该书存在！");
                                    //是否已借阅
                                    String checkBorrowedSql = "select state from tb_book where ISBN = '" + borrowBookIsbn + "'";
                                    ResultSet checkResult = statement.executeQuery(checkBorrowedSql);
                                    if (checkResult.next()) {
                                        String state = checkResult.getString(1);
                                        if (state.equals("已借阅")) {
                                            System.out.println("不幸的是，该书籍已被借阅！请等该书籍归还后再借阅！");
                                        } else {
                                            // 输入借阅时间
                                            System.out.print("请输入您借阅时的时间，即借阅当天时间(yyyy-MM-dd HH:mm:ss):");
                                            String borrowTimeInput = scanner.nextLine();
                                            Timestamp borrowTime = Timestamp.valueOf(borrowTimeInput);

                                            // 输入归还时间
                                            System.out.print("请输入约定归还时间，即截止时间(yyyy-MM-dd HH:mm:ss):");
                                            String returnDueTimeInput = scanner.nextLine();
                                            Timestamp returnDueTime = Timestamp.valueOf(returnDueTimeInput);

                                            // 插入语句使用 Timestamp 类型
                                            String insertSql = "insert into tb_borrow values ('" + borrowBookIsbn +"','已借阅','" + borrowTime + "','" + returnDueTime + "',null)";
                                            statement.executeUpdate(insertSql);

                                            //确认图书信息
                                            System.out.println("请确认您借阅书籍的信息:");
                                            String searchBookSQL = "select * from tb_book where ISBN = '" + borrowBookIsbn + "'";
                                            ResultSet resultSet = statement.executeQuery(searchBookSQL);
                                            if(resultSet.next()) {
                                                System.out.println("ISBN:" + resultSet.getInt(1)
                                                        + ",书名:" + resultSet.getString(2)
                                                        + ",价格:" + resultSet.getString(3)
                                                        + ",类型:" + resultSet.getString(4)
                                                        + ",作者:" + resultSet.getString(5)
                                                        + ",出版社:" + resultSet.getString(6)
                                                        + ",状态:" + resultSet.getString(7));
                                            }

                                            // 更新状态
                                            String updateStateSql = "update tb_book set state = '已借阅' where ISBN = '" + borrowBookIsbn + "'";
                                            statement.executeUpdate(updateStateSql);
                                            System.out.println("ISBN为"+borrowBookIsbn+"的书籍，借阅成功!");

                                            //逾期每天需支付书籍价格5%的费用
                                            String priceSql = "select price from tb_book where ISBN = '" + borrowBookIsbn + "'";
                                            ResultSet priceResult = statement.executeQuery(priceSql);
                                            if(priceResult.next()) {
                                                //获取价格
                                                int price = priceResult.getInt("price");
                                                // 转为小数计算
                                                float penalty = price * 0.05f;
                                                System.out.println("温馨提醒：逾期未还,需支付书籍价格5%的逾期费用:" + penalty + "元/天");
                                            }
                                        }
                                    }
                                }else {
                                    System.out.println("该书不存在！");
                                }
                                break;
                            case "4":
                                // 输入归还书籍 ISBN
                                System.out.print("请输入要归还的书籍的ISBN:");
                                String returnBookISBN = scanner.nextLine();
                                System.out.print("请输入归还书籍的当天日期，即实际归还时间(yyyy-MM-dd HH:mm:ss):");
                                String returnRealTime = scanner.nextLine();
                                Timestamp returnTime = Timestamp.valueOf(returnRealTime);

                                //判断是否逾期归还，是否要支付逾期费用
                                String priceSql = "select price from tb_book where ISBN = '" + returnBookISBN + "'";
                                ResultSet priceResult = statement.executeQuery(priceSql);
                                if(priceResult.next()) {
                                    //获取价格
                                    int price = priceResult.getInt("price");
                                    // 转为小数计算
                                    float penalty = price * 0.05f;

                                    //从数据库读取应归还时间
                                    String dueTimeSql = "select return_due_time from tb_borrow where ISBN = " + returnBookISBN;
                                    ResultSet dueTimeResult = statement.executeQuery(dueTimeSql);
                                    Timestamp returnDueTime = null;//声明
                                    if(dueTimeResult.next()) {//真正赋值
                                        returnDueTime = dueTimeResult.getTimestamp("return_due_time");
                                    }
                                    //计算逾期天数
                                    long diff = 0;
                                    if (returnDueTime != null) {
                                        diff = returnTime.getTime() - returnDueTime.getTime();
                                    }
                                    long daysOverdue = diff / (1000 * 60 * 60 * 24);

                                    //输出逾期归还天数
                                    System.out.println("很遗憾！您已逾期"+daysOverdue+"天！按照书籍价格5%的逾期费用计算");

                                    //输出总支付费用
                                    float totalPenalty = daysOverdue * penalty;
                                    System.out.println("您需支付逾期费用:" + totalPenalty +"元！请您尽快到图书管理中心缴纳，否则会影响您的借阅信誉！谢谢配合。");

                                    // 从 tb_borrow 删除记录
                                    String deleteSql = "delete from tb_borrow where ISBN = '" + returnBookISBN + "'";
                                    statement.executeUpdate(deleteSql);

                                    // 更新书籍状态
                                    String updateStateSql = "update tb_book set state = '可借阅' where ISBN = '" + returnBookISBN + "'";
                                    statement.executeUpdate(updateStateSql);

                                } else {
                                    //如果不逾期
                                    System.out.println("您在约定归还日期截止前归还此书，无需支付任何费用！");
                                    System.out.println("ISBN为"+returnBookISBN+"的书籍,归还成功!");
                                    System.out.println("感谢您的使用！");

                                    // 从 tb_borrow 删除记录
                                    String deleteSql = "delete from tb_borrow where ISBN = '" + returnBookISBN + "'";
                                    statement.executeUpdate(deleteSql);

                                    // 更新书籍状态
                                    String updateStateSql = "update tb_book set state = '可借阅' where ISBN = '" + returnBookISBN + "'";
                                    statement.executeUpdate(updateStateSql);
                                }
                                break;
                            case "5"://查看全部
                                ResultSet resultSet = statement.executeQuery("select * from tb_book;");
                                System.out.println("数据库中图书信息如下：");
                                while (resultSet.next()) {
                                    System.out.println("ISBN：" + resultSet.getInt(1)
                                            + "，书名：" + resultSet.getString(2)
                                            + "，价格：" + resultSet.getFloat(3)
                                            + "，类型：" + resultSet.getString(4)
                                            + "，作者：" + resultSet.getString(5)
                                            + "，出版社：" + resultSet.getString(6)
                                            + "，状态：" + resultSet.getString(7));
                                }
                                break;
                            case "6":
                                isGoBack =true;
                                break;
                            case "7":
                                break hh;
                            default:
                                System.out.println("无效的选择,请重试。");
                        }
                        if (isGoBack)break;
                    }
                }
                break;
            } else {
                System.out.print("你输入的账号或者密码没有存在库中，是否要注册？（yes or no）：");
                String select = scanner.nextLine();
                if (select.equals("yes")){
                    System.out.print("请输入您的姓名：");
                    String newname = scanner.nextLine();
                    System.out.print("请输入您的性别：");
                    String newgender = scanner.nextLine();
                    System.out.print("请输入您的年龄：");
                    String newage = scanner.nextLine();
                    System.out.print("请输入新的账号：");
                    String newaccount = scanner.nextLine();
                    System.out.print("请输入您的密码：");
                    String newpassword = scanner.nextLine();
                    String addNewAccountSql = ("insert into tb_user values(null,'" + newname + "','" + newgender + "','" + newage + "','" + newaccount + "','" + newpassword + "') ;");
                    statement.executeUpdate(addNewAccountSql);
                    System.out.println("写入成功！请登录：");
                }else {
                    System.out.println("账号或密码错误，请重新输入！");
                }
            }
        }
    }
}