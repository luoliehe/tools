package com.llh.utils.tree.ext;

import com.llh.utils.tree.TreeBuilder.AbsNode;

/**
 * 
 * Ztree 前端需要的数据格式
 * 
 * @author luolh
 * @param <E>
 */
public abstract class ZTreeNode<E> extends AbsNode<E> {

	private Integer id;
	private Integer pId;
	private String name;
	private String title;
	private String url;
	private boolean open;
	private boolean checked;
	private boolean chkDisabled;
	private String icon;
	private boolean iconClose;
	private boolean iconOpen;
	
	//TODO more diy field

	public ZTreeNode(E obj) {
		super(obj);
		this.id = getSelfId(obj);
		this.pId = getParentId(obj);
		this.name = getName(obj);
		this.title = getTitle(obj);
		this.url = getUrl(obj);
		this.open = isOpen(obj);
		this.checked = isChecked(obj);
		this.chkDisabled = isChkDisabled(obj);
		this.icon = getIcon(obj);
		this.iconClose = getIconClose(obj);
		this.iconOpen = getIconOpen(obj);
	}
	
	/**
	 * 记录 treeNode 节点的 展开/折叠 状态。
	 * 默认值：false
	 * @param obj
	 * @return
	 */
	public boolean isOpen(E obj) {
		return false;
	}
	
	/**
	 * 节点的 checkBox / radio 的 勾选状态
	 *  默认值：false
	 * @param obj
	 * @return
	 */
	public boolean isChecked(E obj) {
		return false;
	}
	
	/**
	 * 设置节点的 checkbox / radio 是否禁用 [setting.check.enable = true 时有效]
	 * 默认值：false
	 * @param obj
	 * @return
	 */
	public boolean isChkDisabled(E obj) {
		return false;
	}
	
	/**
	 * 节点自定义图标的 URL 路径。
	 * 父节点如果只设置 icon ，会导致展开、折叠时都使用同一个图标
	 * 图标图片的 url 可以是相对路径也可以是绝对路径
	 * 默认值：无
	 * @param obj
	 * @return
	 */
	public String getIcon(E obj) {
		return null;
	}

	/**
	 * 父节点自定义折叠时图标的 URL 路径。
	 * 默认值：无
	 * @param obj
	 * @return
	 */
	public boolean getIconClose(E obj) {
		return false;
	}

	/**
	 * 父节点自定义展开时图标的 URL 路径。
	 * 默认值：无
	 * @param obj
	 * @return
	 */
	public boolean getIconOpen(E obj) {
		return false;
	}
	
	/**
	 * 节点名称。
	 * 默认值：无
	 * @param obj
	 * @return 节点显示的名称字符串，标准 String 即可，所有特殊字符都会被自动转义
	 */
	public abstract String getName(E obj);
	
	/**
	 * zTree 节点数据保存节点提示信息的属性名称。 
	 * @param obj
	 * @return 默认值：""
	 */
	public String getTitle(E obj){
		return "";
	}
	
	/**
	 * zTree 节点数据保存节点链接的目标 URL 的属性名称。
	 * @param obj
	 * @return  默认值："url"
	 */
	public String getUrl(E obj){
		return "url";
	}

	public Integer getId() {
		return id;
	}

	public Integer getpId() {
		return pId;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public boolean isOpen() {
		return open;
	}

	public boolean isChecked() {
		return checked;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public String getIcon() {
		return icon;
	}

	public boolean isIconClose() {
		return iconClose;
	}

	public boolean isIconOpen() {
		return iconOpen;
	}
}
