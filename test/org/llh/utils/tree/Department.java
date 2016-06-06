package org.llh.utils.tree;


/**
 * 部门实体
 * @author luolh
 */
public class Department {
		
		private Integer id;
		private Integer pid;
		private String name;
		
		public Department() {
			super();
		}
		public Department(Integer id, Integer pid, String name) {
			super();
			this.id = id;
			this.pid = pid;
			this.name = name;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getPid() {
			return pid;
		}
		public void setPid(Integer pid) {
			this.pid = pid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
