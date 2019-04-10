package cn.sucec.major_adjust_system.model;

/**
  *   记录了文件中的被列为预警等原因
 * @author WangChuo
 *
 */
public class Reason {
	
	private Integer id; //序号
	private String content; //内容
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "Reason [id=" + id + ", content=" + content + "]";
	}
	
	public Reason() {
		super();
	}
	
	public Reason(Integer id, String content) {
		super();
		this.id = id;
		this.content = content;
	}
	
}
