package ua.com.alevel.data.services;

import ua.com.alevel.data.exception.StudySessionDataLayerException;
import ua.com.alevel.data.model.dto.TeacherGroupRecord;
import ua.com.alevel.data.model.dto.TeacherRecord;
import ua.com.alevel.data.model.dto.save.TeacherRecordSave;

public interface TeacherService extends BaseService<TeacherRecord, TeacherRecordSave> {

    TeacherGroupRecord defineTheMostSuccessGroupByTeacherId(Long id) throws StudySessionDataLayerException;

}
