package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TeacherLectureDAO extends DAOBase{
	Connection conn = null; 
	Statement stmt = null;	
	PreparedStatement pstmt = null;
	ResultSet rs = null; 
	
	TeacherLectureDTO member = new TeacherLectureDTO();
	TeacherDTO tea = null;
	DepartDTO depart = null;
	public ArrayList<TeacherLectureDTO> search(String text1) {
		String SQL = "select * from lecture left join teacher on lecture.teacher_id = teacher.id left join subject on lecture.subject_id = subject.id left join lectureday on lecture.id = lectureday.lecture_id left join depart on depart.id = teacher.depart_id left join room on lectureday.room_id = room.id left join building on room.building_id = building.id  WHERE teacher.id = 2 and DATE(lectureday.normdate)=DATE('"+text1+"') or DATE(lectureday.restdate)=DATE('"+text1+"')";
		ArrayList<TeacherLectureDTO> list = new ArrayList<TeacherLectureDTO>();
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				tea = new TeacherDTO();
				depart = new DepartDTO();
				TeacherLectureDTO teacher = new TeacherLectureDTO();
				teacher.setId(rs.getInt("lecture.id"));
				teacher.setSubject_name(rs.getString("subject.name"));
				teacher.setClassification(rs.getInt("lectureday.classification"));
				depart.setName(rs.getString("depart.name"));
				tea.setId(Integer.parseInt(rs.getString("teacher.id")));
				tea.setName(rs.getString("teacher.name"));
				tea.setDepart_id(depart);
				teacher.setTeacher_id(tea);
				teacher.set_class(rs.getString("lecture.class"));
				teacher.setNumber(rs.getInt("lecture.number"));
				teacher.setNormdate(rs.getString("lectureday.normdate"));
				teacher.setNormstart(rs.getInt("lectureday.normstart"));
				teacher.setNormhour(rs.getInt("lectureday.normhour"));
				teacher.setHo(rs.getString("room.ho"));
				teacher.setRoomName(rs.getString("room.name"));
				teacher.setBuildName(rs.getString("building.name"));
				teacher.setTh(rs.getInt("lectureday.th"));
				
				teacher.setNormstate(rs.getInt("lectureday.normstate"));
				teacher.setRestdate(rs.getString("lectureday.restdate"));
				teacher.setReststart(rs.getInt("lectureday.reststart"));
				teacher.setResthour(rs.getInt("lectureday.resthour"));
				list.add(teacher);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/*
	 * 메소드 이름 : search
	 * 기능 : lecture의 id로 SQL 값 얻어옴
	 * 
	 */
	public ArrayList<TeacherLectureDTO> search(int id) {
		String SQL = "select * from lecture left join teacher on lecture.teacher_id = teacher.id left join subject on lecture.subject_id = subject.id left join lectureday on lecture.id = lectureday.lecture_id left join depart on depart.id = teacher.depart_id left join room on lectureday.room_id = room.id left join building on room.building_id = building.id  WHERE teacher.id = 2";
		ArrayList<TeacherLectureDTO> list = new ArrayList<TeacherLectureDTO>();
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				tea = new TeacherDTO();
				depart = new DepartDTO();
				TeacherLectureDTO teacher = new TeacherLectureDTO();
				teacher.setId(rs.getInt("lecture.id"));
				teacher.setSubject_name(rs.getString("subject.name"));
				teacher.setClassification(rs.getInt("lectureday.classification"));
				depart.setName(rs.getString("depart.name"));
				tea.setId(Integer.parseInt(rs.getString("teacher.id")));
				tea.setName(rs.getString("teacher.name"));
				tea.setDepart_id(depart);
				teacher.setTeacher_id(tea);
				teacher.set_class(rs.getString("lecture.class"));
				teacher.setNumber(rs.getInt("lecture.number"));
				teacher.setNormstart(rs.getInt("lectureday.normstart"));
				teacher.setNormhour(rs.getInt("lectureday.normhour"));
				teacher.setHo(rs.getString("room.ho"));
				teacher.setRoomName(rs.getString("room.name"));
				teacher.setBuildName(rs.getString("building.name"));
				list.add(teacher);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public TeacherLectureDTO lectureInfo(String text1, String id) {

		String SQL = "select * from lecture left join teacher on lecture.teacher_id = teacher.id left join subject on lecture.subject_id = subject.id left join lectureday on lecture.id = lectureday.lecture_id left join depart on depart.id = teacher.depart_id left join room on lectureday.room_id = room.id left join building on room.building_id = building.id  WHERE teacher.id = 2 and DATE(lectureday.normdate)=DATE('"+text1+"') and lecture.id = '"+id+"';";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			TeacherLectureDTO teacher = new TeacherLectureDTO();
			while(rs.next()) {
				tea = new TeacherDTO();
				depart = new DepartDTO();
				teacher.setId(rs.getInt("lecture.id"));
				teacher.setSubject_name(rs.getString("subject.name"));
				depart.setName(rs.getString("depart.name"));
				tea.setId(Integer.parseInt(rs.getString("teacher.id")));
				tea.setName(rs.getString("teacher.name"));
				tea.setDepart_id(depart);
				teacher.setTeacher_id(tea);
				teacher.set_class(rs.getString("lecture.class"));
				teacher.setNumber(rs.getInt("lecture.number"));
				teacher.setNormstart(rs.getInt("lectureday.normstart"));
				teacher.setNormhour(rs.getInt("lectureday.normhour"));
				teacher.setHo(rs.getString("room.ho"));
				teacher.setRoomName(rs.getString("room.name"));
				teacher.setBuildName(rs.getString("building.name"));
			}
			return teacher;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<StudentLectureInfoDTO> stuList(String day, String lecture_id, int [] nStuCheck){
		String SQL = "SELECT * FROM lecture left join lectureday on lecture.id = lectureday.lecture_id left join mylecture on lecture.id = mylecture.lecture_id left join student on mylecture.student_id = student.id left join depart on student.depart_id = depart.id where lectureday.normdate = DATE('"+day+"') and lectureday.lecture_id = "+lecture_id+";";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			ArrayList<StudentLectureInfoDTO> stulist = new ArrayList<StudentLectureInfoDTO>();
			StudentLectureInfoDTO aStu = null;
			StudentDTO std = null;
			ArrayList<String> thList = null;
			
			rs.next();
			int th = rs.getInt("lectureday.th");
			int hour = rs.getInt("lectureday.normhour");
			
			do {
				thList = new ArrayList<String>();
				aStu = new StudentLectureInfoDTO();
				aStu.setDepart(rs.getString("depart.name"));
				for (int i = (th-1)*hour+1; i <= th * hour; i++) {	//th 주차
					thList.add(rs.getString("h"+i));
				}
				
				attendcheck(thList, nStuCheck);
				aStu.setH(thList);
				
				std = new StudentDTO();
				std.setId(rs.getInt("student.id"));
				std.setName(rs.getString("student.name"));
				std.setSchoolno(rs.getString("student.schoolno"));
				std.setState(rs.getString("student.state"));
				aStu.stu = std;
				
				stulist.add(aStu);
			}while(rs.next());
			return stulist;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void stuCheck(String day, String lecture_id, String rowno, String colno, String v){
		String SQL = "SELECT * FROM lecture left join lectureday on lecture.id = lectureday.lecture_id left join mylecture on lecture.id = mylecture.lecture_id left join student on mylecture.student_id = student.id left join depart on student.depart_id = depart.id where lectureday.normdate = DATE('"+day+"') and lectureday.lecture_id = "+lecture_id+" and student.id="+rowno+";";
		ArrayList<String> thList = null;
		
		try {
			thList = new ArrayList<String>();
			
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();

			//
			rs.next();
			int th = rs.getInt("lectureday.th");
			int hour = rs.getInt("lectureday.normhour");
			
			int iattend = 20;//rs.getInt("mylecture.iattend");
			int ixhour = 0;//rs.getInt("mylecture.ixhour");
			int ilate = 0;//rs.getInt("mylecture.ilate");
			//int check = rs.getInt(str);
			//if(check != Integer.parseInt(v));
			
			thList = new ArrayList<String>();
			for(int i = 0; i < hour * 15; i++) 
				thList.add(rs.getString("mylecture.h"+(i+1)));
				
			thList.set(((th - 1)*hour + Integer.parseInt(colno)-1), v);
			for(int i = 0; i < hour * 15; i++)
			{
				if(thList.get(i) == null);
				
				else if(thList.get(i).equals("2"))
					ixhour++;
				
				else if(thList.get(i).equals("1"))
					ilate++;
			}
			
			if(ixhour != 0 || ilate >= hour)
				iattend=weekhour1[hour][ixhour+(ilate/hour)];
				
			
			SQL = "UPDATE mylecture SET ilate='"+ilate+"',ixhour='"+ixhour+"',iattend='"+iattend+"', h"+((th - 1)*hour + Integer.parseInt(colno))+" = "+v+" WHERE mylecture.student_id = "+rowno+";";
			pstmt = conn.prepareStatement(SQL);
			int n = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void stuAllCheck(String day, String lecture_id){
		String SQL = "SELECT lectureday.th, lectureday.normhour FROM lecture left join lectureday on lecture.id = lectureday.lecture_id left join mylecture on lecture.id = mylecture.lecture_id left join student on mylecture.student_id = student.id left join depart on student.depart_id = depart.id where lectureday.normdate=DATE('"+day+"') and lectureday.lecture_id = '"+lecture_id+"';";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			rs.next();
			int th = rs.getInt("lectureday.th");
			int hour = rs.getInt("lectureday.normhour");
			
			ArrayList<StudentLectureInfoDTO> list = stuList(day, lecture_id, new int[3]);
			
			//ilate 지각 회수, ixhour 결석 시간
			
			String h ="";
			for (int j = ((th - 1)*hour + 1); j <= th * hour; j++)
				h += ("h"+Integer.toString(j))+"=0,";
			h = h.substring(0, h.length()-1);
			for(int i = 0; i < list.size(); i++) {
				SQL = "update mylecture set "+h+" where mylecture.student_id="+list.get(i).getStu().getId();
				pstmt = conn.prepareStatement(SQL);
				int n = pstmt.executeUpdate();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void attendcheck(ArrayList<String> thList, int [] nStuCheck) {
		int a,b,c;
		a=b=c=0;
		
		if(thList.contains("2"))
			a++;
		else if(thList.contains("1"))
			b++;
		else if(thList.contains("0"))
			c++;
		else if(thList.contains(null));
		
		nStuCheck[2] += a;
		nStuCheck[1] += b;
		nStuCheck[0] += c;
	}
}
/*
*/