/*
Student Name: Kai Yin Lau, Lisha Tan
Assignment # SOC Project
 Class: Fall 2013 Web App Development: CIS 4160 QMWA [2475] (Baruch )
Features Included: Define data and use by other pages

Advance Feature(if Any): 

References(if Any): 
Important: CODE COMMENTS

*/

package main.java.coreservlets;

public class DataBean {
	// Data set of Course Result
	private String course;
	private String code;
	private String section;
	private String dayAndTime;
	private String dates;
	private String bldgAndRm;
	private String instructor;
	private String seatsAvail;
	private String comments;
	
	// Data set of User's Input
	private String semesterInput;
	private String deptInput;
	private String discInput;
	private String diviInput;
	private String daysInput;
	private String timeInput;
	private String crsNumInput;
	private String instructorInput;
	
	// Data set for summer sections
	private String summer_sDate;
	private String summer_eDate;
	
	// Last Update Time
	private String lastUpdateTime;

	// Data set for details page
	private String details_semester;	
	private String details_couseTitle;	
	private String details_crsCode;
	private String details_crsSection;
	private String details_dept;
	private String details_division;	
	private String details_dates;
	private String details_seatsAvail;
	
	private String details_meetingDay_1;
	private String details_meetingDay_2;
	private String details_meetingDay_3;
	private String details_meetingDay_4;
	private String details_meetingDay_5;
	
	private String details_crsCreditHours;
	private String details_crsDescription;
	private String details_crsComments;
	private String details_preRequisite;
	
	
	public DataBean() {}
    
	// Last Update Time get/set method
    public String getLastUpdateTime() {
    	return lastUpdateTime;
    }
    public void setLastUpdateTime(String updateTime) {
    	this.lastUpdateTime = updateTime;
    }
    
    // Data set of User's Input
    public String getSemesterInput() {
    	return semesterInput;
    }
    public void setSemesterInput(String semesterInput) {
    	this.semesterInput = semesterInput;
    }
    
    public String getDeptInput() {
    	return deptInput;
    }
    public void setDeptInput(String deptInput) {
    	this.deptInput = deptInput;
    }
    
    public String getDiscInput() {
    	return discInput;
    }
    public void setDiscInput(String discInput) {
    	this.discInput = discInput;
    }
    
    public String getDiviInput() {
    	return diviInput;
    }
    public void setDiviInput(String diviInput) {
    	this.diviInput = diviInput;
    }
    
    public String getDaysInput() {
    	return daysInput;
    }
    public void setDaysInput(String daysInput) {
    	this.daysInput = daysInput;
    }
    
    public String getTimeInput() {
    	return timeInput;
    }
    public void setTimeInput(String timeInput) {
    	this.timeInput = timeInput;
    }
    
    public String getCrsNumInput() {
    	return crsNumInput;
    }
    public void setCrsNumInput(String crsNumInput) {
    	this.crsNumInput = crsNumInput;
    }
    
    public String getInstructorInput() {
    	return instructorInput;
    }
    public void setInstructorInput(String instructorInput) {
    	this.instructorInput = instructorInput;
    }
    
    // Data set of Course Result set/get methods
    public String getCourse() {
		return course;
	}    
    public void setCourse(String course) {
		this.course = course;
	} 
    
    public String getCode() {
		return code;
	}
    public void setCode(String code) {
		this.code = code;
	}    
    
    public String getSection() {
		return section;
	}
    public void setSection(String section) {
		this.section = section;
	}        
    
    public String getDayAndTime() {
		return dayAndTime;
	}
    public void setDayAndTime(String dayAndTime) {
		this.dayAndTime = dayAndTime;
	}    
    
    public String getDates() {
		return dates;
	}
    public void setDates(String dates) {
		this.dates = dates;
	}    
    
    public String getBldgAndRm() {
		return bldgAndRm;
	}
    public void setBldgAndRm(String bldgAndRm) {
		this.bldgAndRm = bldgAndRm;
	}
        
    public String getInstructor() {
		return instructor;
	}
    public void setInstructor(String instructor) {
  		this.instructor = instructor;
  	}
    
