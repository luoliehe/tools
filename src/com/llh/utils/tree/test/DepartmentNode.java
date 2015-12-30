package com.llh.utils.tree.test;

import com.llh.utils.tree.TreeBuilder.AbsNode;

/**
 * warp really node object, this class need extend from {@link AbsNode}
 * 
 * @author luolh
 */
public class DepartmentNode extends AbsNode<Department> {

	// TODO more user define field

	public DepartmentNode(Department t) {
		super(t);
	}

	@Override
	public Integer getSelfId() {
		return getObj().getId();
	}

	@Override
	public Integer getParentId() {
		return getObj().getPid();
	}

}