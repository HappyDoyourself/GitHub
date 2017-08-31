package com.baomidou.springwind.controller;

import com.baomidou.framework.controller.SuperController;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.kisso.common.encrypt.SaltEncoder;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.common.Constant;
import com.baomidou.springwind.entity.Activity;
import com.baomidou.springwind.entity.User;
import com.baomidou.springwind.service.IActivityService;
import com.baomidou.springwind.util.DateUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import static com.sun.tools.doclets.formats.html.markup.HtmlStyle.title;

/**
 * Created by fht on 2017-05-03 19:29.
 */
@Controller
@RequestMapping("activities/")
public class ActivitiesController extends BaseController{

    @Autowired
    private IActivityService activityService;


    @Permission("5001")
    @RequestMapping("/list")
    public String list(Model model) {
        return "/activity/list";
    }

   // @ResponseBody
    @Permission("5001")
    @RequestMapping("query")
    public String query(Model model,HttpServletRequest request){
        Wrapper<Activity> wrapper = new Wrapper<Activity>() {
            @Override
            public String getSqlSegment() {
                return null;
            }
        };
        Page<Activity> page = getPage();
        return jsonPage(activityService.selectPage(page,wrapper.like("title","1")));
    }

    @Permission("5001")
    @RequestMapping("/edit")
    public String edit(Model model, Long id ){
        if ( id != null ) {
            model.addAttribute("activity", activityService.selectById(id));
        }
        model.addAttribute("activityList", activityService.selectList(null));
        return "/activity/edit";
    }

    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/yuding")
    public String yuding(){
        System.out.println("请求成功");
        return "/activity/yuding";
    }

    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/signup")
    public String signup(){
        System.out.println("请求成功");
        return "/activity/signup";
    }

    @ResponseBody
    @Permission("5001")
    @RequestMapping("/getActivityList")
    public String getUserList() {
        Page<Activity> page = getPage();
        return jsonPage(activityService.selectPage(page, null));
    }


    @ResponseBody
    @Permission("5001")
    @RequestMapping(value = "/editActivity")
    public String editActivity(HttpServletRequest request,Activity activity) throws ParseException {
        activity = new Activity();
        boolean rlt = false;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        //获得物理路径webapp所在路径
        String pathRoot = request.getSession().getServletContext().getRealPath("");
        String path="";
        upload.setHeaderEncoding("UTF-8");// 解决乱码关键
        Map<String,String>  map = new HashMap<>();
         try {
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item : list){
                if(item.isFormField()){
                  //注意此处是没有图片的其他属性
                    String value = null;
                    try {
                        value = new String(item.getString("utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    map.put(item.getFieldName(),value);
                }else{
                    //生成uuid作为文件名称
                    String uuid = UUID.randomUUID().toString().replaceAll("-","");
                    //获得文件后缀名称
                    String imageName=item.getName().substring(item.getName().lastIndexOf(".")+1);
                    if(imageName.indexOf("jpg")<0 && imageName.indexOf("png")<0 && imageName.indexOf("gif")<0){
                        continue;
                    }
                    String fileUrl = Constant.FILE_URL;
                    path = fileUrl + uuid+"."+imageName;
                    map.put(item.getFieldName(),Constant.FILE_URL_SQL+uuid+"."+imageName);
                    upload.setHeaderEncoding("UTF-8");
                    try {
                                            //得到上传文件的扩展名
                                              String fileExtName = imageName.substring(imageName.lastIndexOf(".")+1);
                                                 //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
                                           System.out.println("上传的文件的扩展名是："+fileExtName);
                                            //获取item中的上传文件的输入流
                                                 InputStream in = item.getInputStream();
                                                  //得到文件保存的名称
                                                String saveFilename = path;
                                                 //得到文件的保存目录
                                                  String realSavePath = makePath(pathRoot, fileUrl);
                                                  //创建一个文件输出流
                                                   FileOutputStream out = new FileOutputStream(pathRoot+path);
                                                  //创建一个缓冲区
                                                byte buffer[] = new byte[1024];
                                                  //判断输入流中的数据是否已经读完的标识
                                                  int len = 0;
                                                 //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                                                  while((len=in.read(buffer))>0){
                                                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                                                            out.write(buffer, 0, len);
                                                  }
                                                  //关闭输入流
                                                   in.close();
                                                  //关闭输出流
                                                  out.close();
                                                //删除处理文件上传时生成的临时文件
                                              item.delete();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        activity.setBeginTime(DateUtils.toDate(map.get("beginTime"),"yyyy-MM-dd HH:mm:ss") );
        activity.setPlaces(Integer.valueOf(map.get("places")));
        activity.setCity(map.get("city"));
        activity.setContent(map.get("content"));
        activity.setTag(Integer.valueOf(map.get("tag")));
        activity.setDeposit(new BigDecimal(map.get("deposit")));
        activity.setDetailAddress(map.get("detailAddress"));
        activity.setPic(map.get("newPic")==null?map.get("pic"):map.get("newPic"));
        activity.setEndTime(DateUtils.toDate(map.get("endTime"),"yyyy-MM-dd HH:mm:ss"));
        activity.setFullPrice(new BigDecimal(map.get("fullPrice")));
        activity.setTitle(map.get("title"));
        activity.setUpdateTime(new Date());
        long curTime = System.currentTimeMillis();
        long endTime = DateUtils.dateToLong(map.get("endTime"));
        if(endTime >= curTime){
            activity.setState(Constant.ACTIVITY_ON);
        }else{
            activity.setState(Constant.ACTIVITY_OFF);
        }
         if(StringUtils.isNotBlank(map.get("id"))){
             activity.setId(Long.valueOf(map.get("id")));
             rlt = activityService.updateById(activity);
         }else {
             activity.setAddTime(new Date());
             rlt = activityService.insert(activity);
         }
        return callbackSuccess(rlt);
    }

    @ResponseBody
    @Permission("2001")
    @RequestMapping("/delActivity/{id}")
    public String delUser(@PathVariable Integer id) {
        activityService.deleteById(id);
        return Boolean.TRUE.toString();
    }

       private String makePath(String filename,String fileUrl){
               //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
             /*  int hashcode = filename.hashCode();
               int dir1 = hashcode&0xf;  //0--15
              int dir2 = (hashcode&0xf0)>>4;  //0-15
            //构造新的保存目录
             String dir = savePath + "\\" + dir1 + "\\" + dir2;  //upload\2\3  upload\3\5*/
               //File既可以代表文件也可以代表目录
              File file = new File(filename+fileUrl);
             //如果目录不存在
               if(!file.exists()){
                    //创建目录
                      file.mkdirs();
                   }
              return filename+fileUrl;
           }
}
