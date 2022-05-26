package com.ruskaof.client.data;

import com.ruskaof.common.data.StudyGroup;

import java.time.LocalDate;
import java.util.Objects;

public class StudyGroupRow {
    private int id;
    private String name;
    private long x;
    private double y;
    private LocalDate creationDate;
    private Integer studentsCount;
    private String formOfEducation;
    private String semester;
    private String adminName;
    private int adminHeight;
    private String adminNationality;
    private float adminX;
    private long adminY;
    private String adminLocationName;
    private String authorName;

    public StudyGroupRow(int id, String name, long x, double y, LocalDate creationDate, int studentsCount, String formOfEducation, String semester, String adminName, int adminHeight, String adminNationality, float adminX, long adminY, String adminLocationName, String authorName) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.formOfEducation = formOfEducation;
        this.semester = semester;
        this.adminName = adminName;
        this.adminHeight = adminHeight;
        this.adminNationality = adminNationality;
        this.adminX = adminX;
        this.adminY = adminY;
        this.adminLocationName = adminLocationName;
        this.authorName = authorName;
    }

    public static StudyGroupRow mapStudyGroupToRow(StudyGroup studyGroup) {
        return new StudyGroupRow(
                studyGroup.getId(),
                studyGroup.getName(),
                studyGroup.getCoordinates().getX(),
                studyGroup.getCoordinates().getY(),
                studyGroup.getCreationDate(),
                studyGroup.getStudentsCount(),
                studyGroup.getFormOfEducation().toString(),
                studyGroup.getSemesterEnum() == null ? null : studyGroup.getSemesterEnum().toString(),
                studyGroup.getGroupAdmin().getName(),
                studyGroup.getGroupAdmin().getHeight(),
                studyGroup.getGroupAdmin().getNationality() == null ? null : studyGroup.getGroupAdmin().getNationality().toString(),
                studyGroup.getGroupAdmin().getLocation().getX(),
                studyGroup.getGroupAdmin().getLocation().getY(),
                studyGroup.getGroupAdmin().getLocation().getName(),
                studyGroup.getAuthorName()
        );
    }

    @Override
    public String toString() {
        return "StudyGroupRow{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", x=" + x
                + ", y=" + y
                + ", creationDate=" + creationDate
                + ", studentsCount=" + studentsCount
                + ", formOfEducation='" + formOfEducation + '\''
                + ", semester='" + semester + '\''
                + ", adminName='" + adminName + '\''
                + ", adminHeight='" + adminHeight + '\''
                + ", adminNationality='" + adminNationality + '\''
                + ", adminX=" + adminX
                + ", adminY=" + adminY
                + ", adminLocationName='" + adminLocationName + '\''
                + ", authorName='" + authorName + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudyGroupRow that = (StudyGroupRow) o;
        return id == that.id && x == that.x && Double.compare(that.y, y) == 0 && studentsCount == that.studentsCount && Float.compare(that.adminX, adminX) == 0 && adminY == that.adminY && Objects.equals(name, that.name) && Objects.equals(creationDate, that.creationDate) && Objects.equals(formOfEducation, that.formOfEducation) && Objects.equals(semester, that.semester) && Objects.equals(adminName, that.adminName) && Objects.equals(adminHeight, that.adminHeight) && Objects.equals(adminNationality, that.adminNationality) && Objects.equals(adminLocationName, that.adminLocationName) && Objects.equals(authorName, that.authorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, x, y, creationDate, studentsCount, formOfEducation, semester, adminName, adminHeight, adminNationality, adminX, adminY, adminLocationName, authorName);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }

    public String getFormOfEducation() {
        return formOfEducation;
    }

    public String getSemester() {
        return semester;
    }

    public String getAdminName() {
        return adminName;
    }

    public int getAdminHeight() {
        return adminHeight;
    }

    public String getAdminNationality() {
        return adminNationality;
    }

    public float getAdminX() {
        return adminX;
    }

    public long getAdminY() {
        return adminY;
    }

    public String getAdminLocationName() {
        return adminLocationName;
    }

    public String getAuthorName() {
        return authorName;
    }
}
