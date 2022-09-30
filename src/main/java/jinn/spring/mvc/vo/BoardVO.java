package jinn.spring.mvc.vo;

public class BoardVO {
	private String b_no;
	private String title;
	private String userid;
	private String regdate;
	private String views;
	private String contents;
	
	public BoardVO() {
	}

	public String getB_no() {
		return b_no;
	}

	public void setB_no(String b_no) {
		this.b_no = b_no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getViews() {
		return views;
	}

	public void setViews(String views) {
		this.views = views;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		String frm = "BoardVO [b_no=%s, title=%s, userid=%s, regdate=%s, views=%s, contents=%s";
		
		String result = String.format(frm, b_no, title, userid, regdate, views, contents);
		
		return result;
	}
	
}
