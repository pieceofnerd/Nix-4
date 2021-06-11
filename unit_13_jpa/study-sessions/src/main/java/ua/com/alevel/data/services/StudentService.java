package ua.com.alevel.data.services;

import ua.com.alevel.data.exception.StudySessionDataLayerException;

import ua.com.alevel.data.model.dto.StudentNextLessonRecord;
import ua.com.alevel.data.model.dto.StudentRecord;
import ua.com.alevel.data.model.dto.save.StudentRecordSave;

public interface StudentService extends BaseService<StudentRecord, StudentRecordSave> {

    StudentNextLessonRecord findNextLessonByStudentId(Long id) throws StudySessionDataLayerException;



}
