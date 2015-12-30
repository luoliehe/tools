package com.llh.utils.tree.test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.llh.utils.tree.TreeBuilder;
import com.llh.utils.tree.TreeBuilder.AbsNode;

/**
 * TreeBuilder test class
 * 
 * @author luolh
 *
 */
public class Test {
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Department d1 = new Department(1,null, "公司");
		Department d2 = new Department(2,1, "开发部");
		Department d3 = new Department(3,1, "运维部");
		Department d4 = new Department(4,2, "测试组");
		
		List<Department> ds = Arrays.asList(d1,d2,d3,d4);
		
		//The first method 
		List<AbsNode<Department>> rootNode1 = new TreeBuilder<Department>().warpAndBuilder(ds, DepartmentNode.class);
		
		
		//The second method 
		List<DepartmentNode> nodes = TreeBuilder.warp(ds, DepartmentNode.class);
		List<AbsNode<Department>> rootNode2 = new TreeBuilder<Department>().builder(nodes);
		
		
		System.out.println(new Gson().toJson(rootNode1));
		
		System.out.println(new Gson().toJson(rootNode2));
		 
	}
	
}
