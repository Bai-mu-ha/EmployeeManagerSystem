package com.hengbai.bean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private int id;             // 员工编号
    private String name;        // 员工姓名
    private int age;            // 员工年龄
    private String sex;         // 员工性别
    private String job;         // 员工职位
    private String phone;       // 员工电话
    private String email;       // 员工邮箱
    private String entryTime;   //入职时间
    private double salary;      //薪水
}