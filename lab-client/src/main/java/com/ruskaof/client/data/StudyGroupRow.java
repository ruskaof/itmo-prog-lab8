package com.ruskaof.client.data;

import com.ruskaof.client.ClientApi;
import com.ruskaof.common.data.Coordinates;
import com.ruskaof.common.data.Country;
import com.ruskaof.common.data.FormOfEducation;
import com.ruskaof.common.data.Location;
import com.ruskaof.common.data.Person;
import com.ruskaof.common.data.Semester;
import com.ruskaof.common.data.StudyGroup;

import java.time.LocalDate;
import java.util.Objects;

public class StudyGroupRow {
    private static final long X_FIELD_LIMITATION = -896;
    private static final double Y_FIELD_LIMITATION = 135;
    private static final int STRING_LENGTH_LIMITATION = 100;
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
    private String color;


    //CHECKSTYLE:OFF
    public StudyGroupRow(int id, String name, long x, double y, LocalDate creationDate, Integer studentsCount, String formOfEducation, String semester, String adminName, int adminHeight, String adminNationality, float adminX, long adminY, String adminLocationName, String authorName, String color) {
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
        this.color = color;
    }
    //CHECKSTYLE:ON

    public String getColor() {
        return color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
        ClientApi.getInstance().update(this.mapToStudyGroup());
    }

    public void setX(long x) {
        if (x >= X_FIELD_LIMITATION) {
            this.x = x;
            ClientApi.getInstance().update(this.mapToStudyGroup());
        }
    }

    public void setY(double y) {
        if (y <= Y_FIELD_LIMITATION) {
            this.y = y;
            ClientApi.getInstance().update(this.mapToStudyGroup());
        }
    }


    public void setStudentsCount(Integer studentsCount) {
        this.studentsCount = studentsCount;

        ClientApi.getInstance().update(this.mapToStudyGroup());

    }

    public void setFormOfEducation(String formOfEducation) {
        this.formOfEducation = formOfEducation;
        ClientApi.getInstance().update(this.mapToStudyGroup());

    }

    public void setSemester(String semester) {
        if (semester.isEmpty()) {
            this.semester = null;
        } else {
            this.semester = semester;
        }
        ClientApi.getInstance().update(this.mapToStudyGroup());

    }

    public void setAdminName(String adminName) {
        if (adminName.length() <= STRING_LENGTH_LIMITATION) {
            this.adminName = adminName;
            ClientApi.getInstance().update(this.mapToStudyGroup());
        }

    }

    public void setAdminHeight(int adminHeight) {
        this.adminHeight = adminHeight;
        ClientApi.getInstance().update(this.mapToStudyGroup());

    }

    public void setAdminNationality(String adminNationality) {
        if (adminNationality.isEmpty()) {
            this.adminNationality = null;
        } else {
            this.adminNationality = adminNationality;
        }
        ClientApi.getInstance().update(this.mapToStudyGroup());

    }

    public void setAdminX(float adminX) {
        this.adminX = adminX;
        ClientApi.getInstance().update(this.mapToStudyGroup());

    }

    public void setAdminY(long adminY) {
        this.adminY = adminY;
        ClientApi.getInstance().update(this.mapToStudyGroup());

    }

    public void setAdminLocationName(String adminLocationName) {
        if (this.adminLocationName.length() <= STRING_LENGTH_LIMITATION) {
            this.adminLocationName = adminLocationName;
            ClientApi.getInstance().update(this.mapToStudyGroup());
        }
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
        ClientApi.getInstance().update(this.mapToStudyGroup());

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
                studyGroup.getAuthorName(),
                studyGroup.getColor()
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
                this.getAuthorName(),
                this.getColor());
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

    //CHECKSTYLE:OFF
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
    //CHECKSTYLE:ON

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
