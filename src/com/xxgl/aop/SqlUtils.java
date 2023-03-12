package com.xxgl.aop;
import com.fh.util.PageData;

import net.sf.json.JSONObject;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public class SqlUtils {

    /**
     * 参数解析成字符串
     * @param a
     * @return
     */
    public static String arrayToString(Object[] a) {
        if (a == null)
            return "null";

        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0;; i++) {
            if (a[i] instanceof Object[]) {
                b.append(arrayToString((Object[]) a[i]));
            } else {
                b.append(String.valueOf(a[i]));
            }
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }

    /**
     * 参数解析成对象
     * @param object
     * @return
     */
    public static Object wrapCollection(Object object) {
        DefaultSqlSession.StrictMap map;
        if (object instanceof Collection) {
            map = new DefaultSqlSession.StrictMap();
            map.put("collection", object);
            if (object instanceof List) {
                map.put("list", object);
            }

            return map;
        } else if (object != null && object.getClass().isArray()) {
            map = new DefaultSqlSession.StrictMap();
            map.put("array", object);
            return map;
        } else {
            return object;
        }
    }

    /**
     * 获取aop中的SQL语句
     * @param point
     * @return
     * @throws IllegalAccessException
     */
    public static String getMybatisSql(JoinPoint point,SqlSessionFactory sqlSessionFactory) throws IllegalAccessException {
        Map<String,Object> map = new HashMap<>();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Configuration configuration = sqlSessionFactory.getConfiguration();
        Object[] objects = point.getArgs(); //获取实参
        MappedStatement mappedStatement = configuration.getMappedStatement(objects[0].toString());
        BoundSql boundSql = mappedStatement.getBoundSql(wrapCollection(objects[1]));
        return showSql(configuration,boundSql);
    }

    /**
     * 解析BoundSql，生成不含占位符的SQL语句
     * @param configuration
     * @param boundSql
     * @return
     */
    private  static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    String[] s =  metaObject.getObjectWrapper().getGetterNames();
                    s.toString();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }

    /**
     * 若为字符串或者日期类型，则在参数两边添加''
     * @param obj
     * @return
     */
    public static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

    /**
     * 注释解析
     * @param operate
     * @return
     */
    public static String getParameterComments(PageData pd,List<PageData> fieldList) throws ParseException {
        String fieldAndAnnotation = null;// 字段加注解
        String columnName = "";// 字段名
        String columnComment = "";// 字段注释
        //JSONObject jsonObj = null;
        //jsonObj = JSONObject.fromObject(operate);
       // JSONArray jsonObj = JSONArray.parseArray(operate) ;
        String str="";
        for (PageData pdata : fieldList) {
            fieldAndAnnotation = "";// 字段加注释
            columnName = pdata.get("COLUMN_NAME") != null ? pdata
                    .get("COLUMN_NAME").toString() : "";
            columnComment = pdata.get("COMMENTS") != null ? pdata
                    .get("COMMENTS").toString() : pdata.get("COLUMN_NAME") != null ? pdata
                    .get("COLUMN_NAME").toString() : "";
            fieldAndAnnotation = columnComment;
            /*if(operate.toUpperCase().contains("{"+columnName.toUpperCase() + "=")){
           	 	operate = operate.toUpperCase().replaceAll("\\{"+columnName.toUpperCase() + "=","\\{"+fieldAndAnnotation + "=");
            }else if(operate.toUpperCase().contains(","+columnName.toUpperCase() + "=")){
           	 	operate = operate.toUpperCase().replaceAll(","+columnName.toUpperCase() + "=",","+fieldAndAnnotation + "=");
            }else if (operate.toUpperCase().contains(columnName.toUpperCase() + "=")) {
               operate = operate.toUpperCase().replaceAll(columnName.toUpperCase() + "=",fieldAndAnnotation + "=");
            }*/
            
            if(!str.equals("")){
            	str=str+",";
            }
            if(pd!=null){
            	if(columnComment==null||columnComment.equals("")){
            		columnComment=columnName;
            	}
            	if(columnName.equals("id")){
            		columnName="主键";
            	}
            	str=str+columnComment+"="+(pd.get(columnName)==null?"":pd.get(columnName));
            }
            
            /*if(pdata.get("TYPE").equals("DATE")){//包含时间
               int start =operate.indexOf(fieldAndAnnotation)+1;
               int index=0;
               String tempStr=operate.substring(start);
               if(tempStr.indexOf(",")!=-1){//不是最后一个参数
                   index=tempStr.indexOf(",");
               }else{//最后一个参数
                   index=tempStr.length()-2;
               }
               String dateStr=tempStr.substring(0,index);
               SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss z yyyy", Locale.ENGLISH);
               Date date = sf.parse(dateStr);
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               String result =tempStr;
               result.replaceAll(dateStr,sdf.format(date));
               operate.replaceAll(tempStr,result);
            }
*/
        }
        str=str.replace("=NULL", "");
        return str;
    }


}
