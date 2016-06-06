package org.llh.utils.lrc;

import java.util.HashMap;
import java.util.Map;

/**
 * 歌词实体类
 * @author victor.luo
 */
public class LrcEntity {
	
	//曲名
	private String title;
	
	//专辑名
	private String album;
	
	//艺人名
	private String artists;
	
	//即LRC歌词文件的制作者
	private String by;
	
	//补偿时值。500=0.5秒，正负值分别提前和延长相应的时间（其值多为500的倍数）
	private String offset;
	
	//内容
	private Map<Integer, String> contentMap;
	
	/**
	 * 曲名
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 曲名
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 专辑名
	 * @return
	 */
	public String getAlbum() {
		return album;
	}

	/**
	 * 专辑名
	 * @param album
	 */
	public void setAlbum(String album) {
		this.album = album;
	}

	/**
	 * 艺人名
	 * @return
	 */
	public String getArtists() {
		return artists;
	}

	/**
	 * 艺人名
	 * @param artists
	 */
	public void setArtists(String artists) {
		this.artists = artists;
	}
	
	/**
	 * 即LRC歌词文件的制作者
	 * @return
	 */
	public String getBy() {
		return by;
	}

	/**
	 * 即LRC歌词文件的制作者
	 * @param by
	 */
	public void setBy(String by) {
		this.by = by;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public Map<Integer, String> getContentMap() {
		return contentMap;
	}

	/**
	 * 设置某一秒的歌词
	 * @param time
	 * @param lrc
	 */
	public void putLrc(Integer time, String lrc){
		if(contentMap == null){
			synchronized (this) {
				if(contentMap == null){
					contentMap = new HashMap<>();
				}
			}
		}
		contentMap.put(time, lrc);
	}
	
	/**
	 * 获得某一秒的歌词
	 * @param time
	 * @return
	 */
	public String getLrc(Integer time){
		return contentMap.get(time);
	}

	@Override
	public String toString() {
		return "LrcEntity [title=" + title + ", album=" + album + ", artists=" + artists + ", by=" + by + ", offset="
				+ offset + ", contentMap=" + contentMap + "]";
	}
	
}
