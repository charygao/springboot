package com.yq.jsdemo;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Simple to Introduction
 * className: JavaJSDemo
 *
 * @author EricYang
 * @version 2018/12/22 9:51
 */

@Slf4j
public class JavaJSListDemo {

    private static final String JS_ENGINE_NAME= "nashorn";
    private final ScriptEngineManager sem = new ScriptEngineManager();
    private final ScriptEngine engine = sem.getEngineByName(JS_ENGINE_NAME);

    public static void main(String[] args) {
        JavaJSListDemo demo = new JavaJSListDemo();

        demo.invokeFunctionByFileWithParams();
        //demo.invokeFunctionByFileWithListParams();
    }

    private void invokeFunctionByFileWithParams() {
        log.info("---          invokeFunctionWithParams         ---" );
        try {
            log.info("Current dir={}", System.getProperty("user.dir"));
            File file = new File("./javademo/src/main/resources/demoWithParams.js");
            Reader reader = Files.newBufferedReader(file.toPath(), Charset.defaultCharset());

            Object obj1 = engine.eval(reader);

            Invocable jsInvoke = (Invocable) engine;
            JSONObject user = new JSONObject();
            user.put("name", "张三");
            user.put("age", 18);
            Object obj2 = jsInvoke.invokeFunction("myFunc", user, 3);
            //Object getUpdatedUser = engine.eval("print(user)");
            //脚本中修改了user对象，可以在java中直接体现
            log.info("script={}, result={}, user={}", obj1, obj2, user);
        }
        catch(Exception ex) {
            log.warn("exception", ex);
        }
    }

    private void invokeFunctionByFileWithListParams() {
        log.info("---         With List Params         ---" );
        try {
            log.info("Current dir={}", System.getProperty("user.dir"));
            File file = new File("./javademo/src/main/resources/demoWithListParams.js");
            Reader reader = Files.newBufferedReader(file.toPath(), Charset.defaultCharset());

            Object obj1 = engine.eval(reader);

            Invocable jsInvoke = (Invocable) engine;
            List<Short> dataList = new ArrayList<>();
            //15,01,00,00,00,0x0B,01,03,08,01,83,0x63,0x88,02,03,0xEA,0x1B
            dataList.add((short)0x15);
            dataList.add((short)0x01);
            dataList.add((short)0x00);
            dataList.add((short)0x00);
            dataList.add((short)0x00);
            dataList.add((short)0x0B);
            dataList.add((short)0x01);
            dataList.add((short)0x03);
            dataList.add((short)0x08);

            dataList.add((short)0x01);
            dataList.add((short)0x83);
            dataList.add((short)0x63);
            dataList.add((short)0x88);


            dataList.add((short)0x02);
            dataList.add((short)0x03);
            dataList.add((short)0xEA);
            dataList.add((short)0x1B);

            Object obj2 = jsInvoke.invokeFunction("myFunc", Arrays.toString(dataList.toArray()), "device1234");

            log.info("function={}", obj1);
            log.info("result={}", obj2);
        }
        catch(Exception ex) {
            log.warn("exception", ex);
        }
    }
}

