package org.bank.utils;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CommonUtils {

    public static void copyProperties(Object source, Object target) throws Exception{
        Class sourceClass=Class.forName(source.getClass().getName());
        Class targetClass=Class.forName(target.getClass().getName());
        Field[] sourceFields=sourceClass.getDeclaredFields();
        Field[] targetFields=targetClass.getDeclaredFields();
        CommonUtils commonUtils=new CommonUtils();
        for(Field sourceField:sourceFields){
            Object value=commonUtils.invokeGetMethod(source,sourceField.getName(),null);
            boolean flag=false;
            if(value instanceof String){
                if(value!=null && !"".equals(((String) value).trim())){
                    flag=true;
                }
            }else{
                if(value!=null){
                    flag=true;
                }
            }
            if(flag){
                for(Field targetField:targetFields){
                    if(sourceField.getName().equals(targetField.getName())){
                        Object[] objects=new Object[1];
                        objects[0]=value;
                        commonUtils.invokeSetMethod(target,targetField.getName(),objects);
                    }
                }
            }
        }
        
    }

    private  Object invokeGetMethod(Object filterBean, String filedName, Object[] args){
        String methodName=filedName.substring(0,1).toUpperCase()+filedName.substring(1);
        Method method=null;
        try{
            method=Class.forName(filterBean.getClass().getName()).getDeclaredMethod("get"+methodName);
            return method.invoke(filterBean);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    private  Object invokeSetMethod(Object targetBean, String filedName, Object[] args){
        String methodName=filedName.substring(0,1).toUpperCase()+filedName.substring(1);
        Method method=null;
        try{
            Class[] parameterTypes=new Class[1];
            Class targetClass=Class.forName(targetBean.getClass().getName());
            Field field=targetClass.getDeclaredField(filedName);
            parameterTypes[0]=field.getType();
            method=targetClass.getDeclaredMethod("set"+methodName,parameterTypes);
            return method.invoke(targetBean,args);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }

    }
}
