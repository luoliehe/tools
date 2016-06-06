package org.llh.utils.tree;

import org.llh.utils.tree.TreeBuilder.AbsNode;

/**
 * warp really node object, this class need extend from {@link AbsNode}
 * 
 * @author luolh
 */
public class DepartmentNode extends AbsNode<Department> {

	// TODO more user define field	
	private String name;
	private Integer id;
	
	public DepartmentNode(Department t) {
		super(t);
		this.name = "NMME_"+t.getId();
		this.id = t.getId();
	}

	@Override
	public Integer getSelfId(Department obj) {
		return obj.getId();
	}

	@Override
	public Integer getParentId(Department obj) {
		return obj.getPid();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}
}