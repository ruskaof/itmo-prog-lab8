package com.ruskaof.client.data;

import com.ruskaof.client.ClientApi;
import com.ruskaof.common.data.*;
import com.ruskaof.common.util.DataCantBeSentException;

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

    public void setId(int id) {
        System.out.println("fads");
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
        try {
            ClientApi.getInstance().update(this.mapToStudyGroup());
        } catch (DataCantBeSentException e) {
            e.printStackTrace();
        }
    }

    public void setX(long x) {
        this.x = x;
        try {
            ClientApi.getInstance().update(this.mapToStudyGroup());
        } catch (DataCantBeSentException e) {
            e.printStackTrace();
        }
    }

    public void setY(double y) {
        this.y = y;
        try {
            ClientApi.getInstance().update(this.mapToStudyGroup());
        } catch (DataCantBeSentException e) {
            e.printStackTrace();
        }
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        try {
            ClientApi.getInstance().update(this.mapToStudyGroup());
        } catch (DataCantBeSentException e) {
            e.printStackTrace();
        }
    }

    public void setStudentsCount(Integer studentsCount) {
        this.studentsCount = studentsCount;
        try {
            ClientApi.getInstance().update(this.mapToStudyGroup());
        } catch (DataCantBeSentException e) {
            e.printStackTrace();
        }
    }

    public void setFormOfEducation(String formOfEducation) {
        this.formOfEducation = formOfEducation;
        try {
            ClientApi.getInstance().update(this.mapToStudyGroup());
        } catch (DataCantBeSentException e) {
            e.printStackTrace();
        }
    }

    public void setSemester(String semester) {
        this.semester = semester;
        try {
            ClientApi.getInstance().update(this.mapToStudyGroup());
        } catch (DataCantBeSentException e) {
            e.printStackTrace();
        }
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
        try {
            ClientApi.getInstance().update(this.mapToStudyGroup());
        } catch (DataCantBeSentException e) {
            e.printStackTrace();
        }
    }

    public void setAdminHeight(int adminHeight) {
        this.adminHeight = adminHeight;
        try {
            ClientApi.getInstance().update(this.mapToStudyGroup());
        } catch (DataCantBeSentException e) {
            e.printStackTrace();
        }
    }

    public void setAdminNationality(String adminNationality) {
        this.adminNationality = adminNationality;
        try {
            ClientApi.getInstance().update(this.mapToStudyGroup());
        } catch (DataCantBeSentException e) {
            e.printStackTrace();
        }
    }

    public void setAdminX(float adminX) {
        this.adminX = adminX;
        try {
            ClientApi.getInstance().update(this.mapToStudyGroup());
        } catch (DataCantBeSentException e) {
            e.printStackTrace();
        }
    }

    public void setAdminY(long adminY) {
        this.adminY = adminY;
        try {
            ClientApi.getInstance().update(this.mapToStudyGroup());
        } catch (DataCantBeSentException e) {
            e.printStackTrace();
        }
    }

    public void setAdminLocationName(String adminLocationName) {
        this.adminLocationName = adminLocationName;
        try {
            ClientApi.getInstance().update(this.mapToStudyGroup());
        } catch (DataCantBeSentException e) {
            e.printStackTrace();
        }
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
        try {
            ClientApi.getInstance().update(this.mapToStudyGroup());
        } catch (DataCantBeSentException e) {
            e.printStackTrace();
        }
    }

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

    public StudyGroup mapToStudyGroup() {
        final StudyGroup studyGroup = new StudyGroup(
                this.getName(),
                new Coordinates(
                        this.getX(),
                        this.getY()
                ),
                this.getStudentsCount(),
                FormOfEducation.valueOf(this.getFormOfEducation()),
                this.getSemester() == null ? null : Semester.valueOf(this.getSemester()),
                new Person(
                        this.getName(),
                        this.getAdminHeight(),
                        this.getAdminNationality() == null ? null : Country.valueOf(this.getAdminNationality()),
                        new Location(
                                this.getAdminX(), this.getAdminY(), this.getAdminLocationName()
                        )
                ),
                this.getCreationDate(),
                this.getAuthorName()
        );
        studyGroup.setId(this.id);
        return studyGroup;
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
