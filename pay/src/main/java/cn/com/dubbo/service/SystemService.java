package cn.com.dubbo.service;

import com.jiuyao.ec.common.model.SysCode;


//import net.rubyeye.xmemcached.exception.MemcachedException;
//
//import com.founder.ec.base.service.BaseService;
//import com.founder.ec.common.model.AuthorityModule;
//import com.founder.ec.common.model.LoginDto;
//import com.founder.ec.common.model.SysCacheKey;
//import com.founder.ec.common.model.SysCodeType;
//import com.founder.ec.common.model.SysModule;
//import com.founder.ec.common.model.SysModuleButton;
//import com.founder.ec.common.model.SysRole;
//import com.founder.ec.common.model.SysRoleButtonRight;
//import com.founder.ec.common.model.SysRoleUser;
//import com.founder.ec.common.model.SysSso;
//import com.founder.ec.common.model.SysUser;
//import com.founder.ec.member.model.Member;
//import com.founder.ec.notice.model.Sms;
//import com.founder.ec.order.model.DeptGroup;
//import com.founder.ec.order.model.OrderWorkflow;

public interface SystemService extends BaseService{
//	public SysUser checkAuth(LoginDto dto);
//	public Map<String,Object> getCodeTypes(SysCodeType param, int pageNo, int pageSize);
//	public SysCodeType getCodeType(Integer id);
//	public Map<String,Object> getCodesByTypeNo(String codeTypeNo);
//	public List<SysCode> getCodeList(String codeTypeNo);
//	public void deleteCode(Integer id);
//	public void deleteCodeType(Integer id);
//	public void saveCode(SysCode code);
//	public void saveCode(List<SysCode> code,Integer editUserId);
	public SysCode getCode(SysCode code);
//	public void updateCodeType(SysCodeType codeType);
//	List<SysRole> findAllRoles(String systemNo);
//	Map<String,Object>findAllRoles(int pageNo,int pageSize,String systemNo);
//	public List<SysUser> findAllUsers(String systemNo);
//	Map<String,Object>findAllUsers(int pageNo,int pageSize,String systemNo);
//	void saveRoleUser(Integer roleId,Integer userId,List<SysRoleUser>rList);
//	List<SysRoleUser> findUsersByRole(Integer roleId);
//	public SysUser getUserByIdd(Integer id);
//	public HashMap<String,Object> getUsers(SysUser param, int pageNo, int pageSize);
//	public void updateUser(SysUser user);
//	public void saveUser(SysUser user);
//	public void doMultiLogicDelUser(SysUser user);
//	public HashMap<String,Object> getRoles(SysRole param, int pageNo, int pageSize);
//	public Integer getUserBySysNoAndName(SysUser user);
//	public SysUser getUserById(Integer id);
//	
//	public void updateRole(SysRole role);
//	public void updateRoleAuthority(SysRole role);
//	public void saveRole(SysRole role);
//	public void doMultiLogicDelRole(SysRole role);
//	public Integer getRoleBySysNoAndRoleNo(SysRole role);
//	
//	public List<SysModule> getModulesByParent(SysModule module);
//	
//	/**
//	 * 鏍规嵁sys_no , typeNo鑾峰彇浠ｇ爜
//	 * @param param
//	 * @return
//	 */
//	public List<SysCode> getCodeBySysNoAndTypeNo(HashMap<String,String> param);
//	
//	/**
//	 * 鏍规嵁澶氫釜typeNo鑾峰彇浠ｇ爜
//	 * @param param
//	 * @return
//	 */
//	public List<SysCode> getCodeBySysNoAndMultiTypeNo(HashMap<String,Object> param);
//	
//	/**
//	 * 鏍规嵁userId锛宻ysNo鏌ヨ鎺堟潈moduleId
//	 * @param param
//	 * @return
//	 */
//	public List<AuthorityModule> getAuthModuleIds(Map<String,Object> param);
//	
//	/**
//	 * 鏍规嵁涓�粍id锛岀埗id鏌ヨmodule
//	 * @param param
//	 * @return
//	 */
//	public List<SysModule> getByIdsAndParentId(Map<String, Object> param);
//	
//	
//	/**
//	 * 鏍规嵁缂栫爜绫诲瀷鍜岀紪鐮侊紝鏌ユ壘缂栫爜淇℃伅
//	 * @author chen_zhenyu
//	 * @param map
//	 * @return SysCode
//	 */
//	public SysCode getCodeByTypeNoAndCodeNo(HashMap<String, Object> map);
//
//	/**
//	 * 鏍规嵁id鑾峰彇role
//	 * @author wangbin.sz
//	 * @param id
//	 * @return
//	 */
//	public SysRole getRoleById(Integer id);
//	
//	/**@author wangbin.sz
//	 * 鏍规嵁CodeTypeNo鍜孋odeNo鏌ユ壘CodeId
//	 * @param code
//	 * @return
//	 */
//	public Integer getCodeIdByTypeNoAndCodeNo(SysCode code);
//	
	
//	/**
//	 * 鏍规嵁鏉′欢鑾峰彇浠ｇ爜琛ㄤ俊鎭�
//	 * @param code
//	 * @return
//	 */
//	public List<SysCode> getCodes(SysCode code);
	
//	/**
//	 * 鏍规嵁codeTypeNo,systemNo锛屼粠sys_code琛ㄤ腑鑾峰彇鍊硷紝鍦ㄤ笅鎷夋涓樉绀�
//	 * @author chen_zhenyu
//	 * @param codeTypeNo
//	 * @return
//	 */
//	public List<Object>  getComboxValue(String systemNo,String codeTypeNo);
//	
//	
//	public HashMap<String,Object> getRoleUsers(int pageNo,int pageSize,HashMap<String,Object> param);
//	/**
//	 * 璁板綍鐭俊
//	 * @param notice
//	 */
//	public void saveNotice(Sms notice);
//	
//	/**
//	 * 鏍规嵁鍦板潃淇℃伅鑾峰彇鐢ㄦ埛
//	 * @param param
//	 * @return
//	 */
//	public List<SysUser> getUsersByArea(SysUser user);
//	
//	/**
//	 * 鑾峰彇鎺堟潈鑿滃崟
//	 * @param param
//	 * @return
//	 */
//	public List<SysModule> getAuthModules(HashMap<String,Object> param);
//	public List<Object> getAuditorListForSelect(SysRole sysRole);
//	
//	/**
//	 * 璁板綍SysSso
//	 * @param sso
//	 */
//	public void saveSysSso(SysSso sso);
//	
//	/**
//	 * 鍗曠偣鐧诲綍楠岃瘉
//	 * @param sso
//	 * @return
//	 */
//	public SysUser doValidateSso(SysSso sso);
//	
//	/**
//	 * 鏍规嵁鏉′欢鏌ユ壘module
//	 * @author chen_zhenyu
//	 * @param sysModule
//	 * @return
//	 */
//	public SysModule findModuleByCondition(SysModule sysModule);
//	
//	/**
//	 * 鎸夋潯浠惰幏鍙杕odule鍒楄〃
//	 * @param sysModule
//	 * @return
//	 */
//	public List<SysModule> getModuleList(SysModule sysModule);
//
//	/**
//	 * 鏌ヨCacheKey
//	 * @param param
//	 * @param pageNo
//	 * @param pageSize
//	 * @return
//	 */
//	public HashMap<String,Object> getCacheKeys(SysCacheKey param, int pageNo, int pageSize);
//	
//	/**
//	 * 鍒犻櫎CacheKey
//	 * @param request
//	 */
//	public String deleteCachKeys(HttpServletRequest request);
//	
//	/**
//	 * 鑾峰彇甯︽湁鎸夐挳鏉冮檺淇℃伅鐨勮鑹�
//	 * @param systemNo
//	 * @return
//	 */
//	public List<SysRole> findButtonRightRoles(String systemNo);
//	
//	/**
//	 * 鑾峰彇鏉冮檺鎸夐挳
//	 * @param systemNo
//	 * @return
//	 */
//	public List<SysModuleButton> findModuleButtons(String systemNo);
//	
//	/**
//	 * 鏇存柊鎸夐挳鏉冮檺
//	 * @param buttonRightList
//	 */
//	public void updateRoleButtonRight(SysRoleButtonRight right,List<SysRoleButtonRight> buttonRightList);
//	
//	/**
//	 * 妫�煡鐢ㄦ埛鐨勬寜閽潈闄�
//	 * @param buttonNo
//	 * @param userId
//	 * @return
//	 */
//	public boolean checkUserButtonRight(String buttonNo,Integer userId);
//	
//	/**
//	 * 妫�煡IP鍦板潃鏄惁鍚堟硶
//	 * @param type
//	 * @param ip
//	 * @return
//	 */
//	public boolean checkIpAddress(Integer type,String ip);
//	
//	/**
//	 * 鏍规嵁鐢ㄦ埛id鏌ヨ姝ょ敤鎴风殑瑙掕壊闆�
//	 * @param type
//	 * @param ip
//	 * @return
//	 */
//	public List<HashMap> getSysRoleByUserId(SysRoleUser sysUser);
//	
//	
//	/**
//	 * 闆嗗悎绯荤粺鍗曠偣鐧诲綍
//	 * @param dto
//	 * @return
//	 */
//	public SysUser checkAuthMySso(LoginDto dto);
//	
//	/**
//	 * 闆嗗悎绯荤粺鐢ㄦ埛鐧诲綍楠岃瘉
//	 * @param dto
//	 * @return
//	 */
//	public SysUser checkAuthTwo(LoginDto dto);
//	
//	public Integer getUserIdByUserName(SysUser user);
//	
//	/**
//	 * 灏唌emberId瀛樺叆memcache
//	 * @param sessionKey
//	 * @param userId
//	 * @return
//	 * @throws TimeoutException
//	 * @throws InterruptedException
//	 * @throws MemcachedException
//	 */
//	public Long sessionKey(String sessionKey,Long userId);
//	/**
//	 * 灏唌emcache鍐呯殑 sessionKey  鍒犻櫎
//	 * @param sessionKey 
//	 * @param userId
//	 * @return
//	 * @throws TimeoutException
//	 * @throws InterruptedException
//	 * @throws MemcachedException
//	 */
//	public Long removeSessionKey(String sessionKey,Long userId);
//	/**
//	 * 鏍规嵁memberid鏌ヨ鐢ㄦ埛淇℃伅
//	 * @param memberId
//	 * @return
//	 */
//	public Member getMemberById(Integer memberId);
//	/**
//	 * @Description: 鑾峰彇鎵�湁鐨勭敤鎴烽泦鍚�
//	 * @param  @return
//	 * @return List<SysUser>    杩斿洖绫诲瀷 
//	 * @throws 
//	 * @author lk
//	 * @version 2014-8-21 涓嬪崍2:29:48
//	 */
//	public List<SysUser> getUserList();
//
//	/**
//	 * @Description: 娣诲姞缂栫爜绫诲瀷
//	 * @param codeType
//	 * @author yangyue
//	 * @version 2014-9-2810:22:00
//	 */
//	public void saveCodeType(SysCodeType codeType);
//
//	/**
//	 * 楠岃瘉姝ょ敤鎴锋槸鍚︽湁鏌ョ湅瀹㈡埛鐢佃瘽鍙风爜鍜屾敹璐у湴鍧�殑鏉冮檺
//	 * @return
//	 */
//	public Integer checkName(Integer userId);
//	
//	/**
//	 * 鏍规嵁绫诲瀷缂栫爜鏌ヨ鍏宠仈浠ｇ爜鏁�
//	 * @param codeTypeNo
//	 * @return
//	 */
//	public Integer getCodeCounts(Integer codeTypeId);
//	
//	public List<SysRoleUser> getSysRoleUserByUserId(SysRoleUser sysRoleUser);
//	
//	/**
//	 * 璐︽埛杩囨湡锛屽瘑鐮佷慨鏀�
//	 */
//	public void syncSysUserValidateTime();
//	
//	/**
//	 * 渚濇嵁鐢ㄦ埛涓枃鍚嶅拰鎷奸煶浠ュ強瑙掕壊缂栧彿妫�煡鐢ㄦ埛
//	 * @param orderWorkflow
//	 * @return
//	 */
//	public List<SysUser> getUserByNameOrRealNameAndRoleNo(SysUser sysUser);
//	/**
//	 * 查询已删除的cas用户数据
//	 * @return List<SysUser>
//	 */
//	public List<SysUser> getDeletedCASUsers();
//	/**
//	 * 同步fecerp的用户数据到fec
//	 */
//	public void syncSysUser();
//	
//	public List<SysUser> getCASUserList(SysUser sysUser) ;
//
//	/**
//	 * 返回用户数量
//	 * @param sysUser
//	 * @return
//	 */
//	public Long getSysUserId(SysUser sysUser);
//       /**
//     * @Description: 根据组长Id获取部门
//     * @param @param Integer
//     * @param @return
//     * @return List<DeptGroup> 返回类型
//     * @throws
//     * @author luning
//     * @version 2015-9-22
//     */
//	public List<DeptGroup> getGrpListByLeaderId(Integer leaderId);
//    
//    /**
//     * @Description: 根据用户Id获取组别
//     * @param  Integer
//     * @return List<DeptGroup>    返回类型 
//     * @throws 
//     * @author luning
//     * @version 2015-9-22
//     */
//    public List<DeptGroup> getDeptGroupByUserId(Integer userId);
}
