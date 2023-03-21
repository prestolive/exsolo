package cn.exsolo.springmvcext.in;

import cn.hutool.http.ContentType;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author prestolive
 * @date 2023/1/18
 **/
public class PlatParamsRequestWrapper extends HttpServletRequestWrapper {


    /**
     * 请求参数
     */
    private Map<String, String[]> params;

    /**
     * 用于保存读取body中数据
     */
    private byte[] body;

    /**
     * 用于保存读取body中数据
     */
    private String bodyMessage;

    /**
     * 自定义构造方法
     * @param request
     * @throws IOException
     */
    public PlatParamsRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        //参数保存
        this.params = new HashMap<>();
        //初始化参数
        String contentType = request.getContentType();
        contentType=contentType==null?ContentType.JSON.toString():contentType.toLowerCase();
        //如果是application/json
        if (contentType.startsWith(ContentType.JSON.toString())) {
            //解析数据流数据
            saveInputStreamData(request);
            JSONObject parameter = JSONUtil.parseObj(this.getBodyMessage());
            this.addAllParameters(parameter);
        } else if (contentType.startsWith(ContentType.XML.toString())) {
            saveInputStreamData(request);
            JSONObject parameter = JSONUtil.parseFromXml(this.getBodyMessage()).getJSONObject("request");
            this.addAllParameters(parameter);
        } else {
            Enumeration<String> headerNames = request.getParameterNames();
            while (headerNames.hasMoreElements()) {
                String key = headerNames.nextElement();
                this.addParameter(key, request.getParameter(key));
            }
        }
    }


    /**
     * 覆盖（重写）父类的方法
     * @return
     * @throws IOException
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 覆盖（重写）父类的方法
     * @return
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(this.body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }
        };
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return new Vector(params.keySet()).elements();
    }

    @Override
    public String getParameter(String name) {
        String[] values = this.params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = this.params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values;
    }


    /**
     * 获取body中的数据
     * @return
     */
    public byte[] getBody() {
        return this.body;
    }

    /**
     * 把处理后的参数放到body里面
     * @param body
     */
    public void setBody(byte[] body) {
        this.body = body;
    }

    /**
     * 获取处理过的参数数据
     * @return
     */
    public String getBodyMessage() {
        return this.bodyMessage;
    }

    /**
     * 设置参数
     * @param otherParams
     */
    private void addAllParameters(Map<String, Object> otherParams) {
        for (Map.Entry<String, Object> entry : otherParams.entrySet()) {
            addParameter(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 设置参数
     * @param name
     * @param value
     */
    private void addParameter(String name, Object value) {
        if (this.params == null) {
            this.params = new HashMap<>();
        }
        if (value != null) {
            if (value instanceof String[]) {
                this.params.put(name, (String[]) value);
            } else if (value instanceof String) {
                this.params.put(name, new String[]{(String) value});
            } else {
                this.params.put(name, new String[]{String.valueOf(value)});
            }
        }
    }

    /**
     * 保存请求的InputSteam的数据
     * @param request
     * @throws IOException
     */
    private void saveInputStreamData(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();
        ServletInputStream inputStream = request.getInputStream();
        this.body = new byte[contentLength];
        inputStream.read(this.body, 0, contentLength);
        this.bodyMessage =   new String(this.body, StandardCharsets.UTF_8);
    }

}
