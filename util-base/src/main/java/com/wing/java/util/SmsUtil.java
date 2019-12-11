//package com.wing.java.util;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.ailk.smsmanage.model.PmSms;
//import com.ailk.smsmanage.model.PmSmsRecharge;
//import com.ailk.smsmanage.model.SmsTemplate;
//import com.ailk.smsmanage.service.IPmSmsSvc;
//
//@Component
//public class SmsUtil {
//	
//	private static SmsUtil smsUtil;
//	
//	@Autowired
//	private IPmSmsSvc pmSmsSvc;
//	
//	@PostConstruct  
//    public void init() {
//		smsUtil = this;
//		smsUtil.pmSmsSvc = this.pmSmsSvc;
//    }
//	
//	
//	
//	/**
//	 * 短信模板业务编码
//	 */
//	//系统模板
//	public static final String BUSCODE_REPAIR 			= "1000";			//报修分配后如果报修人没有安装业主APP则发送短信推荐下载APP
//	public static final String BUSCODE_REMINDER 		= "1001";			//短信催缴
//	public static final String BUSCODE_SMS_ALERT 		= "1002";			//短信余量提醒
//	public static final String BUSCODE_SMS_REPORT 		= "1003";			//短信报表
//	
//	//自定义模板
////	public static final String BUSCODE_1 				= "2000";			//自定义模板1
////	public static final String BUSCODE_2 				= "2001";			//自定义模板2
////	public static final String BUSCODE_3 				= "2002";			//自定义模板3
////	public static final String BUSCODE_4 				= "2003";			//自定义模板4
//	
//	
//	/**
//	 * 短信发送状态
//	 */
//	public static final String SMS_STATE_WAIT 		= "0";			//待发送
//	public static final String SMS_STATE_PROCESS 	= "1";			//发送中
//	public static final String SMS_STATE_SUCCESS 	= "2";			//发送成功
//	public static final String SMS_STATE_FAIL 		= "3";			//发送失败
//	public static final String SMS_STATE_CANCEL 	= "4";			//取消发送
//	
//	
//	
//	
//	
//	/**
//	 * 根据公司编码查询【自定义短信模板】列表
//	 * 优先级：本公司 > 总公司 > 系统默认
//	 */
//	public static List<SmsTemplate> getSmsTemplateList(String companyCode){
//		return smsUtil.pmSmsSvc.findSmsTemplateList(companyCode);
//	}
//	
//	/**
//	 * 根据业务编码查询短信模板
//	 * 优先级：本公司 > 总公司 > 系统默认
//	 */
//	public static SmsTemplate getSmsTemplate(String companyCode, String busCode){
//		return smsUtil.pmSmsSvc.findTemplateByBuscode(companyCode, busCode);
//	}
//	
//	/**
//	 * 根据模板，设置短信通用内容
//	 */
//	public static void setSms(PmSms sms, SmsTemplate template){
//		smsUtil.pmSmsSvc.setSms(sms, template);
//	}
//	
//	/**
//	 * 发送短信,【涉及到计算短信余量等操作】，【默认使用该方法】
//	 * 
//	 * 参数："application", "applicationCode", "busCode", "sender", "veceiver", 
//	 * 		"templateParam", "templateContent", "appId", "templateId", "scheduleTime"
//	 * 
//	 * 返回插入成功数量、插入失败数量、发送成功数量
//	 */
//	public static Map<String, Integer> sendSmsList(List<PmSms> smsList){
//		return smsUtil.pmSmsSvc.sendSmsList(smsList);
//	}
//	
//	/**
//	 * 仅仅插入短信表，不做其他操作
//	 * 如果使用该方法，请先确保在程序中有计算短信余量的代码，否则不会减少公司短信余量；
//	 * 
//	 * 参数："application", "applicationCode", "busCode", "sender", "veceiver", "state",
//	 * 		"templateParam", "templateContent", "appId", "templateId", "scheduleTime"
//	 * 
//	 * 返回插入成功数量、插入失败数量、发送成功数量
//	 */
//	public static Map<String, Integer> insertSmsList(List<PmSms> smsList){
//		return smsUtil.pmSmsSvc.insertSmsList(smsList);
//	}
//	
//	/**
//	 * 重发短信
//	 * 参数：id
//	 * 
//	 * 返回重发成功数量、失败数量
//	 */
//	public static Map<String, Integer> reSendSmsList(long[] ids){
//		Map<String, Integer> map = smsUtil.pmSmsSvc.reSendSmsList(ids);
//		return map;
//	}
//	
//	/**
//	 * 取消发送短信
//	 * 参数：id
//	 * 
//	 * 返回取消成功数量、失败数量
//	 */
//	public static Map<String, Integer> cancelSendSmsList(long[] ids){
//		Map<String, Integer> map = smsUtil.pmSmsSvc.cancelSendSmsList(ids);
//		return map;
//	}
//	
//	/**
//	 * 查询公司短信余量，返回单个字段
//	 */
//	public static int getSmsRechargeNum(String companyCode){
//		return smsUtil.pmSmsSvc.getSmsRechargeNum(companyCode);
//	}
//	
//	/**
//	 * 查询公司短信余量，返回多个字段
//	 */
//	public static Map getSmsRecharge(String companyCode){
//		return smsUtil.pmSmsSvc.getSmsRecharge(companyCode);
//	}
//	
//	/**
//	 * 设置公司短信余量
//	 * 有则修改、无则新增
//	 */
//	public static boolean setSmsRecharge(PmSmsRecharge recharge){
//		smsUtil.pmSmsSvc.setSmsRecharge(recharge);
//		return true;
//	}
//	
//	/**
//	 * 减少公司短信余量
//	 */
//	public static boolean minusSmsRecharge(String companyCode, int usedCount){
//		smsUtil.pmSmsSvc.minusSmsRecharge(companyCode, usedCount);
//		return true;
//	}
//	
//	/**
//	 * 增加公司短信余量
//	 */
//	public static boolean addSmsRecharge(String companyCode, int addCount){
//		smsUtil.pmSmsSvc.addSmsRecharge(companyCode, addCount);
//		return true;
//	}
//	
//	
//	
//	
//	
//}
