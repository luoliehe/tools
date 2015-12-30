package com.llh.utils.tree;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树结构数据创建器<br>
 * 调用方法 {@link #builder(List, Class)} 可以进行一步到位的进行树的生成<br> 
 * 包装节点需继承{@link AbsNode}
 * 
 * @param <E> 真正的节点对象
 * 
 * @author victor.luo
 */
final public class TreeBuilder<E>{
	
	private Map<Integer, AbsNode<E>> map;
	
	/***
	 * 包装数据成节点对象，并且构建树结构数据，返回根节点数组
	 * @param list 需要包装的原始数据数组
	 * @param c 包装成的节点对象 c 基础于{@link AbsNode}
	 * @return 根节点数组
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public List<AbsNode<E>> builder(List<E> list,
			Class<? extends AbsNode<E>> c) throws NoSuchMethodException,
			SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		List<AbsNode<E>> warpList = warp(list, c);
		
		List<AbsNode<E>> root = builder(warpList);

		return root;
	}
	
	/**
	 * 将数据结构组织成树
	 * 创建一个树对象，返回所有的 root 根列表
	 * @param list 返回构建好的所有的根节点列表
	 * @return
	 */
	public List<AbsNode<E>> builder(List<? extends AbsNode<E>> list){
		
		map = new HashMap<Integer, AbsNode<E>>();

		for (AbsNode<E> node : list) {
			map.put(node.getSelfId(), node);
		}
		 
		List<AbsNode<E>> roots = new ArrayList<AbsNode<E>>();
		
		//组织树结构关系
		for (Map.Entry<Integer, AbsNode<E>> entry : map.entrySet()) {
			
			AbsNode<E> node = entry.getValue();
			AbsNode<E> parent = map.get(node.getParentId());
			
			if (parent == null) {
				roots.add(node);
			} else {
				parent.addChildren(node);
			}
		}
		
		return roots;
	}
	
	/**
	 * 获得一个节点
	 * @param id
	 * @return
	 */
	public AbsNode<E> getNode(Integer id){
		return map == null ? null : map.get(id);
	}
	
	/**
	 * 将原始对象包装成节点对象,节点对象需要覆盖 {@link AbsNode} 的构造方法 
	 * 
	 * @param list 需要包装的原始数据
	 * @param c 包装成的目标对象, 目标类型的为 {@link AbsNode}的子类
	 * @return 返回包装成 warpClass 类型的数组
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static <E, N extends AbsNode<E>> List<N> warp(List<E> list,
			Class<? extends AbsNode<E>> c) throws NoSuchMethodException,
			SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		List<N> warpList = null;
		
		if (list != null) {
			
			warpList = new ArrayList<N>();

			for (E e : list) {
				
				Constructor<?> constructor = c.getConstructor(e.getClass());

				@SuppressWarnings("unchecked")
				N obj = (N) constructor.newInstance(e);
				
				warpList.add(obj);
			}	
		}
		
		return warpList;
	}
	
	/**
	 * 抽象节点对象，需要使用 {@link TreeBuilder#builder(List)}
	 * 需要继承此类进行包装真正的节点对象
	 * @author vector.luo
	 *
	 * @param <E> 节点中包装的 真正的用户节点对象
	 */
	public static abstract class AbsNode<E> {

		private E obj;
		private List<AbsNode<E>> childs;

		public AbsNode(E t) {
			this.obj = t;
		}
		
		/**
		 * 获得自身的ID
		 * @return
		 */
		public abstract Integer getSelfId();

		/**
		 * 获得父级的ID
		 * @return
		 */
		public abstract Integer getParentId();
		
		/**
		 * 获得原始对象
		 * @return
		 */
		public E getObj() {
			return obj;
		}
		
		public void addChildren(AbsNode<E> node) {
			if (childs == null) {
				childs = new ArrayList<AbsNode<E>>();
			}
			childs.add(node);
		}
		
		public List<AbsNode<E>> getChildren(){
			return childs;
		}
	}
}
