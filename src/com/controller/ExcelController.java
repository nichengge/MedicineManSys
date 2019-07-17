package com.controller;
import com.entity.*;
import com.service.*;
import com.utils.ExcelUtil;
import com.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@Controller
public class ExcelController {

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private SellOrderService sellOrderService;

    @Autowired
    private MedicineTypeService medicineTypeService;

    @Autowired
    private InOrderService inOrderService;

    @Autowired
    private UserService userService;
    /**
     * 导出excel
     * @return
     */
    @RequestMapping("/excel/{page}")
    public String buyMedicine(HttpServletResponse response,
                              HttpServletRequest request,
                              @PathVariable String page) throws Exception{
        List<MedicineType> typeList = medicineTypeService.allList();
        List<User> userList = userService.allList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  ",  
                Locale.ENGLISH);
        if (page.equals("numList")) {
            try {
                List<Medicine> list = medicineService.allList();
                String[] titles = new String[]{"编号", "中文名称", "英文名称", "价格", "进价", "库存", "生产商", "药品类别"};
                List<Map<String, Object>> objList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> tempMap = new HashMap<>();
                    tempMap.put("编号", list.get(i).getId());
                    tempMap.put("中文名称", list.get(i).getcName());
                    tempMap.put("英文名称", list.get(i).geteName());
                    tempMap.put("价格", list.get(i).getPrice());
                    tempMap.put("进价", list.get(i).getOldPrice());
                    tempMap.put("库存", list.get(i).getNums());
                    tempMap.put("生产商", list.get(i).getManufacturer());               
                    for (int j=0; j<typeList.size(); j++) {
                        if (list.get(i).getTypeId() == typeList.get(j).getId()) {
                            tempMap.put("药品类别", typeList.get(j).getcTypeName());  
                            break;
                        }
                    }
                    objList.add(tempMap);
                }
                ExcelUtil.exportExcel(response, "库存信息表", titles, objList);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (page.equals("inOrderList")) {
            try {
                List<InOrder> list = inOrderService.allNlist();
                String[] titles = new String[]{"编号", "中文名称", "英文名称", "进价", "购买数量", "下单日期", "下单者"};
                List<Map<String, Object>> objList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> tempMap = new HashMap<>();
                    tempMap.put("编号", list.get(i).getId());
                    tempMap.put("中文名称", list.get(i).getcName());
                    tempMap.put("英文名称", list.get(i).geteName());
                    tempMap.put("价格", list.get(i).getPrice());
                    tempMap.put("购买数量", list.get(i).getNums()); 
                    tempMap.put("下单日期", sdf.format(list.get(i).getCreateDate()));
                    for (int j=0; j<userList.size(); j++) {
                        if (list.get(i).getUserId() == userList.get(j).getId()) {
                            tempMap.put("下单者", userList.get(j).getTrueName());
                            break;
                        }
                    }
                    objList.add(tempMap);
                }
                ExcelUtil.exportExcel(response, "订单信息表", titles, objList);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (page.equals("YInOrderList")) {
                try {
                    List<InOrder> list = inOrderService.allYlist();
                    String[] titles = new String[]{"编号", "中文名称", "英文名称", "进价", "购买数量", "下单日期", "下单者"};
                    List<Map<String, Object>> objList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        Map<String, Object> tempMap = new HashMap<>();
                        tempMap.put("编号", list.get(i).getId());
                        tempMap.put("中文名称", list.get(i).getcName());
                        tempMap.put("英文名称", list.get(i).geteName());
                        tempMap.put("进价", list.get(i).getPrice());
                        tempMap.put("购买数量", list.get(i).getNums());
                        tempMap.put("下单日期", sdf.format(list.get(i).getCreateDate()));
                        for (int j=0; j<userList.size(); j++) {
                            if (list.get(i).getUserId() == userList.get(j).getId()) {
                                tempMap.put("下单者", userList.get(j).getTrueName());
                                break;
                            }
                        }
                        objList.add(tempMap);
                    }
                    ExcelUtil.exportExcel(response, "进货记录表", titles, objList);
                } catch (Exception e) {
                    System.out.println(e);
                }
        } 
        
        else if (page.equals("buyMedicine")) {
            try {
                List<Medicine> list = medicineService.allList();
                String[] titles = new String[]{"编号", "中文名称", "英文名称", "价格", "库存", "生产日期", "质保期" ,"药品类别"};
                List<Map<String, Object>> objList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> tempMap = new HashMap<>();
                    tempMap.put("编号", list.get(i).getId());
                    tempMap.put("中文名称", list.get(i).getcName());
                    tempMap.put("英文名称", list.get(i).geteName());
                    tempMap.put("价格", list.get(i).getPrice());
                    tempMap.put("库存", list.get(i).getNums());
                    tempMap.put("生产日期", sdf.format(list.get(i).getProductDate()));
                    tempMap.put("质保期", list.get(i).getSafeDate());
                    for (int j=0; j<typeList.size(); j++) {
                        if (list.get(i).getTypeId() == typeList.get(j).getId()) {
                            tempMap.put("药品类别", typeList.get(j).getcTypeName());
                        }
                    }   
                    objList.add(tempMap);
                }
                ExcelUtil.exportExcel(response, "选购药品表", titles, objList);
            } catch (Exception e) {
                System.out.println(e);
            }
        } 
        return null;
    }
}