    public String getSeatsAvail() {
		return seatsAvail;
	}
    public void setSeatsAvail(String seatsAvail) {
  		this.seatsAvail = seatsAvail;
  	}
    
    public String getComments() {
		return comments;
	}
    public void setComments(String comments) {
		this.comments = comments;
	}
    
    // Data set of summer sections get/set methods    
	public String getSummer_sDate() {
		return summer_sDate;
	}

	public void setSummer_sDate(String summer_sDate) {
		this.summer_sDate = summer_sDate;
	}

	public String getSummer_eDate() {
		return summer_eDate;
	}

	public void setSummer_eDate(String summer_eDate) {
		this.summer_eDate = summer_eDate;
	}	
	
	// Data set for details page get/set methods
	public String getDetails_semester() {
		return details_semester;
	}

	public void setDetails_semester(String details_semester) {
		this.details_semester = details_semester;
	}

	public String getDetails_couseTitle() {
		return details_couseTitle;
	}

	public void setDetails_couseTitle(String details_couseTitle) {
		this.details_couseTitle = details_couseTitle;
	}

	public String getDetails_crsCode() {
		return details_crsCode;
	}

	public void setDetails_crsCode(String details_crsCode) {
		this.details_crsCode = details_crsCode;
	}

	public String getDetails_crsSection() {
		return details_crsSection;
	}

	public void setDetails_crsSection(String details_crsSection) {
		this.details_crsSection = details_crsSection;
	}

	public String getDetails_dept() {
		return details_dept;
	}

	public void setDetails_dept(String details_dept) {
		this.details_dept = details_dept;
	}

	public String getDetails_division() {
		return details_division;
	}

	public void setDetails_division(String details_division) {
		this.details_division = details_division;
	}

	public String getDetails_dates() {
		return details_dates;
	}

	public void setDetails_dates(String details_dates) {
		this.details_dates = details_dates;
	}

	public String getDetails_seatsAvail() {
		return details_seatsAvail;
	}

	public void setDetails_seatsAvail(String details_seatsAvail) {
		this.details_seatsAvail = details_seatsAvail;
	}

	public String getDetails_meetingDay_1() {
		return details_meetingDay_1;
	}

	public void setDetails_meetingDay_1(String details_meetingDay_1) {
		this.details_meetingDay_1 = details_meetingDay_1;
	}
	
	public String getDetails_meetingDay_2() {
		return details_meetingDay_2;
	}

	public void setDetails_meetingDay_2(String details_meetingDay_2) {
		this.details_meetingDay_2 = details_meetingDay_2;
	}

	public String getDetails_meetingDay_3() {
		return details_meetingDay_3;
	}

	public void setDetails_meetingDay_3(String details_meetingDay_3) {
		this.details_meetingDay_3 = details_meetingDay_3;
	}

	public String getDetails_meetingDay_4() {
		return details_meetingDay_4;
	}

	public void setDetails_meetingDay_4(String details_meetingDay_4) {
		this.details_meetingDay_4 = details_meetingDay_4;
	}

	public String getDetails_meetingDay_5() {
		return details_meetingDay_5;
	}

	public void setDetails_meetingDay_5(String details_meetingDay_5) {
		this.details_meetingDay_5 = details_meetingDay_5;
	}
	
	public String getDetails_crsCreditHours() {
		return details_crsCreditHours;
	}

	public void setDetails_crsCreditHours(String details_crsCreditHours) {
		this.details_crsCreditHours = details_crsCreditHours;
	}

	public String getDetails_crsDescription() {
		return details_crsDescription;
	}

	public void setDetails_crsDescription(String details_crsDescription) {
		this.details_crsDescription = details_crsDescription;
	}

	public String getDetails_crsComments() {
		return details_crsComments;
	}

	public void setDetails_crsComments(String details_crsComments) {
		this.details_crsComments = details_crsComments;
	}

	public String getDetails_preRequisite() {
		return details_preRequisite;
	}

	public void setDetails_preRequisite(String details_preRequisite) {
		this.details_preRequisite = details_preRequisite;
	}


}
