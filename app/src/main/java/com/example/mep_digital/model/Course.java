package com.example.mep_digital.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Course implements Serializable {

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("students")
    @Expose
    private List<Student> students = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("grade")
    @Expose
    private Integer grade;
    @SerializedName("news")
    @Expose
    private List<News> news = null;
    @SerializedName("schedule")
    @Expose
    private List<Schedule> schedule = null;
    @SerializedName("assignments")
    @Expose
    private List<Assignment> assignments = null;
    @SerializedName("chat")
    @Expose
    private List<Chat> chat = null;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("teacher")
    @Expose
    private Teacher teacher;

    /**
     * No args constructor for use in serialization
     *
     */
    public Course() {
    }

    /**
     *
     * @param news
     * @param schedule
     * @param assignments
     * @param teacher
     * @param chat
     * @param v
     * @param grade
     * @param name
     * @param students
     * @param id
     * @param _id
     */
    public Course(String _id, List<Student> students, String id, String name, Integer grade, List<News> news, List<Schedule> schedule, List<Assignment> assignments, List<Chat> chat, Integer v, Teacher teacher) {
        super();
        this._id = _id;
        this.students = students;
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.news = news;
        this.schedule = schedule;
        this.assignments = assignments;
        this.chat = chat;
        this.v = v;
        this.teacher = teacher;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<Chat> getChat() {
        return chat;
    }

    public void setChat(List<Chat> chat) {
        this.chat = chat;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Course{" +
                ", id='" + id + '\'' +
                '}';
    }
}
