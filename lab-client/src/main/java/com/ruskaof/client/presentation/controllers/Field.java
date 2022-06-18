package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.data.StudyGroupRow;

public enum Field {
    ID {
        @Override
        public boolean filter(double leftValue, double rightValue, StudyGroupRow studyGroupRow) {
            return studyGroupRow.getId() >= leftValue && studyGroupRow.getId() <= rightValue;
        }

        @Override
        public int compare(StudyGroupRow o1, StudyGroupRow o2) {
            return Integer.compare(o1.getId(), o2.getId());
        }
    },
    NAME {
        @Override
        public boolean filter(double leftValue, double rightValue, StudyGroupRow studyGroupRow) {
            System.out.println(studyGroupRow.getName().length());
            System.out.println(leftValue);
            System.out.println(rightValue);
            if (studyGroupRow.getName() == null) {
                return false;
            }
            return studyGroupRow.getName().length() >= leftValue && studyGroupRow.getName().length() <= rightValue;
        }
        @Override
        public int compare(StudyGroupRow o1, StudyGroupRow o2) {
            return o1.getName().compareTo(o2.getName());
        }
    },
    X {
        @Override
        public boolean filter(double leftValue, double rightValue, StudyGroupRow studyGroupRow) {
            return studyGroupRow.getX() >= leftValue && studyGroupRow.getX() <= rightValue;
        }

        @Override
        public int compare(StudyGroupRow o1, StudyGroupRow o2) {
            return Long.compare(o1.getX(), o2.getX());
        }
    },
    Y {
        @Override
        public boolean filter(double leftValue, double rightValue, StudyGroupRow studyGroupRow) {
            return studyGroupRow.getY() >= leftValue && studyGroupRow.getY() <= rightValue;
        }
        @Override
        public int compare(StudyGroupRow o1, StudyGroupRow o2) {
            return Double.compare(o1.getY(), o2.getY());
        }
    },
    CREATION_DATE {
        @Override
        public boolean filter(double leftValue, double rightValue, StudyGroupRow studyGroupRow) {
            if (studyGroupRow.getCreationDate() == null) {
                return false;
            }
            return studyGroupRow.getCreationDate().toString().length() >= leftValue && studyGroupRow.getCreationDate().toString().length() <= rightValue;
        }

        @Override
        public int compare(StudyGroupRow o1, StudyGroupRow o2) {
            return o1.getCreationDate().compareTo(o2.getCreationDate());
        }
    },
    STUDENTS_COUNT {
        @Override
        public boolean filter(double leftValue, double rightValue, StudyGroupRow studyGroupRow) {
            if (studyGroupRow.getStudentsCount() == null) {
                return false;
            }
            return studyGroupRow.getStudentsCount() >= leftValue && studyGroupRow.getStudentsCount() <= rightValue;
        }
        @Override
        public int compare(StudyGroupRow o1, StudyGroupRow o2) {
            return o1.getCreationDate().compareTo(o2.getCreationDate());
        }
    },
    FORM_OF_EDUCATION {
        @Override
        public boolean filter(double leftValue, double rightValue, StudyGroupRow studyGroupRow) {
            if (studyGroupRow.getFormOfEducation() == null) {
                return false;
            }
            return studyGroupRow.getFormOfEducation().toString().length() >= leftValue && studyGroupRow.getFormOfEducation().toString().length() <= rightValue;
        }
        @Override
        public int compare(StudyGroupRow o1, StudyGroupRow o2) {
            return o1.getFormOfEducation().compareTo(o2.getFormOfEducation());
        }
    },
    SEMESTER {
        @Override
        public boolean filter(double leftValue, double rightValue, StudyGroupRow studyGroupRow) {
            if (studyGroupRow.getSemester() == null) {
                return false;
            }
            return studyGroupRow.getSemester().toString().length() >= leftValue && studyGroupRow.getSemester().toString().length() <= rightValue;
        }
        @Override
        public int compare(StudyGroupRow o1, StudyGroupRow o2) {
            if (o1.getSemester() == null) {
                return -1;
            }
            if (o2.getSemester() == null) {
                return 1;
            }
            return o1.getSemester().compareTo(o2.getSemester());
        }
    },
    ADMIN_NAME {
        @Override
        public boolean filter(double leftValue, double rightValue, StudyGroupRow studyGroupRow) {
            if (studyGroupRow.getAdminName() == null) {
                return false;
            }
            return studyGroupRow.getAdminName().toString().length() >= leftValue && studyGroupRow.getAdminName().toString().length() <= rightValue;
        }
        @Override
        public int compare(StudyGroupRow o1, StudyGroupRow o2) {
            if (o1.getAdminName() == null) {
                return -1;
            }
            if (o2.getAdminName() == null) {
                return 1;
            }
            return o1.getAdminName().compareTo(o2.getAdminName());
        }
    },
    ADMIN_HEIGHT {
        public boolean filter(double leftValue, double rightValue, StudyGroupRow studyGroupRow) {
            return studyGroupRow.getAdminHeight() >= leftValue && studyGroupRow.getAdminHeight() <= rightValue;
        }

        @Override
        public int compare(StudyGroupRow o1, StudyGroupRow o2) {
            return Integer.compare(o1.getAdminHeight(), o2.getAdminHeight());
        }
    },
    ADMIN_NATIONALITY {
        @Override
        public boolean filter(double leftValue, double rightValue, StudyGroupRow studyGroupRow) {
            if (studyGroupRow.getAdminNationality() == null) {
                return false;
            }
            return studyGroupRow.getAdminNationality().toString().length() >= leftValue && studyGroupRow.getAdminNationality().toString().length() <= rightValue;
        }
        @Override
        public int compare(StudyGroupRow o1, StudyGroupRow o2) {
            if (o1.getAdminNationality() == null) {
                return -1;
            }
            if (o2.getAdminNationality() == null) {
                return 1;
            }
            return o1.getAdminNationality().compareTo(o2.getAdminNationality());
        }
    },
    ADMIN_X {
        public boolean filter(double leftValue, double rightValue, StudyGroupRow studyGroupRow) {
            return studyGroupRow.getAdminX() >= leftValue && studyGroupRow.getAdminX() <= rightValue;
        }
        @Override
        public int compare(StudyGroupRow o1, StudyGroupRow o2) {
            return Float.compare(o1.getAdminX(), o2.getAdminX());
        }
    },
    ADMIN_Y {
        public boolean filter(double leftValue, double rightValue, StudyGroupRow studyGroupRow) {
            return studyGroupRow.getAdminY() >= leftValue && studyGroupRow.getAdminY() <= rightValue;
        }
        @Override
        public int compare(StudyGroupRow o1, StudyGroupRow o2) {
            return Long.compare(o1.getAdminY(), o2.getAdminY());
        }
    },
    ADMIN_LOCATION_NAME {
        @Override
        public boolean filter(double leftValue, double rightValue, StudyGroupRow studyGroupRow) {
            if (studyGroupRow.getAdminLocationName() == null) {
                return false;
            }
            return studyGroupRow.getAdminLocationName().toString().length() >= leftValue && studyGroupRow.getAdminLocationName().toString().length() <= rightValue;
        }
        @Override
        public int compare(StudyGroupRow o1, StudyGroupRow o2) {
            if (o1.getAdminLocationName() == null) {
                return -1;
            }
            if (o2.getAdminLocationName() == null) {
                return 1;
            }
            return o1.getAdminLocationName().compareTo(o2.getAdminLocationName());
        }
    },
    AUTHOR_NAME{
        @Override
        public boolean filter(double leftValue, double rightValue, StudyGroupRow studyGroupRow) {
            if (studyGroupRow.getAuthorName() == null) {
                return false;
            }
            return studyGroupRow.getAuthorName().toString().length() >= leftValue && studyGroupRow.getAuthorName().toString().length() <= rightValue;
        }
        @Override
        public int compare(StudyGroupRow o1, StudyGroupRow o2) {
            return o1.getAuthorName().compareTo(o2.getAuthorName());
        }
    };

    public abstract boolean filter(double leftValue, double rightValue, StudyGroupRow studyGroupRow);
    public abstract int compare(StudyGroupRow o1, StudyGroupRow o2);
}
