package com.zijin.dong.entity.bytecode;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ClassFile {

    /* --------------字节码内参数-------------- */

    // 魔数，u4
    private String magic;

    // 次版本，u2
    private String minorVersion;

    // 主版本， u2
    private String majorVersion;

    // 常量池数量，u2
    private Integer constantPoolCount;

    // 常量池，struct
    private ConstantPoolInfo[] constantPoolInfos;

    // 访问标志，u2
    private String accessFlag;

    // 类索引，u2
    private String thisClass;

    // 父类索引，u2
    private String superClass;

    // 接口数量，u2
    private Integer interfaceCount;

    // 接口集，u2
    private Interface[] interfaces;

    // 字段数量，u2
    private Integer fieldCount;

    // 字段集，struct
    private FieldInfo[] fieldInfos;

    // 方法数量，u2
    private Integer methodCount;

    // 方法集，struct
    private MethodInfo[] methodInfos;

    // 属性数量，u2
    private Integer attributeCount;

    // 属性集，struct
    private AttributeInfo[] attributeInfos;

    /* -------------------------------------------- */
    // 属性-字节数集合
    private static final TreeMap<String, Integer> map = new TreeMap<>();

    static {
        map.put("magic", 4);
        map.put("minorVersion", 2);
        map.put("majorVersion", 2);
        map.put("constantPoolCount", 2);

        // 结构体
        map.put("constantPoolInfo", -1);

        map.put("accessFlag", 2);
        map.put("thisClass", 2);
        map.put("superClass", 2);
        map.put("interfaceCount", 2);
        map.put("interfaces", 2);
        map.put("fieldCount", 2);

        // 结构体
        map.put("fieldInfos", -1);

        map.put("methodCount", 2);

        // 结构体
        map.put("methodInfos", -1);

        map.put("attributeCount", 2);

        // 结构体
        map.put("attributeInfos", -1);
    }

    public ClassFile(InputStream in) {

    }
}
