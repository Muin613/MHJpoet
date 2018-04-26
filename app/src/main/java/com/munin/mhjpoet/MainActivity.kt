package com.munin.mhjpoet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

/**
 *
 *MethodSpec 代表一个构造函数或方法声明。
TypeSpec 代表一个类，接口，或者枚举声明
FieldSpec 代表一个成员变量，一个字段声明。
JavaFile包含一个顶级类的Java文件。
ParameterSpec 用来创建参数
AnnotationSpec 用来创建注解
TypeName 类型，如在添加返回值类型是使用 TypeName.VOID
ClassName 用来包装一个类
$L代表的是字面量
$S for Strings
$N for Names(我们自己生成的方法名或者变量名等等)
$T for Types
http://note.youdao.com/noteshare?id=d3737f65df05b1bec5e43c70203055e3&sub=6A46317302E742ED9BA1476D5E2529EF
 *
 *
 * */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        `MuninMain$$YM`()
    }
}
